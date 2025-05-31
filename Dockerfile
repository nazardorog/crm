# Альтернативний мінімальний Dockerfile
FROM selenium/standalone-chrome:latest

# Встановити як root для встановлення пакетів
USER root

# Встановлення Java та Gradle
RUN apt-get update && apt-get install -y \
    openjdk-11-jdk \
    wget \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# Встановлення Gradle
RUN wget -O /tmp/gradle.zip https://services.gradle.org/distributions/gradle-8.5-bin.zip \
    && unzip /tmp/gradle.zip -d /opt/ \
    && mv /opt/gradle-8.5 /opt/gradle \
    && ln -s /opt/gradle/bin/gradle /usr/local/bin/gradle \
    && rm /tmp/gradle.zip

# Встановлення змінних середовища
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin

# Створення робочої директорії
WORKDIR /app

# Створення користувача для тестів
RUN useradd -m -s /bin/bash testuser \
    && chown -R testuser:testuser /app

# Копіювання файлів проекту
COPY --chown=testuser:testuser build.gradle .
COPY --chown=testuser:testuser gradle/ gradle/
COPY --chown=testuser:testuser gradlew .
COPY --chown=testuser:testuser gradlew.bat .

# Надання прав на виконання gradlew
RUN chmod +x gradlew

# Перехід на користувача testuser
USER testuser

# Завантаження залежностей Gradle
RUN ./gradlew build --no-daemon || true

# Створення директорій для результатів тестів та логів
RUN mkdir -p test-results logs

# Копіювання сирцевого коду
COPY --chown=testuser:testuser src/ src/

# Команда за замовчуванням
CMD ["./gradlew", "test", "--info"]