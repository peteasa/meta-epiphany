#
#

SUMMARY = "Essential build dependencies for nativesdk"
LICENSE = "GPL"

inherit packagegroup

RDEPENDS:packagegroup-nativesdk-epiphany-sdk-buildessentialfromsource = "\
    epiphany-sdk-cross-canadian-arm-dev \
    epiphany-sdk-cross-canadian-arm \
    "
