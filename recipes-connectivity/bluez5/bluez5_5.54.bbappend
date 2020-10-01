FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://main.conf"

PACKAGECONFIG ??= "obex-profiles \
    readline \
    a2dp-profiles \
    avrcp-profiles \
    tools \
    deprecated \
"

do_install_append() {
	install -m 0644 ${WORKDIR}/main.conf ${D}/${sysconfdir}/bluetooth/
}
