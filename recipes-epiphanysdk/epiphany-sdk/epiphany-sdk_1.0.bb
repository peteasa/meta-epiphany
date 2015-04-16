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

## Switch off parallel build so that utils/e-loader waits for e-loader build
PARALLEL_MAKE = ""

