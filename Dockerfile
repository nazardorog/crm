FROM maven:3.8-openjdk-17

WORKDIR /app

# Копіюємо файли pom.xml та src
WORKDIR /app
#COPY pom.xml .
#COPY src ./src

# Встановлюємо залежності
RUN mvn dependency:go-offline

# Команда за замовчуванням
CMD ["mvn", "test"]