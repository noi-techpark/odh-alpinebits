/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.guestrequests.entity.read;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Payment card information.
 */
@Entity
@Table(name = "payment_card")
public class PaymentCardEntity implements Serializable {

    private static final long serialVersionUID = -2052369453345563623L;

    @Id
    @GeneratedValue
    private Long id;

    private String cardCode;

    private Integer expireDate;

    private String cardHolderName;

    private String cardNumberPlain;

    private String encryptedValue;

    private String encryptionMethod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public Integer getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Integer expireDate) {
        this.expireDate = expireDate;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumberPlain() {
        return cardNumberPlain;
    }

    public void setCardNumberPlain(String cardNumberPlain) {
        this.cardNumberPlain = cardNumberPlain;
    }

    public String getEncryptedValue() {
        return encryptedValue;
    }

    public void setEncryptedValue(String encryptedValue) {
        this.encryptedValue = encryptedValue;
    }

    public String getEncryptionMethod() {
        return encryptionMethod;
    }

    public void setEncryptionMethod(String encryptionMethod) {
        this.encryptionMethod = encryptionMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentCardEntity that = (PaymentCardEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PaymentCardEntity{" +
                "id=" + id +
                ", cardCode='" + cardCode + '\'' +
                ", expireDate=" + expireDate +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", cardNumberPlain='" + cardNumberPlain + '\'' +
                ", encryptedValue='" + encryptedValue + '\'' +
                ", encryptionMethod='" + encryptionMethod + '\'' +
                '}';
    }
}
