package com.example.kwangs.Temp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatController {

	private Logger log = LoggerFactory.getLogger(DatController.class.getName());
    @Value("${dat.file.path}")
    private String datFilePath;
	
    @GetMapping("/loadDataFromDatFile")
    public DatSaveData loadDataFromDatFile(String id) throws IOException, ClassNotFoundException{
    	File userFolder = new File(datFilePath + File.separator + id);
        File dataFile = new File(userFolder, "userFolder.dat");
        DatSaveData data = null;
        
        if (!dataFile.exists()) {
            throw new FileNotFoundException("Data file not found: " + dataFile.getAbsolutePath());
        }
        log.info("...dataFile "+dataFile);
        try {
            // .dat 파일에서 데이터 읽기
            FileInputStream fis = new FileInputStream(dataFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            data = (DatSaveData) ois.readObject();
            log.info(".... "+data.getFldrid()+" ... "+data.getBizunitcd()+" ... "+data.getFldrname());
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return data;
    }
}
