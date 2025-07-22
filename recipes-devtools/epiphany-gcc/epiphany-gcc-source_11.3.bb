FILESEXTRAPATHS =. "${COREBASE}/meta/recipes-devtools/gcc/gcc:"

require recipes-devtools/gcc/gcc-${PV}.inc
require recipes-devtools/gcc/gcc-source.inc
require epiphany-gcc-shared-source.inc

PN = "epiphany-gcc-source-${PV}"
WORKDIR = "${TMPDIR}/work-shared/epiphany-gcc-${PV}-${PR}"

STAMP = "${STAMPS_DIR}/work-shared/epiphany-gcc-${PV}-${PR}"
STAMPCLEAN = "${STAMPS_DIR}/work-shared/epiphany-gcc-${PV}-*"

EXCLUDE_FROM_WORLD = "1"
BRANCH = "y2024.2"
BASEURI="git://github.com/peteasa/epiphany-gcc.git;branch=${BRANCH};protocol=https"
SRCREV = "4017359d1fcc3b870f763c4f79e0ba1ae3c2a7f4"
