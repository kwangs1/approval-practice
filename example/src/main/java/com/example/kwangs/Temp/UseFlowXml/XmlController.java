package com.example.kwangs.Temp.UseFlowXml;

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
	
	@Value("${temp.data.basepath}")
	private String basePath;
	@Autowired
	private saveXmlTemp saveXmlTemp;
	
	@GetMapping("/getXmlData")
	public List<participantVO> getXmlDate(String id){
		List<participantVO> pp = new ArrayList<>();
		
		try {
			File userFolder = new File(basePath + id + "/ApprFlow.xml");
			
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
	
	//결재선 지정 시 결재선 지정한 유저의 결재선 지정 데이터 임시저장
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
	
	//결재선 지정 시 결재선 지정한 유저의 결재선 지정 데이터 임시저장 한 데이터 가져오기
	@GetMapping("/getSaveFlowUseInfoTemp")
	public List<userVO> getSaveFlowUseInfoTemp(String id) {
		//xml의 데이터를 가져와서 담을 곳
		List<userVO> user = new ArrayList<>();
		try {
			//xml파일이 저장된 곳
			File userFolder = new File(basePath + id + "/ViewApprFlow.xml");
			log.info("getSaveFlowUseInfoTemp .."+userFolder);
			
			//xml을 파싱하여 데이터 추출
			//DocumentBuilderFactory 을 사용하여 xml 문서를 파싱할 builder 생성
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			//xml 파일 파싱하고, 파싱된 문서 doc변수에 저장
			Document doc = builder.parse(userFolder);
			
			//xml문서의 루트요소 이동
			doc.getDocumentElement().normalize();
			//xml문서에서의 participant태그 가진 요소 모두 가져오기
			NodeList nodeList = doc.getElementsByTagName("participant");
			
			//participant태그에서 가져온 요소를 데이터 추출
			for(int i =0; i < nodeList.getLength(); i++) {
				//participant태그를 하나씩 가져와 각각 Element 객체로 변환
				Node node = nodeList.item(i);
				//자식 노드가 요소일 경우에만 실행
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
