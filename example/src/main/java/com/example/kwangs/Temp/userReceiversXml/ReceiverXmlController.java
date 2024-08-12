package com.example.kwangs.Temp.userReceiversXml;

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

import com.example.kwangs.dept.service.deptVO;

@RestController
public class ReceiverXmlController {
	private Logger log = LoggerFactory.getLogger(ReceiverXmlController.class);
	
	@Value("${temp.data.basepath}")
	private String basePath;
	@Autowired
	private saveReceiverXmlTemp temp;
	
	@PostMapping("/SaveReceiverTemp")
	public void SaveReceiverTemp(@RequestBody List<deptVO> clickDept, HttpServletRequest request) {
		String id = (String)request.getSession().getAttribute("userId");
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<receivers>");
		
		for(deptVO dept : clickDept) {
			xmlBuilder.append("<receiver>");
			xmlBuilder.append("<deptid>").append(dept.getDeptid()).append("</deptid>");
			xmlBuilder.append("<sendername>").append(dept.getSendername()).append("</sendername>");
			xmlBuilder.append("</receiver>");
		}
		xmlBuilder.append("</receivers>");
		
		String xmlData = xmlBuilder.toString();
		log.info("SaveReceiverTemp Success");
		temp.SaveReceiverTemp(id, xmlData);
	}
	
	@GetMapping("/getSaveReceiverTemp")
	public List<deptVO> getSaveReceiverTemp(String id){
		List<deptVO> dept = new ArrayList<>();
		try {
			File userFolder = new File(basePath + File.separator + id + "/userdata_receiver.xml");
			log.info("getSaveReceiverTemp .."+userFolder);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			Document doc = builder.parse(userFolder);
			doc.getDocumentElement().normalize();
			
			NodeList nodeList = doc.getElementsByTagName("receiver");			
			for(int i=0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)node;
					deptVO TempDept = new deptVO();
					TempDept.setDeptid(element.getElementsByTagName("deptid").item(0).getTextContent());
					TempDept.setSendername(element.getElementsByTagName("sendername").item(0).getTextContent());
					dept.add(TempDept);
				}
			}
		}catch(Exception e) {
			log.error("XML 파일 저장경로에 해당 사용자의 임시저장된 XML파일이 존재하지 않습니다.[수신처]");
		}
		return dept;
	}
}
