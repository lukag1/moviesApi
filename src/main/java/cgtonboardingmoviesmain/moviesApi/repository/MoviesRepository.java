package cgtonboardingmoviesmain.moviesApi.repository;

import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.domain.Roles;
import cgtonboardingmoviesmain.moviesApi.dto.CreateMovieDto;
import cgtonboardingmoviesmain.moviesApi.logger.LoggerModel;

import java.util.List;


public interface MoviesRepository {

    List<Movie> findAll(LoggerModel lm);

    Movie findById(int id, LoggerModel lm);

    int save(Movie movie,LoggerModel lm);

    void saveRoles(List<Roles> roles, int movieID,LoggerModel lm);

    List<Roles> getRoles(int id,LoggerModel lm);

    void delete(int id,LoggerModel lm);

    void update(CreateMovieDto createMovieDto, int id,LoggerModel lm);
}
