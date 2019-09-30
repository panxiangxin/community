package life.pxx.community.advice;

import com.alibaba.fastjson.JSON;
import life.pxx.community.dto.ResultDTO;
import life.pxx.community.exception.CustomizeErrorCode;
import life.pxx.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author pxx
 * Date 2019/9/29 16:52
 * @Description
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	ModelAndView handle(Throwable ex, Model model , HttpServletRequest request, HttpServletResponse response) {
		String contentType = request.getContentType();
		
		if ("application/json".equals(contentType)) {
			ResultDTO resultDTO;
			//返回JSON
			if (ex instanceof CustomizeException) {
				resultDTO = ResultDTO.error((CustomizeException) ex);
			} else {
				resultDTO = ResultDTO.error(CustomizeErrorCode.SYSTEM_ERROR);
			}
			try {
				response.setContentType("application/json");
				response.setCharacterEncoding("utf-8");
				response.setStatus(200);
				PrintWriter writer = response.getWriter();
				writer.write(JSON.toJSONString(resultDTO));
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			if (ex instanceof CustomizeException) {
				model.addAttribute("message", ex.getMessage());
			} else {
				model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR);
			}
			return new ModelAndView("error");
		}
		
	}
}
