package com.fwcd.fructose.exception;

/**
 * This class is meant to indicate unfished pieces of code whilst
 * not having to specify a return type for the given method.<br>
 * <b><u>This class is intentionally marked as deprecated to cause a compiler
 * error where this exception is thrown. Thus you should feel free to use this exception.
 * It makes sense to additionally include a todo comment!</u></b>
 */
@Deprecated
public class TodoException extends RuntimeException {
	private static final long serialVersionUID = 3042893373197517970L;
	
	/**
	 * Constructs a new TodoException. <b><u>This class is intentionally
	 * marked as deprecated to cause a compiler error where this exception is thrown. Thus
	 * you should feel free to use this exception.
	 * It makes sense to additionally include a todo comment!</u></b>
	 */
	public TodoException() {
		super("TODO - Tried to execute unfinished code.");
	}
}
