package com.example.kwangs.Temp.userReceiversXml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class saveReceiverXmlTemp {
	private Logger log = LoggerFactory.getLogger(saveReceiverXmlTemp.class);
	
	@Value("${temp.data.basepath}")
	private String basePath;
	
	public void SaveReceiverTemp(String id,String xmlData) {
		try {
			File userFolder = new File(basePath + File.separator + id);
			if(!userFolder.exists()) {
				boolean created = userFolder.mkdirs();
				if(!created) {
					throw new IOException("SaveReceiverTemp Failed to create directory: "+userFolder.getAbsolutePath());
				}
			}
			
			File file = new File(userFolder, "userdata_receiver.xml");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(xmlData);
			fileWriter.close();
			log.info("SaveReceiverTemp 데이터가 파일로 저장되었습니다. 경로: "+file.getAbsolutePath());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
