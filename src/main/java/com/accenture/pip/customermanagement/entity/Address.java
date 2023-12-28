package com.accenture.pip.customermanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;

@Entity
@Table(name = "ADDRESS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ADDRESS_ID")
    @UuidGenerator
    private String addressId;

    @Column(name = "HOUSE_NUMBER")
    private String houseNo;

    @Column(name = "STREET")
    private String street;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "PINCODE")
    private int pinCode;

    @ManyToOne
    @JoinColumn(name="CUSTOMER_ID", nullable=false)
    private Customer customer;
}
