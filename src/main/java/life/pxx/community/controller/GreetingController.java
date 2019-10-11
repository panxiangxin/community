package life.pxx.community.controller;

import life.pxx.community.dto.PaginationDTO;
import life.pxx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @author pxx
 * Date 2019/9/27 12:03
 * @Description
 */
@Controller
public class GreetingController {
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/")
	public String greeting(Model model,
						   @RequestParam(value = "page",defaultValue = "1") Integer page,
						   @RequestParam(value = "size",defaultValue = "5") Integer size,
						   @RequestParam(value = "search",required = false) String search,
						   HttpServletRequest request) {
		PaginationDTO paginationDTO = questionService.list(search,page,size);
		model.addAttribute("pagination",paginationDTO);
		HttpSession session = request.getSession();
		session.setAttribute("search",search);
//		model.addAttribute("search",search);
			return "index";
	}
	
}
