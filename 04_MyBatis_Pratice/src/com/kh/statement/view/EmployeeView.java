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
			case 3: findByJobEmployee(); break;
			case 4: findAllDetail(); break;
			case 5: getHighSalaryEmployees(); break;
			case 6: getLowSalaryEmployees(); break;
			case 7: save(); break;
			case 8: update(); break;
			case 9: delete(); break;
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
				System.out.print("사번 : " + employee.getEmpId()+ ", ");
				System.out.print("\t\t사원명 : " + employee.getEmpName() + ", ");
				System.out.print("\t\t급여 : " + employee.getSalary() + ", ");
				System.out.print("\t\t부서명 : " + employee.getDeptTitle() + ", ");
				System.out.print("\t\t직급명 : " + employee.getJobName());
				System.out.println();
			});
		}
	}
	
	private void findByDmtEmployee() {
		System.out.println("\n부서가 동일한 사원을 조회하는 서비스 입니다.");
		System.out.println("부서명을 입력해주세요 > ");
		String deptTitle = sc.nextLine();
		
		List<Employee> employees = ec.findByDmtEmployee(deptTitle);
		
		
		if(employees.isEmpty()) {
			System.out.println("조회에 실패하셨습니다.");
		} else {
			employees.stream().forEach(employee -> {
				System.out.print("사번 : " + employee.getEmpId()+ ", ");
				System.out.print("\t사원명 : " + employee.getEmpName() + ", ");
				System.out.print("\t부서명 : " + employee.getDeptTitle());
				System.out.println();
			});
		}
		
	}
	
	private void findByJobEmployee() {
		
		System.out.println("직급이 동일한 사원을 조회하는 서비스 입니다");
		System.out.println("직급명을 입력해주세요 > ");
		String jobName = sc.nextLine();
		
		List<Employee> employees = ec.findByJobEmployee(jobName);
		
		if(employees.isEmpty()) {
			System.out.println("조회결과가 존재하지 않습니다.");
		}else {
			employees.stream().forEach(employee -> {
				System.out.print("직급코드 : " + employee.getJobCode()+ ", ");
				System.out.print("\t사원명 : " + employee.getEmpName() + ", ");
				System.out.println("\t직급명 : " + employee.getJobName());
			});
		}
	}
	
	private void findAllDetail() {
		System.out.println("\n상세 조회 서비스입니다.");
		System.out.println("사번을 입력해주세요 > ");
		int empId = sc.nextInt();
		sc.nextLine();
		
		List<Employee> employees = ec.findAllDetail(empId);
		
		if(employees.isEmpty()) {
			System.out.println("조회결과가 존재하지 않습니다.");
		} else {
			employees.stream().forEach(e -> {
				System.out.println("사원번호 : " + e.getEmpId());
				System.out.println("직원명 : " + e.getEmpName());
				System.out.println("주민등록번호 : " + e.getEmpNo());
				System.out.println("이메일 : " + e.getEmail());
				System.out.println("전화번호 : " + e.getPhone());
				System.out.println("부서코드 : " + e.getDeptCode());
				System.out.println("직급코드 : " + e.getJobCode());
				System.out.println("급여등급 : " + e.getSalLevel());
				System.out.println("급여 : " + e.getSalary());
				System.out.println("보너스율 : " + e.getBonus());
				System.out.println("관리자사번 : " + e.getManagerId());
				System.out.println("입사일 : " + e.getHireDate());
				System.out.println("퇴사일 : " + e.getEntDate());
				System.out.println("재직여부 : " + e.getEntYn());
			});

		}
	}
	
	private void getHighSalaryEmployees() {
		
		System.out.println("\n급여가 높은 상위 다섯명을 조회하는 프로그램입니다.");
		
		List<Employee> employees = ec.getHighSalaryEmployees();
		
		if(employees.isEmpty()) {
			System.out.println("조회 결과에 실패하셨습니다.");
		}else {
			employees.stream().forEach(e-> {
				System.out.println(e.getEmpName() + " : " + e.getSalary());
			});
		
		}
		
	}
	
	private void getLowSalaryEmployees() {
		System.out.println("\n급여가 낮은 다섯명을 조회하는 프로그램입니다.");
		
		List<Employee> employees = ec.getLowSalaryEmployees();
		
		if(employees.isEmpty()) {
			System.out.println("조회 결과에 실패하셨습니다.");
		} else {
			employees.stream().forEach(e -> {
				System.out.println(e.getEmpName() +  " : " + e.getSalary());
			});
		}
	}
	
	private void save() {
		System.out.println();
	    System.out.println("=== 사원 추가 ===");
	    
	    System.out.print("사원명 > ");
	    String empName = sc.nextLine();

	    System.out.print("주민등록번호 > ");
	    String empNo = sc.nextLine();
	    
	    System.out.print("이메일 > ");
	    String email = sc.nextLine();

	    System.out.print("전화번호 > ");
	    String phone = sc.nextLine();

	    System.out.print("직급코드 예: J1) > ");
	    String jobCode = sc.nextLine();

	    System.out.print("급여등급 예: S1) > ");
	    String salLevel = sc.nextLine();

	    System.out.print("급여 숫자) > ");
	    int salary = sc.nextInt();
	    sc.nextLine();
	    
	    int result = ec.save(empName, empNo, email, phone, jobCode, salLevel, salary);

	    // 결과 출력
	    if(result > 0) {
	        System.out.println("사원 추가가 완료되었습니다.");
	    } else {
	        System.out.println("사원 추가에 실패했습니다.");
	    }
	    System.out.println();
	}
	
	private void update() {
		System.out.println("\n정보 수정 서비스 입니다.");
		
		System.out.println("사번을 입력해주세요 > ");
		String empId = sc.nextLine();
		
		System.out.println("변경 급여를 입력해주세요 > ");
		int newSalary = sc.nextInt();
		sc.nextLine();
		
		System.out.println("변경 직급을 입력해주세요 > ");
		String newJobName = sc.nextLine();
		
		System.out.println("변경 부서를 입력해주세요 > ");
		String newDeptTitle = sc.nextLine();
		
		int result = ec.update(empId, newSalary, newJobName, newDeptTitle);
		
		if(result > 0) { 
			System.out.println("성공했습니다");
			System.out.println("사원번호 : " + empId + ", 급여 : " + newSalary + ", 직급 : " + newJobName + ", 부서 :" + newDeptTitle);
		} else {
			System.out.println("실패했습니다");
		}
		
	}
	
	private void delete() {
		
		System.out.println("\n사원 삭제하는 서비스 입니다");
		
		System.out.println("삭제할 사원 번호를 입력해주요 > ");
		String empId = sc.nextLine();
		
		int result = ec.delete(empId);
		
		if(result > 0) {
			System.out.println("삭제에 성공했습니다.");
		} else {
			System.out.println("삭제에 실패했습니다.");
		}
		
	}
	
	
}
