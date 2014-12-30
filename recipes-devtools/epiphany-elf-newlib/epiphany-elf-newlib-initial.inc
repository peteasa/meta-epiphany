require epiphany-elf-newlib-common.inc

DEPENDS = "virtual/${TARGET_PREFIX}gcc-initial"
PROVIDES = "virtual/${TARGET_PREFIX}newlib-initial"

# Add paths of HOST and TARGET compilers
STAGINGCC = "epiphany-elf-gcc-cross-initial-${TARGET_ARCH}"
STAGINGCC_class-nativesdk = "gcc-crosssdk-initial-${TARGET_ARCH}"
PATH_prepend = "${STAGING_BINDIR_TOOLCHAIN}.${STAGINGCC}:${STAGING_BINDIR_TOOLCHAIN}:${STAGING_DIR_NATIVE}${bindir_native}/${HOST_SYS}.gcc-cross-initial-${TARGET_ARCH}:"

PACKAGES = ""

EXTRA_OECONF += "--disable-shared"

NEWLIBBUILDTREENAME = "gcc-build-internal-"

do_populate_sysroot[sstate-outputdirs] = "${STAGING_DIR_TCBOOTSTRAP}/"

