package org.cinema;

import org.cinema.entities.Film;
import org.cinema.metier.ICinema;
import org.cinema.metier.ICinemaInitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaRestController implements CommandLineRunner {

	@Autowired
	private ICinemaInitServiceImpl cinemaIniImpl;
	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;
	public static void main(String[] args) {
		SpringApplication.run(CinemaRestController.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repositoryRestConfiguration.exposeIdsFor(Film.class);
		cinemaIniImpl.initVilles();
		cinemaIniImpl.initCinemas();
		cinemaIniImpl.initSalles();
		cinemaIniImpl.initPlaces();
		cinemaIniImpl.initSeances();
		cinemaIniImpl.initCategories();
		cinemaIniImpl.initFilms();
		cinemaIniImpl.initProjections();
		cinemaIniImpl.initTickets();
		
		
		
	}

}
