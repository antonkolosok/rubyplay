package com.rubyplayapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Visitor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank
	private String name;

	@JsonIgnore
	@ManyToMany
	private final Set<NightClub> nightClubs = new HashSet<>();

	public void addNightClub(final NightClub nightClub) {
		this.nightClubs.add(nightClub);
		nightClub.getVisitors().add(this);
	}

	public void removeNightClub(final NightClub nightClub) {
		this.nightClubs.remove(nightClub);
		nightClub.getVisitors().remove(this);
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<NightClub> getNightClubs() {
		return this.nightClubs;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Visitor visitor = (Visitor) o;

		if (this.id != null ? !this.id.equals(visitor.id) : visitor.id != null) {
			return false;
		}
		return this.name != null ? this.name.equals(visitor.name) : visitor.name == null;
	}

	@Override
	public int hashCode() {
		int result = this.id != null ? this.id.hashCode() : 0;
		result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
		return result;
	}
}
