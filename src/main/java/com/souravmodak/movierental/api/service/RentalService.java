package com.souravmodak.movierental.api.service;

import com.souravmodak.movierental.models.Customer;
import com.souravmodak.movierental.models.Movie;
import com.souravmodak.movierental.models.Rental;
import com.souravmodak.movierental.repo.CustomerRepository;
import com.souravmodak.movierental.repo.MovieRepository;
import com.souravmodak.movierental.repo.RentalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final MovieRepository movieRepository;
    private final CustomerRepository customerRepository;

    public RentalService(RentalRepository rentalRepository, MovieRepository movieRepository, CustomerRepository customerRepository) {
        this.rentalRepository = rentalRepository;
        this.movieRepository = movieRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Optional<Rental> rentMovie(Long customerId, Long movieId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        Optional<Movie> movieOpt = movieRepository.findById(movieId);

        if (customerOpt.isEmpty() || movieOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid customer ID or movie ID");
        }

        Movie movie = movieOpt.get();
        if (movie.getAvailableCopies() <= 0) {
            throw new IllegalStateException("No copies available for rent");
        }

        // Decrease available copies and save
        movie.setAvailableCopies(movie.getAvailableCopies() - 1);
        movieRepository.save(movie);

        // Create rental record
        Rental rental = new Rental(null, customerOpt.get(), movie, LocalDate.now(), null);
        return Optional.of(rentalRepository.save(rental));
    }

    @Transactional
    public void returnMovie(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Rental not found"));

        // Set return date
        rental.setReturnDate(LocalDate.now());
        rentalRepository.save(rental);

        // Increase available copies
        Movie movie = rental.getMovie();
        movie.setAvailableCopies(movie.getAvailableCopies() + 1);
        movieRepository.save(movie);
    }

    public List<Rental> getRentalHistory(Long customerId) {
        return rentalRepository.findByCustomerId(customerId);
    }
}
