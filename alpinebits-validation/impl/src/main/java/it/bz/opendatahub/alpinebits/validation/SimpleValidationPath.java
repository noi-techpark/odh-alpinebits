// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation;

import java.util.Objects;

/**
 * This class implements a {@link ValidationPath} and can
 * be used to track the path from the beginning of a
 * validation to the current positions.
 *
 * It is encouraged that exceptions include the path
 * information to simplify the determination of the
 * exception cause.
 */
public final class SimpleValidationPath implements ValidationPath {

    public static final String DELIMITER = "->";
    public static final Character OPEN_INDEX_CHAR = '[';
    public static final Character CLOSE_INDEX_CHAR = ']';
    public static final Character OPEN_ATTRIBUTE_CHAR = '{';
    public static final Character CLOSE_ATTRIBUTE_CHAR = '}';

    private StringBuilder validationPath;

    private SimpleValidationPath(String validationPath) {
        if (validationPath == null) {
            throw new IllegalArgumentException("Initial validation path required");
        }
        this.validationPath = new StringBuilder(validationPath);
    }

    public static SimpleValidationPath fromPath(String begin) {
        return new SimpleValidationPath(begin);
    }

    public SimpleValidationPath withElement(String path) {
        String newPath = new StringBuilder(this.validationPath).append(DELIMITER).append(path).toString();
        return new SimpleValidationPath(newPath);
    }

    public SimpleValidationPath withAttribute(String path) {
        String newPath = new StringBuilder(this.validationPath)
                .append(OPEN_ATTRIBUTE_CHAR)
                .append(path)
                .append(CLOSE_ATTRIBUTE_CHAR)
                .toString();
        return new SimpleValidationPath(newPath);
    }

    public SimpleValidationPath withIndex(int index) {
        String newPath = new StringBuilder(this.validationPath)
                .append(OPEN_INDEX_CHAR)
                .append(index)
                .append(CLOSE_INDEX_CHAR)
                .toString();
        return new SimpleValidationPath(newPath);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleValidationPath that = (SimpleValidationPath) o;
        return Objects.equals(validationPath.toString(), that.validationPath.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(validationPath.toString());
    }

    @Override
    public String toString() {
        return this.validationPath.toString();
    }
}
