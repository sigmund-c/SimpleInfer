FROM adoptopenjdk/openjdk11

# INSTALL INTELLIJ IDEA
ARG IDEA_VERSION=2019.3
ARG IDEA_BUILD=2019.3.2

## install dependencies, add non-root user as "developer" to prevent unwanted changes
RUN  \
  apt-get update && apt-get install --no-install-recommends -y \
  gcc git openssh-client less \
  libxtst-dev libxext-dev libxrender-dev libfreetype6-dev \
  libfontconfig1 libgtk2.0-0 libxslt1.1 libxxf86vm1 \
  && rm -rf /var/lib/apt/lists/* \
  && useradd -ms /bin/bash developer

## download IntelliJ Idea
ARG idea_source=https://download.jetbrains.com/idea/ideaIC-${IDEA_BUILD}.tar.gz
ARG idea_local_dir=.IdeaIC${IDEA_VERSION}

WORKDIR /opt/idea

RUN curl -fsSL $idea_source -o /opt/idea/installer.tgz \
  && tar --strip-components=1 -xzf installer.tgz \
  && rm installer.tgz

USER developer
ENV HOME /home/developer

RUN mkdir /home/developer/.Idea \
  && ln -sf /home/developer/.Idea /home/developer/$idea_local_dir



# INSTALL ANDREA'S CUSTOM INFER
USER root
WORKDIR /usr/local

## install dependencies, then git clone
RUN apt-get update && apt-get install --no-install-recommends -y pkg-config autoconf automake unzip bubblewrap libgmp-dev libsqlite3-dev libmpfr-dev maven vim patch make && \
	curl -sL https://raw.githubusercontent.com/ocaml/opam/master/shell/install.sh -o install.sh && \
	chmod +x install.sh && \
	echo | ./install.sh && \
	git clone --single-branch --branch master https://github.com/andrecostea/infer.git && \
	DEBIAN_FRONTEND="noninteractive" apt-get -y install tzdata
ENV TZ=Asia/Singapore

## install infer
WORKDIR infer

RUN opam init --disable-sandboxing && \
	./build-infer.sh java && \
	make install



# DOWNLOAD & INSTALL INTELLIJ PLUGIN
WORKDIR /usr/local

RUN curl https://github.com/sigmund-c/SimpleInfer/releases/download/v0.4/SimpleInfer-0.4.zip -O -J -L && \
	unzip SimpleInfer-0.4.zip -d /opt/idea/plugins



# INSTALL ANTLR FOR JAVA
RUN git clone --single-branch --branch master https://github.com/andrecostea/antlr-java.git

WORKDIR antlr-java

RUN mvn clean compile assembly:single && \
	mvn install:install-file -Dfile="target/antlr-1-jar-with-dependencies.jar" -DgroupId=org.racerdfix.antlr -DartifactId=antlr -Dversion=1.0 -Dpackaging=jar



# INSTALL HIPPODROME
WORKDIR /usr/local

ADD "https://www.random.org/cgi-bin/randbyte?nbytes=10&format=h" skipcache
RUN git clone --single-branch --branch master https://github.com/andrecostea/hippodrome.git


WORKDIR /usr/local/hippodrome

RUN mvn install -DskipTests

## make "hippodrome" an executable, and accessible from anywhere through /usr/local/bin that is included in PATH
WORKDIR /usr/local/hippodrome/target

RUN echo $'#!/bin/sh\n\
	MYSELF=`which "$0" 2>/dev/null`\n\
	[ $? -gt 0 -a -f "$0" ] && MYSELF="./$0"\n\
	java=java\n\
	if test -n "$JAVA_HOME"; then\n\
		java="$JAVA_HOME/bin/java"\n\
	fi\n\
	java_args=-Xmx1g\n\
	exec "$java" $java_args -jar $MYSELF "$@"\n\
	exit 1'  > stub.sh

RUN cat stub.sh hippodrome-1.0-jar-with-dependencies.jar >> hippodrome && \
	chmod +x hippodrome && \
	cp hippodrome /usr/local/bin && \
	cp hippodrome-1.0-jar-with-dependencies.jar /


# GET BENCHMARK FILES FOR COMMAND LINE TESTING
WORKDIR /usr/local/

RUN curl -L -O https://github.com/sigmund-c/hippodromeDockerFiles/archive/main.zip && \
	unzip main.zip
	
## specify infer annotations as part of Java classpath
ENV CLASSPATH=/usr/local/hippodromeDockerFiles-main/infer-annotation-0.18.0.jar
	


# START INTELLIJ WHEN RUNNING DOCKER
ENTRYPOINT [ "/opt/idea/bin/idea.sh" ]