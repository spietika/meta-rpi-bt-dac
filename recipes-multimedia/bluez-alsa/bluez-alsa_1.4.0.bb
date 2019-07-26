# bluez-alsa recipe based on the recipe in wiki: https://github.com/Arkq/bluez-alsa/wiki/Yocto-OE-recipe
#  - dropped libortp dependency
#  - updated to latest release tag
#  - no systemd dependency

SUMMARY = "Bluetooth Audio ALSA Backend"
HOMEPAGE = "https://github.com/Arkq/bluez-alsa"
SECTION = "devel"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3d7d6ac7e2dbd2505652dceb3acdf1fe"

DEPENDS = "alsa-lib bluez5 glib-2.0 sbc"

SRC_URI = "git://github.com/Arkq/bluez-alsa.git;branch=master;protocol=https;tag=v${PV} \
           file://bluealsa.sh"
           
S = "${WORKDIR}/git"

INITSCRIPT_PARAMS = "start 21 2 3 4 5 ."
INITSCRIPT_NAME = "bluealsa.sh"

inherit pkgconfig autotools update-rc.d

do_install () {
    autotools_do_install
    install -d ${D}${sysconfdir}/init.d
    install -m 0755    ${WORKDIR}/bluealsa.sh	${D}${sysconfdir}/init.d
}

FILES_${PN} += "${libdir}/alsa-lib/lib*.so ${datadir}/alsa"
FILES_${PN}-dev += "${libdir}/alsa-lib/*.la"
FILES_${PN}-staticdev += "${libdir}/alsa-lib/lib*.a"
FILES_${PN}-dbg += "${libdir}/alsa-lib/.debug/*.so"

