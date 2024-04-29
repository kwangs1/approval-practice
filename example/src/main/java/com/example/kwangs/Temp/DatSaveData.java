package com.example.kwangs.Temp;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class DatSaveData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	 	private String fldrid;
	    private String bizunitcd;
	    private String procdeptid;
	    private String fldrname;

	    public DatSaveData(String fldrid, String bizunitcd, String procdeptid, String fldrname) {
	        this.fldrid = fldrid;
	        this.bizunitcd = bizunitcd;
	        this.procdeptid = procdeptid;
	        this.fldrname = fldrname;
	    }

		public String getFldrid() {
			return fldrid;
		}

		public void setFldrid(String fldrid) {
			this.fldrid = fldrid;
		}

		public String getBizunitcd() {
			return bizunitcd;
		}

		public void setBizunitcd(String bizunitcd) {
			this.bizunitcd = bizunitcd;
		}

		public String getProcdeptid() {
			return procdeptid;
		}

		public void setProcdeptid(String procdeptid) {
			this.procdeptid = procdeptid;
		}

		public String getFldrname() {
			return fldrname;
		}

		public void setFldrname(String fldrname) {
			this.fldrname = fldrname;
		}
	    
	    
}
