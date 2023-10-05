package cgtonboardingmoviesmain.moviesApi.mapper;

import cgtonboardingmoviesmain.moviesApi.filesHandler.FileManager;
import cgtonboardingmoviesmain.moviesApi.controller.MoviesController;
import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.dto.CreateMovieDto;
import cgtonboardingmoviesmain.moviesApi.dto.MovieDto;
import cgtonboardingmoviesmain.moviesApi.logger.LogLevel;
import cgtonboardingmoviesmain.moviesApi.logger.LoggerImpl;
import cgtonboardingmoviesmain.moviesApi.logger.LoggerModel;
import cgtonboardingmoviesmain.moviesApi.service.RolesService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class Mapper {
    RolesService rolesService;
    FileManager fileManager;

    LoggerImpl logger;
    public Mapper(RolesService rolesService, FileManager fileManager,LoggerImpl logger) {
        this.rolesService = rolesService;
        this.fileManager = fileManager;
        this.logger = logger;
    }

    public MovieDto movieToMovieDto(Movie movie, LoggerModel lm){
        MovieDto movieDto = new MovieDto();
        logger.formatLogMessageGen(LogLevel.INFO, lm, "Mapping movie to Movie Dto started");
        movieDto.setMovieId(movie.getMovieId());
        movieDto.setName(movie.getName());
        movieDto.setHref(linkTo(MoviesController.class).slash(movieDto.getMovieId()).toString());
        movieDto.setReleaseDate(movie.getReleaseDate());
        movieDto.setLanguage(movie.getLanguage());
        movieDto.setDirector(movie.getDirector());
        movieDto.setDescription(movie.getDescription());
        movieDto.setYear(movie.getYear());
        movieDto.setRuntime(movie.getRuntime());
        movieDto.setRoles(rolesService.getRoleFull(movie, lm));
        movieDto.setMovieImageName(movie.getMovieImageName());
        movieDto.setGenreName(rolesService.getGenreIdToGenreName(movie.getGenre(),lm));
        movieDto.setMovieImage(fileManager.saveCode(movie));
        logger.formatLogMessageGen(LogLevel.INFO, lm, "Mapping movie to Movie Dto finished");
        return movieDto;
    }

    public Movie createMovieDtoToMovie(CreateMovieDto createMovieDto,LoggerModel lm){
        Movie movie = new Movie();
        logger.formatLogMessageGen(LogLevel.INFO, lm, "Mapping Movie Dto to movie started");

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

        logger.formatLogMessageGen(LogLevel.INFO, lm, "Mapping Movie Dto to movie finished");
        return movie;
    }

}

