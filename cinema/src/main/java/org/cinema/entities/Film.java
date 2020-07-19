package org.cinema.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Film implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 75)
	private String titre;
	@Column(length = 75)
	private String description ;
	@Column(length = 75)
	private String realisateur;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSortie;
	private double duree;
	private String photo;
	@OneToMany(mappedBy = "film")
	private Collection<Projection> projections ;
	@ManyToOne
	private Categorie categorie;
}
