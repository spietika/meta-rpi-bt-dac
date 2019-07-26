SUMMARY = "A2DP agent"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS = "glib-2.0"

SRC_URI = "file://agent.c \
           file://a2dp-agent.sh"

S = "${WORKDIR}"

INITSCRIPT_PARAMS = "start 21 2 3 4 5 ."
INITSCRIPT_NAME = "a2dp-agent.sh"

inherit pkgconfig update-rc.d

do_compile() {
     ${CC} ${LDFLAGS} `pkg-config --cflags glib-2.0 gio-2.0` -Wall -Wextra -o a2dp-agent ./agent.c `pkg-config --libs glib-2.0 gio-2.0`
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 a2dp-agent ${D}${bindir}

    install -d ${D}${sysconfdir}/init.d
    install -m 0755    ${WORKDIR}/a2dp-agent.sh	${D}${sysconfdir}/init.d
}

