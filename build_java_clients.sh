#!/usr/bin/env bash

function install_java_client(){
    rm -rf ./build
    mkdir -p build/java_clients
    git clone https://github.com/rh-messaging/cli-java.git
	cd cli-java
	mvn package -B -DskipTests=true
	cp ./cli-artemis-jms/target/cli-artemis-jms-*.jar ../build/java_clients/cli-artemis-jms.jar
	cp ./cli-qpid-jms/target/cli-qpid-jms-*.jar ../build/java_clients/cli-qpid-jms.jar
	cp ./cli-activemq/target/cli-activemq-*.jar ../build/java_clients/cli-activemq.jar
}

install_java_client