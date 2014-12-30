inherit cross
PROVIDES = "virtual/${TARGET_PREFIX}newlib"

PN = "epiphany-elf-newlib-cross-${TARGET_ARCH}"
BPN = "epiphany-elf-newlib"

INHIBIT_DEFAULT_DEPS = "1"
INHIBIT_AUTOTOOLS_DEPS = "1"

EXTRA_OECONF += "--with-sysroot=${STAGING_DIR_TARGET} \
                --disable-install-libbfd \
                --enable-poison-system-directories \
                "

do_install () {
	oe_runmake 'DESTDIR=${D}' install
}
