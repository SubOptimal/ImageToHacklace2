ImageToHacklace2
================

This tool converts images files into a string of hexadecimal bytes which can be downloaded as part of an animation onto the "HackLace 2" (A Necklace for Hackers - see [1]).

**supported image formats**

- Bitmap (BMP)
- Graphics Interchange Format (GIF)
- Portable Network Graphics (PNG)
- JPEG File Interchange Format (JPG / JPEG) - not recommended, based on the compression rate and hence artefacts the result might not look as you expect

A pixel with white color is converted as "LED = on" any other pixel is converted as "LED = off". Best results you get with monochrom images.

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

**usage - convert a template file**

A template file is a Hacklace command script or a Shell script inlcuding a output command (see above example) which includes a placeholders for an image file. If the image file from the placeholderncan be found the placeholder is substituted by hexadecimal bytes representing the image file.

<u>template_example.txt</u>
```
HL
\00 7A 01\ It's hip to be [[square.png]] ! \00\
\00 7A 01\ It's hip to [[smile.png]] ! \00\
\FF\
```

```
java -jar ImageToHacklace2.jar --template template_example.txt
```

Assuming the image file `square.png` exist in the current directory and the image file `smile.png` does not exist. The file `template_example.txt` will be converted to `template_example.hl` like below.

<u>template_example.hl</u>
```
HL
\00 7A 01\ It's hip to be \1F 08 00 7E 42 42 42 42 7E 00\ ! \00\
\00 7A 01\ It's hip to [[smile.png]] ! \00\
\FF\
```

For further information about the format of a hacklace script have a look on the Hacklace 2 wiki[1].

**download compiled version**

If you don't want to build the tool from source, you can download the compiled release version from following URL: https://github.com/SubOptimal/ImageToHacklace2/releases

[1] http://wiki.fab4u.de/wiki/Hacklace
