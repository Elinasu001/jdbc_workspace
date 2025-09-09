package com.kh.statement.run;

import com.kh.statement.view.EmployeeView;
import com.kh.common.Template;

public class EmployeeRun {

	public static void main(String[] args) {
		
		//JDBCTemplate.regisDriver();
		//Template.getSqlSession();
		EmployeeView ev = new EmployeeView();
		ev.mainMenu();
		
	}

}
