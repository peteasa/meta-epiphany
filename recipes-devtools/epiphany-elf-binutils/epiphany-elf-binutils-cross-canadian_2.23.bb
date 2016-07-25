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

inherit exotic-set-cross-canadian-paths

#
# Now all the scripts in this recipe can use TARGET_??
# or HOST_?? 
# or can use the original settings of TARGET_??_GVARIABLE and HOST_??_GVARIABLE
#

require epiphany-elf-binutils-${PV}.inc

##################################################################
# Part two of this refactoring will make this file an append to
# the exotic-binutils-cross-canadian_2.23.bb file and the following will be
# the content of that file!
##################################################################

#
# Now the script
#

require epiphany-elf-binutils.inc
require epiphany-elf-binutils-cross-canadian.inc

#
# Now update DEPENDS to ensure that the correct libraries get built
#

DEPENDS += " nativesdk-${EXOTIC_TARGET_PREFIX}newlib nativesdk-${EXOTIC_TARGET_PREFIX}libgloss "
RDEPENDS_${PN} += "\
       nativesdk-${EXOTIC_TARGET_PREFIX}newlib \
       nativesdk-${EXOTIC_TARGET_PREFIX}newlib-staticdev \
       nativesdk-${EXOTIC_TARGET_PREFIX}newlib-dev \
       nativesdk-${EXOTIC_TARGET_PREFIX}newlib-dbg \
       nativesdk-${EXOTIC_TARGET_PREFIX}libgloss \
       nativesdk-${EXOTIC_TARGET_PREFIX}libgloss-staticdev \
       nativesdk-${EXOTIC_TARGET_PREFIX}libgloss-dev \
       nativesdk-${EXOTIC_TARGET_PREFIX}libgloss-dbg \
       "
