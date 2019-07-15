package com.rubyplayapp.controllers;

import com.rubyplayapp.domain.NightClub;
import com.rubyplayapp.dto.AttendanceDto;
import com.rubyplayapp.dto.VisitorDto;
import com.rubyplayapp.services.VisitorService;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VisitorController {

	private final VisitorService visitorService;

	@Autowired
	public VisitorController(final VisitorService visitorService) {
		this.visitorService = visitorService;
	}

	@PostMapping("/visitor")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody @Valid final VisitorDto visitorDto) {
		this.visitorService.create(visitorDto.getName());
	}

	@PostMapping("/visitor/attend")
	@ResponseStatus(HttpStatus.OK)
	public void attend(@RequestBody @Valid final AttendanceDto attendanceDto) {
		this.visitorService.attend(attendanceDto);
	}

	@GetMapping("/visitor/{visitorName}/visited-night-clubs")
	@ResponseStatus(HttpStatus.OK)
	public Set<NightClub> getVisitedNightClubs(@PathVariable final String visitorName) {
		return this.visitorService.getVisitedNightClubs(visitorName);
	}

	@GetMapping("/visitor/{visitorName}/not-visited-night-clubs")
	@ResponseStatus(HttpStatus.OK)
	public Set<NightClub> getNotVisitedNightClubs(@PathVariable final String visitorName) {
		return this.visitorService.getNotVisitedNightClubs(visitorName);
	}

}
