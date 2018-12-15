/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers;

/**
 * This enum defines the valid genders.
 */
public enum Gender {
    FEMALE("Female"),
    MALE("Male"),
    UNKNOWN("Unknown");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Try to find a matching Gender value for the
     * given String <code>s</code>.
     * <p>
     * The match is searched using the {@link Gender#toString()}
     * method. If a match is found, the corresponding Gender
     * is returned. If no match is found, <code>null</code> is returned.
     *
     * @param s string used to find a matching Gender
     * @return Gender whose toString() result matches the parameter
     * <code>s</code>, null if no such match could be found
     */
    public static Gender fromString(String s) {
        if (s == null) {
            return null;
        }
        for (Gender gender : Gender.values()) {
            if (s.equals(gender.toString())) {
                return gender;
            }
        }
        return null;
    }
}
