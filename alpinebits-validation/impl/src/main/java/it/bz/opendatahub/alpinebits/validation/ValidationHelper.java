/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation;

import java.math.BigInteger;
import java.util.Collection;

/**
 * This helper class provides validation methods.
 */
public final class ValidationHelper {

    private final int errorCode;

    private ValidationHelper(int errorCode) {
        this.errorCode = errorCode;
    }

    public static ValidationHelper withClientDataError() {
        return new ValidationHelper(ValidationErrorCode.CLIENT_DATA_ERROR);
    }

    public void expectNotNull(Object object, String message) {
        this.expectNotNull(object, message, null);
    }

    public void expectNotNull(Object object, String message, ValidationPath path) {
        if (object == null) {
            throw new NullValidationException(message + this.getPathMessage(path), errorCode);
        }
    }

    public void expectNull(Object object, String message, ValidationPath path) {
        if (object != null) {
            throw new NotNullValidationException(message + this.getPathMessage(path), errorCode);
        }
    }

    public void expectEquals(Object object1, Object object2, String message, ValidationPath path) {
        if (object1 != null && !object1.equals(object2)) {
            throw new NotEqualValidationException(message + this.getPathMessage(path), errorCode);
        }
        if (object2 != null && !object2.equals(object1)) {
            throw new NotEqualValidationException(message + this.getPathMessage(path), errorCode);
        }
    }

    public void expectArg0LesserOrEqualThanArg1(BigInteger number1, BigInteger number2, String message, ValidationPath path) {
        // Throw exception if number1 > number2
        if (number1.compareTo(number2) > 0) {
            throw new NotLesserOrEqualValidationException(message + this.getPathMessage(path), errorCode);
        }
    }

    public void expectHotelCodeAndNameNotBothNull(String hotelCode, String hotelName, String message, ValidationPath path) {
        if (hotelCode == null && hotelName == null) {
            throw new ValidationException(message + this.getPathMessage(path), errorCode);
        }
    }

    public void expectNonEmptyCollection(Collection<?> collection, String message, ValidationPath path) {
        if (collection.isEmpty()) {
            throw new EmptyCollectionValidationException(message + this.getPathMessage(path), errorCode);
        }
    }

    public void throwValidationException(String message, ValidationPath path) {
        throw new ValidationException(message + this.getPathMessage(path), errorCode);
    }

    private String getPathMessage(ValidationPath path) {
        if (path == null) {
            return "";
        }
        return " (Path: " + path + ")";
    }
}
