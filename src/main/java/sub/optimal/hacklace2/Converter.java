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
package sub.optimal.hacklace2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author suboptimal
 */
public class Converter {

    public static final String HEX_BYTE_MARKER = "\\";
    public static final String DATABLOCK_START = HEX_BYTE_MARKER + "1F";
    public static final int RGB_WHITE = 16777215;
    private static final String ZERO_LENGTH_STRING = "";

    public static Converter getInstance() {
        return new Converter();
    }

    private boolean checkDimension(int height, int width) {
        if (height < 1 || width < 1) {
            System.err.println("image dimension out of range: minimal dimension = 1 x 1 pixel");
            return false;
        }
        if (height > 8) {
            System.err.println("image height out of range: area will be truncated to 8 pixel height");
        }
        if (width > 255) {
            System.err.println("image width out of range: area will be truncated to 255 pixel width");
        }
        return true;
    }

    public String convertToHacklace(File image) {
        try {
            BufferedImage buffImage = ImageIO.read(image);
            int height = buffImage.getHeight();
            int width = buffImage.getWidth();
            if (!checkDimension(height, width)) {
                return ZERO_LENGTH_STRING;
            }
            width = (width < 255 ? width - 1 : 254);
            height = (height < 8 ? height - 1 : 7);
            StringBuilder out = new StringBuilder();
            out.append(String.format("%s %02X", DATABLOCK_START, width + 1));
            for (int column = 0; column <= width; column++) {
                int hlValue = 0;
                for (int row = height; row >= 0; row--) {
                    hlValue <<= 1;
                    if ((buffImage.getRGB(column, row) & RGB_WHITE) != RGB_WHITE) {
                        hlValue++;
                    }
                }
                out.append(String.format(" %02X", hlValue));
            }
            out.append(HEX_BYTE_MARKER);
            return out.toString();
        } catch (IOException ex) {
            System.err.println("failure during reading image file: " + ex.getMessage());
            return ZERO_LENGTH_STRING;
        }
    }

}
