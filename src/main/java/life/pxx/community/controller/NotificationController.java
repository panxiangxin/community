package life.pxx.community.controller;

import life.pxx.community.dto.NotificationDTO;
import life.pxx.community.dto.PaginationDTO;
import life.pxx.community.enums.NotificationEnum;
import life.pxx.community.model.Notification;
import life.pxx.community.model.User;
import life.pxx.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pxx
 * Date 2019/10/9 20:29
 * @Description
 */
@Controller
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/notification/{id}")
	public String profile(@PathVariable(name = "id") Long id,
						  HttpServletRequest request){
		
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "redirect:/";
		}
		
		NotificationDTO notificationDTO = notificationService.read(id,user);
		if((NotificationEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) ||
				   (NotificationEnum.REPLY_COMMENT.getType() == notificationDTO.getType())){
			return "redirect:/question/" + notificationDTO.getOuterid();
		}else {
			return "redirect:/";
		}
	}
}
