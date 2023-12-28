package com.accenture.pip.customermanagement.repository;

import com.accenture.pip.customermanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    public Optional<Customer> findByEmailOrContactNumber(String email, String contactNumber);

    public Optional<Customer> findByCustomerId(String customerId);

    @Query("select c from Customer c where c.createdAt <= :creationDateTime")
    List<Customer> findAllWithCreationDateTimeBefore(
            @Param("creationDateTime") LocalDateTime creationDateTime);
}
