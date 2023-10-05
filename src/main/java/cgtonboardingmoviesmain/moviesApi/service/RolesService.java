package cgtonboardingmoviesmain.moviesApi.service;

import cgtonboardingmoviesmain.moviesApi.domain.GenreResponse;
import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.dto.RoleDto;
import cgtonboardingmoviesmain.moviesApi.exception.InternalServerErrorException;
import cgtonboardingmoviesmain.moviesApi.logger.LogLevel;
import cgtonboardingmoviesmain.moviesApi.logger.LoggerImpl;
import cgtonboardingmoviesmain.moviesApi.logger.LoggerModel;
import cgtonboardingmoviesmain.moviesApi.repository.MoviesRepository;
import cgtonboardingmoviesmain.moviesApi.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RolesService {
    RestTemplate restTemplate;
    RolesRepository rolesRepository;

    LoggerImpl logger;

    @Value("${genres.url}")
    private String genresUrl;

    @Autowired
    public RolesService(RestTemplate restTemplate, RolesRepository rolesRepository, LoggerImpl logger) {
        this.restTemplate = restTemplate;
        this.rolesRepository = rolesRepository;
        this.logger = logger;
    }

    public List<RoleDto> getRoleFull(Movie movie, LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO, lm, "Started get full Role in service");
        if (movie == null || movie.getMovieId() == null) {
            logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "movie or movie id is null");
            return Collections.emptyList();
        }
        List<RoleDto> roles = rolesRepository.getRoles(movie, lm);
        logger.formatLogMessageGen(LogLevel.INFO, lm, "Finished getting roles");
        return roles;
    }

    public String getGenreIdToGenreName(int genreId, LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO, lm, "Started geting Genre name from id in service");
        String genreResponse = null;
        try {
            ResponseEntity<GenreResponse> genreResponseEntity = restTemplate.getForEntity(genresUrl, GenreResponse.class, genreId);
            if (genreResponseEntity.getStatusCode().is2xxSuccessful()) {
                genreResponse = genreResponseEntity.getBody().getGenreName();
                if (genreResponseEntity != null) {
                    logger.formatLogMessageGen(LogLevel.INFO, lm, "Succesfuly get genre name");
                } else {
                    logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Genre response is null");
                }
            } else {
                logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Error with http response");
                throw new InternalServerErrorException("Error with genres api");
            }
        }catch (RestClientException e) {
            logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Error with rest client");
            throw new RestClientException("Error with request");
        }
        logger.formatLogMessageGen(LogLevel.INFO, lm, "Genre name is "+genreResponse);
        return genreResponse;
    }

}

