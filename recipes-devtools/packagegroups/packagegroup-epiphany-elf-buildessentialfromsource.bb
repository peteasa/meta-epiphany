#
#

SUMMARY = "Essential build dependencies"
LICENSE = "GPL"

inherit packagegroup
#oops something gone wrong -  epiphany-elf-cpp-symlinks 
RDEPENDS_packagegroup-epiphany-elf-buildessentialfromsource = "\
    autoconf \
    automake \
    epiphany-elf-binutils \
    epiphany-elf-binutils-symlinks \
    epiphany-elf-cpp \
    epiphany-elf-gcc \
    epiphany-elf-gcc-symlinks \
    epiphany-elf-g++ \
    epiphany-elf-g++-symlinks \
    gettext \
    make \
    epiphany-elf-newlib \
    libtool \
    pkgconfig \
    "

