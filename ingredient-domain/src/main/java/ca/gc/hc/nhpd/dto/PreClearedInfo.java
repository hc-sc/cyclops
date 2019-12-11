package ca.gc.hc.nhpd.dto;

import java.math.BigDecimal;

public class PreClearedInfo {
 private long monoId;
 private String name;

	public PreClearedInfo( ) {
		//default constructor
	}
	
	 public PreClearedInfo(Object[] searchResult ) {
		 this.monoId = ((BigDecimal)searchResult[0]).longValue();
	     this.name            = (String)searchResult[1];
	 }
	 
	public long getMonoId() {
		return monoId;
	}
	
	public void setMonoId(long monoId) {
		this.monoId = monoId;
	}
	
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	 
}
