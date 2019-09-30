package life.pxx.community.controller;

import life.pxx.community.dto.CommentDTO;
import life.pxx.community.dto.ResultDTO;
import life.pxx.community.exception.CustomizeErrorCode;
import life.pxx.community.mapper.CommentMapper;
import life.pxx.community.model.Comment;
import life.pxx.community.model.User;
import life.pxx.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pxx
 * Date 2019/9/30 9:43
 * @Description
 */
@Controller
public class CommentController {
	
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private CommentService commentService;
	
	@ResponseBody
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {
		
		User  user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return ResultDTO.error(CustomizeErrorCode.NO_LOGIN);
		}
		
		Comment record = new Comment();
		record.setParentId(commentDTO.getParentId());
		record.setContent(commentDTO.getContent());
		record.setType(commentDTO.getType());
		record.setGmtCreate(System.currentTimeMillis());
		record.setGmtModified(record.getGmtCreate());
		record.setLikeCount(0);
		record.setCommentator(user.getId());
		commentService.insert(record);
		
		return ResultDTO.okOf();
	}
}
