package life.pxx.community.cache;

import life.pxx.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pxx
 * Date 2019/10/9 11:06
 * @Description
 */
public class TagCache {
	public static List<TagDTO> get(){
		ArrayList<TagDTO> tagDTOS = new ArrayList<>();
		TagDTO program = new TagDTO();
		program.setCategoryName("开发语言");
		program.setTags(Arrays.asList("js","php","Java","css","html","python","node","erlang","go","kotlin","C++","C#","C","javaScript","scala","VC++"));
		tagDTOS.add(program);
		
		TagDTO frameWork = new TagDTO();
		frameWork.setCategoryName("平台框架");
		frameWork.setTags(Arrays.asList("spring","django","flask","struts","koa"));
		tagDTOS.add(frameWork);
		
		TagDTO server = new TagDTO();
		server.setCategoryName("服务器");
		server.setTags(Arrays.asList("linux","nginx","docker","apache","ubuntu","window-server"));
		tagDTOS.add(server);
		
		TagDTO dataBase = new TagDTO();
		dataBase.setCategoryName("数据库");
		dataBase.setTags(Arrays.asList("mysql","neo4j","oracle","sqlServer","nosql","redis"));
		tagDTOS.add(dataBase);
		
		TagDTO tools = new TagDTO();
		tools.setCategoryName("开发工具");
		tools.setTags(Arrays.asList("git","xcode","idea","maven","svn","emacs","textmate","eclipse","vim"));
		tagDTOS.add(tools);
		
		return tagDTOS;
	}
	
	public static String filterInvalid(String tags) {
		String[] split = StringUtils.split(tags, ",");
		List<TagDTO> tagDTOS = get();
		
		List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
		String inValid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
		
		return inValid;
	}
}
