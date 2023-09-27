package cgtonboardingmoviesmain.moviesApi.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Roles {
    @NotNull
    private Integer movieId;
    @NotNull
    private Integer actorId;
    @NotEmpty
    private String roleName;

    public Roles() {
    }

    public Roles(Integer movieId, Integer actorId, String roleName) {
        this.movieId = movieId;
        this.actorId = actorId;
        this.roleName = roleName;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "movieId=" + movieId +
                ", actorId=" + actorId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
