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
	
	public PaginationDTO list(int userId, Integer page, Integer size) {
		
		PaginationDTO paginationDTO = new PaginationDTO();
		Integer totalCount = questionMapper.countByUserId(userId);
		Integer totalPage;
		if(totalCount%size == 0){
			totalPage = totalCount/size;
		}else {
			totalPage = totalCount/size + 1;
		}
		if(page < 1){
			page = 1;
		}
		if (page > totalPage) {
			page = totalPage;
		}
		paginationDTO.setPagination(totalPage,page);
		Integer offset = size*(page - 1);
		List<Question> questions = questionMapper.listByUserId(userId,offset,size);
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
	
	public PaginationDTO list(Integer page, Integer size) {
		
		PaginationDTO paginationDTO = new PaginationDTO();
		Integer totalCount = questionMapper.count();
		Integer totalPage;
		if(totalCount%size == 0){
			totalPage = totalCount/size;
		}else {
			totalPage = totalCount/size + 1;
		}
		if(page < 1){
			page = 1;
		}
		if (page > totalPage) {
			page = totalPage;
		}
		paginationDTO.setPagination(totalPage,page);
		
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
	
	public QuestionDTO getById(Integer id) {
		
		Question question = questionMapper.getById(id);
		QuestionDTO questionDTO = new QuestionDTO();
		BeanUtils.copyProperties(question,questionDTO);
		User user = userMapper.findById(question.getCreator());
		questionDTO.setUser(user);
		
		return questionDTO;
	}
	
	
	public void createOrUpdate(Question question) {
		if(question.getId() == null){
			question.setGmtCreate(System.currentTimeMillis());
			question.setGmtModified(question.getGmtCreate());
			questionMapper.create(question);
		}else {
			question.setGmtModified(System.currentTimeMillis());
			questionMapper.update(question);
		}
	}
}
