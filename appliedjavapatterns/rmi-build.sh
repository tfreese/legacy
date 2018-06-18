#! /bin/bash
#
# Thomas Freese

BASEDIR=$(dirname $0)
cd $BASEDIR

mkdir -p target/rmi-test/interface;
mkdir -p target/rmi-test/server;
mkdir -p target/rmi-test/client;

javac -d target/rmi-test/interface src/rmi/IService.java;
javac -d target/rmi-test/server -cp target/rmi-test/interface src/rmi/Service.java src/rmi/MainServer.java;
javac -d target/rmi-test/client -cp target/rmi-test/interface src/rmi/MainClient.java;

jar -cvf /tmp/rmi-interface.jar -C target/rmi-test/interface rmi/IService.class;
jar -cvf /tmp/rmi-server.jar    -C target/rmi-test/server    rmi/;
jar -cvf /tmp/rmi-client.jar    -C target/rmi-test/client    rmi/MainClient.class;