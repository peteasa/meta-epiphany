#
#

SUMMARY = "Essential build dependencies for nativesdk"
LICENSE = "GPL"

inherit packagegroup

RDEPENDS:packagegroup-nativesdk-epiphany-elf-buildessentialfromsource = "\
    gcc-cross-canadian-epiphany \
    gcc-cross-canadian-epiphany-dbg \
    epiphany-binutils-cross-canadian-epiphany \
    epiphany-binutils-cross-canadian-epiphany-dbg \
    epiphany-binutils-cross-canadian-epiphany-dev \
    "