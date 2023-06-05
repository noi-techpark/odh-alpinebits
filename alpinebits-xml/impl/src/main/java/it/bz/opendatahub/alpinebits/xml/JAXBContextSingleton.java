// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.xml;

import it.bz.opendatahub.alpinebits.xml.schema.ota.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * This class holds a {@link JAXBContext} instance as a singleton.
 *
 * The JAXBContext is initialized with the {@link ObjectFactory}. That
 * means, that the JAXBContext provided by this singleton is usable only
 * for classes declared in that ObjectFactory.
 */
public final class JAXBContextSingleton {

    private static final JAXBContext INSTANCE;

    static {
        try {
            INSTANCE = JAXBContext.newInstance(ObjectFactory.class);
        } catch (JAXBException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private JAXBContextSingleton() {
        // Empty
    }

    public static JAXBContext getInstance() {
        return INSTANCE;
    }

}
