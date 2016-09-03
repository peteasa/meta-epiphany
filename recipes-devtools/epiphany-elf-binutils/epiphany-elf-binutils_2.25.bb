##################################################################
# Override variables with the remote target details

EXOTIC_TARGET_ARCH = "epiphany"
EXOTIC_TARGET_OS = "elf"
EXOTIC_TARGET_SYS = "epiphany-elf"

# update these as appropriate!
EXOTIC_TARGET_CFLAGS = "${TARGET_CFLAGS_GVARIABLE}"
EXOTIC_TARGET_CXXFLAGS = "${TARGET_CXXFLAGS_GVARIABLE}"
EXOTIC_TARGET_LDFLAGS = "${TARGET_LDFLAGS_GVARIABLE}" 

inherit exotic-set-paths-host-is-target

#
# Now all the scripts in this recipe can use TARGET_??
# or can use the original settings of TARGET_??_GVARIABLE
#

BASEDEPENDS = "virtual/${HOST_PREFIX_GVARIABLE}gcc virtual/${HOST_PREFIX_GVARIABLE}compilerlibs virtual/libc"

require epiphany-elf-binutils-${PV}.inc

#
# Now the script
#

inherit exotic-binutils
