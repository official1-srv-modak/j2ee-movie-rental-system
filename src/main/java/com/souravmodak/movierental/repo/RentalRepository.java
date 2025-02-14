package com.souravmodak.movierental.repo;

import com.souravmodak.movierental.models.Customer;
import com.souravmodak.movierental.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByCustomer(Customer customer);
    List<Rental> findByReturnDateIsNull();
    List<Rental> findByCustomerId(Long customerId);
}
