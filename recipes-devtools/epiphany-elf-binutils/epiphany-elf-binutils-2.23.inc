##################################################################
# Part two of this refactoring will require this content to
# override the content in exotic-binutils-2.23.inc that will
# itself be based on the official binutils-2.23 release.
##################################################################

# Ensure that ${S} points to the source location
S = "${WORKDIR}/git"

# Sources
FILESEXTRAPATHS_prepend := "${THISDIR}/epiphany-elf-binutils:"

LIC_FILES_CHKSUM="\
    file://src-release;endline=17;md5=4830a9ef968f3b18dd5e9f2c00db2d35\
    file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552\
    file://COPYING.LIB;md5=9f604d8a4f8e74f4f5140845a21b6674\
    file://COPYING3;md5=d32239bcb673463ab874e80d47fae504\
    file://COPYING3.LIB;md5=6a6a8e020838b23406c81b19c1d46df6\
    file://gas/COPYING;md5=d32239bcb673463ab874e80d47fae504\
    file://include/COPYING;md5=59530bdf33659b29e73d4adb9f9f6552\
    file://include/COPYING3;md5=d32239bcb673463ab874e80d47fae504\
    file://bfd/COPYING;md5=d32239bcb673463ab874e80d47fae504\
    "

EBINUTILSBRANCH = "epiphany-binutils-2.23-software-cache"
SRC_URI = "git://github.com/adapteva/epiphany-binutils-gdb.git;branch=${EBINUTILSBRANCH};name=${EBINUTILSBRANCH} "
SRC_URI += " \
     file://epiphany-elf-binutils-bfd-libdir.patch \
     file://epiphany-elf-binutils-opcodes-libdir.patch \
          "

#      file://epiphany-elf-binutils-uclibc-300-001_ld_makefile_patch.patch

# Tag: epiphany-binutils-esdk-2014.11
SRCREV = "cb1b5279004381d35c27a501e582e1eb4d5c4cba"
