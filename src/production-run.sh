#! /bin/bash

PID_FILE=pid.chat

if [ -r "${PID_FILE}" ]; then
	echo "Found pid file"
	pid=$(cat "${PID_FILE}")
	echo "Prepare killing process with pid=${pid}"
	if [ -e "/proc/${pid}" ] && [ -e "/proc/${pid}/exe" ]; then
		kill "${pid}"
		echo "Process killed"
	else
		echo "Skip killing process"
	fi
	rm "${PID_FILE}"
	echo "Removed pid file"
fi

echo "Start Application"
(
	current_app=$(find . -type f | grep -E "chat-.*\.jar" | sort -nr | head -n1)
	echo "Using ${current_app}"

	temp=${current_app##.*/}
	log_dir="logs/${temp%.*}"
	mkdir "$log_dir" -p
	echo "Writing to ${log_dir}"

	log_prefix=$(date +"%Y%m%d-%H%M%S")
	echo "Write to log files with prefix '$log_prefix'"

	java -jar "$current_app" >>"$log_dir/${log_prefix}_console.log" 2>>"$log_dir/${log_prefix}_error.log" &
	echo $! >$PID_FILE &
)
