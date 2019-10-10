package life.pxx.community.controller;

import life.pxx.community.dto.FileDTO;
import life.pxx.community.provider.UCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * @author pxx
 * Date 2019/10/10 13:36
 * @Description
 */
@Controller
public class FileController {
	@Autowired
	private UCloudProvider uCloudProvider;
	
	@RequestMapping("/file/upload")
	@ResponseBody
	public FileDTO upload(HttpServletRequest request){
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
		try {
			assert file != null;
			String fileName = uCloudProvider.upload(file.getInputStream(), file.getContentType(), Objects.requireNonNull(file.getOriginalFilename()));
			FileDTO fileDTO = new FileDTO();
			fileDTO.setSuccess(1);
			fileDTO.setUrl(fileName);
			return fileDTO;
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileDTO fileDTO = new FileDTO();
		fileDTO.setSuccess(1);
		fileDTO.setUrl("/img/pxx.jpg");
		return fileDTO;
	}
}
