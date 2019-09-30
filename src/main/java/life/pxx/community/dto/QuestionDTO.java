package life.pxx.community.dto;

import life.pxx.community.model.User;
import lombok.Data;

/**
 * @author pxx
 * Date 2019/9/28 13:28
 * @Description
 */
@Data
public class QuestionDTO {
	
	private Long id;
	private String title;
	private String description;
	private String tag;
	private Long gmtCreate;
	private Long gmtModified;
	private Long creator;
	private Integer viewCount;
	private Integer commentCount;
	private Integer likeCount;
	private User user;
}
