package fwcd.fructose;

import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * A type that has only one value.
 * 
 * <p>It differs from {@link Void} in that it
 * is represented by an actual instance rather
 * than {@code null}. Furthermore, {@link Unit}
 * is {@link Serializable}.</p>
 */
public final class Unit implements Serializable {
	private static final long serialVersionUID = 2754598407778235649L;
	public static final Unit UNIT = new Unit();
	
	private Unit() {}
	
	@Override
	public String toString() { return "Unit"; }
	
	@Override
	public int hashCode() { return 0; }
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		return obj instanceof Unit;
	}
}
