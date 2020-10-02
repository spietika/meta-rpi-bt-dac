# bluez-alsa recipe based on the recipe in wiki: https://github.com/Arkq/bluez-alsa/wiki/Yocto-OE-recipe
#  - dropped libortp dependency
#  - updated to latest release tag
#  - no systemd dependency
#
# Original license for recipe:
# The MIT License
# 
# Copyright (c) 2016-2020 Arkadiusz Bokowy <arkadiusz.bokowy@gmail.com>
# 
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
# 
# The above copyright notice and this permission notice shall be included in
# all copies or substantial portions of the Software.
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
# THE SOFTWARE
#

SUMMARY = "Bluetooth Audio ALSA Backend"
HOMEPAGE = "https://github.com/Arkq/bluez-alsa"
SECTION = "devel"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=72d868d66bdd5bf51fe67734431de057"

DEPENDS = "alsa-lib bluez5 glib-2.0 sbc fdk-aac"

SRC_URI = "git://github.com/Arkq/bluez-alsa.git;branch=master;protocol=https;tag=v${PV} \
           file://bluealsa.sh"
           
S = "${WORKDIR}/git"

INITSCRIPT_PARAMS = "start 21 2 3 4 5 ."
INITSCRIPT_NAME = "bluealsa.sh"

EXTRA_OECONF +="--enable-aac"

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

