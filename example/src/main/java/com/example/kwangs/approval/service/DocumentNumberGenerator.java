package com.example.kwangs.approval.service;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.kwangs.approval.mapper.approvalMapper;

@Component
public class DocumentNumberGenerator {
	private static Logger log = LoggerFactory.getLogger(DocumentNumberGenerator.class);
	
	@Autowired
	private approvalMapper mapper;
	
	/*
	 * 문서 최종결재시 문서번호 체결에 대한 메서드
	 * synchronized(동기화)를 사용하여, 여러 쓰레드가 동시에 호출 시 하나의 쓰레드가 완전히 완료되기 전까진 다른 쓰레드가 간섭하지 못하게 함.
	 */
	public synchronized String genearteDocumentNumber(String deptid) {
		log.info("deptid{}"+deptid);
		Document document = mapper.findByDeptDocNo(deptid);
		
		//현재 년도가져오기
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		log.info("currentYear value{}"+currentYear);
		
		if(document == null || document.getYear() != currentYear) {
			//새로운 년도이거나 데이터가 없는경우
			document = new Document();
			document.setDeptid(deptid);
			document.setDeptdocno(1);
			document.setYear(currentYear);
			log.info("새로운 년도 및 데이터 없는 경우" +document.getDeptid()+"/"+document.getDeptdocno()+"/"+document.getYear());
		}else {
			document.setDeptdocno(document.getDeptdocno()+1);
			log.info("데이터 있는경우.."+document.getDeptdocno());
		}
		//데이터 저장
		mapper.save(document);
		
		return generateFormattedDocumentNumber(deptid, document.getDeptdocno());
	}
	
	private String generateFormattedDocumentNumber(String deptid, int deptdocno) {
		return "-"+deptdocno;
	}
}
