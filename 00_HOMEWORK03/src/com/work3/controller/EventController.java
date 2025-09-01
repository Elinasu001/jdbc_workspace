package com.work3.controller;

import com.work3.model.dao.EventDao;
import com.work3.model.vo.Event;

public class EventController {
	
	public int save(String eventId, String title, String desc, String startDate, String endDate, int rewardPoint ) {
		Event event = new Event(eventId, title, desc, startDate, endDate, rewardPoint);
		
		int result = new EventDao().save(event);
		
		return result;
	}
	
}
