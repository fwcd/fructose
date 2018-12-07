package fwcd.fructose.exception;

public class SerializationException extends RuntimeException {
	private static final long serialVersionUID = -7387648273641L;
	
	public SerializationException() {
		
	}
	
	public SerializationException(Throwable cause) {
		super(cause);
	}
	
	public SerializationException(String msg) {
		super(msg);
	}
}
