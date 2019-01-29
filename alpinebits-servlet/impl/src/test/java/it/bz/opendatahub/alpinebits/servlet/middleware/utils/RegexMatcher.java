/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.servlet.middleware.utils;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Regex matcher to be used by REST Assured.
 */
public class RegexMatcher extends BaseMatcher<Object> {
    private final String regex;

    public RegexMatcher(String regex){
        this.regex = regex;
    }

    public boolean matches(Object o){
        return ((String)o).matches(regex);

    }

    public void describeTo(Description description){
        description.appendText("matches regex=");
    }

    public static RegexMatcher matches(String regex){
        return new RegexMatcher(regex);
    }
}
