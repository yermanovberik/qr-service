# Используем официальный образ OpenJDK 17
FROM eclipse-temurin:17-jdk

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файл pom.xml и скачиваем зависимости (кэшируем слои)
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Копируем исходный код приложения
COPY src ./src

# Собираем приложение
RUN ./mvnw clean package -DskipTests

# Разрешаем запуск jar файла
RUN chmod +x target/*.jar

# Открываем порт 8089
EXPOSE 8089

# Запускаем приложение
CMD ["java", "-jar", "target/*.jar"]
