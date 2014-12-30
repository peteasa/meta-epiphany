require epiphany-elf-newlib-common.inc

DEPENDS = "virtual/${TARGET_PREFIX}gcc virtual/${TARGET_PREFIX}g++"

# Add paths of HOST and TARGET compilers
PATH_prepend = "${STAGING_BINDIR_TOOLCHAIN}:${STAGING_DIR_NATIVE}${bindir_native}/${TARGET_ARCH}${TARGET_VENDOR}-${TARGET_OS}:"

# fix up commands used for packaging
export RANLIB = "${TARGET_SYS}-ranlib"
export OBJCOPY = "${TARGET_SYS}-objcopy"
export OBJDUMP = "${TARGET_SYS}-objdump"
export STRIP = "${TARGET_SYS}-strip"

PACKAGES = "\
    ${PN} \
    ${PN}-dev \
    ${PN}-staticdev \
    ${PN}-dbg \
"

FILES_${PN} = " \
    ${prefix}/${TARGET_SYS}/include/* \
" 
FILES_${PN}-dev = "\
"
FILES_${PN}-dbg += "${prefix}/lib/${TARGET_SYS}/.debug/"
FILES_${PN}-staticdev = "	\
    /usr/lib/${TARGET_SYS}/* \
	"
do_install_append () {

    # Tidy up remaining folders
    rm -rf ${D}/${prefix}/share
    rm -rf ${D}/${prefix}/${TARGET_SYS}/lib
}

NEWLIBBUILDTREENAME = "gcc-build-internal-"

do_package[depends] += "virtual/${MLPREFIX}libc:do_packagedata"
do_package_write_ipk[depends] += "virtual/${MLPREFIX}libc:do_packagedata"
do_package_write_deb[depends] += "virtual/${MLPREFIX}libc:do_packagedata"
do_package_write_rpm[depends] += "virtual/${MLPREFIX}libc:do_packagedata"

INSANE_SKIP_${PN}-staticdev = "arch"
INSANE_SKIP_${PN}-dbg += "arch"

