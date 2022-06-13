!#/bin/bash

echo "Start Spring Boot Application!"
CURRENT_PID=$(ps -ef | grep java | grep server | awk '{print $2}')
echo "$CURRENT_PID"

 if [ -z $CURRENT_PID ]; then
echo ">현재 구동중인 어플리케이션이 없으므로 종료하지 않습니다."

else
echo "> kill -9 $CURRENT_PID"
sudo kill -9 $CURRENT_PID
sleep 10
fi
 echo ">어플리케이션 배포 진행!"
nohup java -jar /var/lib/jenkins/workspace/server/build/libs/server-0.0.1-SNAPSHOT.jar & >> /var/lib/jenkins/workspace/logs/server.log &
