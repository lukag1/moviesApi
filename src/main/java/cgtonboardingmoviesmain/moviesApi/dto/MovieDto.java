package cgtonboardingmoviesmain.moviesApi.dto;


import cgtonboardingmoviesmain.moviesApi.domain.Roles;

import java.time.LocalDate;
import java.util.List;

public class MovieDto {

    private Integer movieId;

    private String name;

    private String genreName;

    private String href;

    private LocalDate releaseDate;

    private String language;

    private String director;

    private String description;

    private int year;

    private String runtime;

    private List<RoleDto> roles;

    private String movieImage;

    private String movieImageName;

    public MovieDto() {
    }

    public MovieDto(Integer movieId, String name, String genreName, String href, LocalDate releaseDate, String language,
                    String director, String description, int year, String runtime, List<RoleDto> roles, String movieImage,
                    String movieImageName) {
        this.movieId = movieId;
        this.name = name;
        this.genreName = genreName;
        this.href = href;
        this.releaseDate = releaseDate;
        this.language = language;
        this.director = director;
        this.description = description;
        this.year = year;
        this.runtime = runtime;
        this.roles = roles;
        this.movieImage = movieImage;
        this.movieImageName = movieImageName;
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

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
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

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovieImageName() {
        return movieImageName;
    }

    public void setMovieImageName(String movieImageName) {
        this.movieImageName = movieImageName;
    }

    @Override
    public String toString() {
        return "MovieDto{" +
                "movieId=" + movieId +
                ", name='" + name + '\'' +
                ", genreName='" + genreName + '\'' +
                ", href='" + href + '\'' +
                ", releaseDate=" + releaseDate +
                ", language='" + language + '\'' +
                ", director='" + director + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", runtime='" + runtime + '\'' +
                ", roles=" + roles +
                ", movieImage='" + movieImage + '\'' +
                ", movieImageName='" + movieImageName + '\'' +
                '}';
    }
}
