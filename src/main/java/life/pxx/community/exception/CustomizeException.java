package life.pxx.community.exception;

/**
 * @author pxx
 * Date 2019/9/29 17:43
 * @Description
 */
public class CustomizeException extends RuntimeException {
	private String message;
	
	public CustomizeException(String message) {
		this.message = message;
	}
	
	public CustomizeException(ICustomizeErrorCode errorCode) {
		this.message = errorCode.getMessage();
	}
	@Override
	public String getMessage() {
		return message;
	}
}
