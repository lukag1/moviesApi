package cgtonboardingmoviesmain.moviesApi.service;

import cgtonboardingmoviesmain.moviesApi.domain.GenreResponse;
import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.dto.RoleDto;
import cgtonboardingmoviesmain.moviesApi.repository.MoviesRepository;
import cgtonboardingmoviesmain.moviesApi.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RolesService {
    JdbcTemplate jdbcTemplate;
    RestTemplate restTemplate;
    MoviesRepository moviesRepository;

    RolesRepository rolesRepository;

    @Value("${genres.url}")
    private String genresUrl;

    @Autowired
    public RolesService(JdbcTemplate jdbcTemplate, RestTemplate restTemplate,MoviesRepository moviesRepository,RolesRepository rolesRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.restTemplate = restTemplate;
        this.moviesRepository = moviesRepository;
        this.rolesRepository = rolesRepository;
    }

    public List<RoleDto> getRoleFull(Movie movie) {
        if (movie == null || movie.getMovieId() == null) {
            return Collections.emptyList();
        }
        return rolesRepository.getRoles(movie);
    }

    public String getGenreIdToGenreName(int genreId){
        ResponseEntity<GenreResponse> genreResponseEntity = restTemplate.getForEntity(genresUrl, GenreResponse.class, genreId);
        String genreResponse = genreResponseEntity.getBody().getGenreName();

        return genreResponse;
    }

}

