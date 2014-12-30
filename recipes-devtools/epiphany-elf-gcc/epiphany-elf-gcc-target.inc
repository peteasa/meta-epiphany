
require epiphany-elf-gcc-configure-common.inc

# Add paths of HOST and TARGET compilers
PATH_prepend = "${STAGING_BINDIR_TOOLCHAIN}:${STAGING_DIR_NATIVE}${bindir_native}/${TARGET_ARCH}${TARGET_VENDOR}-${TARGET_OS}:"

EXTRA_OECONF_PATHS = "\
    --with-build-sysroot=${STAGING_DIR_TARGET} \
    --with-native-system-header-dir=${STAGING_DIR_TARGET}${target_includedir} \
    --with-gxx-include-dir=${includedir}/c++/${BINV} \
"
#oops something gone wrong -  epiphany-elf-cpp-symlinks
PACKAGES = "\
    ${PN} ${PN}-plugins ${PN}-symlinks \
    epiphany-elf-g++ epiphany-elf-g++-symlinks \
    epiphany-elf-cpp  \
    ${PN}-plugin-dev \
    ${PN}-doc \
    ${PN}-dev \
    ${PN}-dbg \
"

FILES_${PN} = "\
    ${bindir}/${TARGET_PREFIX}gcov \
    ${bindir}/${TARGET_PREFIX}gcc* \
    ${libexecdir}/gcc/${TARGET_SYS}/${BINV}/collect2 \
    ${libexecdir}/gcc/${TARGET_SYS}/${BINV}/cc* \
    ${libexecdir}/gcc/${TARGET_SYS}/${BINV}/lto* \
    ${libexecdir}/gcc/${TARGET_SYS}/${BINV}/lib*${SOLIBS} \
    ${libexecdir}/gcc/${TARGET_SYS}/${BINV}/liblto*${SOLIBSDEV} \
    ${gcclibdir}/${TARGET_SYS}/${BINV}/*.o \
    ${gcclibdir}/${TARGET_SYS}/${BINV}/specs \
    ${gcclibdir}/${TARGET_SYS}/${BINV}/lib*${SOLIBS} \
    ${gcclibdir}/${TARGET_SYS}/${BINV}/include \
    ${gcclibdir}/${TARGET_SYS}/${BINV}/include-fixed \
"
INSANE_SKIP_${PN} += "dev-so"

FILES_${PN}-dbg += "\
    ${libexecdir}/gcc/${TARGET_SYS}/${BINV}/.debug/ \
    ${libexecdir}/gcc/${TARGET_SYS}/${BINV}/plugin/.debug/ \
"
#oops something gone wrong - nothing in this group
FILES_${PN}-dev = "\
    ${gcclibdir}/${TARGET_SYS}/${BINV}/lib*${SOLIBSDEV} \
    ${libexecdir}/gcc/${TARGET_SYS}/${BINV}/lib*${SOLIBSDEV} \
"
FILES_${PN}-plugin-dev = "\
    ${gcclibdir}/${TARGET_SYS}/${BINV}/plugin/include/ \
    ${libexecdir}/gcc/${TARGET_SYS}/${BINV}/plugin/gengtype \
    ${gcclibdir}/${TARGET_SYS}/${BINV}/plugin/gtype.state \
"
#oops something gone wrong -    ${bindir}/epiphany-elf-gccbug 
FILES_${PN}-symlinks = "\
    ${bindir}/epiphany-elf-gcc \
    ${bindir}/epiphany-elf-cc \
"

FILES_${PN}-plugins = "\
    ${gcclibdir}/${TARGET_SYS}/${BINV}/plugin \
"
ALLOW_EMPTY_${PN}-plugins = "1"

#oops something gone wrong -   ${base_libdir}/epiphany-elf-cpp 
FILES_epiphany-elf-cpp = "\
    ${bindir}/${TARGET_PREFIX}cpp \
    ${libexecdir}/${TARGET_SYS}/gcc/${TARGET_SYS}/${BINV}/cc1 \
"
#oops something gone wrong -   FILES_epiphany-elf-cpp-symlinks = "${bindir}/epiphany-elf-cpp"

FILES_epiphany-elf-gcov = "${bindir}/${TARGET_PREFIX}gcov"
FILES_epiphany-elf-gcov-symlinks = "${bindir}/gcov"

FILES_epiphany-elf-g++ = "\
    ${bindir}/${TARGET_PREFIX}g++ \
    ${libexecdir}/${TARGET_SYS}/gcc/${TARGET_SYS}/${BINV}/cc1plus \
"
#oops something gone wrong -    ${bindir}/epiphany-elf-g++ 
FILES_epiphany-elf-g++-symlinks = "\
    ${bindir}/epiphany-elf-c++ \
"

FILES_${PN}-doc = "\
    ${infodir} \
    ${mandir} \
    ${gcclibdir}/${TARGET_SYS}/${BINV}/include/README \
"

do_compile () {
	oe_runmake all-host
}

do_install () {
	oe_runmake 'DESTDIR=${D}' install-host

	# Add unwind.h, it comes from libgcc which we don't want to build again
	install ${STAGING_LIBDIR_NATIVE}/${TARGET_SYS}/gcc/${TARGET_SYS}/${BINV}/include/unwind.h ${D}${libdir}/gcc/${TARGET_SYS}/${BINV}/include/

	# Info dir listing isn't interesting at this point so remove it if it exists.
	if [ -e "${D}${infodir}/dir" ]; then
		rm -f ${D}${infodir}/dir
	fi

	# Cleanup some of the ${libdir}{,exec}/epiphany-elf-gcc stuff ...
	rm -r ${D}${libdir}/gcc/${TARGET_SYS}/${BINV}/install-tools
	rm -r ${D}${libexecdir}/gcc/${TARGET_SYS}/${BINV}/install-tools
	rm -rf ${D}${libexecdir}/gcc/${TARGET_SYS}/${BINV}/*.la
	rmdir ${D}${includedir}
	rm -rf ${D}${libdir}/gcc/${TARGET_SYS}/${BINV}/finclude

	# Hack around specs file assumptions
	test -f ${D}${libdir}/gcc/${TARGET_SYS}/${BINV}/specs && sed -i -e '/^*cross_compile:$/ { n; s/1/0/; }' ${D}${libdir}/gcc/${TARGET_SYS}/${BINV}/specs

	# Cleanup manpages..
	rm -rf ${D}${mandir}/man7

	cd ${D}${bindir}

	# We care about g++ not c++
	rm -f *c++

	# We don't care about the gcc-<version> ones for this
	rm -f *gcc-?.?*

	# We use libiberty from binutils
	#find ${D}/usr/${TARGET_SYS} -name libiberty.a | xargs rm -f
	find ${D}/usr/${TARGET_SYS} -name libiberty.h | xargs rm -f
    rm -rf ${D}/usr/${TARGET_SYS} 

	# Not sure why we end up with these but we don't want them...
	rm -f ${TARGET_PREFIX}${TARGET_PREFIX}*

	ln -sf epiphany-elf-g++ epiphany-elf-c++
	ln -sf epiphany-elf-gcc epiphany-elf-cc

	chown -R root:root ${D}
}
