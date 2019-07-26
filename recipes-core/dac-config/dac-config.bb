SUMMARY = "Configurations for Bluetooth DAC"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://bt-init.sh"

S = "${WORKDIR}"

INITSCRIPT_PARAMS = "start 21 2 3 4 5 ."
INITSCRIPT_NAME = "bt-init.sh"

inherit update-rc.d

do_install() {
    # hciattach looks for firmware in /etc/firmware
    install -d ${D}/etc
    ln -s -r ${D}/lib/firmware ${D}/etc/firmware

    install -d ${D}${sysconfdir}/init.d
    install -m 0755    ${WORKDIR}/bt-init.sh	${D}${sysconfdir}/init.d
}

