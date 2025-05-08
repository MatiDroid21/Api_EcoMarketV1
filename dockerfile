FROM eclipse-temurin:23-jdk AS builder

WORKDIR /app

COPY . .

COPY ./wallet ./app/wallet

RUN chmod +x mvnw

RUN chmod -R 755 ./app/wallet

RUN ./mvnw dependency:go-offline

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:23-jre 

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar 
COPY --from=builder /app/wallet /app/wallet

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


