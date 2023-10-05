package cgtonboardingmoviesmain.moviesApi.repository;

import cgtonboardingmoviesmain.moviesApi.filesHandler.FileManager;
import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.domain.Roles;
import cgtonboardingmoviesmain.moviesApi.dto.CreateMovieDto;
import cgtonboardingmoviesmain.moviesApi.exception.ResourceNotFoundException;
import cgtonboardingmoviesmain.moviesApi.exception.SqlSyntaxException;
import cgtonboardingmoviesmain.moviesApi.logger.LogLevel;
import cgtonboardingmoviesmain.moviesApi.logger.LoggerImpl;
import cgtonboardingmoviesmain.moviesApi.logger.LoggerModel;
import cgtonboardingmoviesmain.moviesApi.mapper.RowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieRepositoryImp implements MoviesRepository {

    JdbcTemplate jdbcTemplate;
    FileManager fileManager;
    RowMapper rowMapper;
    LoggerImpl logger;

    public MovieRepositoryImp(JdbcTemplate jdbcTemplate,FileManager fileManager,RowMapper rowMapper,LoggerImpl logger) {
        this.fileManager = fileManager;
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.logger = logger;
    }

    @Override
    public List<Movie> findAll(LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO, lm, "Started find All in repository");
        List<Movie> movies = null;
        try {
            String sql = "SELECT * FROM moviesdb.movies";
            movies = jdbcTemplate.query(sql, new RowMapper.MovieRowMapper());
        } catch (BadSqlGrammarException e) {
            logger.formatLogMessageException(LogLevel.ERROR, lm,"BadSqlGrammarException", "Error with database sql code");
            throw new SqlSyntaxException("Error with database sql code");
        } catch (DataAccessException e) {
            logger.formatLogMessageException(LogLevel.ERROR, lm,"DataAccessException", "Error with data acess");
            //throw
        }
        if(movies != null){
            //moguca greska
            logger.formatLogMessageJSON(LogLevel.INFO, lm, movies);
        }else {
            logger.formatLogMessageGen(LogLevel.INFO, lm, "Movie list is empty");
        }
        return movies;
    }

    @Override
    public Movie findById(int id, LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO, lm, "Started find by Id in repository");
        Movie movie;
        try {
            String sql = "SELECT * FROM moviesdb.movies WHERE movie_id = ?";
            movie = jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper.MovieRowMapper());
            logger.formatLogMessageGen(LogLevel.INFO, lm, "Successfully found movie");
        } catch (EmptyResultDataAccessException ex) {
            logger.formatLogMessageException(LogLevel.ERROR, lm, "EmptyResultDataAccessException", "Error with finding movie");
            throw new ResourceNotFoundException("Movie not found with ID: " + id);
        } catch (SqlSyntaxException ex) {
            logger.formatLogMessageException(LogLevel.ERROR, lm, "SqlSyntaxException", "Error with sql code");
            throw new SqlSyntaxException("Error with database sql code");
        }
        return movie;
    }

    @Override
    public int save(Movie movie, LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO, lm, "Started saving movie in repository");
        Number generatedKey = null;
        if (movie != null) {
            KeyHolder keyHolder = null;
            try {
                String SQL = "INSERT INTO movies (name, genre, release_date, language, stars, writers, director, runtime, description, year, comment, movie_image_name, rating) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                keyHolder = new GeneratedKeyHolder();

                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, movie.getName());
                    ps.setInt(2, movie.getGenre());
                    ps.setDate(3, java.sql.Date.valueOf(movie.getReleaseDate()));
                    ps.setString(4, movie.getLanguage());
                    ps.setString(5, movie.getStars());
                    ps.setString(6, movie.getWriters());
                    ps.setString(7, movie.getDirector());
                    ps.setString(8, movie.getRuntime());
                    ps.setString(9, movie.getDescription());
                    ps.setInt(10, movie.getYear());
                    ps.setString(11, movie.getComment());
                    ps.setString(12, movie.getMovieImageName());
                    ps.setDouble(13, movie.getRating());
                    return ps;
                }, keyHolder);

                generatedKey = keyHolder.getKey();
                if (generatedKey != null) {
                    logger.formatLogMessageGen(LogLevel.INFO, lm, "Successfully saved movie");
                    movie.setMovieId(generatedKey.intValue());
                } else {
                    logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Failed to retrieve the generated movieId.");
                    throw new RuntimeException("Failed to retrieve the generated movieId.");
                }
            } catch (BadSqlGrammarException e) {
                logger.formatLogMessageException(LogLevel.ERROR, lm, "SqlSyntaxException", "Error with sql code");
                throw new SqlSyntaxException("Error with sql code");
            } catch (DataAccessException e) {
                //throw
                //logg
            }
        }else {
            logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Failed to get movie to save");
        }
        return generatedKey.intValue();
    }


    @Override
    public void saveRoles(List<Roles> actors, int movieID, LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO, lm, "Started saving roles in repository");
        if(actors != null){
            try {
                for(Roles r: actors){
                    String SQL = "INSERT INTO roles (actor_id, movie_id, role_name) VALUES (?, ?, ?)";
                    jdbcTemplate.update(SQL, r.getActorId(),movieID ,r.getRoleName());
                    logger.formatLogMessageGen(LogLevel.INFO, lm, "Successfully saved role");
                }
            }catch (BadSqlGrammarException e){
                logger.formatLogMessageException(LogLevel.ERROR, lm, "SqlSyntaxException", "Error with sql code");
                throw new SqlSyntaxException("Error with sql code");
            }catch (DataAccessException e){
                //log
                //throw
            }
        }else {
            logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Failed to get actors to save");

        }
    }

    @Override
    public List<Roles> getRoles(int movieId, LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO, lm, "Started geting roles in repository");
        List<Roles> rolesList = new ArrayList<>();
        try{
            String sql = "SELECT * FROM moviesdb.roles WHERE movie_id = ?";
            rolesList = jdbcTemplate.query(sql, new Object[]{movieId}, new RowMapper.RoleRowMapper());
        }catch (BadSqlGrammarException e){
            logger.formatLogMessageException(LogLevel.ERROR, lm, "SqlSyntaxException", "Error with sql code");
            throw new SqlSyntaxException("Error with sql code");
        }
        if(rolesList != null){
            logger.formatLogMessageJSON(LogLevel.INFO, lm, "Roles", rolesList);
        }else {
            logger.formatLogMessageGen(LogLevel.INFO, lm, "Roles are empty");
        }
        return rolesList;
    }

    @Override
    public void delete(int id, LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO, lm, "Started deleting roles in repository");
        Movie movie = findById(id, lm);
        if(movie != null){
            try{
                fileManager.deleteImage(movie,lm);

                String deleteRolesSql = "DELETE FROM roles WHERE movie_id = ?";
                jdbcTemplate.update(deleteRolesSql, id);

                String sql = "DELETE FROM moviesdb.movies WHERE movie_id = ?";
                jdbcTemplate.update(sql, id);
                logger.formatLogMessageGen(LogLevel.INFO, lm, "Deleted movie");
            }catch (BadSqlGrammarException e){
                logger.formatLogMessageException(LogLevel.ERROR, lm, "SqlSyntaxException", "Error with sql code");
                throw new SqlSyntaxException("Error with sql code");
            }catch (DataAccessException e){
                //throw
            }
        }else {
            logger.formatErrorLogMessageError(LogLevel.ERROR, lm, "Movie didnt exist");
        }

    }

    @Override
    public void update(CreateMovieDto createMovieDto, int id, LoggerModel lm) {
        logger.formatLogMessageGen(LogLevel.INFO, lm, "Started updating in repository");
        try {
            jdbcTemplate.update(
                    "UPDATE movies SET name = ?, genre = ?, release_date = ?, year = ?, language = ?, description = ?, director = ?, runtime = ?, movie_image_name = ? WHERE movie_id = ?",
                    createMovieDto.getName(),
                    createMovieDto.getGenreId(),
                    createMovieDto.getReleaseDate(),
                    createMovieDto.getYear(),
                    createMovieDto.getLanguage(),
                    createMovieDto.getDescription(),
                    createMovieDto.getDirector(),
                    createMovieDto.getRuntime(),
                    fileManager.updateImage(createMovieDto, findById(id, lm),lm),
                    id
            );
            logger.formatLogMessageGen(LogLevel.INFO, lm, "Updated in repository");

        }catch (DataAccessException e){
           //logg
            //throw
        }
    }

}
