require epiphany-elf-libgcc-common.inc

DEPENDS = "virtual/${TARGET_PREFIX}gcc virtual/${TARGET_PREFIX}g++"

PACKAGES = "\
    ${PN} \
    ${PN}-dev \
    ${PN}-libgcov \
"

# TODO does not like     ${@base_conditional('TARGET_SYS_NO_EXTENSION', '${TARGET_SYS}', '', '${libdir}/${TARGET_SYS_NO_EXTENSION}', d)}

FILES_${PN} = "${base_libdir}/libgcc*.so.*"

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

