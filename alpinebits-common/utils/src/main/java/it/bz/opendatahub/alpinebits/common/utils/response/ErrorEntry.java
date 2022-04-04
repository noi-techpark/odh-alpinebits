/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.utils.response;

import it.bz.opendatahub.alpinebits.common.constants.OTACodeErrorCodes;

import java.util.Objects;

/**
 * Use this class as parameter for some those methods in {@link MessageAcknowledgementTypeBuilder}
 * that build Error outcomes.
 */
public final class ErrorEntry {

    private final String message;
    private final OTACodeErrorCodes code;

    public ErrorEntry(String message, OTACodeErrorCodes code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public OTACodeErrorCodes getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorEntry that = (ErrorEntry) o;
        return Objects.equals(message, that.message) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, code);
    }

    @Override
    public String toString() {
        return "MessageAndCode{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

}