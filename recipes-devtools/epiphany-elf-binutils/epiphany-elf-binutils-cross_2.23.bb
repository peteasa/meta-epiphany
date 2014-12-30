# TODO bit of a hack here just to get it working for now
STAGING_BINDIR_TOOLCHAIN = "${STAGING_DIR_NATIVE}${bindir_native}/${TARGET_SYS}:${STAGING_DIR_NATIVE}${bindir_native}/${TARGET_SYS}"

require epiphany-elf-binutils.inc
require epiphany-elf-binutils-${PV}.inc
require epiphany-elf-binutils-cross.inc
