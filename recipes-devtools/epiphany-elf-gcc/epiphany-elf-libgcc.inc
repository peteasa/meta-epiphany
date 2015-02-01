require epiphany-elf-libgcc-common.inc

DEPENDS = "virtual/${TARGET_PREFIX}gcc virtual/${TARGET_PREFIX}g++ ${TARGET_PREFIX}newlib"

PACKAGES = "\
    ${PN} \
    ${PN}-dev \
    ${PN}-libgcov \
"

# TODO does not like     ${@base_conditional('TARGET_SYS_NO_EXTENSION', '${TARGET_SYS}', '', '${libdir}/${TARGET_SYS_NO_EXTENSION}', d)}

FILES_${PN} = "${base_libdir}/libgcc*.so.*"

ALLOW_EMPTY_${PN} = "1"

FILES_${PN}-dev = "\
    ${libdir}/gcc/${TARGET_SYS}/${BINV}/*crt* \
    ${libdir}/gcc/${TARGET_SYS}/${BINV}/libgcc* \
"
FILES_${PN}-libgcov = "\
    ${libdir}/gcc/${TARGET_SYS}/${BINV}/libgcov.a \
"

EPIPHANYLIBGCCBUILDTREENAME = "gcc-build-internal-"

do_package[depends] += "virtual/${MLPREFIX}libc:do_packagedata"
do_package_write_ipk[depends] += "virtual/${MLPREFIX}libc:do_packagedata"
do_package_write_deb[depends] += "virtual/${MLPREFIX}libc:do_packagedata"
do_package_write_rpm[depends] += "virtual/${MLPREFIX}libc:do_packagedata"

INSANE_SKIP_${PN}-dev = "staticdev"
INSANE_SKIP_${PN}-libgcov = "staticdev"

## look at package_rpm.bbclass -
## need to override pkgarch = d.expand('${PACKAGE_ARCH_EXTEND}-poky-linux-gnueabi')
## at the moment it looks like pkgarch = d.expand('${PACKAGE_ARCH_EXTEND}-e-os')
TARGET_VENDO="-poky-linux"
HOST_OS="gnueabi"
