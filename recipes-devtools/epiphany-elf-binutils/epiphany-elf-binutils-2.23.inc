
SUMMARY = "GNU binary utilities for epiphany processor"
DESCRIPTION = "The GNU Binutils are a collection of binary tools. \
The main ones are ld (GNU Linker), and as (GNU Assembler). This \
package also includes addition tools such as addr2line (Converts \
addresses into filenames and line numbers), ar (utility for creating, \
modifying and extracting archives), nm (list symbols in object \
files), objcopy (copy and translate object files), objdump (Display \
object information), and other tools and related libraries."

HOMEPAGE = "http://www.adapteva.com/epiphany-sdk/"
BUGTRACKER = "https://github.com/adapteva/epiphany-binutils-gdb/issues"

SECTION = "devel"
LICENSE = "GPLv3"

# Ensure that ${S} points to the source location
S = "${WORKDIR}/git"

# Sources
FILESEXTRAPATHS_prepend := "${THISDIR}/epiphany-elf-binutils/2.23:"

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
