package cgtonboardingmoviesmain.moviesApi.controller;

import cgtonboardingmoviesmain.moviesApi.dto.CreateMovieDto;
import cgtonboardingmoviesmain.moviesApi.dto.MovieDto;
import cgtonboardingmoviesmain.moviesApi.service.MoviesService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping(value = "/movies")
public class MoviesController {
    MoviesService moviesService;

    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAll(){;
        return new ResponseEntity<>(moviesService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<MovieDto>> getById(@PathVariable int id){
        return new ResponseEntity<>(moviesService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieDto> addMovie(@RequestBody CreateMovieDto createMovieDto){
        return new ResponseEntity<>(moviesService.addMovie(createMovieDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@RequestBody CreateMovieDto createMovieDto, @PathVariable int id){
        return new ResponseEntity<>(moviesService.updateMovie(createMovieDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable int id){
        moviesService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
