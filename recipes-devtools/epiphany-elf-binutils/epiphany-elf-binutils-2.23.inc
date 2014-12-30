
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

EBINUTILSBRANCH = "epiphany-binutils-2_23"
SRC_URI = "git:///home/pas/Projects/meta-yocto-parallella/epiphany-binutils.git;branch=${EBINUTILSBRANCH};name=${EBINUTILSBRANCH} \
     file://epiphany-elf-binutils-uclibc-300-001_ld_makefile_patch.patch \
     file://epiphany-elf-binutils-bfd-libdir.patch \
     file://epiphany-elf-binutils-opcodes-libdir.patch \
          "

SRCREV = "2a5ae813deb8eb663e7286a1e517f166a40d801e"