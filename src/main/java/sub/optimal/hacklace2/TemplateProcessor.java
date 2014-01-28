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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sub.optimal.util.FileUtil;

/**
 *
 * @author suboptimal
 */
public class TemplateProcessor {

    /**
     * Pattern to match an image placeholder in the format <code>[[filename]]</code>.
     */
    private static final Pattern placeholder = Pattern.compile("\\[\\[([^\\]]*)\\]\\]");
    private Converter converter;

    private TemplateProcessor() {
    }

    /**
     * Return an instance of the converter.
     *
     * @return new instance of the {@link Converter}
     */
    public static TemplateProcessor getInstance() {
        return new TemplateProcessor();
    }

    /**
     * Process a Hacklace script template. Placeholder for existing image files will be substituted.
     *
     * @param template Hacklace template file
     * @throws IOException if reading from the template file or writing to the output file fails
     */
    public void processTemplateFile(File template) throws IOException {
        converter = Converter.getInstance();

        if (!FileUtil.isReadableFile(template)) {
            System.err.println(String.format("%s: cannot be accessed", template.getName()));
            return;
        }

        String templateFileName = template.getName();
        int indexLastDot = templateFileName.lastIndexOf(".");
        String baseName = templateFileName.substring(0, indexLastDot < 0 ? templateFileName.length() : indexLastDot);
        File hacklaceFile = new File(baseName + ".hl");
        if (FileUtil.isReadableFile(hacklaceFile)) {
            System.err.println(String.format("%s: output file already exist", hacklaceFile.getName()));
            return;
        }

        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(template), "ISO-8859-1"));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(hacklaceFile), "ISO-8859-1"));

            String s;
            while ((s = reader.readLine()) != null) {
                writer.write(replacePlaceholder(s));
                // TODO check for DOS/UNIX lineends
                writer.write('\n');
            }
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ioe) {
                    System.err.println(String.format("%s: failed to close the file - %s", hacklaceFile.getName(), ioe.getMessage()));
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    System.err.println(String.format("%s: failed to close the file - %s", hacklaceFile.getName(), ioe.getMessage()));
                }
            }
        }
    }

    /**
     * Replace an image placeholder in the passed line by hexadecimal bytes in Hacklace 2 animation
     * format.<br>
     * The placeholder has following format: [[filename]]<br>
     * - if <code>filename</code> point to a readable file, the placeholder is substituted
     *
     * @param line input string
     * @return output line, placeholder for existing image files are substituted
     */
    public String replacePlaceholder(String line) {
        Matcher matcher = placeholder.matcher(line);
        StringBuffer lineBuffer = new StringBuffer();
        StringBuilder matchBuffer = new StringBuilder();
        while (matcher.find()) {
            matchBuffer.setLength(0);
            String fileName = matcher.group(1);
            File file = new File(fileName);
            if (FileUtil.isReadableFile(file)) {
                String hacklaceBytes = converter.convertToHacklace(file);
                if (hacklaceBytes.length() != 0) {
                    System.out.println(String.format("%s: image converted", file.getName()));
                    matchBuffer.append(Converter.getHexByteMarker());
                    matchBuffer.append(hacklaceBytes);
                    matchBuffer.append(Converter.getHexByteMarker());
                } else {
                    System.out.println(String.format("%s: image could not be not converted", file.getName()));
                    matchBuffer.append(matcher.group());
                }
            } else {
                System.out.println(String.format("%s: image skipped", file.getName()));
                matchBuffer.append(matcher.group());
            }
            matcher.appendReplacement(lineBuffer, matchBuffer.toString());
        }
        matcher.appendTail(lineBuffer);
        return lineBuffer.toString();
    }
}
