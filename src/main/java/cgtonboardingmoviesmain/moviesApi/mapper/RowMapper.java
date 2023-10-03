package cgtonboardingmoviesmain.moviesApi.mapper;

import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.domain.Roles;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RowMapper {

    public static class RoleRowMapper implements org.springframework.jdbc.core.RowMapper<Roles> {
        @Override
        public Roles mapRow(ResultSet rs, int rowNum) throws SQLException {
            Roles role = new Roles();
            role.setMovieId(rs.getInt("movie_id"));
            role.setActorId(rs.getInt("actor_id"));
            role.setRoleName(rs.getString("role_name"));
            return role;
        }
    }
    public static class MovieRowMapper implements org.springframework.jdbc.core.RowMapper<Movie> {
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


}
