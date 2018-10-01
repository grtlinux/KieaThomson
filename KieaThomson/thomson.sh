#!/bin/bash
# =========================================================
# program: thomson.sh
# author: Kiea Seok Kang
# company: TAIN, Inc.
# date: 2018.09.30
# shell: /bin/bash
#
# $ tr -d '\015' < inFile > outFile
#
# $ crontab -e
#    */10 * * * *    /home/kang/bin/thomson.sh restart
#
#    5   * * * *    /home/kang/bin/thomson.sh start
#    55 23 * * *    /home/kang/bin/thomson.sh stop
#
#
#


# =========================================================
# environment
#
JAVA_HOME=/hw01/fepe01/_KANG/jdk1.8.0_152
PATH=$JAVA_HOME/bin:$PATH

JOB_HOME=/home/kang/bin
JOB_PROG=kieaLoop.sh

#
# function: func_usage
#
func_usage()
{
	echo ""
	echo "  USAGE: $0 [start|stop|restart|status]"
	echo "    start|begin|run      : run program"
	echo "    stop|finish|fin|kill : kill program"
	echo "    restart|rerun        : kill & run program"
	echo "    status|stat          : status program"
	echo ""
}

#
# function: func_status
#
func_status()
{
	PID=`ps -ef | grep $JOB_PROG | grep -v grep | awk '{print $2}'`
	if [ "$PID" = "" ];
	then
		echo "  STATUS: be not running..."
	else
		echo "  STATUS: be running PID=$PID."
	fi
}

#
# function: func_start
#
func_start()
{
	PID=`ps -ef | grep $JOB_PROG | grep -v grep | awk '{print $2}'`
	if [ "$PID" = "" ];
	then
		echo "  START: be starting..."
		cd $JOB_HOME
		./kieaLoop.sh > ./out &
		#java -jar KieaThomson.jar > /dev/null &
		sleep 2
		PID=`ps -ef | grep $JOB_PROG | grep -v grep | awk '{print $2}'`
		echo "  START: be started. PID=$PID."
	else
		echo "  START: already running. PID=$PID."
	fi
}

#
# function: func_stop
#
func_stop()
{
	PID=`ps -ef | grep $JOB_PROG | grep -v grep | awk '{print $2}'`
	if [ "$PID" = "" ];
	then
		echo "  STOP: not running..."
	else
		kill -9 $PID
		echo "  STOP: be killed. PID=$PID."
	fi
}

#
# main entry point
#

if [ $# -gt 0 ];
then
	CMD=`echo $1 | tr 'A-Z' 'a-z'`
	echo "  * command ---> $CMD"
	case $CMD in
		"start" | "begin" | "run" )
			func_start
			;;
		"stop" | "finish" | "fin" | "kill" )
			func_stop
			;;
		"restart" | "rerun" )
			func_stop
			sleep 2
			func_start
			;;
		"status" | "stat" )
			func_status
			;;
		* )
			func_usage
			;;
	esac
else
	func_usage
fi



