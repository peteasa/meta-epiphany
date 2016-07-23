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

	cp ${B}/e-hal/src/epiphany-hal-api.h ${D}/${prefix}/include/
	cp ${B}/e-hal/src/epiphany-hal.h ${D}/${prefix}/include/

	cd ${D}/${prefix}/include/
	ln -sf epiphany-hal.h e-hal.h

	cp ${B}/e-hal/src/epiphany-hal-data.h ${D}/${prefix}/include/
	cp ${B}/e-hal/src/epiphany-hal-data-local.h ${D}/${prefix}/include/
	cp ${B}/e-hal/src/epiphany-shm-manager.h ${D}/${prefix}/include/

	cp ${B}/e-lib/include/e_lib.h ${D}/${prefix}/epiphany-elf/include/

	cd ${D}/${prefix}/epiphany-elf/include/
	ln -sf e_lib.h e-lib.h
	
	cp  ${B}/e-trace/include/a_trace.h ${D}/${prefix}/include/
	cp  ${B}/e-trace/include/e-trace.h ${D}/${prefix}/include/
	cp  ${B}/e-trace/include/e_trace_shared.h ${D}/${prefix}/include/

	cp ${B}/e-lib/include/e_common.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/e-lib/include/e_coreid.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/e-lib/include/e_ctimers.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/e-lib/include/e_dma.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/e-lib/include/e_ic.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/e-lib/include/e_mem.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/e-lib/include/e_mutex.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/e-lib/include/e_regs.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/e-lib/include/e_shm.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/e-lib/include/e_trace.h ${D}/${prefix}/epiphany-elf/include/
	cp ${B}/e-lib/include/e_types.h ${D}/${prefix}/epiphany-elf/include/

	cp ${B}/e-hal/src/e-loader.h ${D}/${prefix}/include/

	cd ${D}/${prefix}/include/
	ln -sf e-loader.h e_loader.h

	cp  ${B}/e-utils/e-clear-shmtable/Debug/e-clear-shmtable ${D}/${prefix}/bin/
	cp  ${B}/e-utils/e-dump-regs/Debug/e-dump-regs ${D}/${prefix}/bin/
	cp  ${B}/e-utils/e-hw-rev/Debug/e-hw-rev ${D}/${prefix}/bin/
	cp  ${B}/e-utils/e-loader/Debug/e-loader ${D}/${prefix}/bin/
	cp  ${B}/e-utils/e-meshdump/Debug/e-meshdump ${D}/${prefix}/bin/
	cp  ${B}/e-utils/e-read/Debug/e-read ${D}/${prefix}/bin/
	cp  ${B}/e-utils/e-reset/Debug/e-reset ${D}/${prefix}/bin/
	cp  ${B}/e-utils/e-write/Debug/e-write ${D}/${prefix}/bin/

	## SDK seems to want additional links for various files so provide these here
	cp  /dev/null ${D}/${prefix}/bin/epiphany-elf-ar
	cp  /dev/null ${D}/${prefix}/bin/epiphany-elf-as
	cp  /dev/null ${D}/${prefix}/bin/epiphany-elf-gcc
	cd ${D}/${prefix}/bin
	ln -s epiphany-elf-ar e-ar
	ln -s epiphany-elf-as e-as
	ln -s epiphany-elf-gcc e-gcc
	rm -f epiphany-elf-*

	cp ${B}/e-xml/Release/libe-xml.so ${D}/${prefix}/lib/
	cp ${B}/e-loader/Release/libe-loader.so ${D}/${prefix}/lib/
	cp ${B}/e-hal/Release/libe-hal.so ${D}/${prefix}/lib/
	cp ${B}/e-lib/Release/libe-lib.a ${D}/${prefix}/lib/epiphany-elf/

	cd ${D}/${prefix}/lib/epiphany-elf/
	ln -sf libe-lib.a libelib.a

	# cp -r ${B}/bsps/p64v1521 ${EPIPHANY_HOME}/bsps
	# cp -r ${B}/bsps/parallella64 ${EPIPHANY_HOME}/bsps
	cp -r ${B}/bsps/parallella_E16G3_1GB ${EPIPHANY_HOME}/bsps
	# cp -r ${B}/bsps/zed_E16G3_512mb ${EPIPHANY_HOME}/bsps
	# cp -r ${B}/bsps/zed_E64G4_512mb ${EPIPHANY_HOME}/bsps

	# cp  ${B}/e-hal/Release/libe-hal.so ${EPIPHANY_HOME}/bsps/p64v1521 
	# cp  ${B}/e-hal/Release/libe-hal.so ${EPIPHANY_HOME}/bsps/parallella64 
	cp  ${B}/e-hal/Release/libe-hal.so ${EPIPHANY_HOME}/bsps/parallella_E16G3_1GB 
	# cp  ${B}/e-hal/Release/libe-hal.so ${EPIPHANY_HOME}/bsps/zed_E16G3_512mb 
	# cp  ${B}/e-hal/Release/libe-hal.so ${EPIPHANY_HOME}/bsps/zed_E64G4_512mb

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
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/bin/e-*
	rm -rf ${STAGING_DIR_TARGET}${prefix_native}/epiphany
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_common.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_coreid.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_ctimers.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_dma.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_ic.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_lib.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e-lib.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_mem.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_mutex.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_regs.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_shm.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_trace.h	
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/epiphany-elf/include/e_types.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/include/epiphany-hal.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/include/epiphany-hal-api.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/include/epiphany-hal-data.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/include/epiphany-hal-data-local.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/include/epiphany-shm-manager.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/include/a_trace.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/include/e-trace.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/include/e_trace_shared.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/include/e-hal.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/include/e-loader.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/include/e_loader.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/include/memman.h
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/lib/epiphany-elf/libelib.a
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/lib/epiphany-elf/libe-liba
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/lib/libe-hal.so
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/lib/libe-loader.so
	rm -f ${STAGING_DIR_TARGET}${prefix_native}/lib/libe-xml.so
}
