package cgtonboardingmoviesmain.moviesApi.repository;

import cgtonboardingmoviesmain.moviesApi.FilesHandler.FileManager;
import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.domain.Roles;
import cgtonboardingmoviesmain.moviesApi.dto.CreateMovieDto;
import cgtonboardingmoviesmain.moviesApi.exception.InternalServerErrorException;
import cgtonboardingmoviesmain.moviesApi.exception.ResourceNotFoundException;
import cgtonboardingmoviesmain.moviesApi.exception.SqlSyntaxException;
import cgtonboardingmoviesmain.moviesApi.mapper.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class MovieRepositoryImp implements MoviesRepository {

    JdbcTemplate jdbcTemplate;
    FileManager fileManager;
    RowMapper rowMapper;


    public MovieRepositoryImp(JdbcTemplate jdbcTemplate,FileManager fileManager,RowMapper rowMapper) {
        this.fileManager = fileManager;
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }


    @Override
    public List<Movie> findAll() {
        String sql = "SELECT * FROM moviesdb.movies";
        List<Movie> movies = jdbcTemplate.query(sql, new RowMapper.MovieRowMapper());
        return movies;
    }

    @Override
    public Movie findById(int id) {
        try {
            String sql = "SELECT * FROM moviesdb.movies WHERE movie_id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper.MovieRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException("Movie not found with ID: " + id);
        } catch (SqlSyntaxException ex){
            throw new SqlSyntaxException("Error with database sql code");
        }catch (InternalServerErrorException ex){
            throw new InternalServerErrorException("Error with server");
        }
    }




    @Override
    public int save(Movie movie) {

        String SQL = "INSERT INTO movies (name, genre, release_date, language, stars, writers, director, runtime, description, year, comment, movie_image_name, rating) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

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

        Number generatedKey = keyHolder.getKey();

        if (generatedKey != null) {
            movie.setMovieId(generatedKey.intValue());
            return generatedKey.intValue();
        } else {
            throw new RuntimeException("Failed to retrieve the generated movieId.");
        }
    }

    @Override
    public void saveRoles(List<Roles> actors, int movieID) {
        for(Roles r: actors){
            String SQL = "INSERT INTO roles (actor_id, movie_id, role_name) VALUES (?, ?, ?)";

            jdbcTemplate.update(SQL, r.getActorId(),movieID ,r.getRoleName());
        }
    }

    @Override
    public List<Roles> getRoles(int movieId) {
        String sql = "SELECT * FROM moviesdb.roles WHERE movie_id = ?";
        return jdbcTemplate.query(sql, new Object[]{movieId}, new RowMapper.RoleRowMapper());
    }

    @Override
    public void delete(int id) {
        Movie movie = findById(id);
        fileManager.deleteImage(movie);

        String deleteRolesSql = "DELETE FROM roles WHERE movie_id = ?";
        jdbcTemplate.update(deleteRolesSql, id);

        String sql = "DELETE FROM moviesdb.movies WHERE movie_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void update(CreateMovieDto createMovieDto, int id) {
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
                fileManager.updateImage(createMovieDto, findById(id)),
                id
        );
    }


}
