package life.pxx.community.service;

import life.pxx.community.dto.PaginationDTO;
import life.pxx.community.dto.QuestionDTO;
import life.pxx.community.mapper.QuestionMapper;
import life.pxx.community.mapper.UserMapper;
import life.pxx.community.model.Question;
import life.pxx.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pxx
 * Date 2019/9/28 13:30
 * @Description
 */
@Service
public class QuestionService {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private QuestionMapper questionMapper;
	
	public PaginationDTO list(Integer page, Integer size) {
		
		PaginationDTO paginationDTO = new PaginationDTO();
		Integer totalCount = questionMapper.count();
		paginationDTO.setPagination(totalCount,page,size);
		if(page < 1){
			page = 1;
		}
		if (page > paginationDTO.getTotalPage()) {
			page = paginationDTO.getTotalPage();
		}
		
		Integer offset = size*(page - 1);
		List<Question> questions = questionMapper.list(offset,size);
		List<QuestionDTO> questionDTOS=new ArrayList<>();
		
		for (Question question: questions) {
			User user = userMapper.findById(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question,questionDTO);
			questionDTO.setUser(user);
			questionDTOS.add(questionDTO);
		}
		paginationDTO.setQuestions(questionDTOS);
		
		
		return paginationDTO;
	}
}
