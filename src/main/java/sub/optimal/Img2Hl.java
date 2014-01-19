/*
 * Copyright (C) 2014 suboptimal
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package sub.optimal;

import java.io.File;
import sub.optimal.hacklace2.Converter;

/**
 * Main class of ImageToHacklace2 converter.<br><br>
 * This tool converts images files into a string of hexadecimal bytes which can be downloaded<br>
 * as part of an animation onto the "HackLace2"
 * (see <a href="http://wiki.fab4u.de/wiki/Hacklace">http://wiki.fab4u.de/wiki/Hacklace</a>).
 *
 * @author SubOptimal
 * @version 0.1.0
 */
public class Img2Hl {
    private static final String VERSION = "0.1.0";

    private static String templateFileName;
    private static String imageFileName;

    public static void main(String[] args) {
        processParameters(args);
        if (imageFileName != null) {
        processImageFile();
        } else if (templateFileName != null) {
            processTemplateFile();
        }
    }

    private static void processTemplateFile() {
        throw new UnsupportedOperationException("Not supported yet - see TODO");
    }

    private static void processImageFile() {
        File image = new File(imageFileName);
        if (!image.exists() || !image.isFile()) {
            System.err.println(String.format("file %s: does not exist", image.getName()));
            System.exit(1);
        }

        if (!image.canRead()) {
            System.err.println(String.format("file %s: no read permission", image.getName()));
            System.exit(1);
        }

        Converter converter = Converter.getInstance();
        System.out.println(converter.convertToHacklace(image));
    }

    private static void processParameters(String[] args) {
        if (args.length == 0) {
            showUsage();
            System.exit(1);
        }

        for (int i = 0; i < args.length; i++) {
            String opt = args[i];
            if (opt.startsWith("--template")) {
                // TODO will be implemented as next ;-)
                processTemplateFile();
                if ((i + 1) < args.length) {
                    templateFileName = args[++i];
                } else {
                    System.err.println("missed parameter for option --template");
                    System.exit(1);
                }
            } else if (opt.startsWith("--")) {
                System.err.printf("unknown option passed '%s'%n", opt);
                System.exit(1);
            } else {
                imageFileName = opt;
            }
        }
    }

    private static void showUsage() {
        System.err.printf("ImageToHacklace2 " + VERSION + "%n%n");
        System.err.printf("usage: java -jar ImageToHacklace2 [ image_file | --template template_file ]%n%n");
        System.err.printf("%16s%s%n", "image_file -", "an image file to convert into the hacklace databytes");
        System.err.printf("%16s%s%n", "", "recommended formats: BMP, GIF, PNG");
        System.err.printf("%16s%s%n", "--template -", "template hacklace configuration file with placeholders");
        System.err.printf("%16s%s%n", "", "for image files");
    }
}
