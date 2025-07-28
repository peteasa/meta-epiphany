inherit autotools

DESCRIPTION = "Epiphany SDK Build and Install Package"
HOMEPAGE = "http://www.adapteva.com/"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

# 2019.1
BRANCH = "2019.1"
SRC_URI = "git://github.com/adapteva/epiphany-libs.git;branch=${BRANCH};protocol=https"
SRCREV = "0710d8afbd352c9c1dac7cb1617346f738561c86"

S = "${WORKDIR}/git"

# The target library is built separately
EXTRA_OECONF = "--disable-elib"

FILES:${PN} += "${datadir}/epiphany"

DEPENDS:remove = "virtual/libc"

do_configure:prepend () {
    cd ${S}
    ./bootstrap --force
    cd ${B}
}
