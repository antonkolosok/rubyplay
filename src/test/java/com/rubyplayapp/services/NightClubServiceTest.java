package com.rubyplayapp.services;

import static com.rubyplayapp.utils.NightClubTestData.NIGHTCLUB_NAME;
import static com.rubyplayapp.utils.NightClubTestData.preparedNightClub;
import static com.rubyplayapp.utils.VisitorTestData.VISITOR_NAME;
import static com.rubyplayapp.utils.VisitorTestData.preparedVisitor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rubyplayapp.domain.NightClub;
import com.rubyplayapp.domain.Visitor;
import com.rubyplayapp.exceptions.EntityNotFoundException;
import com.rubyplayapp.exceptions.NightClubException;
import com.rubyplayapp.repositories.NightClubRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class NightClubServiceTest {


	@InjectMocks
	private NightClubService nightClubService;

	@Mock
	private NightClubRepository nightClubRepository;

	@Test
	public void whenCreate_NightClubNameUnique_CreateNightClub() {
		final NightClub expectedNightClub = preparedNightClub();
		final NightClub nightClubForSave = preparedNightClub();
		nightClubForSave.setId(null);

		when(this.nightClubRepository.existsByName(NIGHTCLUB_NAME)).thenReturn(false);
		when(this.nightClubRepository.save(nightClubForSave)).thenReturn(expectedNightClub);

		final NightClub actualNightClub = this.nightClubService.create(NIGHTCLUB_NAME);

		verify(this.nightClubRepository, times(1)).existsByName(NIGHTCLUB_NAME);
		verify(this.nightClubRepository, times(1)).save(nightClubForSave);

		assertThat(actualNightClub).isEqualTo(expectedNightClub);
	}

	@Test(expected = NightClubException.class)
	public void whenCreate_NightClubNameNotUnique_ThrowException() {
		when(this.nightClubRepository.existsByName(NIGHTCLUB_NAME)).thenReturn(true);

		this.nightClubService.create(NIGHTCLUB_NAME);
	}

	@Test
	public void whenGetAllVisitors_NightClubExists_ReturnVisitors() {
		final Set<Visitor> expectedVisitors = new HashSet<>();
		expectedVisitors.add(preparedVisitor());

		final NightClub nightClub = preparedNightClub();
		nightClub.setVisitors(expectedVisitors);

		when(this.nightClubRepository.findByName(NIGHTCLUB_NAME)).thenReturn(Optional.of(nightClub));

		final Set<Visitor> actualVisitors = this.nightClubService.getAllVisitors(NIGHTCLUB_NAME);

		verify(this.nightClubRepository, times(1)).findByName(NIGHTCLUB_NAME);

		assertThat(actualVisitors).isEqualTo(expectedVisitors);
	}

	@Test(expected = EntityNotFoundException.class)
	public void whenGetAllVisitors_NightClubNotExists_ThrowException() {
		when(this.nightClubRepository.findByName(NIGHTCLUB_NAME)).thenReturn(Optional.empty());

		this.nightClubService.getAllVisitors(NIGHTCLUB_NAME);
	}

	@Test
	public void whenFindNightClub_NightClubExist_ReturnNightClub() {
		final NightClub expectedNightClub = preparedNightClub();

		when(this.nightClubRepository.findByName(NIGHTCLUB_NAME)).thenReturn(Optional.of(expectedNightClub));

		final NightClub actualNightClub = this.nightClubService.find(NIGHTCLUB_NAME);

		verify(this.nightClubRepository, times(1)).findByName(NIGHTCLUB_NAME);

		assertThat(actualNightClub).isEqualTo(expectedNightClub);
	}

	@Test(expected = EntityNotFoundException.class)
	public void whenFindNightClub_NightClubNotExist_ThrowException() {
		when(this.nightClubRepository.findByName(NIGHTCLUB_NAME)).thenReturn(Optional.empty());

		this.nightClubService.find(NIGHTCLUB_NAME);
	}

	@Test
	public void whenGetNotVisitedByVisitor_NightClubExists_ReturnNightClubs() {
		final Set<NightClub> expectedNightClubs = new HashSet<>();
		expectedNightClubs.add(preparedNightClub());

		when(this.nightClubRepository.findNotVisitedByVisitor(VISITOR_NAME)).thenReturn(expectedNightClubs);

		final Set<NightClub> actualNightClubs = this.nightClubService.getNotVisitedByVisitor(VISITOR_NAME);

		verify(this.nightClubRepository, times(1)).findNotVisitedByVisitor(VISITOR_NAME);

		assertThat(actualNightClubs).isEqualTo(expectedNightClubs);
	}

}
