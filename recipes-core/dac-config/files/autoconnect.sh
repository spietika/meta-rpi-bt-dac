#!/bin/sh

ADDR=$(bluetoothctl paired-devices | tail -n1 | cut -f2 -d' ')

if [ -z "$ADDR" ]
then
    exit 1
fi

expect <<EOF &
spawn "bluetoothctl"
expect "\[bluetooth\]"
send "scan on\n"

expect "$ADDR"
send "connect $ADDR\n"

expect "Connection successful"
send "quit\n"
EOF

