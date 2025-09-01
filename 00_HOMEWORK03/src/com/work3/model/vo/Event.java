package com.work3.model.vo;

import java.util.Objects;

public class Event {
	private int eventNo; //EVENT_NO NUMBER PRIMARY KEY,
	private String eventId; //EVENT_ID VARCHAR2(15) UNIQUE NOT NULL,
	private String title; //TITLE VARCHAR2(255) NOT NULL,
	private String desc; //DESCRIPTION CLOB,
	private String startDate; //START_DATE DATE NOT NULL,
	private String endDate; //END_DATE DATE NOT NULL,
	private int rewardPoint; //REWARD_POINT NUMBER NOT NULL
	public Event() {
		super();
	}
	public Event(String eventId, String title, String desc, String startDate, String endDate, int rewardPoint) {
		super();
		this.eventId = eventId;
		this.title = title;
		this.desc = desc;
		this.startDate = startDate;
		this.endDate = endDate;
		this.rewardPoint = rewardPoint;
	}
	public Event(int eventNo, String eventId, String title, String desc, String startDate, String endDate,
			int rewardPoint) {
		super();
		this.eventNo = eventNo;
		this.eventId = eventId;
		this.title = title;
		this.desc = desc;
		this.startDate = startDate;
		this.endDate = endDate;
		this.rewardPoint = rewardPoint;
	}
	@Override
	public int hashCode() {
		return Objects.hash(desc, endDate, eventId, eventNo, rewardPoint, startDate, title);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(desc, other.desc) && Objects.equals(endDate, other.endDate)
				&& Objects.equals(eventId, other.eventId) && eventNo == other.eventNo
				&& rewardPoint == other.rewardPoint && Objects.equals(startDate, other.startDate)
				&& Objects.equals(title, other.title);
	}
	@Override
	public String toString() {
		return "Event [eventNo=" + eventNo + ", eventId=" + eventId + ", title=" + title + ", desc=" + desc
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", rewardPoint=" + rewardPoint + "]";
	}
	
	
	
	
}
