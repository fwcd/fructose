package fwcd.fructose.math;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

public class QuaternionTest {
	private static final double EPSILON = 0.0001;
	
	@Test
	public void testQuaternion() {
		assertThat(Quaternion.ZERO, closeToQuat(0));
		assertThat(Quaternion.ONE, closeToQuat(1));
		assertThat(Quaternion.I, closeToQuat(0, 1, 0, 0));
		assertThat(Quaternion.J, closeToQuat(0, 0, 1, 0));
		assertThat(Quaternion.K, closeToQuat(0, 0, 0, 1));
		assertThat(Quaternion.I.multiply(Quaternion.I), closeToQuat(-1));
		assertThat(quat(0, 2, -1, -3).abs(), closeTo(Math.sqrt(14), EPSILON));
		assertThat(quat(0, 2, -1, -3).conjugate(), closeToQuat(0, -2, 1, 3));
		assertThat(quat(0, 2, -1, -3).reciprocal(), closeToQuat(0, -1.0 / 7.0, 1.0 / 14.0, 3.0 / 14.0));
		assertThat(quat(0, 3, 4, 3).multiply(quat(-1, 3.9, 4, -3)), closeToQuat(-18.7, -27, 16.7, -6.6));
	}
	
	private Quaternion quat(double real, double i, double j, double k) {
		return Quaternion.of(real, i, j, k);
	}
	
	private Matcher<Quaternion> closeToQuat(double real) {
		return closeToQuat(real, 0, 0, 0);
	}
	
	private Matcher<Quaternion> closeToQuat(double real, double i, double j, double k) {
		return closeToQuat(quat(real, i, j, k));
	}
	
	private Matcher<Quaternion> closeToQuat(Quaternion expected) {
		return new BaseMatcher<Quaternion>() {
			@Override
			public boolean matches(Object item) {
				return expected.equals((Quaternion) item, EPSILON);
			}
			
			@Override
			public void describeTo(Description description) {
				description
						.appendText("Quaternion should equal ")
						.appendValue(expected);
			}
		};
	}
}
