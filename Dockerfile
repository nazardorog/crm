FROM eclipse-temurin:17-jdk

# Встановити Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Робоча директорія
WORKDIR /app

# Копіюємо pom.xml і завантажуємо залежності
COPY pom.xml .
RUN mvn dependency:go-offline

# Копіюємо весь проєкт
COPY . .

## Створення скрипта для запуску тестів та генерації звіту
#RUN echo '#!/bin/bash\n\
#echo "🚀 Запуск тестів..."\n\
#\n\
## Запуск тестів\n\
#mvn clean test\n\
#\n\
## Зберігаємо код виходу тестів\n\
#TEST_EXIT_CODE=$?\n\
#\n\
#echo "📊 Генерація Allure звіту..."\n\
#\n\
## Перевіряємо чи є результати тестів\n\
#if [ -d "/app/target/allure-results" ] && [ "$(ls -A /app/target/allure-results)" ]; then\n\
#    # Генеруємо звіт\n\
#    allure generate /app/target/allure-results --clean -o /app/target/allure-report\n\
#    echo "✅ Звіт згенеровано в /app/target/allure-report"\n\
#    echo "🌐 Звіт буде доступний через allure-service на http://localhost:5050"\n\
#else\n\
#    echo "⚠️  Результати тестів не знайдено в /app/target/allure-results"\n\
#fi\n\
#\n\
#if [ $TEST_EXIT_CODE -eq 0 ]; then\n\
#    echo "✅ Тести пройшли успішно!"\n\
#else\n\
#    echo "❌ Деякі тести провалилися (код виходу: $TEST_EXIT_CODE)"\n\
#fi\n\
#\n\
#echo "⏳ Утримуємо контейнер активним для перегляду результатів..."\n\
## Утримуємо контейнер активним\n\
#tail -f /dev/null' > /app/run-tests.sh

## Надаємо права на виконання скрипта
#RUN chmod +x /app/run-tests.sh

# Команда за замовчуванням
CMD ["mvn", "test"]