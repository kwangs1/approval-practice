package com.example.kwangs.Temp.UseFlowXml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class saveXmlTemp {
	private Logger log = LoggerFactory .getLogger(saveXmlTemp.class);

	@Value("${temp.data.basepath}")
	private String basePath;

	//기안 뷰에 그려진 결재선 xml로 저장
	public void SaveFlowUseInfoTemp(String id, String xmlData) {
		try {
			File userFolder = new File(basePath + File.separator + id);
			if(!userFolder.exists()) {
				boolean created = userFolder.mkdirs();
				if(!created) {
					throw new IOException("SaveFlowUseInfoTemp Failed to create directory:" + userFolder.getAbsolutePath());
				}
			}
			
			File file = new File(userFolder, "userdata_flow.xml");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(xmlData);
			fileWriter.close();
			
			log.info("SaveFlowUseInfoTemp 데이터가 파일로 저장 되었습니다. 경로: "+file.getAbsolutePath());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}	

}
