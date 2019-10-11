package life.pxx.community.controller;

import life.pxx.community.cache.HotTagCache;
import life.pxx.community.dto.PaginationDTO;
import life.pxx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @author pxx
 * Date 2019/9/27 12:03
 * @Description
 */
@Controller
public class GreetingController {
	@Autowired
	private QuestionService questionService;
	@Autowired
	private HotTagCache hotTagCache;
	
	@GetMapping("/")
	public String greeting(Model model,
						   @RequestParam(value = "page",defaultValue = "1") Integer page,
						   @RequestParam(value = "size",defaultValue = "5") Integer size,
						   @RequestParam(value = "search",required = false) String search,
						   @RequestParam(value = "tag",required = false) String tag,
						   HttpServletRequest request) {
		PaginationDTO paginationDTO = questionService.list(search,tag,page,size);
		model.addAttribute("pagination",paginationDTO);
		List<String> hotTags = hotTagCache.getHotTags();
		model.addAttribute("tags",hotTags);
		model.addAttribute("tag",tag);
		HttpSession session = request.getSession();
		session.setAttribute("search",search);
			return "index";
	}
	
}
