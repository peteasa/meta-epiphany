##################################################################
# Override variables with the remote target details

EXOTIC_TARGET_ARCH = "epiphany"
EXOTIC_TARGET_OS = "elf"
EXOTIC_TARGET_SYS = "epiphany-elf"

# update these as appropriate!
EXOTIC_TARGET_CFLAGS = "${TARGET_CFLAGS_GVARIABLE}"
EXOTIC_TARGET_CXXFLAGS = "${TARGET_CXXFLAGS_GVARIABLE}"
EXOTIC_TARGET_LDFLAGS = "${TARGET_LDFLAGS_GVARIABLE}" 

inherit exotic-set-paths-host-is-target

#
# Now all the scripts in this recipe can use TARGET_??
# or can use the original settings of TARGET_??_GVARIABLE
#

BASEDEPENDS = "virtual/${HOST_PREFIX_GVARIABLE}gcc virtual/${HOST_PREFIX_GVARIABLE}compilerlibs virtual/libc"

require epiphany-elf-binutils-${PV}.inc

##################################################################
# Part two of this refactoring will make this file an append to
# the exotic-binutils_2.23.bb file and the following will be
# the content of that file!
##################################################################

#
# Now the script
#

require epiphany-elf-binutils.inc

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
        chrpath -d ${D}${bindir}/${EXOTIC_TARGET_PREFIX}ar
        chrpath -d ${D}${bindir}/${EXOTIC_TARGET_PREFIX}ranlib
}
