package cgtonboardingmoviesmain.moviesApi.mapper;

import cgtonboardingmoviesmain.moviesApi.FileManager;
import cgtonboardingmoviesmain.moviesApi.controller.MoviesController;
import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.dto.CreateMovieDto;
import cgtonboardingmoviesmain.moviesApi.dto.MovieDto;
import cgtonboardingmoviesmain.moviesApi.exception.InternalServerErrorException;
import cgtonboardingmoviesmain.moviesApi.service.RolesService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class Mapper {
    JdbcTemplate jdbcTemplate;
    RolesService rolesService;

    FileManager fileManager;

    public Mapper(RolesService rolesService,JdbcTemplate jdbcTemplate, FileManager fileManager) {
        this.rolesService = rolesService;
        this.jdbcTemplate = jdbcTemplate;
        this.fileManager = fileManager;
    }

    public MovieDto movieToMovieDto(Movie movie){
        MovieDto movieDto = new MovieDto();

        movieDto.setMovieId(movie.getMovieId());
        movieDto.setName(movie.getName());
        movieDto.setHref(linkTo(MoviesController.class).slash(movieDto.getMovieId()).toString());
        movieDto.setReleaseDate(movie.getReleaseDate());
        movieDto.setLanguage(movie.getLanguage());
        movieDto.setDirector(movie.getDirector());
        movieDto.setDescription(movie.getDescription());
        movieDto.setYear(movie.getYear());
        movieDto.setRuntime(movie.getRuntime());
        movieDto.setRoles(rolesService.getRoleFull(movie));
        movieDto.setMovieImageName(movie.getMovieImageName());
        movieDto.setGenreName(rolesService.getGenreIdToGenreName(movie.getGenre()));
        movieDto.setMovieImage(fileManager.saveCode(movie));

        return movieDto;
    }

    public Movie createMovieDtoToMovie(CreateMovieDto createMovieDto){
        Movie movie = new Movie();

        movie.setName(createMovieDto.getName());
        movie.setGenre(createMovieDto.getGenreId());
        movie.setReleaseDate(createMovieDto.getReleaseDate());
        movie.setLanguage(createMovieDto.getLanguage());
        movie.setDirector(createMovieDto.getDirector());
        movie.setRuntime(createMovieDto.getRuntime());
        movie.setDescription(createMovieDto.getDescription());
        movie.setYear(createMovieDto.getYear());
        movie.setMovieImageName(fileManager.saveImageName(createMovieDto));
        movie.setRoles(createMovieDto.getActors());

        return movie;
    }

}

