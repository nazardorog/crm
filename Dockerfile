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

# Команда за замовчуванням
CMD ["tail", "-f", "/dev/null"]