
IMAGE_INSTALL += " \
	alsa-utils-aplay \
        bluez-alsa \
	dac-config \
        a2dp-agent \
        python \
        rpi-gpio \
        bluez5-noinst-tools \
	"

IMAGE_CMD_rpi-sdimg_append() {
    dd if=/dev/zero bs=1M count=10 > ${WORKDIR}/ext4.img
    mkfs.ext4 ${WORKDIR}/ext4.img
    cat ${WORKDIR}/ext4.img >> ${SDIMG}

    parted -s ${SDIMG} -- unit KiB mkpart primary ext2 ${SDIMG_SIZE} -1s
}

