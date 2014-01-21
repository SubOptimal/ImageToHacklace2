ImageToHacklace2
================

This tool converts images files into a string of hexadecimal bytes which can be downloaded as part of an animation onto the "HackLace 2" (A Necklace for Hackers - see [1]).

**supported image formats**

- Bitmap (BMP)
- Graphics Interchange Format (GIF)
- Portable Network Graphics (PNG)
- JPEG File Interchange Format (JPG / JPEG) - not recommended, based on the compression rate and the resulting artefacts the result might not be what you expect

A pixel with white color will be converted as "LED = on" any other pixel will be converted as "LED = off". Best results you will get with monochrom images.

**supported image size**

- width: 1 <= x <= 255
- height: 1 <= y <= 8

If the image is greater in one dimension then this dimension will be truncated to the maximum value. The origin of ordinates is the upper left corner of the image. The height will be converted always as 8 pixel height. If the height of the input image is less than 8 pixel, the missed pixel converted as "LED = off".

**usage - convert a single image**

```
java -jar ImageToHacklace2.jar image_file
```

The output, for example might look like `\1F 08 00 20 42 40 5C 40 42 20\`, you can use as datablock for the Hacklace 2 animation app.

see an example for the Linux shell:

```
printf '\eHL\\00 70 01 1F 08 00 20 42 40 5C 40 42 20\\ \\00 1F FF\\' > /dev/ttyUSB0
```

For further information about the above command have a look on the Hacklace 2 wiki[1].

[1] http://wiki.fab4u.de/wiki/Hacklace
