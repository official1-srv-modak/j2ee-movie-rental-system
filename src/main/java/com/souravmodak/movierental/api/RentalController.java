package com.souravmodak.movierental.api;

import com.souravmodak.movierental.api.service.RentalService;
import com.souravmodak.movierental.models.Rental;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rentals")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/rent")
    public String rentMovieForm(Model model) {
        model.addAttribute("rental", new Rental());
        return "rent-movie";
    }

    @PostMapping("/rent")
    public String rentMovie(@RequestParam Long customerId, @RequestParam Long movieId) {
        rentalService.rentMovie(customerId, movieId);
        return "redirect:/rentals";
    }

    @GetMapping
    public String rentalHistory(Model model, @RequestParam Long customerId) {
        List<Rental> rentals = rentalService.getRentalHistory(customerId);
        model.addAttribute("rentals", rentals);
        return "rentals";
    }

    @PostMapping("/return/{rentalId}")
    public String returnMovie(@PathVariable Long rentalId) {
        rentalService.returnMovie(rentalId);
        return "redirect:/rentals";
    }
}
