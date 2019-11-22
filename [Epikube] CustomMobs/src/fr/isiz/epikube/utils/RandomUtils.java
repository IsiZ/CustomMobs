package fr.isiz.epikube.utils;

import java.util.List;
import java.util.Random;

public class RandomUtils {

	public static final Random random;

	static {
		random = new Random(System.nanoTime());
	}

	public static Boolean getRandomByPercent(Integer percent) {
		Integer rdm = new Random().nextInt(101);
		if (rdm < percent) {
			return true;
		} else {
			return false;
		}
	}

	public static <T> T getRandom(List<T> list) {
		int rdm = random.nextInt(list.size());
		return list.get(rdm);
	}

	@SafeVarargs
	public static <T> T getRandom(T... list) {
		int rdm = random.nextInt(list.length);
		return list[rdm];
	}

}
