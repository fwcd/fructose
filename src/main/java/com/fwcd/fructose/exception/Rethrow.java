package com.fwcd.fructose.exception;

/**
 * Indicates that the given exception is a wrapper
 * for another Throwable. The source/cause can be
 * fetched through getCause().<br><br>
 * 
 * This exception should (in most cases) only be used, when a rethrown
 * checked exception is not intended to be catched.
 * 
 * @author Fredrik
 *
 */
public class Rethrow extends RuntimeException {
	private static final long serialVersionUID = -8354898302253502862L;
	
	public Rethrow(Throwable e) {
		super(e);
	}
	
	/**
	 * Rethrows the given exception as unchecked.
	 * 
	 * @deprecated Completely hides the exception making it uncatchable!
	 * @param e - Any exception
	 * @return Won't actually return anything because the exception is thrown before
	 * @throws T - A compiler trick to circumvent checked exceptions
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <E extends Throwable> RuntimeException of(Throwable e) throws E {
		throw (E) e;
	}
}
