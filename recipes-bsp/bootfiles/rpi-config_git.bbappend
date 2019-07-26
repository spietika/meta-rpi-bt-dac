ENABLE_UART = "1"

do_deploy_append_raspberrypi0-wifi() {
    echo "# DAC device tree overlay" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
    echo "dtoverlay=hifiberry-dac" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
}
