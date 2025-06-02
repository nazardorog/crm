# Базовий образ із Chrome і WebDriver
FROM selenium/standalone-chrome:latest

# Встановити як root для додавання пакетів
USER root

# Встановлення Java та Maven
RUN apt-get update && apt-get install -y \
    openjdk-11-jdk \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Встановлення змінних середовища
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
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
