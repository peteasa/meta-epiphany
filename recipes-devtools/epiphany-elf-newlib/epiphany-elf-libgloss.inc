##
## modelled libgcc.inc
##

require epiphany-elf-libgloss-common.inc

DEPENDS = "virtual/${TARGET_PREFIX}binutils virtual/${TARGET_PREFIX}gcc virtual/${TARGET_PREFIX}g++ ${TARGET_PREFIX}newlib"

PACKAGES = "\
    ${PN} \
    ${PN}-dev \
    ${PN}-staticdev \
    ${PN}-dbg \
"

FILES_${PN} = " \
    ${prefix}/lib/${TARGET_SYS}/*.o \
" 
FILES_${PN}-dev = "\
"
FILES_${PN}-dbg += "${prefix}/lib/${TARGET_SYS}/.debug/"
FILES_${PN}-staticdev = "	\
    ${prefix}/lib/${TARGET_SYS}/*.a \
	"

# debugedit chokes on bare metal binaries
INHIBIT_PACKAGE_STRIP = "1"

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
