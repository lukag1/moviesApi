package cgtonboardingmoviesmain.moviesApi.service;

import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.domain.Roles;
import cgtonboardingmoviesmain.moviesApi.dto.RoleDto;
import cgtonboardingmoviesmain.moviesApi.repository.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class RolesService {
    JdbcTemplate jdbcTemplate;
    RestTemplate restTemplate;
    MoviesRepository moviesRepository;

    @Value("${genres.url}")
    private String genresUrl;

    @Autowired
    public RolesService(JdbcTemplate jdbcTemplate, RestTemplate restTemplate,MoviesRepository moviesRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.restTemplate = restTemplate;
        this.moviesRepository = moviesRepository;
    }

    public List<RoleDto> getRoleFull(Movie movie) {
        if (movie == null || movie.getMovieId() == null) {
            return Collections.emptyList();
        }

        List<Roles> rolesList = moviesRepository.getRoles(movie.getMovieId());

        List<RoleDto> roleDtoList = new ArrayList<>();

        for (Roles role : rolesList) {
            int actorId = role.getActorId();
            String sql = "SELECT full_name, image_name FROM actorsdb.actors WHERE actor_id = ?";

            Map<String, Object> result = jdbcTemplate.queryForMap(sql, actorId);

            RoleDto roleDto = new RoleDto();
            roleDto.setActorId(actorId);
            roleDto.setFullName((String) result.get("full_name"));
            roleDto.setImageName((String) result.get("image_name"));
            roleDto.setActorImage("actorImageId_"+actorId+".jpg");
            roleDto.setRoleName(role.getRoleName());

            roleDtoList.add(roleDto);
        }

        return roleDtoList;
    }

    public String getGenreIdToGenreName(int genreId){
        ResponseEntity<GenreResponse> genreResponseEntity = restTemplate.getForEntity(genresUrl, GenreResponse.class, genreId);
        String genreResponse = genreResponseEntity.getBody().getGenreName();

        return genreResponse;
    }

}

