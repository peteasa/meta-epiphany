##################################################################
#
# This recipe is for version esdk.5.13.09.10
# The recipe for version 2015.? will be very similar
#
##################################################################

require epiphany-sdk_1.0.inc

FILES_${PN} += " \
    ${prefix}/lib/epiphany-elf/*.a \
"

do_install () {

	mkdir -p ${D}${prefix}/include
	mkdir -p ${D}${prefix}/epiphany-elf/include
	mkdir -p ${D}${prefix}/lib/epiphany-elf
	mkdir -p ${D}${prefix}/bin
	mkdir -p ${EPIPHANY_HOME}/bsps
	mkdir -p ${EPIPHANY_HOME}/tools/host

	cp ${B}/src/e-hal/src/epiphany-hal-api.h ${D}/${prefix}/include/
	cp ${B}/src/e-hal/src/epiphany-hal.h ${D}/${prefix}/include/

	cd ${D}/${prefix}/include/
	ln -sf epiphany-hal.h e-hal.h

	cp ${B}/src/e-hal/src/epiphany-hal-data.h ${D}/${prefix}/include/
	cp ${B}/src/e-hal/src/epiphany-hal-data-local.h ${D}/${prefix}/include/

	# cp ${B}/src/e-hal/src/epiphany-shm-manager.h ${D}/${prefix}/include/

	cp ${B}/src/e-lib/include/e_lib.h ${D}/${prefix}/epiphany-elf/include/

	cd ${D}/${prefix}/epiphany-elf/include/
	ln -sf e_lib.h e-lib.h

	cp ${B}/src/e-lib/include/e_common.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/src/e-lib/include/e_types.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/src/e-lib/include/e_regs.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/src/e-lib/include/e_dma.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/src/e-lib/include/e_ctimers.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/src/e-lib/include/e_ic.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/src/e-lib/include/e_mem.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/src/e-lib/include/e_mutex.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/src/e-lib/include/e_coreid.h ${D}/${prefix}/epiphany-elf/include/
	# cp ${B}/src/e-lib/include/e_trace.h ${D}/${prefix}/epiphany-elf/include/
	# cp ${B}/src/e-lib/include/e_shm.h ${D}/${prefix}/epiphany-elf/include/

	cp ${B}/src/e-loader/src/e-loader.h ${D}/${prefix}/include/

	cd ${D}/${prefix}/include/
	ln -sf e-loader.h e_loader.h

	cp  ${B}/src/e-utils/e-objcopy ${D}/${prefix}/bin/
	cp  ${B}/src/e-utils/e-hw-rev/e-hw-rev.sh ${D}/${prefix}/bin/e-hw-rev
	cp  ${B}/src/e-utils/e-hw-rev/e-hw-rev ${D}/${prefix}/bin/e-hw-rev.e
	cp  ${B}/src/e-utils/e-loader/e-loader.sh ${D}/${prefix}/bin/e-loader
	cp  ${B}/src/e-utils/e-loader/Debug/e-loader ${D}/${prefix}/bin/e-loader.e
	cp  ${B}/src/e-utils/e-read/e-read.sh ${D}/${prefix}/bin/e-read
	cp  ${B}/src/e-utils/e-read/Debug/e-read ${D}/${prefix}/bin/e-read.e
	cp  ${B}/src/e-utils/e-reset/e-reset.sh ${D}/${prefix}/bin/e-reset
	cp  ${B}/src/e-utils/e-reset/e-reset ${D}/${prefix}/bin/e-reset.e
	cp  ${B}/src/e-utils/e-write/e-write.sh ${D}/${prefix}/bin/e-write
	cp  ${B}/src/e-utils/e-write/Debug/e-write ${D}/${prefix}/bin/e-write.e

	## SDK seems to want additional links for various files so provide these here
	cp  /dev/null ${D}/${prefix}/bin/epiphany-elf-ar
	cp  /dev/null ${D}/${prefix}/bin/epiphany-elf-as
	cp  /dev/null ${D}/${prefix}/bin/epiphany-elf-gcc
	cd ${D}/${prefix}/bin
	ln -s epiphany-elf-ar e-ar
	ln -s epiphany-elf-as e-as
	ln -s epiphany-elf-gcc e-gcc
	rm -f epiphany-elf-*

	cp ${B}/src/e-xml/Release/libe-xml.so ${D}/${prefix}/lib/

	cp ${B}/src/e-loader/Release/libe-loader.so ${D}/${prefix}/lib/

	cp ${B}/src/e-hal/Release/libe-hal.so ${D}/${prefix}/lib/

	cp ${B}/src/e-lib/Release/libe-lib.a ${D}/${prefix}/lib/epiphany-elf/

	cd ${D}/${prefix}/lib/epiphany-elf/
	ln -sf libe-lib.a libelib.a

	# cp -r ${B}/bsps/p64v1521 ${EPIPHANY_HOME}/bsps
	# cp -r ${B}/bsps/parallella64 ${EPIPHANY_HOME}/bsps
	cp -r ${B}/bsps/parallella_E16G3_1GB ${EPIPHANY_HOME}/bsps
	cp -r ${B}/bsps/zed_E16G3_512mb ${EPIPHANY_HOME}/bsps
	cp -r ${B}/bsps/zed_E64G4_512mb ${EPIPHANY_HOME}/bsps

	# cp  ${B}/src/e-hal/Release/libe-hal.so ${EPIPHANY_HOME}/bsps/p64v1521 
	# cp  ${B}/src/e-hal/Release/libe-hal.so ${EPIPHANY_HOME}/bsps/parallella64 
	cp  ${B}/src/e-hal/Release/libe-hal.so ${EPIPHANY_HOME}/bsps/parallella_E16G3_1GB 
	cp  ${B}/src/e-hal/Release/libe-hal.so ${EPIPHANY_HOME}/bsps/zed_E16G3_512mb 
	cp  ${B}/src/e-hal/Release/libe-hal.so ${EPIPHANY_HOME}/bsps/zed_E64G4_512mb

	cd ${EPIPHANY_HOME}/tools/host/
	ln -sf ../../../../include ./



	cd ${EPIPHANY_HOME}/bsps/
	ln -sf parallella_E16G3_1GB current

}

# Now add the ${D}${prefix}/epiphany-elf ${D}${prefix}/bin ${D}${prefix}/epiphany directory to the sysroot staging folder list
SYSROOT_PREPROCESS_FUNCS += "epiphany_sdk_sysroot_preprocess"
epiphany_sdk_sysroot_preprocess () {
	sysroot_stage_dir ${D}${prefix}/epiphany-elf ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf
	sysroot_stage_dir ${D}${prefix}/bin ${STAGING_DIR_TARGET}${prefix_native}/bin
	sysroot_stage_dir ${D}${prefix}/epiphany ${STAGING_DIR_TARGET}${prefix_native}/epiphany
}

# Now ensure that the directory is correctly cleaned up
CLEANFUNCS += "epiphany_sdk_sstate_clean"
epiphany_sdk_sstate_clean () {
	rm ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_common.h
	rm ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_coreid.h
	rm ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_ctimers.h
	rm ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_dma.h
	rm ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_ic.h
	rm ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_lib.h
	rm ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e-lib.h
	rm ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_mem.h
	rm ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_mutex.h
	rm ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_regs.h
	rm ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_types.h
	rm ${STAGING_DIR_TARGET}${prefix_native}/bin/e-*
	rm -rf ${STAGING_DIR_TARGET}${prefix_native}/epiphany
}
