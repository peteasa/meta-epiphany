require epiphany-elf-binutils-cross_${PV}.bb

inherit crosssdk

PN = "epiphany-elf-binutils-elf-crosssdk-${CROSS_TARGET_ARCH_GVARIABLE}"

PROVIDES = "virtual/${CROSS_TARGET_PREFIX_GVARIABLE}binutils-crosssdk"

do_configure_prepend () {
	sed -i 's#/usr/local/lib /lib /usr/lib#${SDKPATHNATIVE}/lib ${SDKPATHNATIVE}/usr/lib /usr/local/lib /lib /usr/lib#' ${S}/ld/configure.tgt
}
