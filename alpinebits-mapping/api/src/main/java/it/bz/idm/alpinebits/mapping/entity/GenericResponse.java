/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity;

import java.util.List;

/**
 * A generic AlpineBits response, used e.g. in Inventory.
 */
public class GenericResponse {

    private String success;

    private List<Error> errors;

    private List<Warning> warnings;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public List<Warning> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Warning> warnings) {
        this.warnings = warnings;
    }

    @Override
    public String toString() {
        return "GenericResponse{" +
                "success='" + success + '\'' +
                ", errors=" + errors +
                ", warnings=" + warnings +
                '}';
    }
}
