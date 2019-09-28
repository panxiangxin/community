package life.pxx.community.model;

import lombok.Data;

/**
 * @author pxx
 * Date 2019/9/27 16:43
 * @Description
 */
@Data
public class User {
	private int id;
	private String name;
	private String accountId;
	private String token;
	private Long gmtCreate;
	private String bio;
	private Long gmtModified;
	private String avatarUrl;
}
