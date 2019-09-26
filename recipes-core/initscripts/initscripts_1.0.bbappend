
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

do_install_append () {
	update-rc.d -f -r ${D} mountnfs.sh remove
	update-rc.d -f -r ${D} read-only-rootfs-hook.sh remove
}

