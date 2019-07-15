package com.rubyplayapp.repositories;

import com.rubyplayapp.domain.NightClub;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NightClubRepository extends JpaRepository<NightClub, Integer> {

	Optional<NightClub> findByName(final String name);

	Boolean existsByName(final String name);


	@Query(value = "SELECT * FROM night_club " +
			"WHERE night_club.id NOT IN " +
			"(SELECT nc.id FROM night_club as nc " +
			"INNER JOIN visitor_night_clubs as vnc ON nc.id = vnc.night_clubs_id " +
			"INNER JOIN visitor as v ON v.id = vnc.visitors_id " +
			"WHERE v.name = :name)", nativeQuery = true)
	Set<NightClub> findNotVisitedByVisitor(@Param("name") final String name);
}
