package com.souravmodak.movierental.api;

import com.souravmodak.movierental.api.service.CustomerService;
import com.souravmodak.movierental.models.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String listCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customers";  // This should be mapped to a Thymeleaf template named `customers.html`
    }

    @GetMapping("/register")
    public String registerCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register-customer";
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute Customer customer) {
        customerService.registerCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/{id}")
    public String customerDetails(@PathVariable Long id, Model model) {
        customerService.getCustomerById(id).ifPresent(customer -> model.addAttribute("customer", customer));
        return "customer-details";
    }
}
