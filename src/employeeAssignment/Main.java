package employeeAssignment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
	
	public static void main(String args[])
	  {
		File file = new File("file\\employee.csv");
		File success_file = new File("file\\successReport.csv");
		File failure_file = new File("file\\failureReport.csv");
		try {
			DBConnection.emptyDB();
			Scanner sc = new Scanner(file);
			sc.nextLine();
			while (sc.hasNextLine())
			{
				insertmethod(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Exceptions "+e);
			e.printStackTrace();
		}
		
		try
		{
			List<EmployeeReport> success_report = DBConnection.getReport(true);
			List<EmployeeReport> failed_report = DBConnection.getReport(false);
			BufferedWriter out = new BufferedWriter(new FileWriter(success_file));
			//header line
			out.write("EmployeeID,Name,Email,Job Description,Department Name,Manager Name");
			out.newLine();
			for(EmployeeReport er : success_report)
			{
				out.write(er.getEmployee_Id()+","+er.getEmployee_Name()+","+er.getEmail()+","+er.getJob_description()+","+er.getDepartment_Name()+","+er.getManager_Name());
				out.newLine();
			}
			out.close();
			System.out.println("Success Report generated!");
			
			out = new BufferedWriter(new FileWriter(failure_file));
			out.write("EmployeeID,Name,Email,Job Description,Department Name,Manager Name");
			out.newLine();
			for(EmployeeReport er : failed_report)
			{
				out.write(er.getEmployee_Id()+","+er.getEmployee_Name()+","+er.getEmail()+","+er.getJob_description()+","+er.getDepartment_Name()+","+er.getManager_Name());
				out.newLine();
			}
			out.close();
			System.out.println("Failure Report generated!");
			
		}
		catch(Exception e)
		{
			System.out.println("Exceptions - "+e);
		}
	  }
	public static void insertmethod(String record)
	{
		//To Handle empty data separated only by ','
		record = record.replaceAll(",,",",-,");
		record = record.replaceAll(",,",",-,");
		if(record.charAt(record.length()-1)==',')
		{
			record = record+"-";
		}
		
		String values[] = record.split(",");
		EmployeeDetails success_record = new EmployeeDetails();
		EmployeeDetails failure_record = new EmployeeDetails();
		
		//Employee_ID
		if(isNull(values[0]))
		{
			//inserts complete record into failure
			failure_record.setEmployee_Id(values[0]);
			failure_record.setEmployee_Name(values[1]);
			failure_record.setEmail(values[2]);
			failure_record.setPhone_number(values[3]);
			failure_record.setHire_Date(values[4]);
			failure_record.setJob_Id(values[5]);
			failure_record.setSalary(values[6]);
			failure_record.setCommission_Pct(values[7]);
			failure_record.setManager_Id(values[8]);
			failure_record.setDepartment_Id(values[9]);
			DBConnection.insertIntoDB(failure_record, false);
		}
		else
		{
			success_record.setEmployee_Id(values[0]);
			failure_record.setEmployee_Id(values[0]);
		
			//Name
			if(isNameOnlyAlphabet(values[2]))
			{
				if(isNull(values[1]))
				{
					success_record.setEmployee_Name(values[2]);;
					failure_record.setEmployee_Name("-");
				}
				else
				{
					success_record.setEmployee_Name(values[1]+" "+values[2]);
					failure_record.setEmployee_Name("-");
				}
			}
			else
			{
				failure_record.setEmployee_Name(values[1]+" "+values[2]);
			}
			
			//Email
			String email = isValidEmail(values[3]);
			if(email.equalsIgnoreCase("false"))
			{
				failure_record.setEmail(values[3]);
				success_record.setEmail("-");
			}
			else
			{
				success_record.setEmail(email);
				failure_record.setEmail("-");
			}
			
			//PhoneNumber
			if(isValidPhoneNumber(values[4]))
			{
				success_record.setPhone_number(values[4]);
				failure_record.setPhone_number("-");
			}
			else
			{
				success_record.setPhone_number("-");
				failure_record.setPhone_number(values[4]);
			}
			
			//HireDate
			if(isValidDate(values[5]))
			{
				success_record.setHire_Date(values[5]);
				failure_record.setHire_Date("-");
			}
			else
			{
				success_record.setHire_Date("-");
				failure_record.setHire_Date(values[5]);
			}
			
			//JOBID
			if(isNull(values[6]))
			{
				success_record.setJob_Id("-");
				failure_record.setJob_Id("-");
			}
			else
			{
				success_record.setJob_Id(values[6]);
				failure_record.setJob_Id(values[6]);
			}
			
			//Salary
			if(isValidSalary(values[7]))
			{
				success_record.setSalary(values[7]);
				failure_record.setSalary("-");
			}
			else
			{
				failure_record.setSalary(values[7]);
				success_record.setSalary("-");
			}
			
			//COMMISSION_PCT
			success_record.setCommission_Pct(values[8]);
			failure_record.setCommission_Pct("-");
			
			//MANAGER_ID
			if(isNull(values[9]))
			{
				success_record.setManager_Id(values[0]);
				failure_record.setManager_Id(values[0]);
			}
			else
			{
				success_record.setManager_Id(values[9]);
				failure_record.setManager_Id(values[9]);
			}
			
			//DEPARTMENT_ID
			if(isNull(values[10]))
			{
				success_record.setDepartment_Id("-");
				failure_record.setDepartment_Id("-");
			}
			else
			{
				success_record.setDepartment_Id(values[10]);
				failure_record.setDepartment_Id(values[10]);
			}
			if(!(failure_record.getEmployee_Name().equalsIgnoreCase("-") && failure_record.getEmail().equalsIgnoreCase("-")  && 
					failure_record.getPhone_number().equalsIgnoreCase("-") && failure_record.getHire_Date().equalsIgnoreCase("-")
					 && failure_record.getSalary().equalsIgnoreCase("-")))
			{
				//Insert Failure record
				DBConnection.insertIntoDB(failure_record, false);
			}
			//Insert Success record
			DBConnection.insertIntoDB(success_record, true);
		}
		
		
	}
	
	public static boolean isNull(String value)
	{
		if(value.trim().equalsIgnoreCase("")||value.equals("null")||value.equals(null)||value.trim().equalsIgnoreCase("-"))
		{
			return true;
		}
		return false;
	}
	
	public static boolean isNameOnlyAlphabet(String value) 
	{
	    return ((!value.equals(""))
	            && (value != null)
	            && (value.matches("^[a-zA-Z ]*$")));
	}
	
	public static String isValidEmail(String value)
    {
		if(isNull(value))
		{
			return "false";
		}
		else
		{
			if(value.length()<=50)
			{
				if(isNameOnlyAlphabet(value))
				{
					return value+"@abc.com";
				}
		        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
		                            "[a-zA-Z0-9_+&*-]+)*@" +
		                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
		                            "A-Z]{2,7}$";
		                              
		        Pattern pat = Pattern.compile(emailRegex);
		        if(pat.matcher(value).matches())
		        {
		        	return value;
		        }
		        else
		        {
		        	return "false";
		        }
			}
			else
			{
				return "false";
			}
		}
    }
	
	public static boolean isValidPhoneNumber(String value)
	{
		if(isNull(value))
		{
			return false;
		}
		else
		{
			if(value.length()==12)
			{
				//"\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}" pattern for the complete phoneNumber
				if(value.charAt(3)=='.' && value.charAt(7)=='.')
				{
					String phoneRegex ="^[\\.0-9]*$";
					Pattern pat = Pattern.compile(phoneRegex);
					return pat.matcher(value).matches();
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
	}
	
	public static boolean isValidDate(String value)
    {
        String regex = "([0-2][0-9]||3[0-1])-(0[0-9]||1[0-2])-((19|20)\\d\\d)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((CharSequence)value);
        return matcher.matches();
    }
	
	public static boolean isValidSalary(String value)
    {
		if(isNull(value))
		{
			return false;
		}
		else
		{
		String regex = "^([0-9]*)(.[[0-9]+]?)?$";
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((CharSequence)value);
        return matcher.matches();
		}
    }
}
