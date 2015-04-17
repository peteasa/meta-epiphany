##################################################################
#
# This recipe is for version esdk.5.13.09.10
# The recipe for version 2015.? will be very similar
#
##################################################################

DESCRIPTION = "Epiphany SDK Build and Install Package"
HOMEPAGE = "http://www.adapteva.com/"
LICENSE = "GPLv3"

LIC_FILES_CHKSUM = "\
    file://COPYING;md5=d32239bcb673463ab874e80d47fae504 \
"

## Choosing a different name to that used in meta-parallella
## In this recipe we build from source, meta-parallella downloads zip.
PN = "epiphany-sdk"

DEPENDS =+ "virtual/epiphany-elf-gcc epiphany-elf-newlib epiphany-elf-libgcc"

## Define the source directory to help locate the LICENSE file
S = "${WORKDIR}/git"

SRC_URI = " git://github.com/adapteva/epiphany-libs.git;"

## Add the patch file
FILESEXTRAPATHS =. "${FILE_DIRNAME}/files:"
SRC_URI += " \
    file://epiphany-libs-esdk.5.13.09.10.patch \
"

# Tag esdk.5.13.09.10
SRCREV = "544f387c2f7be4de83399daeb86d0f3e35ecdf45"

BBCLASSEXTEND = "nativesdk"

## Setup cross compiler for e-lib perhaps override things from nativesdk.bbclass
export EPIPHANY_CROSS_COMPILE="${STAGING_DIR_NATIVE}/usr/bin/epiphany-elf/epiphany-elf-"
export PARALLELLA_LINUX_HOME="${STAGING_DIR}/${MACHINE}/usr/src/kernel"
export EPIPHANY_ELF_INCLUDE="${STAGING_DIR}/${MACHINE}/usr/epiphany-elf/include"
EPIPHANY_HOME="${D}${prefix}/epiphany/epiphany-sdk"

## Switch off parallel build so that utils/e-loader waits for e-loader build
PARALLEL_MAKE = ""

PACKAGES = "\
    ${PN} \
    ${PN}-dbg \
"

FILES_${PN} = " \
    ${prefix}/bin/* \
    ${prefix}/epiphany/epiphany-sdk/bsps/current \
    ${prefix}/epiphany/epiphany-sdk/bsps/parallella_E16G3_1GB/*.ldf \
    ${prefix}/epiphany/epiphany-sdk/bsps/parallella_E16G3_1GB/*.so \
    ${prefix}/epiphany/epiphany-sdk/bsps/parallella_E16G3_1GB/*.pdf \
    ${prefix}/epiphany/epiphany-sdk/bsps/parallella_E16G3_1GB/*.hdf \
    ${prefix}/epiphany/epiphany-sdk/bsps/parallella_E16G3_1GB/*.xml \
    ${prefix}/epiphany/epiphany-sdk/bsps/zed_E16G3_512mb/*.ldf \
    ${prefix}/epiphany/epiphany-sdk/bsps/zed_E16G3_512mb/*.so \
    ${prefix}/epiphany/epiphany-sdk/bsps/zed_E16G3_512mb/*.pdf \
    ${prefix}/epiphany/epiphany-sdk/bsps/zed_E16G3_512mb/*.hdf \
    ${prefix}/epiphany/epiphany-sdk/bsps/zed_E16G3_512mb/*.xml \
    ${prefix}/epiphany/epiphany-sdk/bsps/zed_E64G4_512mb/*.ldf \
    ${prefix}/epiphany/epiphany-sdk/bsps/zed_E64G4_512mb/*.so \
    ${prefix}/epiphany/epiphany-sdk/bsps/zed_E64G4_512mb/*.pdf \
    ${prefix}/epiphany/epiphany-sdk/bsps/zed_E64G4_512mb/*.hdf \
    ${prefix}/epiphany/epiphany-sdk/bsps/zed_E64G4_512mb/*.xml \
    ${prefix}/include/epiphany-hal-api.h \
    ${prefix}/include/epiphany-hal-data.h \
    ${prefix}/include/epiphany-hal-data-local.h \
    ${prefix}/include/e-loader.h \
    ${prefix}/include/epiphany-hal.h \
    ${prefix}/include/e-hal.h \
    ${prefix}/include/e_loader.h \
    ${prefix}/epiphany-elf/include/e_common.h \
    ${prefix}/epiphany-elf/include/e_coreid.h \
    ${prefix}/epiphany-elf/include/e_ctimers.h \
    ${prefix}/epiphany-elf/include/e_dma.h \
    ${prefix}/epiphany-elf/include/e_ic.h \
    ${prefix}/epiphany-elf/include/e_lib.h \
    ${prefix}/epiphany-elf/include/e-lib.h \
    ${prefix}/epiphany-elf/include/e_mem.h \
    ${prefix}/epiphany-elf/include/e_mutex.h \
    ${prefix}/epiphany-elf/include/e_regs.h \
    ${prefix}/epiphany-elf/include/e_types.h \
    ${prefix}/lib/*.so \
    ${prefix}/lib/epiphany-elf/*.a \
"
INSANE_SKIP_${PN} += "staticdev"
INSANE_SKIP_${PN} += "libdir"

FILES_${PN}-dbg += "\
    ${prefix}/epiphany/epiphany-sdk/bsps/parallella_E16G3_1GB/.debug \
    ${prefix}/epiphany/epiphany-sdk/bsps/zed_E16G3_512mb/.debug \
    ${prefix}/epiphany/epiphany-sdk/bsps/zed_E64G4_512mb/.debug \
    ${prefix}/lib/epiphany-elf/.debug/ \
"
INSANE_SKIP_${PN}-dbg += "libdir"

# Skip the architecture qa check
# this allows epiphany-elf code (e-lib) to be packaged alongside arm code
INSANE_SKIP_${PN} += "arch"
INSANE_SKIP_${PN}-dbg += "arch"

do_install () {
	mkdir -p ${D}${prefix}/include
	mkdir -p ${D}${prefix}/epiphany-elf/include
	mkdir -p ${D}${prefix}/lib
	mkdir -p ${D}${prefix}/lib/epiphany-elf
	mkdir -p ${D}${prefix}/bin
	mkdir -p ${EPIPHANY_HOME}/bsps
	# mkdir -p ${EPIPHANY_HOME}/tools/host
	# mkdir -p ${EPIPHANY_HOME}/tools/e-gnu/epiphany-elf
	# mkdir -p ${EPIPHANY_HOME}/tools/e-gnu/lib
	# mkdir -p ${EPIPHANY_HOME}/tools/e-gnu/libexec

	cp ${B}/src/e-hal/src/epiphany-hal-api.h ${D}/${prefix}/include/
	cp ${B}/src/e-hal/src/epiphany-hal.h ${D}/${prefix}/include/

	ln -sf ${D}/${prefix}/include/epiphany-hal.h ${D}/${prefix}/include/e-hal.h

	cp ${B}/src/e-hal/src/epiphany-hal-data.h ${D}/${prefix}/include/
	cp ${B}/src/e-hal/src/epiphany-hal-data-local.h ${D}/${prefix}/include/
	# cp ${B}/src/e-hal/src/epiphany-shm-manager.h ${D}/${prefix}/include/

	cp ${B}/src/e-lib/include/e_lib.h ${D}/${prefix}/epiphany-elf/include/

	ln -sf ${D}/${prefix}/epiphany-elf/include/e_lib.h ${D}/${prefix}/epiphany-elf/include/e-lib.h

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

	ln -sf ${D}/${prefix}/include/e-loader.h ${D}/${prefix}/include/e_loader.h

	cp ${B}/src/e-xml/Release/libe-xml.so ${D}/${prefix}/lib/

	cp ${B}/src/e-loader/Release/libe-loader.so ${D}/${prefix}/lib/

	cp ${B}/src/e-hal/Release/libe-hal.so ${D}/${prefix}/lib/

	cp ${B}/src/e-lib/Release/libe-lib.a ${D}/${prefix}/lib/epiphany-elf/

	ln -sf ${D}/${prefix}/lib/epiphany-elf/libe-lib.a ${D}/${prefix}/lib/epiphany-elf/libelib.a

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

	ln -sf ${EPIPHANY_HOME}/bsps/parallella_E16G3_1GB ${EPIPHANY_HOME}/bsps/current
}
