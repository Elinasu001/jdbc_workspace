package com.kh.statement.model.dto;

public class EmployeeDTO {
	private String empId;
	private int newSalary;
	private String newJobName;
	private String newDeptTitle;
	public EmployeeDTO() {
		super();
	}
	public EmployeeDTO(String empId, int newSalary, String newJobName, String newDeptTitle) {
		super();
		this.empId = empId;
		this.newSalary = newSalary;
		this.newJobName = newJobName;
		this.newDeptTitle = newDeptTitle;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public int getNewSalary() {
		return newSalary;
	}
	public void setNewSalary(int newSalary) {
		this.newSalary = newSalary;
	}
	public String getNewJobName() {
		return newJobName;
	}
	public void setNewJobName(String newJobName) {
		this.newJobName = newJobName;
	}
	public String getNewDeptTitle() {
		return newDeptTitle;
	}
	public void setnewDeptTitle(String newDeptTitle) {
		this.newDeptTitle = newDeptTitle;
	}
	
}
