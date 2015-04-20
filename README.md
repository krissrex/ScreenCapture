ScreenCapture
=============

A simple screen capture utility in Java.
Please have a look at the [wiki](https://github.com/krissrex/ScreenCapture/wiki).

### Plugins
Have a look at [https://github.com/krissrex/ScreenCapture-Plugins](https://github.com/krissrex/ScreenCapture-Plugins)
for plugins.


Issues
---------

**Rendering is broken**
Try launching with these arguments:

| OS      | Argument                                             |
|---------|------------------------------------------------------|
| Windows | java -jar -Dsun.java2d.d3d=false ScreenCapture.jar   |
| Linux   | java -jar -Dsun.java2d.opengl=True ScreenCapture.jar |

**I can't find my config**
**Config does not work**
The default location for the configuration file is `~/.ScreenCapture/config.txt`. You may specify another file using the `-c` and `--config` flags. If the file doesn't exist, the program will create a default-configfile.
