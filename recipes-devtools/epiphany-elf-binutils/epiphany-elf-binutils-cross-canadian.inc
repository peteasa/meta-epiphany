inherit cross-canadian

SUMMARY = "GNU binary utilities (cross-canadian for ${TARGET_ARCH} target)"
PN = "epiphany-elf-binutils-cross-canadian-${TRANSLATED_TARGET_ARCH}"
BPN = "epiphany-elf-binutils"

DEPENDS = "flex-native bison-native virtual/${HOST_PREFIX}gcc-crosssdk virtual/nativesdk-libc nativesdk-zlib nativesdk-gettext nativesdk-flex"
EXTRA_OECONF += "--with-sysroot=${SDKPATH}/sysroots/${TUNE_PKGARCH}${TARGET_VENDOR}-${TARGET_OS} \
                "

# Override things set in cross-canadian.bbclass
STAGING_BINDIR_TOOLCHAIN = "${STAGING_DIR_NATIVE}${bindir_native}/${SDK_ARCH}${SDK_VENDOR}-${SDK_OS}:${STAGING_DIR_NATIVE}${bindir_native}/${TARGET_SYS}"
STAGING_BINDIR_CROSS = "${STAGING_DIR_NATIVE}${bindir_native}/${TARGET_SYS}"

#base_prefix = "${SDKPATHNATIVE}"
#prefix = "${SDKPATHNATIVE}${prefix_nativesdk}"
#exec_prefix = "${SDKPATHNATIVE}${prefix_nativesdk}"
bindir = "${exec_prefix}/bin/${TARGET_SYS}"
sbindir = "${bindir}"
base_bindir = "${bindir}"
base_sbindir = "${bindir}"
libdir = "${exec_prefix}/lib/${TARGET_SYS}"
libexecdir = "${exec_prefix}/lib/${TARGET_SYS}"

# We have to point binutils at a sysroot but we don't need to rebuild if this changes
# e.g. we switch between different machines with different tunes.
EXTRA_OECONF[vardepsexclude] = "TUNE_PKGARCH"

# Override bitbake.conf FILES_${PN} settings as appropriate
FILES_${PN} = " \
	${bindir}/* \
	${prefix}/${TARGET_SYS}/bin/* \
    ${libdir}/lib*-*.so \
    ${prefix}/${TARGET_SYS}/lib/ldscripts/* \
    "
FILES_${PN}-dbg += " \
    ${prefix}/${TARGET_SYS}/bin/.debug/* \
    "
FILES_${PN}-dev = "	\
    ${libdir}/libbfd.so \
    ${libdir}/libopcodes.so \
    ${libdir}/*.la \
	"
FILES_${PN}-staticdev = "	\
    ${libdir}/*.a \
	"
RPROVIDES_${PN} += "${PN}-symlinks"

do_install () {
	autotools_do_install
	
    # fix directory structure
    mv ${D}${exec_prefix}/lib/lib/libiberty.a ${D}${libdir}
    rm -rf ${D}${exec_prefix}/lib/lib

	cross_canadian_bindirlinks
}

BBCLASSEXTEND = ""
