package com.rubyplayapp.services;

import com.rubyplayapp.domain.NightClub;
import com.rubyplayapp.domain.Visitor;
import com.rubyplayapp.dto.AttendanceDto;
import com.rubyplayapp.exceptions.EntityNotFoundException;
import com.rubyplayapp.exceptions.VisitorException;
import com.rubyplayapp.repositories.VisitorRepository;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class VisitorService {

	private final VisitorRepository visitorRepository;
	private final NightClubService nightClubService;

	public VisitorService(final VisitorRepository visitorRepository,
			final NightClubService nightClubService) {
		this.visitorRepository = visitorRepository;
		this.nightClubService = nightClubService;
	}

	public Visitor create(final String name) {
		if (this.visitorRepository.existsByName(name)) {
			throw new VisitorException("Duplicate visitor name in DB");
		}
		final Visitor visitor = new Visitor();
		visitor.setName(name);
		return save(visitor);
	}

	public Set<NightClub> getVisitedNightClubs(final String name) {
		return find(name).getNightClubs();
	}

	public Set<NightClub> getNotVisitedNightClubs(final String name) {
		if (this.visitorRepository.existsByName(name)) {
			return this.nightClubService.getNotVisitedByVisitor(name);
		}
		throw new VisitorException("No such visitor in DB");
	}

	public void attend(final AttendanceDto attendanceDto) {
		final Visitor visitor = find(attendanceDto.getVisitorName());
		final NightClub nightClub = this.nightClubService.find(attendanceDto.getNightClubName());
		if (!isVisited(visitor, nightClub)) {
			visitor.addNightClub(nightClub);
			save(visitor);
		} else {
			throw new VisitorException(
					String.format("Visitor %s already attended Night Club %s", visitor.getName(), nightClub.getName()));
		}
	}

	private boolean isVisited(final Visitor visitor, final NightClub nightClub) {
		return visitor.getNightClubs().contains(nightClub);
	}

	private Visitor find(final String name) {
		return this.visitorRepository.findByName(name)
				.orElseThrow(() -> new EntityNotFoundException("No such visitor in DB"));
	}

	private Visitor save(final Visitor visitor) {
		return this.visitorRepository.save(visitor);
	}
}
