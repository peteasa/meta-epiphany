#
#

SUMMARY = "Essential build dependencies"
LICENSE = "GPL"

inherit packagegroup

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
    epiphany-elf-gcc-plugin-dev \
    epiphany-elf-gcc-symlinks \
    epiphany-elf-g++ \
    epiphany-elf-g++-symlinks \
    epiphany-elf-cpp  \
    epiphany-elf-gcc-doc \
    epiphany-elf-gcc-dev \
    epiphany-elf-gcc-dbg \
    epiphany-elf-gcc-runtime-dbg \
    epiphany-elf-gcc-runtime-libstdc++-dev \
    epiphany-elf-gcc-runtime-libstdc++-staticdev \
    epiphany-elf-libgcc \
    epiphany-elf-libgcc-dev \
    epiphany-elf-libgcc-libgcov \
    epiphany-elf-newlib \
    epiphany-elf-newlib-staticdev \
    epiphany-elf-newlib-dev \
    epiphany-elf-newlib-dbg \
    epiphany-elf-libgloss \
    epiphany-elf-libgloss-staticdev \
    epiphany-elf-libgloss-dev \
    epiphany-elf-libgloss-dbg \        
    gettext \
    make \
    libtool \
    pkgconfig \
    "

