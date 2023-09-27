package cgtonboardingmoviesmain.moviesApi.service;

import cgtonboardingmoviesmain.moviesApi.domain.Movie;
import cgtonboardingmoviesmain.moviesApi.dto.CreateMovieDto;
import cgtonboardingmoviesmain.moviesApi.dto.MovieDto;
import cgtonboardingmoviesmain.moviesApi.mapper.Mapper;
import cgtonboardingmoviesmain.moviesApi.repository.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class MoviesService {

    MoviesRepository moviesRepository;
    Mapper mapper;
    RestTemplate restTemplate;

    @Autowired
    public MoviesService(MoviesRepository moviesRepository, Mapper mapper,RestTemplate restTemplate) {
        this.moviesRepository = moviesRepository;
        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }

    public List<MovieDto> findAll() {
        return moviesRepository.findAll().stream().map(mapper::movieToMovieDto).toList();
    }
    public Optional<MovieDto> findById(int id) {
        Movie movie =  moviesRepository.findById(id);
        return Optional.ofNullable(mapper.movieToMovieDto(movie));
    }

    public MovieDto addMovie(CreateMovieDto createMovieDto) {
        Movie movie = mapper.createMovieDtoToMovie(createMovieDto);

        int generatedMovieId = moviesRepository.save(movie);

        moviesRepository.saveRoles(createMovieDto.getActors(), generatedMovieId);
        MovieDto movieDto = mapper.movieToMovieDto(movie);
        movieDto.setMovieId(generatedMovieId);
        return movieDto;
    }


    public MovieDto updateMovie(CreateMovieDto createMovieDto, int id) {
        moviesRepository.update(createMovieDto, id);

        Movie updatedMovie = moviesRepository.findById(id);

        MovieDto movieDto = mapper.movieToMovieDto(updatedMovie);
        movieDto.setMovieId(id);

        return movieDto;
    }

    public void deleteMovie(int id) {
        moviesRepository.delete(id);
    }
}
