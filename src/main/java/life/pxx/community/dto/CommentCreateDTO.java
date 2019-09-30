package life.pxx.community.dto;

import lombok.Data;

/**
 * @author pxx
 * Date 2019/9/30 9:47
 * @Description
 */
@Data
public class CommentCreateDTO {

	private Long parentId;
	private Integer type;
	private String content;
}
