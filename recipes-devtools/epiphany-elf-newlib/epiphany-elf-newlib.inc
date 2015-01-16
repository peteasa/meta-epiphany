require epiphany-elf-newlib-common.inc

DEPENDS = "virtual/${TARGET_PREFIX}binutils virtual/${TARGET_PREFIX}gcc virtual/${TARGET_PREFIX}g++"

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

NEWLIBBUILDTREENAME = "newlib-build-internal-"

do_package[depends] += "virtual/${MLPREFIX}libc:do_packagedata"
do_package_write_ipk[depends] += "virtual/${MLPREFIX}libc:do_packagedata"
do_package_write_deb[depends] += "virtual/${MLPREFIX}libc:do_packagedata"
do_package_write_rpm[depends] += "virtual/${MLPREFIX}libc:do_packagedata"

INSANE_SKIP_${PN}-staticdev = "arch"
INSANE_SKIP_${PN}-dbg += "arch"

## look at package_rpm.bbclass -
## need to override pkgarch = d.expand('${PACKAGE_ARCH_EXTEND}-poky-linux-gnueabi')
## at the moment it looks like pkgarch = d.expand('${PACKAGE_ARCH_EXTEND}-e-os')
HOST_OS="poky-linux-gnueabi"
