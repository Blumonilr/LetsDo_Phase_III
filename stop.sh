echo "stop the service"

pid=$(ps -ef | grep "LetsDo_Phase_III.jar" |grep -v grep | awk '{print $2}' )

echo ${pid}

kill -9 $pid
