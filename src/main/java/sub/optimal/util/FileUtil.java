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
package sub.optimal.util;

import java.io.File;

/**
 *
 * @author suboptimal
 */
public class FileUtil {

    /**
     * Check if the passed {@link File} point to an existing file and can be read by the user.
     *
     * @param file file to be checked
     * @return true - <code>file</code> point to a file and is readable<br>
     * false - <code>file</file> is either a directory or is not readable
     */
    public static boolean isReadableFile(File file) {
        boolean state = true;
        if (!file.exists() || !file.isFile()) {
            System.err.println(String.format("%s: does not exist", file.getName()));
            state = false;
        } else if (!file.canRead()) {
            System.err.println(String.format("%s: no read permission", file.getName()));
            state = false;
        }
        return state;
    }
}
