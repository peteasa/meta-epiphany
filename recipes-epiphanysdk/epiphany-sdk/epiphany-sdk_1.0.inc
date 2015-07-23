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

DEPENDS =+ "epiphany-elf-newlib epiphany-elf-libgcc virtual/kernel"

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
    ${prefix}/epiphany/epiphany-sdk/tools \
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

do_install_append () {

	mkdir -p ${EPIPHANY_HOME}/tools/host
	mkdir -p ${EPIPHANY_HOME}/tools/e-gnu/epiphany-elf
	mkdir -p ${EPIPHANY_HOME}/tools/e-gnu/lib
      	mkdir -p ${EPIPHANY_HOME}/tools/e-gnu/libexec

	cd ${EPIPHANY_HOME}/tools/host

	ln -sf ../../../../lib ./
	ln -sf ../../../../bin ./

	cd ${EPIPHANY_HOME}/tools/e-gnu/epiphany-elf

	ln -sf ../../../../../bin ./
	ln -sf ../../../../../epiphany-elf/include ./sys-include
	ln -sf ../../../../../epiphany-elf/include ./
	ln -sf ../../../../../epiphany-elf/lib ./

	cd ${EPIPHANY_HOME}/tools/e-gnu
	ln -sf ../../../../bin ./
	mkdir -p ${D}${prefix}/lib/epiphany-elf/gcc
	cd ${EPIPHANY_HOME}/tools/e-gnu/lib
	ln -sf ../../../../../lib/epiphany-elf/gcc ./
	cd ${EPIPHANY_HOME}/tools/e-gnu/libexec
	ln -sf ../../../../../lib/epiphany-elf/gcc ./
	rmdir ${D}${prefix}/lib/epiphany-elf/gcc
}
