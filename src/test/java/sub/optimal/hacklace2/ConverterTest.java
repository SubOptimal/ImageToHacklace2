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

import java.io.File;
import org.junit.Assert;
import junit.framework.TestSuite;
import org.junit.Test;

/**
 *
 * @author suboptimal
 */
public class ConverterTest extends TestSuite {

    Converter converter = Converter.getInstance();

    @Test
    public void testDotUpperLeft() {
        String fileName = "dot_upper_left.gif";
        String expectedHl = "\\1F 08 01 00 00 00 00 00 00 00\\";
        runConvertToHacklaceTest(fileName, expectedHl);
    }

    @Test
    public void testDotLowerLeft() {
        String fileName = "dot_lower_left.gif";
        String expectedHl = "\\1F 08 80 00 00 00 00 00 00 00\\";
        runConvertToHacklaceTest(fileName, expectedHl);
    }

    @Test
    public void testDotLowerRight() {
        String fileName = "dot_lower_right.gif";
        String expectedHl = "\\1F 08 00 00 00 00 00 00 00 80\\";
        runConvertToHacklaceTest(fileName, expectedHl);
    }

    @Test
    public void testDotUpperRight() {
        String fileName = "dot_upper_right.gif";
        String expectedHl = "\\1F 08 00 00 00 00 00 00 00 01\\";
        runConvertToHacklaceTest(fileName, expectedHl);
    }

    @Test
    public void testPattern5A() {
        String fileName = "pattern_5A.gif";
        String expectedHl = "\\1F 08 5A 5A 5A 5A 5A 5A 5A 5A\\";
        runConvertToHacklaceTest(fileName, expectedHl);
    }

    @Test
    public void testPatternA5() {
        String fileName = "pattern_A5.gif";
        String expectedHl = "\\1F 08 A5 A5 A5 A5 A5 A5 A5 A5\\";
        runConvertToHacklaceTest(fileName, expectedHl);
    }

    @Test
    public void testPattern0F() {
        String fileName = "pattern_0F.gif";
        String expectedHl = "\\1F 08 0F 0F 0F 0F 0F 0F 0F 0F\\";
        runConvertToHacklaceTest(fileName, expectedHl);
    }

    @Test
    public void testPatternF0() {
        String fileName = "pattern_F0.gif";
        String expectedHl = "\\1F 08 F0 F0 F0 F0 F0 F0 F0 F0\\";
        runConvertToHacklaceTest(fileName, expectedHl);
    }

    @Test
    public void testPatternFF() {
        String fileName = "pattern_FF.gif";
        String expectedHl = "\\1F 08 FF FF FF FF FF FF FF FF\\";
        runConvertToHacklaceTest(fileName, expectedHl);
    }

    @Test
    public void testOutOfRangeHeight() {
        String fileName = "out_of_range_height.gif";
        String expectedHl = "\\1F 08 01 02 04 08 10 20 40 80\\";
        runConvertToHacklaceTest(fileName, expectedHl);
    }

    @Test
    public void testOutOfRangeHeight4() {
        String fileName = "out_of_range_height_4.gif";
        String expectedHl = "\\1F 08 08 04 02 01 01 02 04 08\\";
        runConvertToHacklaceTest(fileName, expectedHl);
    }

    @Test
    public void testOutOfRangeWidth() {
        String fileName = "out_of_range_width.gif";
        StringBuilder expectedHl = new StringBuilder("\\1F FF");
        for (int i = 0; i < 31; i++) {
            expectedHl.append(" 01 02 04 08 10 20 40 80");
        }
        expectedHl.append(" 01 02 04 08 10 20 40");
        expectedHl.append("\\");
        runConvertToHacklaceTest(fileName, expectedHl.toString());
    }

    private void runConvertToHacklaceTest(String fileName, String expectedHl) {
        File image = new File(fileName);
        String hlOutput = converter.convertToHacklace(image);
        Assert.assertEquals(expectedHl, hlOutput);
    }
}
