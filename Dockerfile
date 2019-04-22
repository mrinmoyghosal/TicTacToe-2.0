FROM maven:3.6-jdk-8

LABEL maintainer="techkiddy@gmail.com"

RUN mkdir /tictactoe

COPY src/ /tictactoe/src
COPY pom.xml /tictactoe/

WORKDIR /tictactoe/

# Run test and generate coverage report
RUN \
	mvn clean && \
	mvn jacoco:prepare-agent test jacoco:report && \
	mvn package

# run terminal
ENTRYPOINT ["/usr/bin/java", "-jar", "target/tictactoe2-0.0.1-SNAPSHOT.jar"]