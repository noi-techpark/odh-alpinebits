/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity;

/**
 * A general error object that contains information
 * about an error.
 */
public class Error {

    public static final String TYPE_DEFAULT = "13";

    public static final int CODE_UNABLE_TO_PROCESS = 450;

    private String content;

    private String type;

    private Integer code;

    public static Error withDefaultType(Integer code) {
        return Error.withDefaultType(code, null);
    }

    public static Error withDefaultType(Integer code, String content) {
        Error error = new Error();
        error.setType(Error.TYPE_DEFAULT);
        error.setCode(code);
        error.setContent(content);
        return error;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Error{" +
                "content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", code=" + code +
                '}';
    }
}
