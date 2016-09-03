
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
FILESEXTRAPATHS_prepend := "${THISDIR}/epiphany-elf-binutils/2.25:"

LIC_FILES_CHKSUM="\
    file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552\
    file://COPYING.LIB;md5=9f604d8a4f8e74f4f5140845a21b6674\
    file://COPYING3;md5=d32239bcb673463ab874e80d47fae504\
    file://COPYING3.LIB;md5=6a6a8e020838b23406c81b19c1d46df6\
    file://gas/COPYING;md5=d32239bcb673463ab874e80d47fae504\
    file://include/COPYING;md5=59530bdf33659b29e73d4adb9f9f6552\
    file://include/COPYING3;md5=d32239bcb673463ab874e80d47fae504\
    file://libiberty/COPYING.LIB;md5=a916467b91076e631dd8edb7424769c7\
    file://bfd/COPYING;md5=d32239bcb673463ab874e80d47fae504\
    "

EBINUTILSBRANCH = "epiphany-binutils-2.25"
SRC_URI = "git://github.com/adapteva/epiphany-binutils-gdb.git;branch=${EBINUTILSBRANCH};name=${EBINUTILSBRANCH} "

##      file://0003-Point-scripts-location-to-libdir.patch
SRC_URI += " \
     file://0002-configure-widen-the-regexp-for-SH-architectures.patch \
     file://0004-Only-generate-an-RPATH-entry-if-LD_RUN_PATH-is-not-e.patch \
     file://0005-Explicitly-link-with-libm-on-uclibc.patch \
     file://0006-Use-libtool-2.4.patch \
     file://0007-Add-the-armv5e-architecture-to-binutils.patch \
     file://0008-don-t-let-the-distro-compiler-point-to-the-wrong-ins.patch \
     file://0009-Upstream-Status-Inappropriate-distribution-codesourc.patch \
     file://0010-Fix-rpath-in-libtool-when-sysroot-is-enabled.patch \
     file://0011-Change-default-emulation-for-mips64-linux.patch \
     file://0012-Add-XLP-instructions-support.patch \
     file://0013-Fix-an-internal-error-in-do_print_to_mapfile-seen-wi.patch \
     file://0014-gold-arm-Skip-pic-check-for-R_ARM_REL32.patch \
     file://0015-Fix-dynamic-list-so-that-symbols-not-in-the-list-are.patch \
     file://binutils-octeon3.patch \
     file://add-thunderx-support-for-gas.patch \
     file://0020-epiphany-elf-binutils-bfd-libdir.patch \
     file://0021-epiphany-elf-binutils-opcodes-libdir.patch \
          "

# Tag: epiphany-binutils-esdk-2016.3.1
SRCREV = "bf635fd753ce92ff72a29a32656172535550022c"
