package com.souravmodak.movierental.repo;

import com.souravmodak.movierental.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameLike(String name);
    List<Customer> findByEmail(String email);
}
