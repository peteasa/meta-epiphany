TARGET_SYS = "epiphany-elf"
TARGET_PREFIX = "epiphany-elf-"
# TARGET_ARCH := "epiphany-elf"

inherit cross
PROVIDES = "virtual/${TARGET_PREFIX}binutils"

#PN = "binutils-cross-${TARGET_ARCH}"
PN = "epiphany-elf-binutils-cross-epiphany-elf"
BPN = "epiphany-elf-binutils"

INHIBIT_DEFAULT_DEPS = "1"
INHIBIT_AUTOTOOLS_DEPS = "1"

EXTRA_OECONF += "--with-sysroot=${STAGING_DIR_TARGET} \
                --disable-install-libbfd \
                --enable-poison-system-directories \
                "

do_install_append () {

    # TODO why does the cross compiler create /lib/lib folder?
    rm -rf ${D}${prefix}/lib/lib/libiberty.a
}
