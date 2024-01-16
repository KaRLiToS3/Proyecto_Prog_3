import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import monopoly.objects.Achievement;

public class AchievementTest {

	@Test
	public void achievementsTest() {
		Set<Achievement> achievements =  new HashSet<>();
		Set<Achievement> answer = new HashSet<>();

		achievements.add(new Achievement(Achievement.Type.MVP, 0));
		achievements.add(new Achievement(Achievement.Type.CHEAPSKATE));
		achievements.add(new Achievement(Achievement.Type.FLAT_BROKE, 3));
		achievements.add(new Achievement(Achievement.Type.MVP, 0));
		achievements.add(new Achievement(Achievement.Type.CHEAPSKATE, 1));
		achievements.add(new Achievement(Achievement.Type.MVP, 2));

		answer.add(new Achievement(Achievement.Type.MVP, 0));
		answer.add(new Achievement(Achievement.Type.CHEAPSKATE, 1));
		answer.add(new Achievement(Achievement.Type.FLAT_BROKE, 3));

		Achievement ach = new Achievement(Achievement.Type.MVP, 0);

		assertEquals(answer, achievements);
		assertTrue(achievements.contains(ach));
		assertEquals(Achievement.Type.MVP, ach.getType());

		assertEquals(0, ach.getTimes());
		ach.setTimes(1);
		assertEquals(1, ach.getTimes());
		ach.incrementTimes();
		assertEquals(2, ach.getTimes());
		assertEquals("BEGINNER", Achievement.Type.BEGINNER.toString());
		assertEquals("MVP/2", ach.toString());
		
		String[] answ = {"/monopoly/images/MVP.jpg", "/monopoly/images/MVPtext.png"};
		assertArrayEquals(answ, ach.getType().getImg());
	}
}
