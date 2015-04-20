##################################################################
#
# This recipe is required because nativesdk build is not needed
# The same files created during normal build can be copied
#
##################################################################


require epiphany-sdk_1.0.inc

inherit nativesdk

PN="nativesdk-epiphany-sdk"

PACKAGES = "\
    ${PN} \
"

FILES_${PN} += " \
    ${prefix}/epiphany-elf/lib/*.a \
    ${prefix}/lib/epiphany-elf \
"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

## Only need to provide libxx.so files for building in the sdk so ignore dependancies
SKIP_FILEDEPS_${PN} = "1"

do_configure () {
}
do_compile () {
}

do_install () {
	mkdir -p ${D}${prefix}/include
	mkdir -p ${D}${prefix}/epiphany-elf/include
	mkdir -p ${D}${prefix}/epiphany-elf/lib
	mkdir -p ${D}${prefix}/lib
	mkdir -p ${D}${prefix}/bin/epiphany-elf
	mkdir -p ${EPIPHANY_HOME}/bsps

	cp ${STAGING_DIR}/${MACHINE}/usr/include/epiphany-hal-api.h ${D}/${prefix}/include/
	cp ${STAGING_DIR}/${MACHINE}/usr/include/epiphany-hal.h ${D}/${prefix}/include/

	cd ${D}/${prefix}/include/
	ln -sf epiphany-hal.h e-hal.h

	cp ${STAGING_DIR}/${MACHINE}/usr/include/epiphany-hal-data.h ${D}/${prefix}/include/
	cp ${STAGING_DIR}/${MACHINE}/usr/include/epiphany-hal-data-local.h ${D}/${prefix}/include/
	# cp ${STAGING_DIR}/${MACHINE}/usr/include/epiphany-shm-manager.h ${D}/${prefix}/include/

	cp ${STAGING_DIR}/${MACHINE}/usr/epiphany-elf/include/e_lib.h ${D}/${prefix}/epiphany-elf/include/

	cd ${D}/${prefix}/epiphany-elf/include/
	ln -sf e_lib.h e-lib.h

	cp ${STAGING_DIR}/${MACHINE}/usr/epiphany-elf/include/e_common.h ${D}/${prefix}/epiphany-elf/include/
	cp ${STAGING_DIR}/${MACHINE}/usr/epiphany-elf/include/e_types.h ${D}/${prefix}/epiphany-elf/include/
	cp ${STAGING_DIR}/${MACHINE}/usr/epiphany-elf/include/e_regs.h ${D}/${prefix}/epiphany-elf/include/
	cp ${STAGING_DIR}/${MACHINE}/usr/epiphany-elf/include/e_dma.h ${D}/${prefix}/epiphany-elf/include/
	cp ${STAGING_DIR}/${MACHINE}/usr/epiphany-elf/include/e_ctimers.h ${D}/${prefix}/epiphany-elf/include/
	cp ${STAGING_DIR}/${MACHINE}/usr/epiphany-elf/include/e_ic.h ${D}/${prefix}/epiphany-elf/include/
	cp ${STAGING_DIR}/${MACHINE}/usr/epiphany-elf/include/e_mem.h ${D}/${prefix}/epiphany-elf/include/
	cp ${STAGING_DIR}/${MACHINE}/usr/epiphany-elf/include/e_mutex.h ${D}/${prefix}/epiphany-elf/include/
	cp ${STAGING_DIR}/${MACHINE}/usr/epiphany-elf/include/e_coreid.h ${D}/${prefix}/epiphany-elf/include/
	# cp ${STAGING_DIR}/${MACHINE}/usr/epiphany-elf/include/e_trace.h ${D}/${prefix}/epiphany-elf/include/
	# cp ${STAGING_DIR}/${MACHINE}/usr/epiphany-elf/include/e_shm.h ${D}/${prefix}/epiphany-elf/include/

	cp ${STAGING_DIR}/${MACHINE}/usr/include/e-loader.h ${D}/${prefix}/include/

	cd ${D}/${prefix}/include/
	ln -sf e-loader.h e_loader.h

	cp  ${STAGING_DIR}/${MACHINE}/usr/bin/e-objcopy ${D}/${prefix}/bin/
	#cp  ${STAGING_DIR}/${MACHINE}/usr/bin/e-hw-rev ${D}/${prefix}/bin/
	#cp  ${STAGING_DIR}/${MACHINE}/usr/bin/e-hw-rev.e ${D}/${prefix}/bin/
	#cp  ${STAGING_DIR}/${MACHINE}/usr/bin/e-loader ${D}/${prefix}/bin/
	#cp  ${STAGING_DIR}/${MACHINE}/usr/bin/e-loader.e ${D}/${prefix}/bin/
	#cp  ${STAGING_DIR}/${MACHINE}/usr/bin/e-read ${D}/${prefix}/bin/
	#cp  ${STAGING_DIR}/${MACHINE}/usr/bin/e-read.e ${D}/${prefix}/bin/
	#cp  ${STAGING_DIR}/${MACHINE}/usr/bin/e-reset ${D}/${prefix}/bin/
	#cp  ${STAGING_DIR}/${MACHINE}/usr/bin/e-reset.e ${D}/${prefix}/bin/
	#cp  ${STAGING_DIR}/${MACHINE}/usr/bin/e-write ${D}/${prefix}/bin/
	#cp  ${STAGING_DIR}/${MACHINE}/usr/bin/e-write.e ${D}/${prefix}/bin/

	## SDK seems to want additional links for various files so provide these here
	cp  /dev/null ${D}/${prefix}/bin/epiphany-elf/epiphany-elf-ar
	cp  /dev/null ${D}/${prefix}/bin/epiphany-elf/epiphany-elf-as
	cp  /dev/null ${D}/${prefix}/bin/epiphany-elf/epiphany-elf-gcc
	cp  /dev/null ${D}/${prefix}/bin/epiphany-elf/epiphany-elf-objcopy
	cd ${D}/${prefix}/bin
	ln -s epiphany-elf/epiphany-elf-ar e-ar
	ln -s epiphany-elf/epiphany-elf-as e-as
	ln -s epiphany-elf/epiphany-elf-gcc e-gcc
	ln -s epiphany-elf/epiphany-elf-objcopy epiphany-elf-objcopy
	rm -rf epiphany-elf

	##
	## Attempt to create a version of gcc that calls the correct cross compiler
	##
	cd ${D}/${prefix}/bin
	cat <<EOF > arm-linux-gnueabihf-gcc
#!/bin/sh

\${OECORE_NATIVE_SYSROOT}/usr/bin/arm-poky-linux-gnueabi/arm-poky-linux-gnueabi-gcc -march=armv7-a -mthumb-interwork -mfloat-abi=hard -mfpu=neon --sysroot=\${OECORE_TARGET_SYSROOT} "\$@"

EOF

	chmod +x arm-linux-gnueabihf-gcc

	cat <<EOF > gcc
#!/bin/sh

\${OECORE_NATIVE_SYSROOT}/usr/bin/arm-poky-linux-gnueabi/arm-poky-linux-gnueabi-gcc -march=armv7-a -mthumb-interwork -mfloat-abi=hard -mfpu=neon --sysroot=\${OECORE_TARGET_SYSROOT} "\$@"

EOF

	chmod +x gcc

	cp ${STAGING_DIR}/${MACHINE}/usr/lib/libe-xml.so ${D}/${prefix}/lib/
	cp ${STAGING_DIR}/${MACHINE}/usr/lib/libe-loader.so ${D}/${prefix}/lib/
	cp ${STAGING_DIR}/${MACHINE}/usr/lib/libe-hal.so ${D}/${prefix}/lib/

	cp ${STAGING_DIR}/${MACHINE}/usr/lib/epiphany-elf/libe-lib.a ${D}/${prefix}/epiphany-elf/lib

	cd ${D}/${prefix}/epiphany-elf/lib
	ln -sf libe-lib.a libelib.a

	## cp -r ${STAGING_DIR}/${MACHINE}/usr/epiphany/epiphany-sdk/bsps/p64v1521 ${EPIPHANY_HOME}/bsps
	## cp -r ${STAGING_DIR}/${MACHINE}/usr/epiphany/epiphany-sdk/bsps/parallella64 ${EPIPHANY_HOME}/bsps
	cp -r ${STAGING_DIR}/${MACHINE}/usr/epiphany/epiphany-sdk/bsps/parallella_E16G3_1GB ${EPIPHANY_HOME}/bsps
	cp -r ${STAGING_DIR}/${MACHINE}/usr/epiphany/epiphany-sdk/bsps/zed_E16G3_512mb ${EPIPHANY_HOME}/bsps
	cp -r ${STAGING_DIR}/${MACHINE}/usr/epiphany/epiphany-sdk/bsps/zed_E64G4_512mb ${EPIPHANY_HOME}/bsps

	cd ${EPIPHANY_HOME}/bsps/
	ln -sf parallella_E16G3_1GB current

}
