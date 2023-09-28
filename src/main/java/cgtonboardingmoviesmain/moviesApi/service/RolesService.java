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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

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
            roleDto.setActorImage(base64Converter((String) result.get("image_name")));
            roleDto.setRoleName(role.getRoleName());

            roleDtoList.add(roleDto);
        }

        return roleDtoList;
    }

    String base64Converter(String name) {
        File file = new File("C:\\Users\\gluka\\Documents\\Onboarding Project\\actorsApi\\"+name);

        String base64;
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] fileContent = new byte[(int) file.length()];
            fis.read(fileContent);
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            base64 = "<base64 image>," + encodedString;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return base64;
    }

    public String getGenreIdToGenreName(int genreId){
        ResponseEntity<GenreResponse> genreResponseEntity = restTemplate.getForEntity(genresUrl, GenreResponse.class, genreId);
        String genreResponse = genreResponseEntity.getBody().getGenreName();

        return genreResponse;
    }

}

