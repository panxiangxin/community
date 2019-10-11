package life.pxx.community.service;

import life.pxx.community.dto.PaginationDTO;
import life.pxx.community.dto.QueryQuestionDTO;
import life.pxx.community.dto.QuestionDTO;
import life.pxx.community.exception.CustomizeErrorCode;
import life.pxx.community.exception.CustomizeException;
import life.pxx.community.mapper.QuestionExtMapper;
import life.pxx.community.mapper.QuestionMapper;
import life.pxx.community.mapper.UserMapper;
import life.pxx.community.model.Question;
import life.pxx.community.model.QuestionExample;
import life.pxx.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pxx
 * Date 2019/9/28 13:30
 * @Description
 */
@Service
public class QuestionService {
	
	@Autowired
	private QuestionExtMapper questionExtMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private QuestionMapper questionMapper;
	
	public PaginationDTO list(Long userId, Integer page, Integer size) {
		
		PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
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
		int offset = size*(page - 1);
		QuestionExample example1 = new QuestionExample();
		example1.createCriteria()
				.andCreatorEqualTo(userId);
		List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example1, new RowBounds(offset, size));
		List<QuestionDTO> questionDTOS=new ArrayList<>();
		
		for (Question question: questions) {
			User user = userMapper.selectByPrimaryKey(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question,questionDTO);
			questionDTO.setUser(user);
			questionDTOS.add(questionDTO);
		}
		paginationDTO.setData(questionDTOS);
		
		
		return paginationDTO;
	}
	
	public PaginationDTO list(String search,Integer page, Integer size) {
		if (StringUtils.isNotBlank(search)) {
			String[] tags = StringUtils.split(search, " ");
			search = String.join("|", tags);
		}
		
		PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
		QueryQuestionDTO queryQuestionDTO = new QueryQuestionDTO();
		queryQuestionDTO.setSearch(search);
		Integer totalCount = (int)questionExtMapper.countBySearch(queryQuestionDTO);
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
		QuestionExample example = new QuestionExample();
		example.setOrderByClause("gmt_create desc");
		queryQuestionDTO.setPage(offset);
		queryQuestionDTO.setSize(size);
		List<Question> questions = questionExtMapper.selectBySearch(queryQuestionDTO);
		List<QuestionDTO> questionDTOS=new ArrayList<>();
		
		for (Question question: questions) {
			User user = userMapper.selectByPrimaryKey(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question,questionDTO);
			questionDTO.setUser(user);
			questionDTOS.add(questionDTO);
		}
		paginationDTO.setData(questionDTOS);
		
		return paginationDTO;
	}
	
	public QuestionDTO getById(Long id) {
		
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
			questionMapper.insertSelective(question);
		}else {
			question.setGmtModified(System.currentTimeMillis());
			int updated = questionMapper.updateByPrimaryKeySelective(question);
			if (updated != 1) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
		}
	}
	
	public void inView(Long id) {
		Question question = new Question();
		question.setId(id);
		question.setViewCount(1);
		questionExtMapper.updateByPrimaryKeyInc(question);
	}
	
	public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
		if (StringUtils.isBlank(queryDTO.getTag())) {
				return new ArrayList<>();
		}
		String[] tags = StringUtils.split(queryDTO.getTag(), ',');
		String regexpTag = String.join("|", tags);
		
		Question question = new Question();
		question.setId(queryDTO.getId());
		question.setTag(regexpTag);
		List<Question> questions = questionExtMapper.selectRelated(question);
		List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(q,questionDTO);
			return questionDTO;
		}).collect(Collectors.toList());
		return questionDTOS;
	}
}
