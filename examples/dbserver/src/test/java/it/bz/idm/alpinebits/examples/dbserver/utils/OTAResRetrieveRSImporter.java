/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.dbserver.utils;

import it.bz.idm.alpinebits.examples.dbserver.entity.guestrequests.read.HotelReservationEntity;
import it.bz.idm.alpinebits.examples.dbserver.mapper.GuestRequestsEntityMapperInstances;
import it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers.GuestRequestsReadResponse;
import it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers.HotelReservation;
import it.bz.idm.alpinebits.mapping.mapper.guestrequests.GuestRequestsMapperInstances;
import it.bz.idm.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.idm.alpinebits.xml.XmlToObjectConverter;
import it.bz.idm.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAResRetrieveRS;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Command line util to ingest OTAResRetrieveRS documents into
 * the database.
 *
 * Can be used e.g. to populate a database quickly from an
 * OTAResRetrieveRS document.
 */
public class OTAResRetrieveRSImporter {

    public static void main(String[] args) throws IOException, JAXBException {
        if (args.length == 0) {
            //CHECKSTYLE:OFF
            System.out.println("Missing OTAResRetrieveRS XML file");
            //CHECKSTYLE:ON
            System.exit(1);
        }

        EntityManager em = OTAResRetrieveRSImporter.getEntityManager();

        List<HotelReservation> hotelReservations = OTAResRetrieveRSImporter.fromXml(args[0]);

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        hotelReservations.forEach(hotelReservation -> {
            HotelReservationEntity entity = GuestRequestsEntityMapperInstances
                    .HOTEL_RESERVATION_MAPPER.toHotelReservationEntity(hotelReservation);
            em.persist(entity);
        });

        tx.commit();
        em.close();

        //CHECKSTYLE:OFF
        System.out.println("OTAResRetrieveRS XML file " + args[0] + " successful imported into DB");
        //CHECKSTYLE:ON
    }

    private static EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        return emf.createEntityManager();
    }

    private static List<HotelReservation> fromXml(String filename) throws JAXBException, IOException {
        XmlToObjectConverter<OTAResRetrieveRS> converter = OTAResRetrieveRSImporter.getXmlConverter();
        Path path = Paths.get(filename);
        InputStream is = Files.newInputStream(path);
        OTAResRetrieveRS otaResRetrieveRS = converter.toObject(is);
        GuestRequestsReadResponse guestRequestsReadResponse =
                GuestRequestsMapperInstances.HOTEL_RESERVATION_READ_RESPONSE_MAPPER.toHotelReservationReadResult(otaResRetrieveRS);
        return guestRequestsReadResponse.getHotelReservations();
    }

    private static XmlToObjectConverter<OTAResRetrieveRS> getXmlConverter() throws JAXBException {
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        return new JAXBXmlToObjectConverter.Builder(OTAResRetrieveRS.class).schema(schema).build();
    }
}
