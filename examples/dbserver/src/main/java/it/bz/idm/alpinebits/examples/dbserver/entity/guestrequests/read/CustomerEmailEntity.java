/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.dbserver.entity.guestrequests.read;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a {@link CustomerEntity} email.
 */
@Entity
@Table(name = "customer_email")
public class CustomerEmailEntity implements Serializable {

    private static final long serialVersionUID = -6709588768995601690L;

    @Id
    @MapsId
    @OneToOne
    private CustomerEntity customer;

    private String email;

    private String remark;

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerEmailEntity that = (CustomerEmailEntity) o;
        return Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer);
    }

    @Override
    public String toString() {
        return "CustomerEmailEntity{" +
                "customer=" + (customer != null ? customer.getId() : null) +
                ", email='" + email + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
