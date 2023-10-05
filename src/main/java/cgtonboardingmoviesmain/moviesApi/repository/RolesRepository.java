package cgtonboardingmoviesmain.moviesApi.repository;

import cgtonboardingmoviesmain.moviesApi.actorsHelper.ActorsApiConnect;
import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.domain.Roles;
import cgtonboardingmoviesmain.moviesApi.dto.RoleDto;
import cgtonboardingmoviesmain.moviesApi.logger.LogLevel;
import cgtonboardingmoviesmain.moviesApi.logger.LoggerImpl;
import cgtonboardingmoviesmain.moviesApi.logger.LoggerModel;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RolesRepository {
    MoviesRepository moviesRepository;
    JdbcTemplate jdbcTemplate;
    ActorsApiConnect apiConnect;

    LoggerImpl logger;

    public RolesRepository(MoviesRepository moviesRepository, JdbcTemplate jdbcTemplate,ActorsApiConnect apiConnect,LoggerImpl logger) {
        this.moviesRepository = moviesRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.apiConnect = apiConnect;
        this.logger = logger;
    }

    public List<RoleDto> getRoles(Movie movie, LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO,lm,"Started getting roles in repository");
        List<RoleDto> roleDtoList = new ArrayList<>();
        if (movie != null) {
            List<Roles> rolesList = moviesRepository.getRoles(movie.getMovieId(), lm);
            if(rolesList != null){
            try {
                for (Roles role : rolesList) {
                    int actorId = role.getActorId();
                    String sql = "SELECT full_name, image_name FROM actorsdb.actors WHERE actor_id = ?";
                    Map<String, Object> result = jdbcTemplate.queryForMap(sql, actorId);

                    RoleDto roleDto = new RoleDto();
                    roleDto.setActorId(actorId);
                    roleDto.setFullName((String) result.get("full_name"));
                    roleDto.setImageName((String) result.get("image_name"));
                    String actorImage = apiConnect.getActorImage(actorId,lm);
                    if(actorImage.isBlank()){
                        logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Error with gettin image name from actors, image name is blank");
                    }else {
                        logger.formatLogMessageGen(LogLevel.INFO,lm,"Actors api returned good image name");
                        roleDto.setActorImage(actorImage);
                    }
                    roleDto.setRoleName(role.getRoleName());
                    roleDtoList.add(roleDto);
                    logger.formatLogMessageGen(LogLevel.INFO,lm,"Succesfuly getted role in repository");
                }
            } catch (IncorrectResultSizeDataAccessException e) {
                logger.formatLogMessageException(LogLevel.ERROR, lm, "IncorrectResultSizeDataAccessException", "Error with getting role");
                throw new IncorrectResultSizeDataAccessException(1);
            } catch (DataAccessException e) {
                logger.formatLogMessageException(LogLevel.ERROR, lm, "DataAccessException", "Error with DataAccessException");
                //throw new DataAccessException("");
             }
            }
            else {
                logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Role list is empty");
            }
        } else {
            logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Movie doesnt exist");

        }
        return roleDtoList;
    }

}
