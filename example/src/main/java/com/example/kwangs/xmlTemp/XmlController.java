package com.example.kwangs.xmlTemp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.kwangs.participant.service.participantVO;

@Controller
public class XmlController {
	private Logger log = LoggerFactory .getLogger(XmlController.class);
	
	@Value("${data.xml.basepath}")
	private String basePath;
	
	@GetMapping("/getXmlData")
	@ResponseBody
	public List<participantVO> getXmlDate(String id){
		List<participantVO> pp = new ArrayList<>();
		log.info("getXmlData user ? " +id);
		
		try {
			File userFolder = new File(basePath + id + "/userdata.xml");
			log.info("getXmlData 어디경로에서 가져오니? "+userFolder);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(userFolder);
			
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("participant");
			
			for(int i=0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)node;
					participantVO participant = new participantVO();
                    participant.setDeptid(element.getElementsByTagName("deptid").item(0).getTextContent());
                    participant.setDeptname(element.getElementsByTagName("deptname").item(0).getTextContent());
                    participant.setSignerid(element.getElementsByTagName("signerid").item(0).getTextContent());
                    participant.setSignername(element.getElementsByTagName("signername").item(0).getTextContent());
                    participant.setPos(element.getElementsByTagName("pos").item(0).getTextContent());
                    participant.setStatus(element.getElementsByTagName("status").item(0).getTextContent());
					pp.add(participant);
				}
			}
		}catch(Exception e) {
			log.error("XML 파일 저장경로에 해당 사용자의 임시저장된 XML파일이 존재하지 않습니다.");
		}
		return pp;
	}
}
