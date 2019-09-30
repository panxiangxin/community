package life.pxx.community.dto;

import life.pxx.community.model.User;
import lombok.Data;

/**
 * @author pxx
 * Date 2019/9/30 16:44
 * @Description
 */
@Data
public class CommentDTO {
	
	private Long id;
	private Long parentId;
	private Integer type;
	private Long commentator;
	private Long gmtCreate;
	private Long gmtModified;
	private Integer likeCount;
	private String content;
	private User user;
}
