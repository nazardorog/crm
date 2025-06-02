# Базовий образ із Chrome і WebDriver
FROM selenium/standalone-chrome:latest

# Встановити як root для додавання пакетів
USER root

# Встановлення Java та Maven
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Встановити Google Chrome
RUN apt-get update && apt-get install -y \
    wget \
    curl \
    gnupg \
    unzip \
    && curl -fsSL https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /usr/share/keyrings/google-linux.gpg \
    && echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-linux.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update && apt-get install -y google-chrome-stable

# Встановити ChromeDriver
RUN CHROME_VERSION=$(google-chrome --version | grep -oP '\d+\.\d+\.\d+') \
    && CHROMEDRIVER_VERSION=$(curl -sS "https://googlechromelabs.github.io/chrome-for-testing/last-known-good-versions-with-downloads.json" \
        | grep -B10 "\"version\": \"$CHROME_VERSION\"" \
        | grep -oP '"chromedriver":\s*{\s*"linux64":\s*{\s*"url":\s*"\K[^"]+') \
    && wget -O chromedriver.zip $CHROMEDRIVER_VERSION \
    && unzip chromedriver.zip -d /usr/local/bin/ \
    && rm chromedriver.zip

# Встановлення змінних середовища
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin

# Робоча директорія
WORKDIR /app

# Створення користувача для тестів
RUN useradd -m -s /bin/bash testuser \
    && chown -R testuser:testuser /app

# Копіюємо pom.xml і завантажуємо залежності Maven
COPY --chown=testuser:testuser pom.xml .
RUN mvn dependency:go-offline

# Копіюємо весь проект
COPY --chown=testuser:testuser . .

# Переходимо на користувача
USER testuser

# Створюємо директорії для результатів (опціонально)
RUN mkdir -p test-results logs

# Команда за замовчуванням — запуск тестів
CMD ["mvn", "test"]
