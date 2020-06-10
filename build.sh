mvn clean package -pl spi-service -am -DskipTests

# coordinator
mvn clean package -pl spi-service-impl -am -DskipTests

# webapp
mvn clean package -pl spi-biz -am -DskipTests