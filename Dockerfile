# Базовий образ Ubuntu 24.04.2 LTS
FROM ubuntu:24.04

# Встановлення змінних середовища
ENV DEBIAN_FRONTEND=noninteractive
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin
ENV DISPLAY=:99

# Оновлення системи та встановлення базових пакетів
RUN apt-get update && apt-get install -y \
    wget \
    curl \
    gnupg \
    software-properties-common \
    apt-transport-https \
    ca-certificates \
    unzip \
    xvfb \
    x11vnc \
    fluxbox \
    && rm -rf /var/lib/apt/lists/*

# Встановлення Java 11
RUN apt-get update && apt-get install -y \
    openjdk-11-jdk \
    && rm -rf /var/lib/apt/lists/*

# Встановлення Google Chrome
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

# Встановлення ChromeDriver
RUN CHROME_VERSION=$(google-chrome --version | cut -d " " -f3 | cut -d "." -f1) \
    && CHROMEDRIVER_VERSION=$(curl -s "https://chromedriver.storage.googleapis.com/LATEST_RELEASE_$CHROME_VERSION") \
    && wget -O /tmp/chromedriver.zip "https://chromedriver.storage.googleapis.com/$CHROMEDRIVER_VERSION/chromedriver_linux64.zip" \
    && unzip /tmp/chromedriver.zip -d /tmp/ \
    && mv /tmp/chromedriver /usr/local/bin/chromedriver \
    && chmod +x /usr/local/bin/chromedriver \
    && rm /tmp/chromedriver.zip

# Встановлення Gradle
RUN wget -O /tmp/gradle.zip https://services.gradle.org/distributions/gradle-8.5-bin.zip \
    && unzip /tmp/gradle.zip -d /opt/ \
    && mv /opt/gradle-8.5 /opt/gradle \
    && ln -s /opt/gradle/bin/gradle /usr/local/bin/gradle \
    && rm /tmp/gradle.zip

# Створення робочої директорії
WORKDIR /app

# Копіювання файлів проекту
COPY build.gradle .
COPY gradle/ gradle/
COPY gradlew .
COPY gradlew.bat .

# Надання прав на виконання gradlew
RUN chmod +x gradlew

# Завантаження залежностей Gradle
RUN ./gradlew build --no-daemon || true

# Створення директорій для результатів тестів та логів
RUN mkdir -p test-results logs

# Копіювання сирцевого коду (буде перезаписано volume в docker-compose)
COPY src/ src/

# Встановлення прав доступу
RUN useradd -m -s /bin/bash testuser \
    && chown -R testuser:testuser /app

USER testuser

# Команда за замовчуванням
CMD ["sh", "-c", "Xvfb :99 -screen 0 1920x1080x24 & sleep 2 && ./gradlew test --info"]