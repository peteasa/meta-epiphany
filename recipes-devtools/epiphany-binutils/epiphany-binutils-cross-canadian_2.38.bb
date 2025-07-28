FILESEXTRAPATHS =. "${COREBASE}/meta/recipes-devtools/binutils/binutils:"

inherit epiphany-target
require recipes-devtools/binutils/binutils.inc
require recipes-devtools/binutils/binutils-${PV}.inc
require recipes-devtools/binutils/binutils-cross-canadian.inc
require epiphany-binutils-${PV}.inc

PN = "epiphany-binutils-cross-canadian-${TRANSLATED_TARGET_ARCH}"
BPN = "epiphany-binutils"
