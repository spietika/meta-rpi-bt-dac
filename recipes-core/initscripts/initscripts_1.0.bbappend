
do_install_append () {
	update-rc.d -f -r ${D} mountnfs.sh remove
}

