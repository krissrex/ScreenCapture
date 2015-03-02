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

**I cant find my config**
**Config does not work**
If running on linux with _Right Click_ > _Open With Oracle Java x Runtime_, the config is in home (~/config.txt).
