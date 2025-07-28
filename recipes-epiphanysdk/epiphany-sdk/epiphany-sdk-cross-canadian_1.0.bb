inherit cross-canadian

require epiphany-sdk_${PV}.bb

SUMMARY = "epiphany-sdk (cross-canadian for ${TARGET_ARCH} target)"

HOST_SYS = "${SDK_SYS}"

PN = "epiphany-sdk-cross-canadian-${TRANSLATED_TARGET_ARCH}"
BPN = "epiphany-sdk"

DEPENDS = "flex-native bison-native virtual/${TARGET_PREFIX}gcc virtual/${HOST_PREFIX}gcc-crosssdk virtual/nativesdk-libc nativesdk-zlib nativesdk-gettext nativesdk-flex"

# We have to point epiphany-sdk at a sysroot but we don't need to rebuild if this changes
# e.g. we switch between different machines with different tunes.
EXTRA_OECONF[vardepsexclude] = "TUNE_PKGARCH"

BBCLASSEXTEND = ""

