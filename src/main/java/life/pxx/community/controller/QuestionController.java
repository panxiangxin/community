package life.pxx.community.controller;

import life.pxx.community.dto.CommentCreateDTO;
import life.pxx.community.dto.CommentDTO;
import life.pxx.community.dto.QuestionDTO;
import life.pxx.community.service.CommentService;
import life.pxx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author pxx
 * Date 2019/9/29 9:57
 * @Description
 */
@Controller
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private CommentService commentService;
	
	@GetMapping("/question/{id}")
	public String question(@PathVariable(value = "id") Long id, Model model) {
		
		QuestionDTO questionDTO = questionService.getById(id);
		List<CommentDTO> comments = commentService.listByQuestionId(id);
		questionService.inView(id);
		model.addAttribute("question",questionDTO);
		model.addAttribute("comments",comments);
		return "question";
	}
}
