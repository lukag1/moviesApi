package cgtonboardingmoviesmain.moviesApi.mapper;

import cgtonboardingmoviesmain.moviesApi.controller.MoviesController;
import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.dto.CreateMovieDto;
import cgtonboardingmoviesmain.moviesApi.dto.MovieDto;
import cgtonboardingmoviesmain.moviesApi.service.RolesService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class Mapper {
    RestTemplate restTemplate;
    JdbcTemplate jdbcTemplate;
    RolesService rolesService;

    public Mapper(RestTemplate restTemplate, RolesService rolesService,JdbcTemplate jdbcTemplate) {
        this.restTemplate = restTemplate;
        this.rolesService = rolesService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public MovieDto movieToMovieDto(Movie movie){
        MovieDto movieDto = new MovieDto();

        movieDto.setMovieId(movie.getMovieId());
        movieDto.setName(movie.getName());
        movieDto.setHref(linkTo(MoviesController.class).slash(movieDto.getMovieId()).toString());
        movieDto.setReleaseDate(movie.getReleaseDate());
        movieDto.setLanguage(movie.getLanguage());
        movieDto.setDirector(movie.getDirector());
        movieDto.setDescription(movie.getDescription());
        movieDto.setYear(movie.getYear());
        movieDto.setRuntime(movie.getRuntime());
        movieDto.setRoles(rolesService.getRoleFull(movie));
        movieDto.setMovieImageName(movie.getMovieImageName());
        movieDto.setGenreName(rolesService.getGenreIdToGenreName(movie.getGenre()));

        File file = new File(movie.getMovieImageName());

        try (FileInputStream fis = new FileInputStream(file)){
            byte[] fileContent = new byte[(int) file.length()];
            fis.read(fileContent);
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            movieDto.setMovieImage("<base64 image>,"+encodedString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return movieDto;
    }

    public Movie createMovieDtoToMovie(CreateMovieDto createMovieDto){
        Movie movie = new Movie();

        movie.setName(createMovieDto.getName());
        movie.setGenre(createMovieDto.getGenreId());
        movie.setReleaseDate(createMovieDto.getReleaseDate());
        movie.setLanguage(createMovieDto.getLanguage());
        movie.setDirector(createMovieDto.getDirector());
        movie.setRuntime(createMovieDto.getRuntime());
        movie.setDescription(createMovieDto.getDescription());
        movie.setYear(createMovieDto.getYear());

        String[] name = createMovieDto.getMovieImage().split(",");
        String dataUri = name[0];
        Random random = new Random();
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(dataUri);
            String imageName = "movieImage_" + random.nextInt(100) + ".jpg";
            movie.setMovieImageName(imageName);
            try (FileOutputStream fos = new FileOutputStream(imageName)) {
                fos.write(decodedBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        movie.setRoles(createMovieDto.getActors());

        return movie;
    }

}

