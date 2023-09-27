package cgtonboardingmoviesmain.moviesApi.dto;

import cgtonboardingmoviesmain.moviesApi.domain.Roles;

import java.time.LocalDate;
import java.util.List;

public class CreateMovieDto {

        private String name;
        private int genreId;
        private LocalDate releaseDate;
        private int year;
        private String language;
        private String description;
        private String director;
        private String runtime;
        private String movieImage;
        private List<Roles> roles;

        public CreateMovieDto() {

        }

    public CreateMovieDto(String name, int genreId, LocalDate releaseDate, int year, String language, String description,
                          String director, String runtime , String movieImage,List<Roles> roles) {
        this.name = name;
        this.genreId = genreId;
        this.releaseDate = releaseDate;
        this.year = year;
        this.language = language;
        this.description = description;
        this.director = director;
        this.runtime = runtime;
        this.movieImage = movieImage;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public List<Roles> getActors() {
        return roles;
    }

    public void setActors(List<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "CreateMovieDto{" +
                "name='" + name + '\'' +
                ", genreId=" + genreId +
                ", releaseDate=" + releaseDate +
                ", year=" + year +
                ", language='" + language + '\'' +
                ", description='" + description + '\'' +
                ", director='" + director + '\'' +
                ", runtime='" + runtime + '\'' +
                ", movieImage='" + movieImage + '\'' +
                ", roles=" + roles +
                '}';
    }
}
