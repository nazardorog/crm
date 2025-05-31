# Базовий образ — OpenJDK 17 + Maven
FROM maven:3.9-eclipse-temurin-17

# Оновлюємо та встановлюємо залежності, зокрема Chrome і ChromeDriver
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    xvfb \
    libgtk-3-0 \
    libxss1 \
    libasound2 \
    libnss3 \
    libx11-xcb1 \
    libxcomposite1 \
    libxcursor1 \
    libxdamage1 \
    libxi6 \
    libxtst6 \
    libappindicator3-1 \
    libatk-bridge2.0-0 \
    libatk1.0-0 \
    libcups2 \
    libdrm2 \
    libgbm1 \
    libpango-1.0-0 \
    libpangocairo-1.0-0 \
    libxrandr2 \
    fonts-liberation \
    && rm -rf /var/lib/apt/lists/*

# Встановлюємо Google Chrome Stable (підходить для роботи з ChromeDriver)
RUN wget -q -O /tmp/google-chrome.deb https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    apt-get update && \
    apt-get install -y /tmp/google-chrome.deb && \
    rm /tmp/google-chrome.deb

# Встановлюємо ChromeDriver (версію потрібно узгодити з версією Chrome)
ARG CHROMEDRIVER_VERSION=137.0.7151.55
RUN wget -q -O /tmp/chromedriver.zip https://chromedriver.storage.googleapis.com/${CHROMEDRIVER_VERSION}/chromedriver_linux64.zip && \
    unzip /tmp/chromedriver.zip -d /usr/local/bin/ && \
    rm /tmp/chromedriver.zip && \
    chmod +x /usr/local/bin/chromedriver

# Встановлюємо робочу директорію
WORKDIR /usr/src/app

# Копіюємо всі файли проекту в контейнер
COPY . .

# Вказуємо змінні середовища, щоб запускати Chrome в headless режимі без GUI
ENV DISPLAY=:99
ENV JAVA_OPTS="-Djava.awt.headless=true"

# Запуск тестів
CMD ["mvn", "clean", "test"]