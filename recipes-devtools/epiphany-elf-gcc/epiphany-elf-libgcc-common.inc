BPN = "libgcc"

INHIBIT_DEFAULT_DEPS = "1"

# Override bitbake.conf settings for CPP

TUNE_CCARGS=""

##oldincludedir="${exec_prefix}/${TARGET_SYS}/include"
##includedir="${exec_prefix}/${TARGET_SYS}/include"
# Update bitbake.conf setting - libdir="${exec_prefix}/${baselib}"
##libdir="${exec_prefix}/${baselib}/${TARGET_SYS}"
#   following seems to be auto added gcc/${TARGET_SYS}/${BINV}
# Update bitbake.conf setting - libexecdir = "${libdir}/${BPN}"
#   following seems to be auto added gcc/${TARGET_SYS}/${BINV}
##libexecdir="${exec_prefix}/${baselib}/${TARGET_SYS}"  
##gcclibdir = "${libdir}/gcc"

do_configure () {
	target=${TARGET_SYS}
	install -d ${D}${base_libdir} ${D}${libdir}

	hardlinkdir ${STAGING_INCDIR_NATIVE}/../${TARGET_SYS}/include/${EPIPHANYLIBGCCBUILDTREENAME}$target/ ${B}
	mkdir -p ${B}/${BPN}
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

	# unwind.h is installed here which is shipped in gcc-cross
	# as well as target gcc and they are identical so we dont
	# ship one with libgcc here
	rm -rf ${D}${libdir}/gcc/${TARGET_SYS}/${BINV}/include
}

BBCLASSEXTEND = "nativesdk"

