package cgtonboardingmoviesmain.moviesApi.repository;

import cgtonboardingmoviesmain.moviesApi.ActorsApiConnect;
import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.domain.Roles;
import cgtonboardingmoviesmain.moviesApi.dto.RoleDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Repository
public class RolesRepository {

    MoviesRepository moviesRepository;
    JdbcTemplate jdbcTemplate;
    ActorsApiConnect apiConnect;


    public RolesRepository(MoviesRepository moviesRepository, JdbcTemplate jdbcTemplate,ActorsApiConnect apiConnect) {
        this.moviesRepository = moviesRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.apiConnect = apiConnect;
    }

    public List<RoleDto> getRoles(Movie movie){
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
            roleDto.setActorImage(apiConnect.getActorImage(actorId));
            roleDto.setRoleName(role.getRoleName());

            roleDtoList.add(roleDto);
        }

        return roleDtoList;
    }

}
