package com.souravmodak.movierental.api.service;

import com.souravmodak.movierental.models.Movie;
import com.souravmodak.movierental.repo.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public List<Movie> getAvailableMovies() {
        return movieRepository.findByAvailableCopiesGreaterThan(0);
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void updateAvailableCopies(Long movieId, int copies) {
        movieRepository.findById(movieId).ifPresent(movie -> {
            movie.setAvailableCopies(copies);
            movieRepository.save(movie);
        });
    }
}
