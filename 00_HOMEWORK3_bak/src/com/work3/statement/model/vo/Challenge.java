package com.work3.statement.model.vo;

import java.sql.Date;
import java.util.Objects;

public class Challenge {
	private int challengeNo; 	 	 // CHALLENGE_NO
	private String challengeId; 	 // CHALLENGE_ID
	private String title;		     // TITLE
	private String desc; 			 // DESCRIPTION
	private String startDate; 		 // START_DATE
	private String endDate; 		 	 // END_DATE
	private int rewardPoint; 		 // REWARD_POINT
	private int creatorUserNo;       // CREATOR_USER_NO
    private Date enrollDate;         // ENROLL_DATE
    
    public Challenge() {
		super();
	}
	public Challenge(String challengeId, String title, String desc, String startDate, String endDate,
			int rewardPoint, int creatorUserNo) {
		this.challengeId = challengeId;
		this.title = title;
		this.desc = desc;
		this.startDate = startDate;
		this.endDate = endDate;
		this.rewardPoint = rewardPoint;
		this.creatorUserNo = creatorUserNo; 
	}
	 
	public Challenge(int challengeNo, String challengeId, String title, String desc, String startDate, String endDate,
			int rewardPoint, int creatorUserNo, Date enrollDate) {
		super();
		this.challengeNo = challengeNo;
		this.challengeId = challengeId;
		this.title = title;
		this.desc = desc;
		this.startDate = startDate;
		this.endDate = endDate;
		this.rewardPoint = rewardPoint;
		this.creatorUserNo = creatorUserNo;
		this.enrollDate = enrollDate;
	}
	public int getChallengeNo() {
		return challengeNo;
	}
	public void setChallengeNo(int challengeNo) {
		this.challengeNo = challengeNo;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getRewardPoint() {
		return rewardPoint;
	}
	public void setRewardPoint(int rewardPoint) {
		this.rewardPoint = rewardPoint;
	}
	public int getCreatorUserNo() {
		return creatorUserNo;
	}
	public void setCreatorUserNo(int creatorUserNo) {
		this.creatorUserNo = creatorUserNo;
	}
	public Date getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}
	@Override
	public int hashCode() {
		return Objects.hash(challengeId, challengeNo, creatorUserNo, desc, endDate, enrollDate, rewardPoint, startDate,
				title);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Challenge other = (Challenge) obj;
		return Objects.equals(challengeId, other.challengeId) && challengeNo == other.challengeNo
				&& creatorUserNo == other.creatorUserNo && Objects.equals(desc, other.desc)
				&& Objects.equals(endDate, other.endDate) && Objects.equals(enrollDate, other.enrollDate)
				&& rewardPoint == other.rewardPoint && Objects.equals(startDate, other.startDate)
				&& Objects.equals(title, other.title);
	}
	@Override
	public String toString() {
		return "Challenge [challengeNo=" + challengeNo + ", challengeId=" + challengeId + ", title=" + title + ", desc="
				+ desc + ", startDate=" + startDate + ", endDate=" + endDate + ", rewardPoint=" + rewardPoint
				+ ", creatorUserNo=" + creatorUserNo + ", enrollDate=" + enrollDate + "]";
	}
	
}
