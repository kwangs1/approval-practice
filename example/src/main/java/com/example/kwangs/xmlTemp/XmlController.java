package com.example.kwangs.xmlTemp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.kwangs.participant.service.participantVO;
import com.example.kwangs.user.service.userVO;

@RestController
public class XmlController {
	private Logger log = LoggerFactory .getLogger(XmlController.class);
	
	@Value("${data.xml.basepath}")
	private String basePath;
	@Autowired
	private saveXmlTemp saveXmlTemp;
	
	@GetMapping("/getXmlData")
	public List<participantVO> getXmlDate(String id){
		List<participantVO> pp = new ArrayList<>();
		
		try {
			File userFolder = new File(basePath + id + "/userdata.xml");
			
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
	
	@PostMapping("/SaveFlowUseInfoTemp")
	public void SaveFlowTemp(@RequestBody List<userVO> clickedUsers, HttpServletRequest request) {
		String id = (String) request.getSession().getAttribute("userId");
		StringBuilder xmlBuilder  = new StringBuilder();
		xmlBuilder.append("<participants>");
		
		for(userVO user : clickedUsers) {
			xmlBuilder.append("<participant>");
			xmlBuilder.append("<deptid>").append(user.getDeptid()).append("</deptid>");
			xmlBuilder.append("<deptname>").append(user.getDeptname()).append("</deptname>");
			xmlBuilder.append("<id>").append(user.getId()).append("</id>");
			xmlBuilder.append("<name>").append(user.getName()).append("</name>");
			xmlBuilder.append("<pos>").append(user.getPos()).append("</pos>");
			xmlBuilder.append("</participant>");
		}
		xmlBuilder.append("</participants>");
		
		String xmlData = xmlBuilder.toString();
		log.info("SaveFlowUseInfoTemp Success");
		saveXmlTemp.SaveFlowUseInfoTemp(id, xmlData);
	}
	
	@GetMapping("/getSaveFlowUseInfoTemp")
	public List<userVO> getSaveFlowUseInfoTemp(String id) {
		List<userVO> user = new ArrayList<>();
		try {
			File userFolder = new File(basePath + id + "/userdata_flow.xml");
			log.info("getSaveFlowUseInfoTemp .."+userFolder);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(userFolder);
			
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("participant");
			
			for(int i =0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					userVO users = new userVO();
					users.setDeptid(element.getElementsByTagName("deptid").item(0).getTextContent());
					users.setDeptname(element.getElementsByTagName("deptname").item(0).getTextContent());
					users.setId(element.getElementsByTagName("id").item(0).getTextContent());
					users.setName(element.getElementsByTagName("name").item(0).getTextContent());
					users.setPos(element.getElementsByTagName("pos").item(0).getTextContent());
					user.add(users);
				}
			}
		}catch(Exception e) {
			log.error("XML 파일 저장경로에 해당 사용자의 임시저장된 XML파일이 존재하지 않습니다.");
		}
		return user;
	}
}
