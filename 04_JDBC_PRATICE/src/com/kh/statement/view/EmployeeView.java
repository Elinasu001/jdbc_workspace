package com.kh.statement.view;

import java.util.List;
import java.util.Scanner;

import com.kh.statement.controller.EmployeeController;
import com.kh.statement.model.vo.Employee;

public class EmployeeView {
	private Scanner sc = new Scanner(System.in);
	private EmployeeController ec = new EmployeeController();
	
	public void mainMenu() {
		while(true) {
			System.out.println("-------------- 직원 관리 프로그램 --------------");
			System.out.println("1. 회원 전체 조회");
			System.out.println("2. 부서명을 입력받아 부서가 동일한 사원 조회");
			System.out.println("3. 직급명을 입력받아 직급이 동일한 사원 조회");
			System.out.println("4. 사원 상세 조회");
			System.out.println("5. 급여가 높은 상위 다섯명 조회");
			System.out.println("6. 급여가 낮은 하위 다섯명 조회");
			System.out.println("7. 사원 추가 기능");
			System.out.println("8. 사원 정보 수정");
			System.out.println("9. 사원 퇴사 기능");
			System.out.println("10. 종료하기");
			System.out.println("번호를 입력해주세요  > ");
			int menuNo = sc.nextInt();
			sc.nextLine();
			
			switch(menuNo) {
			case 1: findAll(); break;
			case 2: findByDmtEmployee(); break;
			case 3: break;
			case 4: break;
			case 5: break;
			case 6: break;
			case 7: break;
			case 8: break;
			case 9: break;
			case 10: System.out.println("프로그램을 종료합니다."); return;
			default: System.out.println("잘못된 메뉴 선택입니다.");
			}
					
			
		}
	}
	
	private void findAll() {
		
		List<Employee> employees = ec.findAll();
		
		if(employees.isEmpty()) {
			System.out.println("조회된 결과가 존재하지 않습니다.");
		}else {
			employees.stream().forEach(employee ->{
				System.out.print("사번 : " + employee.getEmpId()+ ",");
				System.out.print("사원명 : " + employee.getEmpName() + ",");
				System.out.print("급여 : " + employee.getSalary() + ",");
				System.out.print("직급명 : " + employee.getJobName());
				System.out.println();
			});
		}
	}
	
	private void findByDmtEmployee() {
		System.out.println("\n부서가 동일한 사원을 조회하는 서비스 입니다.");
		System.out.println("부서명을 입력해주세요 > ");
		String deptTitle = sc.nextLine();
		
		Employee employee = ec.findByDmtEmployee(deptTitle);
		
		
		if(employee != null) {
			System.out.println(deptTitle + "인 사람은" + employee.getEmpName() + "입니다.");
		} else {
			System.out.println("조회에 실패하셨습니다.");
		}
		
	}
	
}
