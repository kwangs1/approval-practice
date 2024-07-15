package com.example.kwangs.Temp.UseFolderDat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatController {

	private Logger log = LoggerFactory.getLogger(DatController.class.getName());
    @Value("${temp.data.basepath}")
    private String basePath;
	
    @GetMapping("/loadDataFromDatFile")
    public DatSaveData loadDataFromDatFile(String id) throws IOException, ClassNotFoundException{
    	File userFolder = new File(basePath + File.separator + id);
        File dataFile = new File(userFolder, "userdata_folder.dat");
        DatSaveData data = null;
        
        if (!dataFile.exists()) {
            log.info("Data file not found: " + dataFile.getAbsolutePath());
        }
        try {
            // .dat 파일에서 데이터 읽기
            FileInputStream fis = new FileInputStream(dataFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            data = (DatSaveData) ois.readObject();
            log.info("loadDat Date Getter:: "+data.getFldrid()+" ... "+data.getBizunitcd()+" ... "+data.getFldrname());
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
        	log.error("Dat파일 저장경로에 해당 사용자의 임시저장된 Dat파일이 존재하지 않습니다.");
        }

        return data;
    }
    
	 @PostMapping("/SaveDatToDatFile")
	 public ResponseEntity<String> SaveDatToDatFile(String fldrid, String bizunitcd, String fldrname,
			 	HttpServletRequest request)throws IOException{
		 String id = (String)request.getSession().getAttribute("userId");
		 File userFolder = new File(basePath + File.separator + id);
		 if(!userFolder.exists()) {
			 boolean created = userFolder.mkdirs();
			 if(!created) {
				 throw new IOException("SaveParticipantTemp Failed to create directory: " + userFolder.getAbsolutePath());
			 }
		 }
		 DatSaveData data = new DatSaveData(fldrid,fldrname,bizunitcd);
		 ObjectOutputStream output = null;
		 try {
			 output = new ObjectOutputStream(new FileOutputStream(userFolder+ File.separator + "userdata_folder.dat"));
			 output.writeObject(data);
			 log.info("FLowView .Dat File Save Succes: path:: "+fldrid+", "+fldrname+", "+bizunitcd+", "+id);
		 }finally {
			 if(output != null) {
				 output.close();
			 }
		 }
		 return ResponseEntity.ok("apprfolder temp insert Success");
	 }
}
