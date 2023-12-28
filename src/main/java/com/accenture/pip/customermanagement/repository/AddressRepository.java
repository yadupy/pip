package com.accenture.pip.customermanagement.repository;

import com.accenture.pip.customermanagement.entity.Address;
import com.accenture.pip.customermanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    public Optional<Address> findByAddressId(String addressId);
}
