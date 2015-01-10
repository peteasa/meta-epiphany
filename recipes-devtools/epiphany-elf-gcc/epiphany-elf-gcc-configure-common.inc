
EXTRA_OECONF_BASE ?= ""
EXTRA_OECONF_PATHS ?= ""
EXTRA_OECONF_INITIAL ?= ""
EXTRA_OECONF_INTERMEDIATE ?= ""

# wanted /usr/lib/gcc/epiphany-elf/4.8.2/ etc but cant get this easily so move to
# want /usr/lib/epiphany-elf/gcc/epiphany-elf/4.8.2/
# --build=x86_64-linux --host=arm-poky-linux-gnueabi --target=epiphany-elf
# --prefix=/usr --exec_prefix=/usr --bindir=/usr/bin --sbindir=/usr/sbin
# --libexecdir=/usr/lib/gcc/epiphany-elf/4.8.2 --datadir=/usr/share --sysconfdir=/etc
# --sharedstatedir=/com --localstatedir=/var --libdir=/usr/lib/gcc/epiphany-elf/4.8.2
# --includedir=/usr/epiphany-elf/include --oldincludedir=/usr/epiphany-elf/include
# --infodir=/usr/share/info --mandir=/usr/share/man --disable-silent-rules
# --disable-dependency-tracking
# --with-libtool-sysroot=/home/pas/Projects/meta-yocto-parallella/parallella-yoctobuild/build_parallella/tmp/sysroots/parallella-hdmi
# --disable-gdbtk --disable-werror --enable-fast-install=N/A --without-included-gettext
# --enable-languages=c,c++ --enable-shared --enable-linker-build-id
# --program-prefix=epiphany-elf- --with-sysroot=/    
# --with-build-sysroot=/home/pas/Projects/meta-yocto-parallella/parallella-yoctobuild/build_parallella/tmp/sysroots/parallella-hdmi
# --with-native-system-header-dir=/home/pas/Projects/meta-yocto-parallella/parallella-yoctobuild/build_parallella/tmp/sysroots/parallella-hdmi/usr/epiphany-elf/include
# --with-gxx-include-dir=/usr/epiphany-elf/include/c++/4.8.2  --enable-nls

# gcc etc /usr/bin
# ar etc /usr/arm-linux-gnueabi/bin
# programs: =/usr/lib/epiphany-elf/arm-linux-gnueabi/4.7 .. c1 collect2 libgcca crtend.o etc
# libraries: =/usr/epiphany-elf/lib .. crt1.0 crti.0 .... libc.a

# TODO check --enable-shared --enable-multiarch  
# TODO need to work out where c++ header files are provided for epiphany-elf-gcc-runtime

EXTRA_OECONF = "\
    --enable-multilib \
    --disable-gdbtk \
    --disable-werror \
    --enable-fast-install=N/A \
    --without-included-gettext \
    --enable-languages=c,c++ \
    --enable-linker-build-id \
    --disable-libmudflap \
    --disable-libitm \
    --enable-plugin \
    --with-newlib \
    --program-prefix=${TARGET_PREFIX} \
    ${EXTRA_OECONF_PATHS} \
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

