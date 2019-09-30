package life.pxx.community.exception;

/**
 * @author pxx
 * Date 2019/9/29 17:43
 * @Description
 */
public class CustomizeException extends RuntimeException {
	private String message;
	private Integer code;
	
	public CustomizeException(ICustomizeErrorCode errorCode) {
		this.message = errorCode.getMessage();
		this.code = errorCode.getCode();
	}
	@Override
	public String getMessage() {
		return message;
	}
	
	public Integer getCode() {
		return code;
	}
}
