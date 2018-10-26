#!/usr/bin/env bash

function install_java_client(){
    rm -rf ./build
    rm -rf ./cli-java
    mkdir -p build/java_clients
    git clone https://github.com/rh-messaging/cli-java.git
	cd cli-java
	git checkout c33119eea3f0d4850eafc895ab249ed15abbd266
	mvn package -B -DskipTests=true
	cp ./cli-artemis-jms/target/cli-artemis-jms-*.jar ../build/java_clients/cli-artemis-jms.jar
	cp ./cli-qpid-jms/target/cli-qpid-jms-*.jar ../build/java_clients/cli-qpid-jms.jar
	cp ./cli-activemq/target/cli-activemq-*.jar ../build/java_clients/cli-activemq.jar
	cp ./cli-paho-java/target/cli-paho-java-*.jar ../build/java_clients/cli-paho-java.jar
}

install_java_client