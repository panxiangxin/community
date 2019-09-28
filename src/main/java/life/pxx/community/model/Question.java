package life.pxx.community.model;

import lombok.Data;

/**
 * @author pxx
 * Date 2019/9/28 10:35
 * @Description
 */
@Data
public class Question {
	
	private Integer id;
	private String title;
	private String description;
	private String tag;
	private Long gmtCreate;
	private Long gmtModified;
	private Integer creator;
	private Integer viewCount;
	private Integer commentCount;
	private Integer likeCount;
	
}
