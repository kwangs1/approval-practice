package com.example.kwangs.stamp.service;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class stampVO {

	private String id;
	private int fno;
	private int type;
	private String name;
	private List<stampDTO> stamp;
	
	
	public List<stampDTO> getStamp() {
		return stamp;
	}
	public void setStamp(List<stampDTO> stamp) {
		this.stamp = stamp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getFno() {
		return fno;
	}
	public void setFno(int fno) {
		this.fno = fno;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
