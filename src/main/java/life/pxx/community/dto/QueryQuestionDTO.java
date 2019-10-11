package life.pxx.community.dto;

import lombok.Data;

/**
 * @author pxx
 * Date 2019/10/11 9:31
 * @Description
 */
@Data
public class QueryQuestionDTO {
	private String search;
	private Integer size;
	private Integer page;
	private String tag;
}
