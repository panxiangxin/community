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
	NO_LOGIN("需要先登录才能进行操作！",2003),
	COMMENT_IS_EMPTY("评论内容不能为空！",2007),
	READ_NOTIFICATION_FAILED("兄弟，你这是都别人信息呢！",2008),
	NOTIFICATION_NOT_FOUND("消息不翼而飞了！",2009),
	FILE_UPLOAD_FAIL("文件上传失败！",2010),
	;
	
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
