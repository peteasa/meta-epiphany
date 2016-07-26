# meta-epiphany

The meta-epiphany layer is a proof of concept layer to prove a generic method of creating the set of tools needed to support a Microcontroller or other secondary processor in the [Yocto](http://www.yoctoproject.org/docs/latest/yocto-project-qs/yocto-project-qs.html) environment.

The current stable branch is [master](https://github.com/peteasa/meta-epiphany/tree/master)

The current work in progress branch is [elink-redesign](https://github.com/peteasa/meta-epiphany/tree/elink-redesign)

## Instructions

For full instructions for building a Yocto release for the parallella board see [parallella-yoctobuild wiki](https://github.com/peteasa/parallella-yoctobuild/wiki).

For developer notes about the meta-exotic layer see the [meta-exotic wiki](https://github.com/peteasa/meta-exotic/wiki).

For full instructions about parallella (a one stop environment for parallella development and the home project of this suite of repositories) see the [parallella wiki](https://github.com/peteasa/parallella/wiki).

## Supported CPU types

**Epiphany** - This layer uses meta-exotic layer to create the toolchain used for the [Epiphany processor](http://www.adapteva.com/epiphanyiii).

This layer also uses the toolchain created to build the host and target [Epiphany libraries](https://github.com/adapteva/epiphany-libs).

## Dependencies

This layer depends upon:

	URI: git://git.openembedded.org/bitbake

	URI: git://git.openembedded.org/openembedded-core
	layers: meta

	URI: git://github.com/peteasa/meta-exotic

## Reporting issues with this layer

Please report any issues with this layer at https://github.com/peteasa/meta-epiphany/issues
