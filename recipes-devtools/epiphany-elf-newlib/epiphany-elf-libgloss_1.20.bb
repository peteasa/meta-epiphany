 
# For these recipes we may need to override TARGET and HOST whilst keeping 
# variables derived from the original TARGET and HOST unchanged.
# To do this create new variables to store the original TARGET and HOST
#
# Note that it would be better not to use TARGET_?? and HOST_?? in recipes lower
# in the hierarchy but rather local variables that are passed to the lower
# layers, but for now hack it like this to simulate the proposed future
#
# TODO save other bitbake.conf variables as appropriate
HOST_ARCH_GVARIABLE := "${HOST_ARCH}"
HOST_OS_GVARIABLE := "${HOST_OS}"
HOST_VENDOR_GVARIABLE := "${HOST_VENDOR}"
HOST_SYS_GVARIABLE := "${HOST_SYS}"
HOST_PREFIX_GVARIABLE := "${HOST_PREFIX}"
HOST_CC_ARCH_GVARIABLE := "${HOST_CC_ARCH}"
HOST_LD_ARCH_GVARIABLE := "${HOST_LD_ARCH}"
HOST_AS_ARCH_GVARIABLE := "${HOST_AS}"
HOST_EXEEXT_GVARIABLE := "${HOST_EXEEXT}"

TARGET_ARCH_GVARIABLE := "${TARGET_ARCH}"
TARGET_OS_GVARIABLE := "${TARGET_OS}"
TARGET_VENDOR_GVARIABLE := "${TARGET_VENDOR}"
TARGET_SYS_GVARIABLE := "${TARGET_SYS}"
TARGET_PREFIX_GVARIABLE := "${TARGET_PREFIX}"
TARGET_CC_ARCH_GVARIABLE := "${TARGET_CC_ARCH}"
TARGET_LD_ARCH_GVARIABLE := "${TARGET_LD_ARCH}"
TARGET_AS_ARCH_GVARIABLE := "${TARGET_AS}"
TARGET_EXEEXT_GVARIABLE := "${TARGET_EXEEXT}"

#
# Now update HOST and TARGET variables
#
HOST_ARCH = "${TARGET_ARCH}"
HOST_OS = "${TARGET_OS}"
HOST_VENDOR = "${TARGET_VENDOR}"
HOST_SYS = "${TARGET_SYS}"
HOST_PREFIX = "${TARGET_PREFIX}"
HOST_CC_ARCH = "${TARGET_CC_ARCH}"
HOST_LD_ARCH = "${TARGET_LD_ARCH}"
HOST_AS_ARCH = "${TARGET_AS_ARCH}"
HOST_EXEEXT = "${TARGET_EXEEXT}"
TARGET_ARCH = "epiphany"
TARGET_OS = "e-os"
TARGET_VENDOR = ""
TARGET_SYS = "epiphany-elf"
TARGET_PREFIX = "${TARGET_SYS}-"
TARGET_CC_ARCH = ""
TARGET_LD_ARCH = ""
TARGET_AS_ARCH = ""
TARGET_EXEEXT = ""

# TODO update these as appropriate!
#TARGET_CFLAGS = 
#TARGET_CXXFLAGS = 
TARGET_LDFLAGS = "-Wl,-O1 -Wl, -Wl,--as-needed"

#
# Now all the scripts in this recipe can use TARGET_??
# or HOST_?? 
# or can use the original settings of TARGET_??_GVARIABLE and HOST_??_GVARIABLE
#
# For example override STAGING_BINDIR_TOOLCHAIN to match original TARGET
#
STAGING_BINDIR_TOOLCHAIN = "${STAGING_DIR_NATIVE}${bindir_native}/${TARGET_SYS}"
MULTIMACH_TARGET_SYS = "${PACKAGE_ARCH}${TARGET_VENDOR_GVARIABLE}-${TARGET_OS_GVARIABLE}"
MULTIMACH_HOST_SYS = "${PACKAGE_ARCH}${HOST_VENDOR_GVARIABLE}-${HOST_OS_GVARIABLE}"

#
# Now the script
#
require epiphany-elf-newlib-${PV}.inc
require epiphany-elf-libgloss.inc
