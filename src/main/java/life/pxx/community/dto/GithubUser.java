package life.pxx.community.dto;

import lombok.Data;

/**
 * @author pxx
 * Date 2019/9/27 14:39
 * @Description
 */
@Data
public class GithubUser {
	
	private String name;
	private Long id;
	private String bio;
	private String avatar_url;
	
}
