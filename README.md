# meta-epiphany

The meta-epiphany layer creates the set of tools needed to progamme the Epiphany CPU.

The current stable branch is [y2024.2](https://github.com/peteasa/meta-epiphany/tree/y2024.2)

## Instructions

For full instructions for building a Yocto release for the parallella board see [parallella-yoctobuild wiki](https://github.com/peteasa/parallella-yoctobuild/wiki).

Note in this branch the [meta-exotic](https://github.com/peteasa/meta-exotic/wiki) layer is not used.

## Supported CPU types

**Epiphany III**

This layer uses the toolchain created to build the host and target [Epiphany libraries](https://github.com/adapteva/epiphany-libs).

## Dependencies

This layer depends upon:

	URI: git://git.openembedded.org/bitbake

	URI: git://git.openembedded.org/openembedded-core
	layers: meta

## Reporting issues with this layer

Please report any issues with this layer at https://github.com/peteasa/meta-epiphany/issues
