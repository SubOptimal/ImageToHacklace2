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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

    @Test
    public void testTemplateProcessorExistingImageFile() {
        try {
            String templateBasename = "template_existing_image_file";
            File hacklaceFile = runTemplateProcessorTest(templateBasename);
            assertTemplateProcessortTest(templateBasename, hacklaceFile);
        } catch (IOException ex) {
            System.err.println("no exception expected: " + ex.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void testTemplateProcessorNotAnImageFile() {
        try {
            String templateBasename = "template_not_an_image_file";
            File hacklaceFile = runTemplateProcessorTest(templateBasename);
            assertTemplateProcessortTest(templateBasename, hacklaceFile);
        } catch (IOException ex) {
            System.err.println("no exception expected: " + ex.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void testTemplateProcessorNotExistingImageFile() {
        try {
            String templateBasename = "template_not_existing_image_file";
            File hacklaceFile = runTemplateProcessorTest(templateBasename);
            assertTemplateProcessortTest(templateBasename, hacklaceFile);
        } catch (IOException ex) {
            System.err.println("no exception expected: " + ex.getMessage());
            Assert.fail();
        }
    }

    /**
     * Compares the expected output file wth the actual created output file.
     *
     * @param templateBasename basename of the template file
     * @param hacklaceFile {@link File} object representing the Hacklace file which is created
     * during the conversion of the template
     * @throws IOException in case reading from one file is failed
     */
    private void assertTemplateProcessortTest(String templateBasename, File hacklaceFile) throws IOException {
        File expectedOutput = new File(getExpectedResultFilename(templateBasename));
        BufferedReader expectedHl = new BufferedReader(new FileReader(expectedOutput));
        BufferedReader actualHl = new BufferedReader(new FileReader(hacklaceFile));
        String inLine;
        String outLine;
        do {
            inLine = expectedHl.readLine();
            outLine = actualHl.readLine();
            Assert.assertEquals(inLine, outLine);

        } while (inLine != null && outLine != null);
    }

    /**
     * Executes the template processor test.
     *
     * @param templateBasename basename of the template file
     * @return a {@link File} object representing the Hacklace file which is created during the
     * conversion of the template
     * @throws IOException
     */
    private File runTemplateProcessorTest(String templateBasename) throws IOException {
        File testInput = new File(getTemplateName(templateBasename));
        File hacklaceFile = new File(getHacklaceName(templateBasename));
        if (hacklaceFile.exists()) {
            hacklaceFile.delete();
        }
        TemplateProcessor templateProcessor = TemplateProcessor.getInstance();
        templateProcessor.processTemplateFile(testInput);
        hacklaceFile.deleteOnExit();
        return hacklaceFile;
    }

    /**
     * Executes the convert to hacklace test.
     * @param imageFileName name of the image file to convert
     * @param expectedHl the expected Hacklace hexadecimal bytes string
     */
    private void runConvertToHacklaceTest(String imageFileName, String expectedHl) {
        File image = new File(imageFileName);
        String hlOutput = converter.convertToHacklace(image);
        Assert.assertEquals(expectedHl, hlOutput);
    }

    private String getTemplateName(String templateBasename) {
        return templateBasename + ".txt";
    }

    private String getHacklaceName(String templateBasename) {
        return templateBasename + ".hl";
    }

    private String getExpectedResultFilename(String templateBasename) {
        return templateBasename + ".expected";
    }
}
