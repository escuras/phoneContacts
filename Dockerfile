FROM java:8
COPY target/phone-1.0-SNAPSHOT.jar /phonecontacts.jar 
EXPOSE 8888
CMD java -jar phonecontacts.jar

