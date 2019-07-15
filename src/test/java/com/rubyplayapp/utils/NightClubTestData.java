package com.rubyplayapp.utils;

import com.rubyplayapp.domain.NightClub;

public class NightClubTestData {

	public static final int NIGHTCLUB_ID = 1;
	public static final String NIGHTCLUB_NAME = "nightClubName";

	public static NightClub preparedNightClub() {
		final NightClub nightClub = new NightClub();
		nightClub.setId(NIGHTCLUB_ID);
		nightClub.setName(NIGHTCLUB_NAME);
		return nightClub;
	}

}
