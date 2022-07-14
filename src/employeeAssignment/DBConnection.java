package employeeAssignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class DBConnection {
	//sql server
	//static String url = "jdbc:sqlserver://MAVCHN0522399\\SQLEXPRESS;databaseName=mavdb;integratedSecurity=true;encrypt=false;";
	
	
	static String url = "jdbc:mysql://localhost:3306/userrecords";

	
	
	
	
	public static void insertIntoDB(EmployeeDetails ed,boolean flag)
	{
		try
		{
			String query="";
			if(flag==true)
			{
				query = "Insert into EmployeeDetails values('"+ed.getEmployee_Id()+"','"+ed.getEmployee_Name()+"','"+ed.getEmail()+"','"+ed.getPhone_number()+"','"+ed.getHire_Date()+"','"+ed.getJob_Id()+"','"+ed.getSalary()+"','"+ed.getCommission_Pct()+"','"+ed.getManager_Id()+"','"+ed.getDepartment_Id()+"',"+"current_date()"+")";
			}
			else
			{
				query = "Insert into EmployeeDetails_Failed values('"+ed.getEmployee_Id()+"','"+ed.getEmployee_Name()+"','"+ed.getEmail()+"','"+ed.getPhone_number()+"','"+ed.getHire_Date()+"','"+ed.getJob_Id()+"','"+ed.getSalary()+"','"+ed.getCommission_Pct()+"','"+ed.getManager_Id()+"','"+ed.getDepartment_Id()+"',"+"current_date()"+")";
			}
			System.out.println(query);
			try(Connection conn = DriverManager.getConnection(url, "root","maveric1@"))
				{
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.executeUpdate();
				conn.close();
				}
			catch(SQLException e)
			{
			System.out.println("Connection failed!");
			e.printStackTrace();
			}
			
		}
		catch(Exception e)
		{
		System.out.println("Connection failed!");
		e.printStackTrace();
		}
	}
	
	public static List<EmployeeReport> getReport(boolean flag) {
		List<EmployeeReport> erList = new ArrayList<EmployeeReport>();
		try
		{
			String query="";
			if(flag==true)
			{
				query = "SELECT  emp.employee_Id,emp.employee_Name,emp.email,empj.job_description,get_name(emp.manager_Id) as ManagerName,"
					+ "dept.department_desc\r\n"
					+ "   FROM EmployeeDetails emp"
					+ "   LEFT JOIN Employee_Job empj ON emp.job_Id = empj.job_Id"
					+ "   Left Join Employee_Department dept on emp.department_Id=dept.department_Id";
			}
			else
			{
				query = "SELECT  emp.employee_Id,get_name(emp.employee_Id) as EmployeeName,emp.email,empj.job_description,get_name(emp.manager_Id) as ManagerName,"
						+ "dept.department_desc\r\n"
						+ "   FROM EmployeeDetails_Failed emp\r\n"
						+ "   left join EmployeeDetails empf on emp.employee_Id=empf.employee_Id"
						+ "   LEFT JOIN Employee_Job empj ON emp.job_Id = empj.job_Id"
						+ "   Left Join Employee_Department dept on emp.department_Id=dept.department_Id";
			}

			try(Connection conn = DriverManager.getConnection(url, "root","maveric1@"))
				{
				
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(query);
				while(result.next())
				{
					EmployeeReport er = new EmployeeReport();
					er.setEmployee_Id(result.getString(1));
					er.setEmployee_Name(result.getString(2));
					er.setEmail(result.getString(3));
					er.setJob_description(result.getString(4));
					er.setManager_Name(result.getString(5));
					er.setDepartment_Name(result.getString(6));
					erList.add(er);
				}
				
				conn.close();
				}
		
		}
		catch(SQLException e)
		{
		System.out.println("Connection failed!");
		e.printStackTrace();
		}
		return erList;
	} 
	
	public static void emptyDB()
	{
		String query1 = "Delete from EmployeeDetails";
		String query2 = "Delete from EmployeeDetails_failed";
		try(Connection conn = DriverManager.getConnection(url, "root","maveric1@"))
		{
			PreparedStatement stmt = conn.prepareStatement(query1);
			stmt.executeUpdate();
			System.out.println("EmployeeDetails old records deleted!");
			stmt = conn.prepareStatement(query2);
			stmt.executeUpdate();
			System.out.println("EmployeeDetails_failed old records deleted!");
			conn.close();
		}
		catch(SQLException e)
		{
			System.out.println("Connection failed!");
			e.printStackTrace();
		}
	}
}
