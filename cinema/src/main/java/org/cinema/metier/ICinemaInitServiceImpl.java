package org.cinema.metier;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.cinema.dao.CategorieRepository;
import org.cinema.dao.CinemaRepository;
import org.cinema.dao.FilmRepository;
import org.cinema.dao.PlaceRepository;
import org.cinema.dao.ProjectionRepository;
import org.cinema.dao.TicketRepository;
import org.cinema.dao.SalleRepository;
import org.cinema.dao.SeanceRepository;
import org.cinema.dao.VilleRepository;
import org.cinema.entities.Categorie;
import org.cinema.entities.Cinema;
import org.cinema.entities.Film;
import org.cinema.entities.Place;
import org.cinema.entities.Projection;
import org.cinema.entities.Salle;
import org.cinema.entities.Seance;
import org.cinema.entities.Ticket;
import org.cinema.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@Transactional
public class ICinemaInitServiceImpl implements ICinema {

	@Autowired
	VilleRepository villeRepository;
	@Autowired
	CinemaRepository cinemaRepository;
	@Autowired
	SalleRepository salleRepository;
	@Autowired
	CategorieRepository categorieRepository;
	@Autowired
	FilmRepository filmRepository;
	@Autowired
	SeanceRepository seanceRepository;
	@Autowired
	TicketRepository TicketRepository;
	@Autowired
	ProjectionRepository projectionRepository;
	@Autowired
	PlaceRepository placeRepository;
	
	
	public void initVilles() {
		Stream.of("Casablanca","Marrakech","Rabat","Tanger").forEach(nameVIlle->{
			Ville ville = new Ville();
			ville.setName(nameVIlle);
			villeRepository.save(ville);
		});
		
	}

	@Override
	public void initCinemas() {
		villeRepository.findAll().forEach(v->{
			Stream.of("Megarama","IMax","FouNouN","Chahrazad").forEach(nameCinema->{
				Cinema cinema = new Cinema();
				cinema.setName(nameCinema);
				cinema.setNombreSalles(3+(int)(Math.random()*7));
				cinema.setVille(v);
				cinemaRepository.save(cinema);
			});
		});
		
		
	}

	@Override
	public void initSalles() {
		cinemaRepository.findAll().forEach(cinema->{
			 for(int i=0;i<cinema.getNombreSalles();i++)
			 {
				 Salle salle =new Salle();
				 salle.setName("Salle "+ (i+1));
				 salle.setCinema(cinema);
				 salle.setNbrPlasse(15+(int)(Math.random()*20));
				 salleRepository.save(salle);
			 }
		});
		
	}

	@Override
	public void initPlaces() {
		
		salleRepository.findAll().forEach(salle->{
			 for(int i=0;i<salle.getNbrPlasse();i++)
			 {
				 Place place =new Place();
				 place.setNumero(i+1);
				 place.setSalle(salle);
				 placeRepository.save(place);
			 }
		});
	}

	@Override
	public void initSeances() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(s->{
			Seance seance = new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(s));
				seanceRepository.save(seance);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
	}

	@Override
	public void initCategories() {
		Stream.of("Histoire","Actions","Fiction","Drama").forEach(cat->{
			Categorie categorie = new Categorie();
			categorie.setName(cat);
			categorieRepository.save(categorie);
		});

		
	}

	@Override
	public void initFilms() {
		double []durees = new double[] {1,1.5,2,2.5,3};
		List<Categorie> categories= categorieRepository.findAll();
		Stream.of("Jumanji","The Perfect Date","The Kissing Booth").forEach(nameFilm->{
			Film film = new Film();
			film.setTitre(nameFilm);
			film.setDuree(durees[new Random().nextInt(durees.length)]);
			film.setPhoto(nameFilm.replace(" ", ""));
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			filmRepository.save(film);
		});
		
	}

	@Override
	public void initProjections() {
		double[] prices = new double[] {30,50,470,90,110};
		List<Film> films = filmRepository.findAll() ;
			villeRepository.findAll().forEach(ville->{
				ville.getCinemas().forEach(cinema->{
					cinema.getSalles().forEach(salle->{
							int index=new Random().nextInt(films.size());
							Film film = films.get(index);
							seanceRepository.findAll().forEach(seance->{
								Projection projection = new Projection();
								projection.setDateProjection(new Date());
								projection.setFilm(film);
								projection.setPrix(prices[new Random().nextInt(prices.length)]);
								projection.setSalle(salle);
								projection.setSeance(seance);
								projectionRepository.save(projection);
							});
						
					});
				});
			});
		
	}

	@Override
	public void initTickets() {
		projectionRepository.findAll().forEach(projection->{
			projection.getSalle().getPlaces().forEach(place->{
				Ticket ticket = new Ticket();
				ticket.setPlace(place);
				ticket.setPrix(projection.getPrix());
				ticket.setProjection(projection);
				ticket.setReservee(false);
				TicketRepository.save(ticket);
			});
		});
	}

}
