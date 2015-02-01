##
## modelled on combined version of gcc-configure-common.inc and gcc-target.inc
##          no need to have a separate newlib-configure-common.inc
##          also much simpler so significant parts taken from libgcc
##

# TODO double check the depends and provides.. epiphany-elf-gcc-runtime needs this
#      does virtual/${TARGET_PREFIX}gcc also need an initial version of this?

DEPENDS = "virtual/${TARGET_PREFIX}binutils virtual/${TARGET_PREFIX}gcc virtual/${TARGET_PREFIX}g++"

PACKAGES = "\
    ${PN} \
    ${PN}-dev \
    ${PN}-staticdev \
    ${PN}-dbg \
"

FILES_${PN} = " \
    ${prefix}/${TARGET_SYS}/include/* \
" 
FILES_${PN}-dev = "\
"
FILES_${PN}-dbg += "${prefix}/lib/${TARGET_SYS}/.debug/"
FILES_${PN}-staticdev = "	\
    /usr/lib/${TARGET_SYS}/* \
	"

NEWLIBBUILDTREENAME = "newlib-build-internal-"

do_package[depends] += "virtual/${MLPREFIX}libc:do_packagedata"
do_package_write_ipk[depends] += "virtual/${MLPREFIX}libc:do_packagedata"
do_package_write_deb[depends] += "virtual/${MLPREFIX}libc:do_packagedata"
do_package_write_rpm[depends] += "virtual/${MLPREFIX}libc:do_packagedata"

INSANE_SKIP_${PN}-staticdev = "arch"
INSANE_SKIP_${PN}-dbg += "arch"

## look at package_rpm.bbclass -
## need to override pkgarch = d.expand('${PACKAGE_ARCH_EXTEND}-poky-linux-gnueabi')
## at the moment it looks like pkgarch = d.expand('${PACKAGE_ARCH_EXTEND}-e-os')
HOST_OS="poky-linux-gnueabi"

BPN = "newlib"

##
## configure, compile and install are modelled on libgcc
##
do_configure () {
    target=${TARGET_SYS}
	install -d ${D}${libdir}

	mkdir -p ${B}/$target/${BPN}/
	cd ${B}/$target/${BPN}
	chmod a+x ${S}/${BPN}/configure
	${S}/${BPN}/configure ${CONFIGUREOPTS} ${EXTRA_OECONF}
}

do_compile () {
    target=${TARGET_SYS}
	cd ${B}/$target/${BPN}
	oe_runmake MULTIBUILDTOP=${B}/$target/${BPN}
}

do_install () {
    target=${TARGET_SYS}
	cd ${B}/$target/${BPN}
	oe_runmake 'DESTDIR=${D}' MULTIBUILDTOP=${B}/$target/ install

	# install the runtime in /usr/lib/epiphany-elf not in /usr/epiphany-elf/lib on target
    ## TODO add reason
    mkdir -p ${D}${libdir}
	mv ${D}/usr/$target/lib/* ${D}${libdir}

    # Tidy up remaining folders
    rm -rf ${D}/${prefix}/${TARGET_SYS}/lib	
    rm -rf ${D}/${prefix}/share
}

BBCLASSEXTEND = "nativesdk"
