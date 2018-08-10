VERSION     ?= 1.0-SNAPSHOT
SKIP_TESTS  ?= true
DOCKER_ORG	?= docker.io/kornysd

ifeq ($(SKIP_TESTS),true)
MAVEN_ARGS="-DskipTests"
endif

all: clean_java package_java build_java_clients docker_build docker_tag docker_push

package_java:
	mvn package -DskipTests $(MAVEN_ARGS)

clean_java:
	mvn clean $(MAVEN_ARGS)
	rm -rf build target

clean: clean_java

docker_build: package_java
	if [ -f Dockerfile ]; then docker build --build-arg version=$(VERSION) -t docker-clients:latest . ; fi
	docker images | grep docker-clients

build_java_clients:
	./build_java_clients.sh

docker_push:
	docker push $(DOCKER_ORG)/docker-clients:latest

docker_tag:
	docker tag docker-clients:latest $(DOCKER_ORG)/docker-clients:latest

.PHONY: clean_java package_java build_java_clients docker_build docker_tag docker_push