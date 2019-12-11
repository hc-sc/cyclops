package ca.gc.hc.nhpd.dto;

public class VocabularySearchCriteria{
 	public String searchSynonym;
 	public String searchField;
 	
 	public VocabularySearchCriteria(String synonym, String searchField){
 		this.searchSynonym = synonym;
 		this.searchField = searchField;
 	}
 	
 	public void setSrchSyn(String synonym){
 		this.searchSynonym = synonym;
 	}
 	
 	public String getSearchSynonym(){
 		return searchSynonym;
 	}
 	
 	public void setSrchField(String searchField){
 		this.searchField = searchField;
 	}
 	
 	public String getSrchField(){
 		return searchField;
 	}
 }