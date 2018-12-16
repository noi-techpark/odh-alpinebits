/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.guestrequests.entity.read;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a {@link CustomerEntity} telephone number.
 */
@Entity
@Table(name = "customer_telephone")
public class CustomerTelephoneEntity implements Serializable {

    private static final long serialVersionUID = -2393445728680474891L;

    @Id
    @GeneratedValue
    private Long id;

    private String phoneNumber;

    private String phoneTechType;

    @ManyToOne
    private CustomerEntity customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneTechType() {
        return phoneTechType;
    }

    public void setPhoneTechType(String phoneTechType) {
        this.phoneTechType = phoneTechType;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerTelephoneEntity that = (CustomerTelephoneEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CustomerTelephoneEntity{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", phoneTechType='" + phoneTechType + '\'' +
                ", customer=" + (customer != null ? customer.getId() : null) +
                '}';
    }
}