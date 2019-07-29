#!/bin/sh
### BEGIN INIT INFO
# Provides:          bt-init
# Required-Start:
# Required-Stop:
# Default-Start:     5
# Default-Stop:
# Short-Description: Connect to Bluetooth module with UART and enable a2dp
### END INIT INFO

/usr/bin/hciattach /dev/serial1 bcm43xx 921600 noflow -

bluetoothctl power on

hciconfig hci0 name 'RPi-DAC'
bluetoothctl discoverable on

/usr/bin/bluealsa-aplay --profile-a2dp 00:00:00:00:00:00 &

