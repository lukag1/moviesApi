package cgtonboardingmoviesmain.moviesApi.controller;

import cgtonboardingmoviesmain.moviesApi.Logger.LogLevel;
import cgtonboardingmoviesmain.moviesApi.Logger.LoggerImpl;
import cgtonboardingmoviesmain.moviesApi.Logger.LoggerModel;
import cgtonboardingmoviesmain.moviesApi.dto.CreateMovieDto;
import cgtonboardingmoviesmain.moviesApi.dto.MovieDto;
import cgtonboardingmoviesmain.moviesApi.service.MoviesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/movies")
public class MoviesController {
    MoviesService moviesService;
    LoggerImpl loggerImpl;

    @Autowired
    public MoviesController(MoviesService moviesService, LoggerImpl loggerImpl) {
        this.moviesService = moviesService;
        this.loggerImpl = loggerImpl;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAll(@RequestHeader ("X-Request-ID") int xRequestedId){
        LoggerModel lm = loggerImpl.generateLM(xRequestedId);
        loggerImpl.formatLogMessageGen(LogLevel.INFO, lm, "Get all method invoked");

        List<MovieDto> movieDtos = moviesService.findAll();

        loggerImpl.formatLogMessageGen(LogLevel.INFO, lm, "Get all method finished");
        return new ResponseEntity<>(movieDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<MovieDto>> getById(@PathVariable int id, @RequestHeader ("X-Request-ID") int xRequestedId){
        LoggerModel lm = loggerImpl.generateLM(xRequestedId);
        loggerImpl.formatLogMessageGen(LogLevel.INFO, lm, "Get by Id method invoked");

        Optional<MovieDto> movie= moviesService.findById(id);

        loggerImpl.formatLogMessageGen(LogLevel.INFO, lm, "Get by Id method finished");
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieDto> addMovie(@RequestBody CreateMovieDto createMovieDto, @RequestHeader ("X-Request-ID") int xRequestedId){
        LoggerModel lm = loggerImpl.generateLM(xRequestedId);
        loggerImpl.formatLogMessageGen(LogLevel.INFO, lm, "Add movie method invoked");
        MovieDto movieDto = moviesService.addMovie(createMovieDto);
        loggerImpl.formatLogMessageGen(LogLevel.INFO, lm, "Add movie method finished");
        return new ResponseEntity<>(movieDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@RequestBody CreateMovieDto createMovieDto, @PathVariable int id, @RequestHeader ("X-Request-ID") int xRequestedId){
        LoggerModel lm = loggerImpl.generateLM(xRequestedId);
        loggerImpl.formatLogMessageGen(LogLevel.INFO, lm, "Update movie method invoked");
        MovieDto movieDto = moviesService.updateMovie(createMovieDto, id);
        loggerImpl.formatLogMessageGen(LogLevel.INFO, lm, "Update movie method finished");

        return new ResponseEntity<>(movieDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable int id, @RequestHeader ("X-Request-ID") int xRequestedId){
        LoggerModel lm = loggerImpl.generateLM(xRequestedId);
        loggerImpl.formatLogMessageGen(LogLevel.INFO, lm, "Delete movie method invoked");
        moviesService.deleteMovie(id);
        loggerImpl.formatLogMessageGen(LogLevel.INFO, lm, "Delete movie method deleted");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
