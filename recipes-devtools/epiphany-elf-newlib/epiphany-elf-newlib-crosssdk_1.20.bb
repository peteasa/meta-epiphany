
require epiphany-elf-newlib-cross_${PV}.bb

inherit crosssdk

PN = "epiphany-elf-newlib-crosssdk-${TARGET_ARCH}"

PROVIDES = "virtual/${TARGET_PREFIX}newlib-crosssdk"

do_configure_prepend () {
	sed -i 's#/usr/local/lib /lib /usr/lib#${SDKPATHNATIVE}/lib ${SDKPATHNATIVE}/usr/lib /usr/local/lib /lib /usr/lib#' ${S}/ld/configure.tgt
}
