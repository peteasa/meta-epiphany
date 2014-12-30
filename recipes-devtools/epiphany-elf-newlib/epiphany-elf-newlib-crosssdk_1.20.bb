# Override the TARGET to be "epiphany"
HOST_SYS := "${TARGET_SYS}"
HOST_PREFIX := "${TARGET_PREFIX}"
TARGET_SYS = "epiphany-elf"
TARGET_PREFIX = "epiphany-elf-"

require epiphany-elf-newlib-cross_${PV}.bb

inherit crosssdk

PN = "epiphany-elf-newlib-crosssdk-${TARGET_ARCH}"

PROVIDES = "virtual/${TARGET_PREFIX}newlib-crosssdk"

do_configure_prepend () {
	sed -i 's#/usr/local/lib /lib /usr/lib#${SDKPATHNATIVE}/lib ${SDKPATHNATIVE}/usr/lib /usr/local/lib /lib /usr/lib#' ${S}/ld/configure.tgt
}
