package com.example.kwangs.Temp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class saveDatTemp {
	
	private Logger log = LoggerFactory.getLogger(saveDatTemp.class.getName());
    @Value("${dat.file.path}")
    private String datFilePath;
    
    public void saveDataToDatFile(String fldrid, String fldrname, String bizunitcd, String id)throws IOException{
		File userFolder = new File(datFilePath + File.separator + id);
		if (!userFolder.exists()) {
		    boolean created = userFolder.mkdirs(); // 폴더 생성
		    if (!created) {
		        throw new IOException("SaveParticipantTemp Failed to create directory: " + userFolder.getAbsolutePath());
		    }
		}
		/*
    	apprfolderVO apprfolder = new DataFile(fldrid, bizunitcd, procdeptid, fldrname);
        apprfolder.setFldrid(fldrid);
        apprfolder.setBizunitcd(bizunitcd);
        apprfolder.setProcdeptid(procdeptid);
        apprfolder.setFldrname(fldrname);*/
		DatSaveData data = new DatSaveData(fldrid, fldrname, bizunitcd);
    	ObjectOutputStream output = null;
    	try {
    		output = new ObjectOutputStream(new FileOutputStream(userFolder + File.separator + "userFolder.dat"));
    		output.writeObject(data);
    		log.info(".Dat File Save "+fldrid+", "+fldrname+", "+bizunitcd );
    	}finally{
    		if(output != null) {
    			output.close();
    		}
    	}
    }
    
}
