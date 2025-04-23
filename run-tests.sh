#!/bin/bash

# Стопимо та запускаємо Allure, якщо не працює
echo "🟡 Перезапуск Allure Docker Service..."
docker-compose down
docker-compose up -d

# Чекаємо поки Allure запуститься
echo "⏳ Очікуємо запуск Allure (5 секунд)..."
sleep 5

# Запускаємо тести
echo "🧪 Запускаємо тести..."
mvn clean test

# Чекаємо, поки файли появляться
echo "📁 Чекаємо на появу результатів..."
sleep 2

# Генеруємо звіт через API
echo "🧰 Генеруємо звіт через API..."
curl -X POST "http://localhost:5050/generate-report"

# Чекаємо на генерацію
sleep 3

# Відкриваємо звіт в браузері
echo "🌐 Відкриваємо звіт..."
xdg-open "http://localhost:5050/allure-docker-service/projects/default/reports/latest/index.html" || start "" "http://localhost:5050/allure-docker-service/projects/default/reports/latest/index.html"