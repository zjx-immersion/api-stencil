#!/bin/sh
PROFILE=${ACTIVE_PROFILE:=local}
#EUREKA_INSTANCE_HOSTNAME=$(curl http://rancher-metadata/latest/self/container/service_name)
echo $EUREKA_INSTANCE_HOSTNAME
#java -Xmx512m -Djava.security.egd=file:/dev/./urandom -jar app.jar --spring.profiles.active=$PROFILE --eureka.instance.hostname=$EUREKA_INSTANCE_HOSTNAME
java -Xmx512m -Djava.security.egd=file:/dev/./urandom -jar app.jar --spring.profiles.active=$PROFILE

