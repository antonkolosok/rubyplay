package com.rubyplayapp.controllers;

import com.rubyplayapp.domain.Visitor;
import com.rubyplayapp.dto.NightClubDto;
import com.rubyplayapp.services.NightClubService;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NightClubController {

	private final NightClubService nightClubService;

	public NightClubController(final NightClubService nightClubService) {
		this.nightClubService = nightClubService;
	}

	@PostMapping("/night-club")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody @Valid final NightClubDto nightClubDto) {
		this.nightClubService.create(nightClubDto.getName());
	}

	@GetMapping("/night-club/{nightClubName}/all-visitors")
	@ResponseStatus(HttpStatus.OK)
	public Set<Visitor> getAllVisitors(@PathVariable final String nightClubName) {
		return this.nightClubService.getAllVisitors(nightClubName);
	}

}
