##################################################################
# Override variables with the remote target details

EXOTIC_TARGET_ARCH = "epiphany"
EXOTIC_TARGET_OS = "elf"
EXOTIC_TARGET_SYS = "epiphany-elf"

# update these as appropriate!
EXOTIC_TARGET_CFLAGS = "${TARGET_CFLAGS_GVARIABLE}"
EXOTIC_TARGET_CXXFLAGS = "${TARGET_CXXFLAGS_GVARIABLE}"
EXOTIC_TARGET_CPPFLAGS = "${TARGET_CPPFLAGS_GVARIABLE}"
EXOTIC_TARGET_LDFLAGS = "-Wl,-O1 -Wl, -Wl,--as-needed"

##################################################################
# Part two of this refactoring will make this file an append to
# the exotic-gcc-runtime_4.8.bb file and the following will be
# the content of that file!
# Consider making it clear that this is gcc with newlib!
##################################################################

##################################################################
# Extensions to bitbake.conf
#   Temp location.  These should go into poky/meta/conf/bitbake.conf
##################################################################
#
##################################################################
# Architecture-dependent build variables.
##################################################################

# For these recipes we may need to override TARGET and HOST whilst keeping 
# variables derived from the original TARGET and HOST unchanged.
# To do this create new variables to store the original TARGET and HOST
HOST_ARCH_GVARIABLE := "${HOST_ARCH}"
HOST_OS_GVARIABLE := "${HOST_OS}"
HOST_VENDOR_GVARIABLE := "${HOST_VENDOR}"
HOST_SYS_GVARIABLE := "${HOST_SYS}"
HOST_PREFIX_GVARIABLE := "${HOST_PREFIX}"
HOST_CC_ARCH_GVARIABLE := "${HOST_CC_ARCH}"
HOST_LD_ARCH_GVARIABLE := "${HOST_LD_ARCH}"
HOST_AS_ARCH_GVARIABLE := "${HOST_AS}"
HOST_EXEEXT_GVARIABLE := "${HOST_EXEEXT}"

## Moved to autotools_exotic
#TARGET_ARCH_GVARIABLE := "${TARGET_ARCH}"
#TARGET_OS_GVARIABLE := "${TARGET_OS}"
#TARGET_VENDOR_GVARIABLE := "${TARGET_VENDOR}"
#TARGET_SYS_GVARIABLE := "${TARGET_SYS}"
#TARGET_PREFIX_GVARIABLE := "${TARGET_SYS}-"
#TARGET_CC_ARCH_GVARIABLE := "${TARGET_CC_ARCH}"
#TARGET_LD_ARCH_GVARIABLE := "${TARGET_LD_ARCH}"
#TARGET_AS_ARCH_GVARIABLE := "${TARGET_AS_ARCH}"

##################################################################
# Build flags and options.
##################################################################
#TARGET_CFLAGS_GVARIABLE := "${TARGET_CFLAGS}"
#TARGET_CPPFLAGS_GVARIABLE := "${TARGET_CPPFLAGS}"
#TARGET_CXXFLAGS_GVARIABLE := "${TARGET_CXXFLAGS}"
#TARGET_LDFLAGS_GVARIABLE := "${TARGET_LDFLAGS}"

#
# Now update HOST variables
#
HOST_ARCH = "${EXOTIC_TARGET_ARCH}"
HOST_OS = "${EXOTIC_TARGET_OS}"
HOST_VENDOR = "${EXOTIC_TARGET_VENDOR}"
HOST_SYS = "${EXOTIC_TARGET_SYS}"
HOST_PREFIX = "${EXOTIC_TARGET_PREFIX}"
HOST_CC_ARCH = "${EXOTIC_TARGET_CC_ARCH}"
HOST_LD_ARCH = "${EXOTIC_TARGET_LD_ARCH}"
HOST_AS_ARCH = "${EXOTIC_TARGET_AS_ARCH}"
HOST_EXEEXT = ""

##Moved to autotools_exotic
#TARGET_ARCH = "${EXOTIC_TARGET_ARCH}"
#TARGET_OS = "${EXOTIC_TARGET_OS}"
#TARGET_VENDOR = "${EXOTIC_TARGET_VENDOR}"
#TARGET_SYS = "${EXOTIC_TARGET_SYS}"
#TARGET_PREFIX = "${TARGET_SYS}-"
#TARGET_CC_ARCH = "${EXOTIC_TARGET_CC_ARCH}"
#TARGET_LD_ARCH = "${EXOTIC_TARGET_LD_ARCH}"
#TARGET_AS_ARCH = "${EXOTIC_TARGET_AS_ARCH}"

## moved to autotools_exotic
#TARGET_CFLAGS = 
#TARGET_CXXFLAGS = 
#TARGET_LDFLAGS = "${EXOTIC_TARGET_LDFLAGS}"

#
# Now all the scripts in this recipe can use TARGET_??
# or HOST_?? 
# or can use the original settings of TARGET_??_GVARIABLE and HOST_??_GVARIABLE
#
# For example override STAGING_BINDIR_TOOLCHAIN to match original TARGET
#
# STAGING_BINDIR_TOOLCHAIN = "${STAGING_DIR_NATIVE}${bindir_native}/${TARGET_ARCH_GVARIABLE}${TARGET_VENDOR_GVARIABLE}-${TARGET_OS_GVARIABLE}"
MULTIMACH_TARGET_SYS = "${PACKAGE_ARCH}${HOST_VENDOR}-${HOST_OS}"
MULTIMACH_HOST_SYS = "${PACKAGE_ARCH}${HOST_VENDOR}-${HOST_OS}"

# Move the Staging bin dir to a better location
STAGING_BINDIR_TOOLCHAIN = "${STAGING_DIR_NATIVE}${bindir_native}/${EXOTIC_TARGET_SYS}"

#
# Now the script
#

require epiphany-elf-gcc-${PV}.inc
require epiphany-elf-gcc-runtime.inc

## move the libraries to where the compiler looks for them
FILES_${PN}-dbg = "\
    ${libdir}/gcc/${EXOTIC_TARGET_SYS}/${BINV}/libstdc++.*-gdb.py \
    ${datadir}/gcc-${BINV}/python/libstdcxx \
"

FILES_${PN}-libstdc++ = "${libdir}/gcc/${EXOTIC_TARGET_SYS}/${BINV}/libstdc++.so.*"
FILES_${PN}-libstdc++-dev = "\
    ${includedir}/c++/ \
    ${libdir}/gcc/${EXOTIC_TARGET_SYS}/${BINV}/libstdc++.so \
    ${libdir}/gcc/${EXOTIC_TARGET_SYS}/${BINV}/libstdc++.la \
    ${libdir}/gcc/${EXOTIC_TARGET_SYS}/${BINV}/libsupc++.la \
"
FILES_${PN}-libstdc++-staticdev = "\
    ${libdir}/gcc/${EXOTIC_TARGET_SYS}/${BINV}/libstdc++.a \
    ${libdir}/gcc/${EXOTIC_TARGET_SYS}/${BINV}/libsupc++.a \
"

inherit nativesdk

deltask do_configure
deltask do_compile

DEPENDS = "${EXOTIC_TARGET_PREFIX}gcc-cross-canadian-${EXOTIC_TARGET_ARCH}"

do_install () {
	   # whilst not fool proof this is the best that can be done for now
	   mkdir -p ${D}${exec_prefix}/${EXOTIC_TARGET_SYS}/include
	   cp -r ${STAGING_DIR}/${MACHINE}/usr/${EXOTIC_TARGET_SYS}/include/c++ ${D}${exec_prefix}/${EXOTIC_TARGET_SYS}/include/

	   mkdir -p ${D}${exec_prefix}/lib/${EXOTIC_TARGET_SYS}/gcc/${EXOTIC_TARGET_SYS}/${BINV}/
	   cp ${STAGING_DIR}/${MACHINE}/usr/lib/${EXOTIC_TARGET_SYS}/libstdc* ${D}${exec_prefix}/lib/${EXOTIC_TARGET_SYS}/gcc/${EXOTIC_TARGET_SYS}/${BINV}/
	   cp ${STAGING_DIR}/${MACHINE}/usr/lib/${EXOTIC_TARGET_SYS}/libsupc* ${D}${exec_prefix}/lib/${EXOTIC_TARGET_SYS}/gcc/${EXOTIC_TARGET_SYS}/${BINV}/

	   mkdir -p ${D}${exec_prefix}/share/gcc-${BINV}/python
	   cp -r ${STAGING_DIR}/${MACHINE}/usr/share/gcc-${BINV}/python/libstdcxx ${D}${exec_prefix}/share/gcc-${BINV}/python/
}
