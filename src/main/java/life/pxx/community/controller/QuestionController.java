package life.pxx.community.controller;

import life.pxx.community.dto.QuestionDTO;
import life.pxx.community.mapper.QuestionMapper;
import life.pxx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author pxx
 * Date 2019/9/29 9:57
 * @Description
 */
@Controller
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/question/{id}")
	public String question(@PathVariable(value = "id") Integer id, Model model) {
		
		QuestionDTO questionDTO = questionService.getById(id);
		questionService.inView(id);
		model.addAttribute("question",questionDTO);
		return "question";
	}
}
