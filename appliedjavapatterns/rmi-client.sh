#! /bin/bash
#
# Thomas Freese

BASEDIR=$(dirname $0)
cd $BASEDIR

java -cp /tmp/rmi-interface.jar:/tmp/rmi-client.jar \
rmi.MainClient

#-Djava.rmi.server.useCodebaseOnly=true \
#-Djava.rmi.server.codebase=file:///tmp/rmi-interface.jar/ \
