# Override the TARGET to be "epiphany"
HOST_SYS := "${TARGET_SYS}"
HOST_PREFIX := "${TARGET_PREFIX}"
TARGET_SYS = "epiphany-elf"
TARGET_PREFIX = "epiphany-elf-"

require epiphany-elf-gcc-${PV}.inc
require epiphany-elf-gcc-cross-canadian.inc



