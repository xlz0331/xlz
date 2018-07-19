package com.hwagain.org.register.web;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hwagain.framework.core.response.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hanjin
 * @since 2017-11-04
 */
//@RestController
//@RequestMapping(value="/file",method={RequestMethod.POST})
//@Api(value="文件上传",description="文件上传")
public class FileUploadController {

	@RequestMapping(value="upload",method=RequestMethod.POST)
	@ApiOperation(value="文件上传",notes="文件上传")
	public Response upload(HttpServletRequest request,MultipartFile multipartFile){
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
		               request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			//将request变成多部分request
			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			//获取multiRequest 中所有的文件名
			Iterator iter=multiRequest.getFileNames();  //表单里可以提交了多个附件控件
			if(iter.hasNext()){
				String formFileName=iter.next().toString();//附件控件的名字
	           	List<MultipartFile> files=multiRequest.getFiles(formFileName);//获取相应名字的所有附件
	           	if(files!=null&&files.size()>0){
	           		//遍历附件并上传
	           		for (MultipartFile file : files) {
						System.err.println(file.getOriginalFilename()+"=====");
					}
	           	}
			}
		}
		return null;
	}
}
