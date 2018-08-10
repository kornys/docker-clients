FROM enmasseproject/java-base:8-7

ARG version=latest
ENV VERSION ${version}
ADD target/docker-clients-${VERSION}.jar /docker-clients.jar
RUN mkdir /opt/java_clients
ADD build/java_clients/cli-activemq.jar /opt/java_clients
ADD build/java_clients/cli-artemis-jms.jar /opt/java_clients
ADD build/java_clients/cli-qpid-jms.jar /opt/java_clients
ENV JAVA_OPTS "-DLOG_LEVEL=info"

RUN curl --silent --location https://rpm.nodesource.com/setup_6.x | bash -
RUN yum install -y nodejs
RUN yum install -y python python-devel openssl openssl-devel
RUN curl "https://bootstrap.pypa.io/get-pip.py" -o "get-pip.py" && \
    python get-pip.py
RUN yum install -y gcc gcc-c++

RUN npm install cli-rhea -g
RUN pip install cli-proton-python

EXPOSE 4242/tcp

CMD ["/opt/run-java/launch_java.sh", "/docker-clients.jar"]