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
public class NightClub {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank
	private	String name;

	@JsonIgnore
	@ManyToMany(mappedBy = "nightClubs")
	private Set<Visitor> visitors = new HashSet<>();

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

	public Set<Visitor> getVisitors() {
		return this.visitors;
	}

	public void setVisitors(Set<Visitor> visitors) {
		this.visitors = visitors;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		NightClub nightClub = (NightClub) o;

		if (this.id != null ? !this.id.equals(nightClub.id) : nightClub.id != null) {
			return false;
		}
		return this.name != null ? this.name.equals(nightClub.name) : nightClub.name == null;
	}

	@Override
	public int hashCode() {
		int result = this.id != null ? this.id.hashCode() : 0;
		result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
		return result;
	}
}
