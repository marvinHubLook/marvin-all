FROM openjdk:8-jre
MAINTAINER marvin marvinhublook@gmail.com
ADD target/marvin-wx-${parent.version}.jar /marvin-wx.jar
ENTRYPOINT ["/usr/bin/java","-jar","/marvin-wx.jar"]