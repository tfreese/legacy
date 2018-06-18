#! /bin/bash
#
# Thomas Freese

BASEDIR=$(dirname $0)
cd $BASEDIR

#rmiregistry 1099 &

java -cp /tmp/rmi-interface.jar:/tmp/rmi-server.jar \
rmi.MainServer

#-Djava.rmi.server.useCodebaseOnly=true \
#-Djava.rmi.server.codebase=file:///tmp/rmi-interface.jar/ \
#-Djava.rmi.server.hostname=localhost \
