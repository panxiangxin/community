package life.pxx.community.controller;

import life.pxx.community.dto.PaginationDTO;
import life.pxx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
						   @RequestParam(value = "size",defaultValue = "5") Integer size) {
		PaginationDTO paginationDTO = questionService.list(page,size);
		model.addAttribute("pagination",paginationDTO);
			return "index";
	}
	
}
