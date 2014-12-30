

# Override the TARGET to be "epiphany"
HOST_SYS := "${TARGET_SYS}"
HOST_PREFIX := "${TARGET_PREFIX}"
TARGET_SYS = "epiphany-elf"
TARGET_PREFIX = "epiphany-elf-"

BASEDEPENDS := "virtual/${HOST_PREFIX}gcc virtual/${HOST_PREFIX}compilerlibs virtual/libc"

require epiphany-elf-binutils.inc
require epiphany-elf-binutils-${PV}.inc

DEPENDS += "flex bison zlib"

EXTRA_OECONF += "--with-sysroot=/ \
                --enable-shared \
                --disable-install-libiberty \
                "

# remove rpath from binaries that contain an rpath with build machine paths
DEPENDS += "chrpath-replacement-native"
EXTRANATIVEPATH += "chrpath-native"

do_install_append() {
        # TODO why does the cross compiler create /lib/lib folder?
	    rm -rf ${D}${prefix}/lib/lib/libiberty.a
        rm -rf ${D}${prefix}/lib/ldscripts
        # Remove rpath from the offending binaries
        chrpath -d ${D}${bindir}/epiphany-elf-ar
        chrpath -d ${D}${bindir}/epiphany-elf-ranlib
}
