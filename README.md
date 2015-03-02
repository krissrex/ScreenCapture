ScreenCapture
=============

A simple screen capture utility in Java.



Issues
---------

**Rendering is broken**
Try launching with these arguments:

| OS      | Argument                                             |
|---------|------------------------------------------------------|
| Windows | java -jar -Dsun.java2d.d3d=false ScreenCapture.jar   |
| Linux   | java -jar -Dsun.java2d.opengl=True ScreenCapture.jar |

