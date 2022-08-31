package com.notify.app.modal;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Team {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@OneToMany(targetEntity = Developer.class, fetch = FetchType.LAZY, 
			cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinColumn(name = "teamId")
	private List<Developer> developers;

}
