package life.pxx.community.dto;

import life.pxx.community.exception.CustomizeErrorCode;
import life.pxx.community.exception.CustomizeException;
import lombok.Data;

/**
 * @author pxx
 * Date 2019/9/30 10:03
 * @Description
 */
@Data
public class ResultDTO<T> {
	private Integer code;
	private String message;
	private T data;
	
	public static ResultDTO error(Integer code,String message){
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setCode(code);
		resultDTO.setMessage(message);
		return resultDTO;
	}
	
	public static ResultDTO error(CustomizeErrorCode noLogin) {
		return error(noLogin.getCode(),noLogin.getMessage());
	}
	public static ResultDTO okOf(){
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setCode(200);
		resultDTO.setMessage("请求成功");
		return resultDTO;
	}
	public static <T> ResultDTO okOf(T t){
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setCode(200);
		resultDTO.setMessage("请求成功");
		resultDTO.setData(t);
		return resultDTO;
	}
	public static ResultDTO error(CustomizeException ex) {
		return error(ex.getCode(),ex.getMessage());
	}
}
