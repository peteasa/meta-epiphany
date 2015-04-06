##################################################################
# Part two of this refactoring will move this file to
# exotic-gcc-common.inc
##################################################################


SUMMARY = "GNU cc and gcc C compilers"
HOMEPAGE = "http://www.gnu.org/software/gcc/"
SECTION = "devel"
LICENSE = "GPL"

NATIVEDEPS = ""

inherit autotools_exotic gettext texinfo

BPN = "${EXOTIC_TARGET_PREFIX}gcc"

#python do_preconfigure () {
#    import subprocess
#    cmd = d.expand('PATH=${PATH} cd ${S} && gnu-configize')
#    subprocess.check_output(cmd, stderr=subprocess.STDOUT, shell=True)
#    # See 0044-gengtypes.patch, we need to regenerate this file
#    bb.utils.remove(d.expand("${S}/gcc/gengtype-lex.c"))
#}
#addtask do_preconfigure after do_patch before do_configure
#do_preconfigure[depends] += "gnu-config-native:do_populate_sysroot autoconf-native:do_popula#te_sysroot"

#
# Set some default values
#

BINV = "${PV}"

S = "${TMPDIR}/work-shared/${EXOTIC_TARGET_PREFIX}gcc-${PV}-${PR}/git"
B = "${WORKDIR}/${EXOTIC_TARGET_PREFIX}gcc-${PV}/build.${HOST_SYS}.${EXOTIC_TARGET_SYS}"

# SS means Shared Stamps directory
SS = "${TMPDIR}/stamps/work-shared/${EXOTIC_TARGET_PREFIX}gcc-${PV}-${PR}"
do_fetch[stamp-base] = "${SS}"
do_unpack[stamp-base] = "${SS}"
do_patch[stamp-base] = "${SS}"
do_preconfigure[stamp-base] = "${SS}"
SSCLEAN = "${TMPDIR}/stamps/work-shared/${EXOTIC_TARGET_PREFIX}gcc-[0-9]*-*"
do_fetch[stamp-base-clean] = "${SSCLEAN}"
do_unpack[stamp-base-clean] = "${SSCLEAN}"
do_unpack[umask] = "022"
do_patch[stamp-base-clean] = "${SSCLEAN}"
do_preconfigure[stamp-base-clean] = "${SSCLEAN}"

# SW means Shared Work directory
SW = "${TMPDIR}/work-shared/${EXOTIC_TARGET_PREFIX}gcc-${PV}-${PR}"
SSTATE_SWSPEC = "sstate:${EXOTIC_TARGET_PREFIX}gcc::${PV}:${PR}::${SSTATE_VERSION}:"
WORKDIR_task-unpack = "${SW}"
WORKDIR_task-patch = "${SW}"
WORKDIR_task-preconfigure = "${SW}"

oldincludedir="${exec_prefix}/${EXOTIC_TARGET_SYS}/include"
includedir="${exec_prefix}/${EXOTIC_TARGET_SYS}/include"
# Update bitbake.conf setting - libdir="${exec_prefix}/${baselib}"
libdir="${exec_prefix}/${baselib}/${EXOTIC_TARGET_SYS}"
#   following seems to be auto added gcc/${EXOTIC_TARGET_SYS}/${BINV}
# Update bitbake.conf setting - libexecdir = "${libdir}/${BPN}"
#   following seems to be auto added gcc/${EXOTIC_TARGET_SYS}/${BINV}
libexecdir="${exec_prefix}/${baselib}/${EXOTIC_TARGET_SYS}"  
gcclibdir = "${libdir}/gcc"

target_includedir ?= "${includedir}"
target_libdir ?= "${libdir}"
target_base_libdir ?= "${base_libdir}"
target_prefix ?= "${prefix}"

export CC_FOR_TARGET="${EXOTIC_TARGET_PREFIX}gcc"
export GCC_FOR_TARGET="${EXOTIC_TARGET_PREFIX}gcc"
export CXX_FOR_TARGET="${EXOTIC_TARGET_PREFIX}g++"
export AS_FOR_TARGET="${EXOTIC_TARGET_PREFIX}as"
export LD_FOR_TARGET="${EXOTIC_TARGET_PREFIX}ld"
export NM_FOR_TARGET="${EXOTIC_TARGET_PREFIX}nm"
export AR_FOR_TARGET="${EXOTIC_TARGET_PREFIX}ar"
export RANLIB_FOR_TARGET="${EXOTIC_TARGET_PREFIX}ranlib"

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
