#!/bin/ksh
#ident "%W%"

PID=`ps -u xzy -f | grep xzy | grep Dapp.name=xzy.recipe.server | grep com.food.recipe.Application | grep -v grep | awk '{print$2}' `
if [ ! -z $PID]
then
	echo killing process id : $PID
	kill -9 $PID
	sleep 2
	echo killed done.
fi

echo "cleaning logs..."
LOG_PATH=/share2/share2/log/recipe
LOG_FILE=$LOG_PATH/recipe.log
if [ -f $LOG_FILE ]
then
	mv $LOG_FILE $LOG_PATH/old/recipe_`date + %Y-%m-%d.%H:%M`.log
fi
echo JAVA_OPTIONS
JAVA_OPTIONS=${JAVA_OPTIONS} "-Dapp.name=xzy.recipe.server"
JAVA_OPTIONS=${JAVA_OPTIONS} "-Xms128m -Xmx1024m -XX:HeapDumpOnOutOfMemoryError -XX:HeapDump=$LOG_PATH/"
JAVA_OPTIONS=${JAVA_OPTIONS} "-XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC -Xloggc:$LOG_PATH/recipe-gc.log"
${JAVA_HOME}/bin/java ${JAVA_OPTIONS} com.food.recipe.Application > recipe.log 2>&1 &
echo "starting server..."

sleep 10

PID=`ps -u xzy -f | grep xzy | grep Dapp.name=xzy.recipe.server | grep com.food.recipe.Application | grep -v grep | awk '{print$2}' `
if [ ! -z $PID]
then
	echo "Sucessful started xzy recipe."
else 
	echo "Failed to start xzy recipe."
fi
