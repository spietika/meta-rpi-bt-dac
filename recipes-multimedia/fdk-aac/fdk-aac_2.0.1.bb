# This recipe is ported from http://cgit.openembedded.org/meta-openembedded/tree/meta-multimedia/recipes-multimedia/fdk-aac/fdk-aac_2.0.1.bb
# because it is not currently available in the 'warrior' branch.
#
# Original license for the recipe:
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

SUMMARY = "FDK-AAC audio codec"

DESCRIPTION = "The Fraunhofer FDK AAC Codec Library for Android \
(\"FDK AAC Codec\") is software that implements the MPEG \
Advanced Audio Coding (\"AAC\") encoding and decoding scheme \
for digital audio."

HOMEPAGE = "https://www.iis.fraunhofer.de/en/ff/amm/impl.html"

LICENSE = "Fraunhofer_FDK_AAC_Codec_Library_for_Android"
LICENSE_FLAGS = "commercial"
LIC_FILES_CHKSUM = "file://NOTICE;md5=5985e1e12f4afa710d64ed7bfd291875"
NO_GENERIC_LICENSE[Fraunhofer_FDK_AAC_Codec_Library_for_Android] = "${WORKDIR}/NOTICE"

SRC_URI = "git://github.com/mstorsjo/fdk-aac.git;protocol=git;branch=master"
SRCREV = "d387d3b6ed79ff9a82c60440bdd86e6e5e324bec"

S = "${WORKDIR}/git"

SRC_URI[md5sum] = "fef453b5d6ee28ff302c600b8cded3e7"
SRC_URI[sha256sum] = "07c2a64b098eb48b2e9d729d5e778c08f7d22f28adc8da7c3f92c58da1cbbd8e"

inherit autotools

