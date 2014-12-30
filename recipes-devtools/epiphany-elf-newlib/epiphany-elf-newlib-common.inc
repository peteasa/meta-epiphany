SUMMARY = "GNU newlib ANSI C libraries for epiphany processor"
DESCRIPTION = "GNU newlib is a simple ANSI C library, math library, and \
collection of board support packages"

HOMEPAGE = "http://www.adapteva.com/epiphany-sdk/"
BUGTRACKER = "https://github.com/adapteva/epiphany-newlib/issues"

SECTION = "devel"
LICENSE = "GPLv3"

# TODO bit of a hack here just to get it working for now
STAGING_BINDIR_TOOLCHAIN = "${STAGING_DIR_NATIVE}${bindir_native}/${TARGET_SYS}:${STAGING_DIR_NATIVE}${bindir_native}/${TARGET_SYS}"

NATIVEDEPS = ""

inherit autotools gettext texinfo

INHIBIT_DEFAULT_DEPS = "1"

#
# Set some default values
#
#gcclibdir = "${libdir}/gcc"
BINV = "${PV}"
#S = "${WORKDIR}/epiphany-elf-newlib-${PV}"
#S = "${TMPDIR}/work-shared/epiphany-elf-gcc-${PV}-${PR}/epiphany-elf-newlib-${PV}"
S = "${TMPDIR}/work-shared/epiphany-elf-newlib-${PV}-${PR}/git"
#B = "${WORKDIR}/epiphany-elf-newlib-${PV}/build.${HOST_SYS}.${TARGET_SYS}"
B = "${WORKDIR}/git/build.${HOST_SYS}.${TARGET_SYS}"

# SS means Shared Stamps directory
SS = "${TMPDIR}/stamps/work-shared/epiphany-elf-newlib-${PV}-${PR}"
do_fetch[stamp-base] = "${SS}"
do_unpack[stamp-base] = "${SS}"
do_patch[stamp-base] = "${SS}"
do_preconfigure[stamp-base] = "${SS}"
SSCLEAN = "${TMPDIR}/stamps/work-shared/epiphany-elf-newlib-[0-9]*-*"
do_fetch[stamp-base-clean] = "${SSCLEAN}"
do_unpack[stamp-base-clean] = "${SSCLEAN}"
do_unpack[umask] = "022"
do_patch[stamp-base-clean] = "${SSCLEAN}"
do_preconfigure[stamp-base-clean] = "${SSCLEAN}"

# SW means Shared Work directory
SW = "${TMPDIR}/work-shared/epiphany-elf-newlib-${PV}-${PR}"
SSTATE_SWSPEC = "sstate:epiphany-elf-newlib::${PV}:${PR}::${SSTATE_VERSION}:"
WORKDIR_task-unpack = "${SW}"
WORKDIR_task-patch = "${SW}"
WORKDIR_task-preconfigure = "${SW}"

# fix up commands used for packaging
export OBJCOPY = "${TARGET_SYS}-objcopy"

export AR = "${HOST_PREFIX}ar"
export AS = "${HOST_PREFIX}as"
export LD = "${HOST_PREFIX}ld"
export NM = "${HOST_PREFIX}nm"
export RANLIB = "${HOST_PREFIX}ranlib"
export OBJCOPY = "${HOST_PREFIX}objcopy"
export OBJDUMP = "${HOST_PREFIX}objdump"

export AR_FOR_TARGET = "${TARGET_PREFIX}ar"
export AS_FOR_TARGET = "${TARGET_PREFIX}as"
export LD_FOR_TARGET = "${TARGET_PREFIX}ld"
export NM_FOR_TARGET = "${TARGET_PREFIX}nm"
export RANLIB_FOR_TARGET = "${TARGET_PREFIX}ranlib"

export CC_FOR_HOST = "${CCACHE}${HOST_PREFIX}gcc ${HOST_CC_ARCH}"
export CXX_FOR_HOST = "${CCACHE}${HOST_PREFIX}gcc ${HOST_CC_ARCH}"

export CC_FOR_BUILD = "${BUILD_CC}"

target_includedir ?= "${includedir}"
target_libdir ?= "${libdir}"
target_base_libdir ?= "${base_libdir}"
target_prefix ?= "${prefix}"

CLEANFUNCS += "workshared_clean"
# The do_clean should be exclusive since share ${S}
do_clean[lockfiles] = "${SW}.clean.lock"

python workshared_clean () {
	"""clear the source directory"""
	dir = d.expand("${SW}")
	bb.note("Removing " + dir)
	oe.path.remove(dir)

	"""clear the the stamps in work-shared"""
	dir = "%s.*" % bb.data.expand(d.getVarFlag('do_fetch', 'stamp-base', True), d)
	bb.note("Removing " + dir)
	oe.path.remove(dir)
}

# We need to ensure that for the shared work directory, the do_patch singatures match
# The real WORKDIR location isn't a dependency for the shared workdir.
src_patches[vardepsexclude] = "WORKDIR"
should_apply[vardepsexclude] += "PN"

do_configure () {
    target=${TARGET_SYS}
	install -d ${D}${base_libdir} ${D}${libdir}
	hardlinkdir ${STAGING_INCDIR_NATIVE}/${NEWLIBBUILDTREENAME}$target/ ${B}
	mkdir -p ${B}
	mkdir -p ${B}/$target
	cd ${B}
	chmod a+x ${S}/configure
	${S}/configure ${CONFIGUREOPTS} ${EXTRA_OECONF}
}

do_compile () {
    target=${TARGET_SYS}
	cd ${B}
	oe_runmake MULTIBUILDTOP=${B}/$target/
}

do_install () {
    target=${TARGET_SYS}
	cd ${B}
	oe_runmake 'DESTDIR=${D}' MULTIBUILDTOP=${B}/$target/ install

	# install the runtime in /usr/lib/epiphany-elf not in /usr/epiphany-elf/lib on target
	# so that cross-gcc can find it in the sysroot

    mkdir -p ${D}${libdir}/$target
	mv ${D}/usr/$target/lib/* ${D}${libdir}/$target
}

BBCLASSEXTEND = "nativesdk"

addtask multilib_install after do_install before do_package do_populate_sysroot
# this makes multilib gcc files findable for target gcc
# e.g.
#    /usr/lib/i586-pokymllib32-linux/4.7/
# by creating this symlink to it
#    /usr/lib64/x86_64-poky-linux/4.7/32

python do_multilib_install() {
    import re

    multilibs = d.getVar('MULTILIB_VARIANTS', True)
    if not multilibs or bb.data.inherits_class('nativesdk', d):
        return

    binv = d.getVar('BINV', True)

    mlprefix = d.getVar('MLPREFIX', True)
    if ('%slibgcc' % mlprefix) != d.getVar('PN', True):
        return

    if mlprefix:
        orig_tune = d.getVar('DEFAULTTUNE_MULTILIB_ORIGINAL', True)
        orig_tune_params = get_tune_parameters(orig_tune, d)
        orig_tune_baselib = orig_tune_params['baselib']
        orig_tune_bitness = orig_tune_baselib.replace('lib', '')
        if not orig_tune_bitness:
            orig_tune_bitness = '32'

        src = '../../../' + orig_tune_baselib + '/' + \
            d.getVar('TARGET_SYS_MULTILIB_ORIGINAL', True) + '/' + binv + '/'

        dest = d.getVar('D', True) + d.getVar('libdir', True) + '/' + \
            d.getVar('TARGET_SYS', True) + '/' + binv + '/' + orig_tune_bitness

        if os.path.lexists(dest):
            os.unlink(dest)
        os.symlink(src, dest)
        return


    for ml in multilibs.split():
        tune = d.getVar('DEFAULTTUNE_virtclass-multilib-' + ml, True)
        if not tune:
            bb.warn('DEFAULTTUNE_virtclass-multilib-%s is not defined. Skipping...' % ml)
            continue

        tune_parameters = get_tune_parameters(tune, d)
        tune_baselib = tune_parameters['baselib']
        if not tune_baselib:
            bb.warn("Tune %s doesn't have a baselib set. Skipping..." % tune)
            continue

        tune_arch = tune_parameters['arch']
        tune_bitness = tune_baselib.replace('lib', '')
        if not tune_bitness:
            tune_bitness = '32' # /lib => 32bit lib

        src = '../../../' + tune_baselib + '/' + \
            tune_arch + d.getVar('TARGET_VENDOR', True) + 'ml' + ml + \
            '-' + d.getVar('TARGET_OS', True) + '/' + binv + '/'

        dest = d.getVar('D', True) + d.getVar('libdir', True) + '/' + \
            d.getVar('TARGET_SYS', True) + '/' + binv + '/' + tune_bitness

        if os.path.lexists(dest):
            os.unlink(dest)
        os.symlink(src, dest)
}

addtask extra_symlinks after do_multilib_install before do_package do_populate_sysroot
python do_extra_symlinks() {
    targetsysnoext = d.getVar('TARGET_SYS_NO_EXTENSION', True)

    if targetsysnoext != d.getVar('TARGET_SYS', True):
        dest = d.getVar('D', True) + d.getVar('libdir', True) + '/' + targetsysnoext
        src = d.getVar('TARGET_SYS', True)
        if not os.path.lexists(dest) and os.path.lexists(d.getVar('D', True) + d.getVar('libdir', True)):
            os.symlink(src, dest)
}

python () {
    targetsysnoext = d.getVar('TARGET_SYS', True)

    for suffix in [d.getVar('ABIEXTENSION', True), d.getVar('LIBCEXTENSION', True)]:
        if suffix and targetsysnoext.endswith(suffix):
            targetsysnoext = targetsysnoext[:-len(suffix)]

    d.setVar('TARGET_SYS_NO_EXTENSION', targetsysnoext)
}
