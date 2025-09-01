package com.work3.controller;

import com.work3.model.dao.ChallengeDao;
import com.work3.model.vo.Challenge;

public class ChallengeController {
	
	public int save(String challengeId, String title, String desc, String startDate, String endDate, int rewardPoint, int creatorUserNo) {
		
		Challenge challenge = new Challenge(challengeId, title, desc, startDate, endDate, rewardPoint, creatorUserNo);
		
		int result = new ChallengeDao().save(challenge);
		
		return result;
	}
	
}
