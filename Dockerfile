FROM openjdk:8-jdk

RUN apt-get update && \
    apt-get install apt-transport-https bc ca-certificates software-properties-common -y

RUN echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list && \
    apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823 && \
    apt-get update && \
    apt-get install sbt -y

WORKDIR /opt/product-service

COPY . .

#ENTRYPOINT sbt assembly && java -jar "target/scala-2.12/slick-db-list-assembly.jar"

#RUN sbt assembly
#
#ENTRYPOINT ["java"]
#
#CMD ["-jar", "target/scala-2.12/slick-db-list-assembly.jar"]

#EXPOSE 5005
#
ENTRYPOINT ["sbt"]

CMD ["-jvm-debug", "5005", "~run"]