#!/bin/sh
### BEGIN INIT INFO
# Provides:          bluealsa
# Required-Start:
# Required-Stop:
# Default-Start:     5
# Default-Stop:
# Short-Description: Start services BT audio sink
### END INIT INFO

/usr/bin/bluealsa -p a2dp-sink &

