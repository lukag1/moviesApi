package cgtonboardingmoviesmain.moviesApi.repository;

import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.domain.Roles;
import cgtonboardingmoviesmain.moviesApi.dto.CreateMovieDto;
import cgtonboardingmoviesmain.moviesApi.exception.ResourceNotFoundException;
import cgtonboardingmoviesmain.moviesApi.exception.SqlSyntaxException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

@Repository
public class MovieRepositoryImp implements MoviesRepository {

    private JdbcTemplate jdbcTemplate;

    public MovieRepositoryImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Movie> findAll() {
        String sql = "SELECT * FROM moviesdb.movies";
        List<Movie> movies = jdbcTemplate.query(sql, new MovieRowMapper());
        return movies;
    }

    @Override
    public Movie findById(int id) {
        try {
            String sql = "SELECT * FROM moviesdb.movies WHERE movie_id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new MovieRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException("Movie not found with ID: " + id);
        } catch (SqlSyntaxException ex){
            throw new SqlSyntaxException("Error with database sql code");
        }
    }



    private class MovieRowMapper implements RowMapper<Movie> {
        @Override
        public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
            Movie movie = new Movie();
            movie.setMovieId(rs.getInt("movie_id"));
            movie.setName(rs.getString("name"));
            movie.setGenre(rs.getInt("genre"));
            movie.setReleaseDate(rs.getDate("release_date").toLocalDate());
            movie.setLanguage(rs.getString("language"));
            movie.setStars(rs.getString("stars"));
            movie.setWriters(rs.getString("writers"));
            movie.setDirector(rs.getString("director"));
            movie.setRuntime(rs.getString("runtime"));
            movie.setDescription(rs.getString("description"));
            movie.setYear(rs.getInt("year"));
            movie.setComment(rs.getString("comment"));
            movie.setMovieImageName(rs.getString("movie_image_name"));
            movie.setRating(rs.getDouble("rating"));

            return movie;
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
        return jdbcTemplate.query(sql, new Object[]{movieId}, new RoleRowMapper());
    }

    @Override
    public void delete(int id) {
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
                createMovieDto.getMovieImage(),
                id
        );
    }

    private class RoleRowMapper implements RowMapper<Roles> {
        @Override
        public Roles mapRow(ResultSet rs, int rowNum) throws SQLException {
            Roles role = new Roles();
            role.setMovieId(rs.getInt("movie_id"));
            role.setActorId(rs.getInt("actor_id"));
            role.setRoleName(rs.getString("role_name"));
            return role;
        }
    }
}
