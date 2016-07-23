##################################################################
# Override variables with the remote target details

EXOTIC_TARGET_ARCH = "epiphany"
EXOTIC_TARGET_OS = "elf"
EXOTIC_TARGET_SYS = "epiphany-elf"

# update these as appropriate!
EXOTIC_TARGET_CFLAGS = "${TARGET_CFLAGS_GVARIABLE}"
EXOTIC_TARGET_CXXFLAGS = "${TARGET_CXXFLAGS_GVARIABLE}"
EXOTIC_TARGET_CPPFLAGS = "${TARGET_CPPFLAGS_GVARIABLE}"
EXOTIC_TARGET_LDFLAGS = "${TARGET_LDFLAGS_GVARIABLE}" 

##################################################################
# Part two of this refactoring will make this file an append to
# the exotic-binutils_2.23.bb file and the following will be
# the content of that file!
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
## NOTE if HOST_SYS_GVARIABLE := "${HOST_SYS}" => arm-poky-linux-gnueabi
##      if HOST_SYS_GVARIABLE = "${HOST_SYS}" => x86_64-linux
HOST_ARCH_GVARIABLE = "${HOST_ARCH}"
HOST_OS_GVARIABLE = "${HOST_OS}"
HOST_VENDOR_GVARIABLE = "${HOST_VENDOR}"
HOST_SYS_GVARIABLE = "${HOST_SYS}"
HOST_PREFIX_GVARIABLE = "${HOST_PREFIX}"
HOST_CC_ARCH_GVARIABLE = "${HOST_CC_ARCH}"
HOST_LD_ARCH_GVARIABLE = "${HOST_LD_ARCH}"
HOST_AS_ARCH_GVARIABLE = "${HOST_AS}"
HOST_EXEEXT_GVARIABLE = "${HOST_EXEEXT}"

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
# Now update TARGET variables
#
#HOST_ARCH = "${HOST_ARCH_GVARIABLE}"
#HOST_OS = "${HOST_OS_GVARIABLE}"
#HOST_VENDOR = "${HOST_VENDOR_GVARIABLE}"
#HOST_SYS = "${HOST_SYS_GVARIABLE}"
#HOST_PREFIX = "${HOST_PREFIX_GVARIABLE}"
#HOST_CC_ARCH = "${HOST_CC_ARCH_GVARIABLE}"
#HOST_LD_ARCH = "${HOST_LD_ARCH_GVARIABLE}"
#HOST_AS_ARCH = "${HOST_AS_ARCH_GVARIABLE}"
#HOST_EXEEXT = "${HOST_EXEEXT_GVARIABLE}"

## moved to autotools_exotic.bbclass
#TARGET_ARCH = "${EXOTIC_TARGET_ARCH}"
#TARGET_OS = "${EXOTIC_TARGET_OS}"
#TARGET_VENDOR = "${EXOTIC_TARGET_VENDOR}"
#TARGET_SYS = "${EXOTIC_TARGET_SYS}"
#TARGET_PREFIX = "${TARGET_SYS}-"
#TARGET_CC_ARCH = "${EXOTIC_TARGET_CC_ARCH}"
#TARGET_LD_ARCH = "${EXOTIC_TARGET_LD_ARCH}"
#TARGET_AS_ARCH = "${EXOTIC_TARGET_AS_ARCH}"

#
# Now all the scripts in this recipe can use TARGET_??
# or HOST_?? 
# or can use the original settings of TARGET_??_GVARIABLE and HOST_??_GVARIABLE
#
# For example override STAGING_BINDIR_TOOLCHAIN to match original TARGET
#
STAGING_BINDIR_TOOLCHAIN = "${STAGING_DIR_NATIVE}${bindir_native}/${TARGET_ARCH_GVARIABLE}${TARGET_VENDOR_GVARIABLE}-${TARGET_OS_GVARIABLE}"

#
# Now the script
#

require epiphany-elf-binutils.inc
require epiphany-elf-binutils-${PV}.inc
require epiphany-elf-binutils-cross.inc
