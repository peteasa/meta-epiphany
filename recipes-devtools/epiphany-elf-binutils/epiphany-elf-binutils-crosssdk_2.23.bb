require epiphany-elf-binutils-cross_${PV}.bb

inherit crosssdk

PN = "epiphany-elf-binutils-elf-crosssdk-${TARGET_ARCH}"

PROVIDES = "virtual/${TARGET_PREFIX}binutils-crosssdk"

do_configure_prepend () {
	sed -i 's#/usr/local/lib /lib /usr/lib#${SDKPATHNATIVE}/lib ${SDKPATHNATIVE}/usr/lib /usr/local/lib /lib /usr/lib#' ${S}/ld/configure.tgt
}
