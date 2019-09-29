package life.pxx.community.advice;

import life.pxx.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pxx
 * Date 2019/9/29 16:52
 * @Description
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	ModelAndView handle(Throwable ex, Model model) {
		if (ex instanceof CustomizeException) {
			model.addAttribute("message",ex.getMessage());
		}else {
			model.addAttribute("message","服务冒烟了！要不稍后试试？");
		}
		return new ModelAndView("error");
	}
	
}
