package com.example.kwangs.common.file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Component;

@Component
public class fileCheckTask {

	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		
		String str = sdf.format(cal.getTime());
		return str.replace("-", File.separator);
	}
}
