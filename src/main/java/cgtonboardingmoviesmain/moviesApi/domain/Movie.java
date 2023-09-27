package cgtonboardingmoviesmain.moviesApi.domain;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public class Movie {

    @NotNull
    private Integer movieId;

    @NotEmpty
    private List<Roles> roles;

    @NotBlank
    private String name;

    @Min(1)
    @Max(8)
    private int genre;

    @PastOrPresent
    @NotEmpty
    private LocalDate releaseDate;

    private String language;

    private String stars;

    private String writers;

    private String director;

    private String runtime;

    private String description;

    private int year;

    private String comment;

    @NotEmpty
    private String movieImageName;

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private double rating;

    public Movie() {
    }

    public Movie(Integer movieId, String name,List<Roles> roles ,int genre, LocalDate releaseDate, String language, String stars, String writers, String director, String runtime, String description,
                 int year, String comment, String movieImageName, double rating) {
        this.movieId = movieId;
        this.name = name;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.language = language;
        this.stars = stars;
        this.writers = writers;
        this.director = director;
        this.runtime = runtime;
        this.description = description;
        this.year = year;
        this.comment = comment;
        this.movieImageName = movieImageName;
        this.rating = rating;
        this.roles = roles;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getWriters() {
        return writers;
    }

    public void setWriters(String writers) {
        this.writers = writers;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMovieImageName() {
        return movieImageName;
    }

    public void setMovieImageName(String movieImageName) {
        this.movieImageName = movieImageName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", roles=" + roles +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", releaseDate=" + releaseDate +
                ", language='" + language + '\'' +
                ", stars='" + stars + '\'' +
                ", writers='" + writers + '\'' +
                ", director='" + director + '\'' +
                ", runtime='" + runtime + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", comment='" + comment + '\'' +
                ", movieImageName='" + movieImageName + '\'' +
                ", rating=" + rating +
                '}';
    }
}
