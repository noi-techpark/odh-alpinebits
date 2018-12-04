/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.servlet.impl.utils;

import javax.servlet.ServletOutputStream;
import java.io.StringWriter;

/**
 * This class provides builders for {@link ServletOutputStream}, used for testing.
 */
public class ServletOutputStreamBuilder {

    /**
     * Build a {@link ServletOutputStream} that writes the data to
     * the provided {@link StringWriter}.
     *
     * @return a {@link ServletOutputStream}
     */
    public static ServletOutputStream getServletOutputStream(StringWriter sw) {
        return new ServletOutputStream() {
            @Override
            public void write(int b) {
                sw.write(b);
            }
        };
    }
}
