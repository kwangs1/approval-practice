package com.example.kwangs.common.file;

import org.springframework.stereotype.Component;

@Component
public class AttachVO {
	private String uuid;
	private String uploadPath;
	private String fileName;
	private boolean fileType;	
	private String appr_seq;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public boolean isFileType() {
		return fileType;
	}
	public void setFileType(boolean fileType) {
		this.fileType = fileType;
	}
	public String getAppr_seq() {
		return appr_seq;
	}
	public void setAppr_seq(String appr_seq) {
		this.appr_seq = appr_seq;
	}
	@Override
	public String toString() {
		return "AttachVO [uuid=" + uuid + ", uploadPath=" + uploadPath + ", fileName=" + fileName + ", fileType="
				+ fileType + ", appr_seq=" + appr_seq + "]";
	}
	
	
}
