# Override the TARGET to be "epiphany"
HOST_SYS := "${TARGET_SYS}"
HOST_PREFIX := "${TARGET_PREFIX}"
TARGET_SYS = "epiphany-elf"
TARGET_PREFIX = "epiphany-elf-"

BASEDEPENDS := "virtual/${HOST_PREFIX}gcc \
            virtual/${TARGET_PREFIX}newlib \
            virtual/${TARGET_PREFIX}binutils"

require epiphany-elf-gcc-${PV}.inc
require epiphany-elf-gcc-target.inc
