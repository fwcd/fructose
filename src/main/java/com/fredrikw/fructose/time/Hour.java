package com.fredrikw.fructose.time;

/**
 * An immutable representation of an hour.
 * 
 * @author Fredrik W.
 *
 */
public class Hour {
	private final int value;
	
	public Hour(int value) {
		this.value = value;
		
		if (!isValid()) {
			throw new IllegalArgumentException("Not a valid 24-hour-time hour.");
		}
	}

	private boolean isValid() {
		return value >= 0 && value < 24;
	}
	
	public Hour getIncremented(int delta) {
		return new Hour((value + delta) % 24);
	}
	
	@Override
	public String toString() {
		return Integer.toString(value) + "h";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Hour other = (Hour) obj;
		if (value != other.value) {
			return false;
		}
		return true;
	}
}
