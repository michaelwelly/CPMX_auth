FROM maven:3.8.3-openjdk-17
# Устанавливаем рабочую директорию
WORKDIR /app
# Копируем файл jar в контейнер
COPY . /app/
# Открываем порт 8080
EXPOSE 7075
# Запускаем приложение
RUN mvn clean package
CMD ["mvn","spring-boot:run"]