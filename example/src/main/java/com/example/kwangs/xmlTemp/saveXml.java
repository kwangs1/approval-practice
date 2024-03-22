package com.example.kwangs.xmlTemp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class saveXml {
	private Logger log = LoggerFactory .getLogger(saveXml.class);

	@Value("${data.xml.basepath}")
	private String basePath;
	
	public void saveXml(String id,String xmlData) {
		try {
			//folder add
			File userFolder = new File(basePath + File.separator + id);
			if (!userFolder.exists()) {
			    boolean created = userFolder.mkdirs(); // 폴더 생성
			    if (!created) {
			        throw new IOException("Failed to create directory: " + userFolder.getAbsolutePath());
			    }
			}
			
			//xml file add & save
			File file = new File(userFolder, "userdata.xml");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(xmlData);
			fileWriter.close();
			
			log.info("xml 데이터가 파일로 저장 되었습니다. 경로: "+ file.getAbsolutePath());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
