/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.guestrequests.resretrievers;

import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.Company;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAResRetrieveRS;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Map AlpineBits company objects for OTA_ResRetrieveRS
 * requests to business objects and vice versa.
 */
@Mapper
public interface CompanyMapper {

    @Mapping(target = "name", source = "companyName.value")
    @Mapping(target = "code", source = "companyName.code")
    @Mapping(target = "codeContext", source = "companyName.codeContext")
    @Mapping(target = "email.email", source = "email")
    @Mapping(target = "address.countryNameCode", source = "addressInfo.countryName.code")
    @Mapping(target = "telephone", source = "telephoneInfo")
    Company toCompany(
            OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo.Profiles.ProfileInfo.Profile.CompanyInfo companyInfo,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

    @InheritInverseConfiguration
    OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo.Profiles.ProfileInfo.Profile.CompanyInfo toOTACompany(
            Company company,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );
}
