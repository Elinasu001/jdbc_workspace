package com.work3.controller;

import java.util.List;

import com.work3.model.dao.ChallengeDao;
import com.work3.model.dto.TitleDTO;
import com.work3.model.vo.Challenge;

public class ChallengeController {
	
	/**
	 * VIEW로 부터 전달받은 문자열값 다섯개를 DAO로 전달하는 메소드
	 * 
	 * @param challengeId
	 * @param title
	 * @param desc
	 * @param startDate
	 * @param endDate
	 * @param rewardPoint
	 * @param creatorUserNo
	 * @return
	 */
	public int save(String challengeId, String title, String desc, String startDate, String endDate, int rewardPoint, int creatorUserNo) {
		
		Challenge challenge = new Challenge(challengeId, title, desc, startDate, endDate, rewardPoint, creatorUserNo);
		
		int result = new ChallengeDao().save(challenge);
		
		return result;
	}
	
	public List<Challenge> findAll(){
		
		List<Challenge> challenges = new ChallengeDao().findAll();
		
		return challenges;
	}
	
	public List<Challenge> findByKeyword(String keyword){
		List<Challenge> challenges = new ChallengeDao().findByKeyword(keyword);
		return challenges;
	}
	
	public int update(String challengeId, String title, String newTitle) {
		TitleDTO td = new TitleDTO(challengeId, title, newTitle);
		int result = new ChallengeDao().update(td);
		return result;
	}
	
	public int delete(String challengeId, int creatorUserNo) {
		Challenge challenge = new Challenge();
		
		challenge.setChallengeId(challengeId);
		challenge.setCreatorUserNo(creatorUserNo);
		
		int result = new ChallengeDao().delete(challenge);
		
		return result;
	}
	
}
