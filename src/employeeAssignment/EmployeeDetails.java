package employeeAssignment;

import java.sql.Date;

public class EmployeeDetails {
	
	private String employee_Id;
	private String  employee_Name;
	private String email;
	private String phone_number;
	private String hire_Date;
	private String job_Id;
	private String salary;
	private String commission_Pct;
	private String manager_Id;
	private String department_Id;
	private Date record_insDate;
	
	public String getEmployee_Id() {
		return employee_Id;
	}
	public void setEmployee_Id(String employee_Id) {
		this.employee_Id = employee_Id;
	}
	public String getEmployee_Name() {
		return employee_Name;
	}
	public void setEmployee_Name(String employee_Name) {
		this.employee_Name = employee_Name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getHire_Date() {
		return hire_Date;
	}
	public void setHire_Date(String hire_Date) {
		this.hire_Date = hire_Date;
	}
	public String getJob_Id() {
		return job_Id;
	}
	public void setJob_Id(String job_Id) {
		this.job_Id = job_Id;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getCommission_Pct() {
		return commission_Pct;
	}
	public void setCommission_Pct(String commission_Pct) {
		this.commission_Pct = commission_Pct;
	}
	public String getManager_Id() {
		return manager_Id;
	}
	public void setManager_Id(String manager_Id) {
		this.manager_Id = manager_Id;
	}
	public String getDepartment_Id() {
		return department_Id;
	}
	public void setDepartment_Id(String department_Id) {
		this.department_Id = department_Id;
	}
	public Date getRecord_insDate() {
		return record_insDate;
	}
	public void setRecord_insDate(Date record_insDate) {
		this.record_insDate = record_insDate;
	}
	
		
	
}
