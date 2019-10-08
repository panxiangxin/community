package life.pxx.community.enums;

/**
 * @author pxx
 * Date 2019/9/30 10:08
 * @Description 关于评论类型
 */
public enum CommentTypeEnum {
	/**
	 * 1为提问
	 * 2为评论
	 */
	QUESTION(1),COMMENT(2);
	private Integer type;
	
	CommentTypeEnum(Integer type) {
		this.type = type;
	}
	
	public static boolean isExist(Integer type) {
		for (CommentTypeEnum value : CommentTypeEnum.values()) {
			if(value.getType().equals(type)){
				return true;
			}
		}
		return false;
	}
	
	public Integer getType() {
		return type;
	}
}
