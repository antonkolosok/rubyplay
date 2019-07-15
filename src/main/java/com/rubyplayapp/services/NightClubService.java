package com.rubyplayapp.services;

import com.rubyplayapp.domain.NightClub;
import com.rubyplayapp.domain.Visitor;
import com.rubyplayapp.exceptions.EntityNotFoundException;
import com.rubyplayapp.exceptions.NightClubException;
import com.rubyplayapp.exceptions.VisitorException;
import com.rubyplayapp.repositories.NightClubRepository;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class NightClubService {

	private final NightClubRepository nightClubRepository;

	public NightClubService(final NightClubRepository nightClubRepository) {
		this.nightClubRepository = nightClubRepository;
	}

	public NightClub create(final String name) {
		if (this.nightClubRepository.existsByName(name)) {
			throw new NightClubException("Duplicate NightClub name in DB");
		}
		final NightClub nightClub = new NightClub();
		nightClub.setName(name);
		return save(nightClub);
	}

	public Set<Visitor> getAllVisitors(final String nightClubName) {
		return find(nightClubName).getVisitors();
	}


	public NightClub find(final String name) {
		return this.nightClubRepository.findByName(name)
				.orElseThrow(() -> new EntityNotFoundException("No such NightClub in DB"));
	}

	public Set<NightClub> getNotVisitedByVisitor(final String name) {
		return this.nightClubRepository.findNotVisitedByVisitor(name);
	}

	private NightClub save(final NightClub nightClub) {
		return this.nightClubRepository.save(nightClub);
	}

}
