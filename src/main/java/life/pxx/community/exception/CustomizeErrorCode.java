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
	QUESTION_NOT_FOUND("你找的问题不在了。要不换个试试？",2001),
	TARGET_PARAM_NOT_FOUND("未选中任何问题或评论进行回复",2002),
	SYSTEM_ERROR("服务器异常",2004),
	TYPE_PARAM_WRONG("评论类型不存在",2005),
	COMMENT_NOT_FOUND("评论不存在",2006),
	NO_LOGIN("需要先登录才能进行操作！",2003);
	
	private String message;
	private Integer code;
	
	CustomizeErrorCode(String message,Integer code) {
		this.message = message;
		this.code = code;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	@Override
	public Integer getCode() {
		return code;
	}
}
