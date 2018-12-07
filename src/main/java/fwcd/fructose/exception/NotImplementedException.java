package fwcd.fructose.exception;

public class NotImplementedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NotImplementedException() {
		
	}
	
	public NotImplementedException(String reason) {
		super(reason);
	}
}
