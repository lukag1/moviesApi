package cgtonboardingmoviesmain.moviesApi.service;

import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.dto.CreateMovieDto;
import cgtonboardingmoviesmain.moviesApi.dto.MovieDto;
import cgtonboardingmoviesmain.moviesApi.logger.LogLevel;
import cgtonboardingmoviesmain.moviesApi.logger.LoggerImpl;
import cgtonboardingmoviesmain.moviesApi.logger.LoggerModel;
import cgtonboardingmoviesmain.moviesApi.mapper.Mapper;
import cgtonboardingmoviesmain.moviesApi.repository.MoviesRepository;
import jakarta.validation.Valid;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MoviesService {
    MoviesRepository moviesRepository;
    Mapper mapper;

    LoggerImpl logger;

    @Autowired
    public MoviesService(MoviesRepository moviesRepository, Mapper mapper,LoggerImpl logger) {
        this.moviesRepository = moviesRepository;
        this.mapper = mapper;
        this.logger = logger;
    }

    public List<MovieDto> findAll(LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO,lm,"Started findAll in Service");
        List<Movie> movies = moviesRepository.findAll(lm);
        List<MovieDto> movieDtos = movies.stream()
                .map(movie -> mapper.movieToMovieDto(movie, lm))
                .toList();
        return movieDtos;
    }

    public Optional<MovieDto> findById(int id, LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO,lm,"Started find by id in Service");
        Movie movie =  moviesRepository.findById(id, lm);
        return Optional.ofNullable(mapper.movieToMovieDto(movie,lm));
    }

    public MovieDto addMovie(@Valid CreateMovieDto createMovieDto, LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO,lm,"Started addind movie in Service");
        if(createMovieDto !=null) {
            Movie movie = mapper.createMovieDtoToMovie(createMovieDto,lm);
            int generatedMovieId = moviesRepository.save(movie, lm);
            moviesRepository.saveRoles(createMovieDto.getActors(), generatedMovieId, lm);
            MovieDto movieDto = mapper.movieToMovieDto(movie,lm);
            movieDto.setMovieId(generatedMovieId);

            return movieDto;
        }else {
            logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Create movie object is null");
            return null;
        }
    }


    public MovieDto updateMovie(@Valid CreateMovieDto createMovieDto, int id, LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO,lm,"Started updating movie in Service");
        if(createMovieDto !=null){
            moviesRepository.update(createMovieDto, id, lm);
            Movie updatedMovie = moviesRepository.findById(id, lm);
            MovieDto movieDto = mapper.movieToMovieDto(updatedMovie,lm);
            movieDto.setMovieId(id);

            return movieDto;
        }else {
            logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Create movie object is null");
            return null;
        }

    }

    public void deleteMovie(int id, LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO,lm,"Started deleting movie in Service");
        moviesRepository.delete(id, lm);
    }
}
