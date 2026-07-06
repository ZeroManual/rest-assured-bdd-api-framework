FROM maven:3.9.9-eclipse-temurin-17
WORKDIR /workspace
COPY . .
CMD ["mvn", "clean", "test", "-Psmoke-suite", "-Denv=qa"]
