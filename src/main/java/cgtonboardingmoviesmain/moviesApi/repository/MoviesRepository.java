package cgtonboardingmoviesmain.moviesApi.repository;

import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.domain.Roles;
import cgtonboardingmoviesmain.moviesApi.dto.CreateMovieDto;

import java.util.List;


public interface MoviesRepository {

    List<Movie> findAll();

    Movie findById(int id);

    int save(Movie movie);

    void saveRoles(List<Roles> roles, int movieID);

    List<Roles> getRoles(int id);

    void delete(int id);

    void update(CreateMovieDto createMovieDto, int id);
}
