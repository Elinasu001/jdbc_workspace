package com.work3.statement.run;

import com.work3.common.JDBCTemplate;
import com.work3.statement.view.ChallengeView;

public class ChallengeRun {

	public static void main(String[] args) {
		JDBCTemplate.regisDriver();
		ChallengeView cv = new ChallengeView();
		cv.mainMenu();
	}

}
