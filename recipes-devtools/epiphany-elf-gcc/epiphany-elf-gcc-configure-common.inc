
EXTRA_OECONF_BASE ?= ""
EXTRA_OECONF_PATHS ?= ""
EXTRA_OECONF_INITIAL ?= ""
EXTRA_OECONF_INTERMEDIATE ?= ""

EXTRA_OECONF = "\
    --with-newlib \
    --with-cross-host=arm-poky-linux-gnueabi \
    --enable-fast-install=N/A \
    --enable-languages=c,c++ \
    --disable-gdbtk \
    --disable-werror \
    --program-prefix=${TARGET_PREFIX} \
    --with-sysroot=/ \
    --with-libtool-sysroot=${STAGING_DIR_HOST} \
    --with-build-sysroot=${STAGING_DIR_HOST} \
"

export ac_cv_path_SED = 'sed'
export gcc_cv_collect2_libs = 'none required'
# We need to set gcc_cv_collect2_libs else there is cross-compilation badness
# in the config.log files (which might not get generated until do_compile
# hence being missed by the insane do_configure check).

EXTRA_OECONF_GCC_FLOAT ??= ""
CPPFLAGS = ""

SYSTEMHEADERS = "${target_includedir}"
SYSTEMLIBS = "${target_base_libdir}/"
SYSTEMLIBS1 = "${target_libdir}/"

do_configure_prepend () {
	# teach gcc to find correct target includedir when checking libc ssp support
	mkdir -p ${B}/gcc
	echo "NATIVE_SYSTEM_HEADER_DIR = ${SYSTEMHEADERS}" > ${B}/gcc/t-oe
	cat ${S}/gcc/defaults.h | grep -v "\#endif.*GCC_DEFAULTS_H" > ${B}/gcc/defaults.h.new
	cat >>${B}/gcc/defaults.h.new <<_EOF
#define NATIVE_SYSTEM_HEADER_DIR "${SYSTEMHEADERS}"
#define STANDARD_STARTFILE_PREFIX_1 "${SYSTEMLIBS}"
#define STANDARD_STARTFILE_PREFIX_2 "${SYSTEMLIBS1}"
#define SYSTEMLIBS_DIR "${SYSTEMLIBS}"
#endif /* ! GCC_DEFAULTS_H */
_EOF
	mv ${B}/gcc/defaults.h.new ${B}/gcc/defaults.h
}

do_configure () {
	# Setup these vars for cross building only
	# ... because foo_FOR_TARGET apparently gets misinterpreted inside the
	# gcc build stuff when the build is producing a cross compiler - i.e.
	# when the 'current' target is the 'host' system, and the host is not
	# the target (because the build is actually making a cross compiler!)
	if [ "${BUILD_SYS}" != "${HOST_SYS}" ]; then
		export CC_FOR_TARGET="${CC}"
		export GCC_FOR_TARGET="${CC}"
		export CXX_FOR_TARGET="${CXX}"
		export AS_FOR_TARGET="${HOST_PREFIX}as"
		export LD_FOR_TARGET="${HOST_PREFIX}ld"
		export NM_FOR_TARGET="${HOST_PREFIX}nm"
		export AR_FOR_TARGET="${HOST_PREFIX}ar"
		export GFORTRAN_FOR_TARGET="gfortran"
		export RANLIB_FOR_TARGET="${HOST_PREFIX}ranlib"
	fi
	export CC_FOR_BUILD="${BUILD_CC}"
	export CXX_FOR_BUILD="${BUILD_CXX}"
	export CFLAGS_FOR_BUILD="${BUILD_CFLAGS}"
	export CPPFLAGS_FOR_BUILD="${BUILD_CPPFLAGS}"
	export CXXFLAGS_FOR_BUILD="${BUILD_CXXFLAGS}"
	export LDFLAGS_FOR_BUILD="${BUILD_LDFLAGS}"
	export CFLAGS_FOR_TARGET="${TARGET_CFLAGS}"
	export CPPFLAGS_FOR_TARGET="${TARGET_CPPFLAGS}"
	export CXXFLAGS_FOR_TARGET="${TARGET_CXXFLAGS}"
	export LDFLAGS_FOR_TARGET="${TARGET_LDFLAGS}"


	oe_runconf
}

