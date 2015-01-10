#
#

SUMMARY = "Essential build dependencies"
LICENSE = "GPL"

inherit packagegroup
#oops something gone wrong - epiphany-elf-cpp-symlinks 
#oops something gone wrong - epiphany-elf-gcc-plugin-dev 
#oops something gone wrong - epiphany-elf-libgcc*
#oops something gone wrong - epiphany-elf-newlib*
RDEPENDS_packagegroup-epiphany-elf-buildessentialfromsource = "\
    autoconf \
    automake \
    epiphany-elf-binutils \
    epiphany-elf-binutils-dbg \
    epiphany-elf-binutils-dev \
    epiphany-elf-binutils-staticdev \
    epiphany-elf-binutils-symlinks \
    epiphany-elf-gcc \
    epiphany-elf-gcc-plugins \
    epiphany-elf-gcc-symlinks \
    epiphany-elf-g++ \
    epiphany-elf-g++-symlinks \
    epiphany-elf-cpp  \
    epiphany-elf-gcc-doc \
    epiphany-elf-gcc-dev \
    epiphany-elf-gcc-dbg \
    gettext \
    make \
    libtool \
    pkgconfig \
    "

