##
## modelled on libgcc-common.inc
##

BPN = "libgloss"

INHIBIT_DEFAULT_DEPS = "1"

# Override bitbake.conf settings 

TUNE_CCARGS=""

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
	oe_runmake MULTIBUILDTOP=${B}/$target/${BPN}/
}

do_install () {
	target=${TARGET_SYS}
	cd ${B}/$target/${BPN}
	oe_runmake 'DESTDIR=${D}' MULTIBUILDTOP=${B}/$target/${BPN}/ install

	# install the runtime in /usr/lib/epiphany-elf not in /usr/epiphany-elf/lib on target
    ## TODO add reason
    mkdir -p ${D}${libdir}
	mv ${D}/usr/$target/lib/* ${D}${libdir}

    # Tidy up remaining folders
    rm -rf ${D}/${prefix}/${TARGET_SYS}
}

BBCLASSEXTEND = "nativesdk"
