package life.pxx.community.dto;

import lombok.Data;

/**
 * @author pxx
 * Date 2019/9/27 14:05
 * @Description
 */
@Data
public class AccessTokenDTO {
	
	private String client_id;
	private String client_secret;
	private String redirect_uri;
	private String code;
	private String state;
}
