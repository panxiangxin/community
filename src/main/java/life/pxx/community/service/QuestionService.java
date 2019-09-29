package life.pxx.community.service;

import life.pxx.community.dto.PaginationDTO;
import life.pxx.community.dto.QuestionDTO;
import life.pxx.community.exception.CustomizeErrorCode;
import life.pxx.community.exception.CustomizeException;
import life.pxx.community.mapper.QuestionMapper;
import life.pxx.community.mapper.UserMapper;
import life.pxx.community.model.Question;
import life.pxx.community.model.QuestionExample;
import life.pxx.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
		QuestionExample example = new QuestionExample();
		example.createCriteria()
				.andCreatorEqualTo(userId);
		Integer totalCount = (int)questionMapper.countByExample(example);
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
		QuestionExample example1 = new QuestionExample();
		example.createCriteria()
				.andCreatorEqualTo(userId);
		List<Question> questions = questionMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));
		List<QuestionDTO> questionDTOS=new ArrayList<>();
		
		for (Question question: questions) {
			User user = userMapper.selectByPrimaryKey(question.getCreator());
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
		Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
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
		List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
		List<QuestionDTO> questionDTOS=new ArrayList<>();
		
		for (Question question: questions) {
			User user = userMapper.selectByPrimaryKey(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question,questionDTO);
			questionDTO.setUser(user);
			questionDTOS.add(questionDTO);
		}
		paginationDTO.setQuestions(questionDTOS);
		
		return paginationDTO;
	}
	
	public QuestionDTO getById(Integer id) {
		
		Question question = questionMapper.selectByPrimaryKey(id);
		if (question == null) {
			throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
		}
		QuestionDTO questionDTO = new QuestionDTO();
		BeanUtils.copyProperties(question,questionDTO);
		User user = userMapper.selectByPrimaryKey(question.getCreator());
		questionDTO.setUser(user);
		
		return questionDTO;
	}
	public void createOrUpdate(Question question) {
		if(question.getId() == null){
			question.setGmtCreate(System.currentTimeMillis());
			question.setGmtModified(question.getGmtCreate());
			questionMapper.insert(question);
		}else {
			question.setGmtModified(System.currentTimeMillis());
			int updated = questionMapper.updateByPrimaryKeySelective(question);
			if (updated != 1) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
		}
	}
}
