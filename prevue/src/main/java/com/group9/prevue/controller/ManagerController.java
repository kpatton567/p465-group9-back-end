package com.group9.prevue.controller;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.group9.prevue.model.Movie;
import com.group9.prevue.model.EGenre;
import com.group9.prevue.model.Genre;
import com.group9.prevue.model.request.AddMovieRequest;
import com.group9.prevue.model.Showtime;
import com.group9.prevue.model.request.AddShowtimeRequest;
import com.group9.prevue.model.response.MessageResponse;
import com.group9.prevue.repository.GenreRepository;
import com.group9.prevue.repository.MovieRepository;
import com.group9.prevue.repository.ShowtimeRepository;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/manage")
public class ManagerController {

	@Autowired
	private ShowtimeRepository showtimeRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@PostMapping
	public ResponseEntity<?> addShowtime(@RequestBody AddShowtimeRequest request){
		Showtime showtime = new Showtime(request.getTheaterId(), request.getMovieId(), request.getShowtime());
		showtimeRepository.save(showtime);
		return ResponseEntity.ok(new MessageResponse("Showtime added successfully"));
	}
	
	@PostMapping
	public ResponseEntity<?> addMovie(@RequestBody AddMovieRequest request){
		Movie movie = new Movie(request.getTitle(), request.getDescription());
		
		Set<String> strGenres = request.getGenre();
		Set<Genre> genres = new HashSet<>();
		
		if (strGenres != null) {
			strGenres.forEach(genre -> {
				switch (genre) {
				case "action":
					Genre actionGenre = genreRepository.findByGenre(EGenre.GENRE_ACTION).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(actionGenre);
					break;
				case "adventure":
					Genre adventureGenre = genreRepository.findByGenre(EGenre.GENRE_ADVENTURE).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(adventureGenre);
					break;
				case "thriller":
					Genre thrillerGenre = genreRepository.findByGenre(EGenre.GENRE_THRILLER).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(thrillerGenre);
					break;
				case "horror":
					Genre horrorGenre = genreRepository.findByGenre(EGenre.GENRE_HORROR).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(horrorGenre);
					break;
				case "psychological":
					Genre psychGenre = genreRepository.findByGenre(EGenre.GENRE_PSYCHOLOGICAL).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(psychGenre);
					break;
				case "nonfiction":
					Genre nonfictionGenre = genreRepository.findByGenre(EGenre.GENRE_NONFICTION).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(nonfictionGenre);
					break;
				case "biopic":
					Genre biopicGenre = genreRepository.findByGenre(EGenre.GENRE_BIOPIC).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(biopicGenre);
					break;
				case "comedy":
					Genre comedyGenre = genreRepository.findByGenre(EGenre.GENRE_COMEDY).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(comedyGenre);
					break;
				case "crime":
					Genre crimeGenre = genreRepository.findByGenre(EGenre.GENRE_CRIME).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(crimeGenre);
					break;
				case "drama":
					Genre dramaGenre = genreRepository.findByGenre(EGenre.GENRE_DRAMA).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(dramaGenre);
					break;
				case "romance":
					Genre romanceGenre = genreRepository.findByGenre(EGenre.GENRE_ROMANCE).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(romanceGenre);
					break;
				case "scifi":
					Genre scifiGenre = genreRepository.findByGenre(EGenre.GENRE_SCIFI).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(scifiGenre);
					break;
				}
			});
		}
		
		movie.setGenres(genres);
		movieRepository.save(movie);
		return ResponseEntity.ok(new MessageResponse("Movie added successfully"));
	}
}
