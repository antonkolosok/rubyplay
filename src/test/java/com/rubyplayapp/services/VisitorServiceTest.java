package com.rubyplayapp.services;

import static com.rubyplayapp.utils.NightClubTestData.NIGHTCLUB_NAME;
import static com.rubyplayapp.utils.NightClubTestData.preparedNightClub;
import static com.rubyplayapp.utils.VisitorTestData.VISITOR_NAME;
import static com.rubyplayapp.utils.VisitorTestData.preparedVisitor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rubyplayapp.domain.NightClub;
import com.rubyplayapp.domain.Visitor;
import com.rubyplayapp.dto.AttendanceDto;
import com.rubyplayapp.exceptions.EntityNotFoundException;
import com.rubyplayapp.exceptions.VisitorException;
import com.rubyplayapp.repositories.VisitorRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class VisitorServiceTest {

	@InjectMocks
	private VisitorService visitorService;

	@Mock
	private VisitorRepository visitorRepository;

	@Mock
	private NightClubService nightClubService;

	@Captor
	private ArgumentCaptor<Visitor> visitorCaptor;

	@Test
	public void whenCreateVisitor_CreationSuccessful_ReturnValidVisitor() {
		final Visitor expectedVisitor = preparedVisitor();

		final Visitor visitorForSave = preparedVisitor();
		visitorForSave.setId(null);

		when(this.visitorRepository.existsByName(VISITOR_NAME)).thenReturn(false);
		when(this.visitorRepository.save(visitorForSave)).thenReturn(expectedVisitor);

		final Visitor actualVisitor = this.visitorService.create(VISITOR_NAME);

		verify(this.visitorRepository, times(1)).existsByName(VISITOR_NAME);
		verify(this.visitorRepository, times(1)).save(visitorForSave);

		assertThat(actualVisitor).isEqualTo(expectedVisitor);
	}

	@Test(expected = VisitorException.class)
	public void whenCreateVisitor_DuplicateVisitorName_ThrowException() {
		when(this.visitorRepository.existsByName(VISITOR_NAME)).thenReturn(true);

		this.visitorService.create(VISITOR_NAME);
	}

	@Test
	public void whenGetVisitedNightClubs_VisitorExists_ReturnVisitedNightClubs() {
		final Visitor visitor = preparedVisitor();
		final NightClub nightClub = preparedNightClub();
		visitor.addNightClub(nightClub);

		final Set<NightClub> expectedNightClub = new HashSet<>();
		expectedNightClub.add(nightClub);

		when(this.visitorRepository.findByName(VISITOR_NAME)).thenReturn(java.util.Optional.of(visitor));

		final Set<NightClub> actualNightClubs = this.visitorService.getVisitedNightClubs(VISITOR_NAME);

		verify(this.visitorRepository, times(1)).findByName(VISITOR_NAME);

		assertThat(actualNightClubs).isEqualTo(expectedNightClub);
	}

	@Test(expected = EntityNotFoundException.class)
	public void whenGetVisitedNightClubs_VisitorNotExists_ThrowException() {

		when(this.visitorRepository.findByName(VISITOR_NAME)).thenReturn(Optional.empty());

		this.visitorService.getVisitedNightClubs(VISITOR_NAME);
	}

	@Test
	public void whenGetNotVisitedNightClubs_VisitorExists_ReturnNotVisitedNightClubs() {
		final NightClub nightClub = preparedNightClub();

		final Set<NightClub> expectedNightClub = new HashSet<>();
		expectedNightClub.add(nightClub);

		when(this.visitorRepository.existsByName(VISITOR_NAME)).thenReturn(true);
		when(this.nightClubService.getNotVisitedByVisitor(VISITOR_NAME)).thenReturn(expectedNightClub);

		final Set<NightClub> actualNightClubs = this.visitorService.getNotVisitedNightClubs(VISITOR_NAME);

		verify(this.visitorRepository, times(1)).existsByName(VISITOR_NAME);
		verify(this.nightClubService, times(1)).getNotVisitedByVisitor(VISITOR_NAME);

		assertThat(actualNightClubs).isEqualTo(expectedNightClub);
	}

	@Test(expected = VisitorException.class)
	public void whenGetNotVisitedNightClubs_VisitorNotExists_ThrowException() {
		when(this.visitorRepository.existsByName(VISITOR_NAME)).thenReturn(false);

		this.visitorService.getNotVisitedNightClubs(VISITOR_NAME);
	}

	@Test
	public void whenVisitorAttendNightClub_VisitorExistNightClubExist_AttendSuccessful() {
		final AttendanceDto attendanceDto = new AttendanceDto();
		attendanceDto.setVisitorName(VISITOR_NAME);
		attendanceDto.setNightClubName(NIGHTCLUB_NAME);

		final Visitor visitor = preparedVisitor();

		final NightClub nightClub = preparedNightClub();

		final Visitor expectedVisitor = preparedVisitor();
		expectedVisitor.addNightClub(nightClub);

		when(this.visitorRepository.findByName(VISITOR_NAME)).thenReturn(Optional.of(visitor));
		when(this.nightClubService.find(NIGHTCLUB_NAME)).thenReturn(nightClub);
		when(this.visitorRepository.save(any(Visitor.class))).thenReturn(expectedVisitor);

		this.visitorService.attend(attendanceDto);

		verify(this.visitorRepository, times(1)).findByName(VISITOR_NAME);
		verify(this.nightClubService, times(1)).find(NIGHTCLUB_NAME);
		verify(this.visitorRepository, times(1)).save(this.visitorCaptor.capture());

		assertThat(this.visitorCaptor.getValue()).isEqualToComparingFieldByField(expectedVisitor);
	}

	@Test(expected = VisitorException.class)
	public void whenVisitorAttendNightClub_VisitorAlreadyVisitedNightClub_ThrowException() {
		final AttendanceDto attendanceDto = new AttendanceDto();
		attendanceDto.setVisitorName(VISITOR_NAME);
		attendanceDto.setNightClubName(NIGHTCLUB_NAME);

		final Visitor visitor = preparedVisitor();
		final NightClub nightClub = preparedNightClub();
		visitor.addNightClub(nightClub);

		when(this.visitorRepository.findByName(VISITOR_NAME)).thenReturn(Optional.of(visitor));
		when(this.nightClubService.find(NIGHTCLUB_NAME)).thenReturn(nightClub);

		this.visitorService.attend(attendanceDto);
	}
}
