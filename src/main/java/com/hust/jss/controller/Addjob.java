package com.hust.jss.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hust.jss.entity.Task;
import com.hust.jss.service.TaskService;
import com.hust.jss.utils.Config;
import com.hust.jss.utils.UploadUtils;

/**
 * 实现老师布置作业
 * @author 
 *
 */

@Controller
public class Addjob {
	
	@Autowired
	private TaskService taskservice;
	
	@RequestMapping(value = "/uploadtask", method = RequestMethod.POST)
	public String upload(HttpServletRequest request,
			@RequestParam(value="taskname") String taskName,
			@RequestParam(value="datetime") String datetime,
			@RequestParam(value = "uploadfile", required = false) MultipartFile[] uploadfile)
	{
		String road=Config.title+taskName;
		UploadUtils up = new UploadUtils();
		if(up.uploadUtils(uploadfile, road))
		{
			System.out.println("hahahah");
			Task task = new Task();
			task.setTaskName(taskName);
			task.setTaskPath(road);
			//日期转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
		    try {
				Date date = sdf.parse(datetime);
				System.out.println("%%%%%"+date);
				task.setTaskExpiry(date);
				
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			//把作业记录添加到数据库中
			try {
				taskservice.addTask(task);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "redirect:/managejob"; 
	
	}

}