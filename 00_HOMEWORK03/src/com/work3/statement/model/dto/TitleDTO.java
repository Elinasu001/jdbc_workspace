package com.work3.statement.model.dto;

public class TitleDTO {
	
	private String challengeId;
	private String title;
	private String newTitle;
	public TitleDTO() {
		super();
		
	}
	public TitleDTO(String challengeId, String title, String newTitle) {
		super();
		this.challengeId = challengeId;
		this.title = title;
		this.newTitle = newTitle;
	}
	public String getChallengeId() {
		return challengeId;
	}
	public void setChallengeId(String challengeId) {
		this.challengeId = challengeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNewTitle() {
		return newTitle;
	}
	public void setNewTitle(String newTitle) {
		this.newTitle = newTitle;
	}
	
	
}
