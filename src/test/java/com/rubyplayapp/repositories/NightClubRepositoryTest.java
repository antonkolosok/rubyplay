package com.rubyplayapp.repositories;

import static com.rubyplayapp.utils.VisitorTestData.VISITOR_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import com.rubyplayapp.domain.NightClub;
import com.rubyplayapp.domain.Visitor;
import java.util.Set;
import javax.annotation.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class NightClubRepositoryTest {

	@Autowired
	private NightClubRepository nightClubRepository;

	@Autowired
	private VisitorRepository visitorRepository;

	@Before
	public void initDB() {
		Visitor visitor = new Visitor();
		visitor.setName(VISITOR_NAME);
		visitor = this.visitorRepository.save(visitor);

		NightClub nightClub1 = new NightClub();
		nightClub1.setName("NightClub1Name");
		nightClub1 = this.nightClubRepository.save(nightClub1);

		final NightClub nightClub2 = new NightClub();
		nightClub2.setName("NightClub2Name");
		this.nightClubRepository.save(nightClub2);

		final NightClub nightClub3 = new NightClub();
		nightClub3.setName("NightClub3Name");
		this.nightClubRepository.save(nightClub3);

		visitor.addNightClub(nightClub1);
		this.visitorRepository.save(visitor);
	}

	@Test
	public void whenFindNotVisitedByVisitor_ExistTwoNotVisited_ReturnTwo() {

		final Set<NightClub> notVisitedNightClubs = this.nightClubRepository.findNotVisitedByVisitor(VISITOR_NAME);

		assertThat(notVisitedNightClubs.size()).isEqualTo(2);

	}

}
