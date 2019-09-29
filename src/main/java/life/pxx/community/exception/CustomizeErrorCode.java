package life.pxx.community.exception;

/**
 * @author pxx
 * Date 2019/9/29 17:59
 * @Description
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{
	/**
	 * 找不到问题
	 */
	QUESTION_NOT_FOUND("你找的问题不在了。要不换个试试？");
	
	private String message;
	
	CustomizeErrorCode(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
