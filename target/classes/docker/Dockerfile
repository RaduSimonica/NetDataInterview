FROM netdata/jenkins:1.0.0
USER root
RUN apt-get update
RUN apt-get install -y wget
RUN wget --no-verbose -O /tmp/apache-maven-3.8.4-bin.tar.gz https://dlcdn.apache.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz
RUN tar xzf /tmp/apache-maven-3.8.4-bin.tar.gz -C /opt/
RUN ln -s /opt/apache-maven-3.8.4 /opt/maven
RUN ln -s /opt/maven/bin/mvn /usr/local/bin
RUN rm -f /tmp/apache-maven-3.8.4-bin.tar.gz
ENV MAVEN_HOME /opt/maven
RUN chown -R jenkins:jenkins /opt/maven

RUN apt-get update \
      && apt-get install -y sudo \
      && rm -rf /var/lib/apt/lists/*
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers

USER jenkins
# COPY plugins.txt /usr/share/jenkins/plugins.txt
# RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt
