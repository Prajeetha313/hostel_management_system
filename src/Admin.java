import java.util.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.math.*;


public class Admin {
	
	double mess_fee = 10000.0;
	double hostel_fee = 60000.0;
	double total_fee = mess_fee + hostel_fee;
	
	Scanner sc = new Scanner(System.in);
	int validateUser() throws Exception
	{	
		System.out.print("Enter UserName: ");
		String uName = sc.next();  //admin
		
		System.out.print("Enter Password: ");
		String psw = sc.next();  //admin123
		
		Statement stmt = (Statement)ConnectionEstablish.con.createStatement();
		
		String sql = "select password from admin where username = '"+uName+"'";  //admin123
		
		ResultSet rs = stmt.executeQuery(sql);
		String res = "";
		while(rs.next())
		{
			res = rs.getString(1);
		}
		if(rs != null)
		{
			if(psw.equals(res))
			{
				System.out.println("Successfully logged in!!!");
				System.out.println();
				
				
				int flag = 0;
				while(flag != 1)
				{
					System.out.println("1. Add Student        2. Remove Student   3. Modify Student Details   4. View Fee Details");
					System.out.println("5. Edit Notice Board  6. View Complaint   7. Delete Complaint (If problem resolved)");
					System.out.println("8. Change Password    9. View Mess Menu   10. Edit Fee Details        11. Exit");
					System.out.print("Enter any operation to perform: ");
					System.out.println();
					
					int oper = sc.nextInt();
					switch(oper)
					{
						case 1: int n = addStudent(); //1
								if(n != 0)
									System.out.println("Student Details Added Successfully");
								break;
								
						case 2: System.out.print("Delete record using 1. rollNo 2. Year of course completion: ");
								int m = sc.nextInt();
								int removeRecord = removeStudent(m);
								if(removeRecord != 0)
									System.out.println(removeRecord+" records deleted Successfully");
								else
									System.out.println("Deletion Failed!!!");
								break;

						case 3: int number1 = modifyStudent();
								if(number1 == 0)
									System.out.println("Cannot able to modify student details");
								else
									System.out.println(number1+ " row affected");
								break;
								
						case 4: viewFeeDetails();
								break;
									
						case 5: //editNoticeBoard();
								break;
								
						case 6: viewComplaint();
								break;
								
						case 7: System.out.println("Enter Rollno of the student: ");
								String r = sc.next();
								editComplaint(r);
								break;
								
						case 8: if(!resetPassword(uName))
									System.out.println("Can't able to change the password");
									break;
								
						case 9: viewMessMenu();
								break;
						
						case 10: editFeeDetails();
								 break;
								 
						case 11: flag = 1;
								 break;
					}
				}
				return 1;
			}
			else
				System.out.println("Invalid Password!!!");
		}
		return 0;
	}
	
	
	private boolean resetPassword(String uName) throws Exception
	{
		Statement stmt = (Statement)ConnectionEstablish.con.createStatement();
		System.out.print("Enter Current Password: ");
		String old_psw = sc.next();
		String sql = "select password from admin where username = '"+uName+"'";  //admin123
		ResultSet rs = null;
		rs = stmt.executeQuery(sql);
		
		String res = "";
		while(rs.next())
		{
			res = rs.getString(1);
		}
		
		if(old_psw.equals(res))
		{
			int flag = 0;
			while(flag != 1)
			{
				System.out.print("New Password : ");
				String newpsw = sc.next();
				
				System.out.print("Confirm Password: ");
				String cpsw = sc.next();
				
				if(newpsw.equals(cpsw))
				{
					String updatePsw = "update admin set password = '"+newpsw+"' where username = '"+uName+"'";
					int row = stmt.executeUpdate(updatePsw);
					if(row != 0)
					{
						System.out.println("Password Updated Successfully");
						flag = 1;
					}	
				}
				else 
				{
					System.out.println("Password Mismatch!!! Try Again");
					System.out.println();
				}
			}
			return 	true;
		}
		else
		{
			System.out.println("Incorrect Password!!! Re-enter Old Password :");
			System.out.println();
			resetPassword(uName);
		}
		return false;
	}
	
	public void viewComplaint() throws Exception
	{
		Statement stmt = (Statement)ConnectionEstablish.con.createStatement();
		
		String sql = "select * from complaint_box order by date_of_complaint"; 
		ResultSet rs = null;
		rs = stmt.executeQuery(sql);
		System.out.println();
		System.out.println("COMPLAINT BOX");
		System.out.println("------------------------------------------------------------------------------------------------------");
		System.out.format("%10s %20s %50s", "Roll No", "Complaint", "Date");
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------------------");
		
		while(rs.next()) {
			System.out.format("%20s %20s %50s", rs.getString(1), rs.getString(2), rs.getString(3));
			System.out.println();
		}
		System.out.println("------------------------------------------------------------------------------------------------------");
		System.out.println();
	}
	
	public void editComplaint(String r) throws Exception
	{
		Statement stmt = (Statement)ConnectionEstablish.con.createStatement();
		
		String sql = "delete from complaint_box where stud_rollNo = '"+r+"'";
		int row = stmt.executeUpdate(sql);
		if(row != 0)
			System.out.println("Complaint Deleted Successfully");
	}
	
//	public void editNoticeBoard() throws Exception
//	{
//		Statement stmt = (Statement) ConnectionEstablish.con.createStatement();
//		
//		
//	}
	
	public int addStudent() throws Exception
	{
		Statement stmt = (Statement)ConnectionEstablish.con.createStatement();
		
		System.out.println("Enter Student Details: ");
		System.out.print("RollNo: ");
		String rollNo = sc.next();
		
		System.out.print("Name: ");
		String na = sc.next();
		String ss = sc.nextLine();
		String name = na+ss;
		
		System.out.print("Gender: ");
		String gender = sc.next();
		
		System.out.print("DOB (yyyy-mm-dd): ");
		String dob = sc.next();
		
		System.out.print("Address: ");
		String ad = sc.next();
		String ress = sc.nextLine();
		String address = ad+ress;
		
		System.out.print("District: ");
		String district = sc.next();
		
		System.out.print("Pincode: ");
		int pincode = sc.nextInt();
		
		System.out.print("Student Mobile number: ");
		String m = sc.next();
		BigInteger mobile = new BigInteger(m);
		
		System.out.print("Parent/Guardian Name: ");
		String nop = sc.next();
		String nop1 = sc.nextLine();
		String nameOfParent = nop+nop1;
		
		System.out.print("Parent/Guardian Contact number: ");
		String mm = sc.next();
		BigInteger mobileOfParent = new BigInteger(mm);
		
		System.out.print("Email Id: ");
		String email = sc.next();
		
		System.out.print("Religion: ");
		String religion = sc.next();
		
		System.out.print("Caste: ");
		String caste = sc.next();
		
		System.out.print("Distance in km: ");
		int distance = sc.nextInt();
		
		System.out.print("Course of Study: ");
		String course = sc.next();
		
		System.out.print("Department: ");
		String dept = sc.next();
		
		System.out.print("Date of Admission: ");
		String DOA = sc.next();
		
		System.out.print("Expected year of completion: ");
		String year = sc.next();
		
		System.out.print("Create UserName: ");
		String username = sc.next();
		
		System.out.print("Create Password: ");
		String password = sc.next();
		
		System.out.print("Alloted Room No: ");
		int room = sc.nextInt();
		
		String sql = "insert into student values ('"+rollNo+"','"+name+"','"+gender+"','"+dob+"','"+address+"','"+pincode+"','"+district+"','"+mobile+"','"+nameOfParent+"','"+mobileOfParent+"','"+email+"','"+religion+"','"+caste+"','"+distance+"','"+course+"','"+dept+"','"+DOA+"','"+year+"','"+username+"','"+password+"','"+room+"')";
		int row = stmt.executeUpdate(sql);
		
		int rr = stmt.executeUpdate("insert into fee_details values ('"+name+"','"+dept+"','"+mess_fee+"','"+hostel_fee+"','"+rollNo+"', 0.0, '"+total_fee+"')");
		
		System.out.println();
		if(row != 0 && rr != 0)
			return row;
		return 0;
	}
	
	public int removeStudent(int m) throws Exception
	{
		Statement stmt = (Statement)ConnectionEstablish.con.createStatement();
		
		if(m == 1)
		{
			System.out.print("Enter the roll no of the Student to delete record: ");
			String rollNo = sc.next();
			
			stmt.executeUpdate("delete from complaint_box where stud_rollNo = '"+rollNo+"'");
			stmt.executeUpdate("delete from fee_details where rollNo = '"+rollNo+"'");
			stmt.executeUpdate("delete from vacation_details where rollNo = '"+rollNo+"'");
			
			String sql = "insert into entireStudentDatabase (select * from student where rollNo = '"+rollNo+"')"; 
			int row = stmt.executeUpdate(sql);
			
			if(row != 0)
			{
				String sql1 = "delete from student where rollNo = '"+rollNo+"'";
				int del = stmt.executeUpdate(sql1);
				return del;
			}
		}
		else if(m == 2)
		{
			System.out.print("Enter the year: ");
			String year = sc.next();
			
			ResultSet rr = stmt.executeQuery("select rollNo from student where expected_year_of_completion = '"+year+"'");
			while(rr.next())
			{
				String res = rr.getString(1);
				stmt.executeUpdate("delete from complaint_box where stud_rollNo = '"+res+"'");
				stmt.executeUpdate("delete from fee_details where rollNo = '"+res+"'");
				stmt.executeUpdate("delete from vacation_details where rollNo = '"+res+"'");
			}
			
			String sql = "insert into entireStudentDatabase (select * from student where expected_date_of_completion = '"+year+"')"; 
			int row = stmt.executeUpdate(sql);
			
			if(row != 0)
			{
				String sql1 = "delete from student where expected_date_of_completion = '"+year+"'";
				int del = stmt.executeUpdate(sql1);
				return del;
			}
		}
		return 0;
	}
	
	public int modifyStudent() throws Exception
	{
		Statement stmt = (Statement)ConnectionEstablish.con.createStatement();
		
		System.out.print("Enter the student roll number: ");
		String roll = sc.next();
		
		System.out.print("Enter the column name to modify data: ");
		String str = sc.next();
		
		String sql;
		
		System.out.print("Enter new value to update: ");
		
		if(str.equalsIgnoreCase("pincode") || str.equalsIgnoreCase("distance_in_km") || str.equalsIgnoreCase("alloted_room_no"))
		{
			int s = sc.nextInt();
			sql = "update student set "+str+" = "+s+" where rollNo = '"+roll+"'";
		}
		else if(str.equalsIgnoreCase("mobile_no") || str.equalsIgnoreCase("parent_or_guardian_mobile_no"))
		{
			String val = sc.next();
			BigInteger b = new BigInteger(val);
			sql = "update student set "+str+" = '"+b+"' where rollNo = '"+roll+"'";
		}
		else
		{
			String ss1 = sc.next();
			String ss2 = sc.nextLine();
			String ss = ss1+ss2;
			sql = "update student set "+str+" = '"+ss+"' where rollNo = '"+roll+"'";
		}
		
		int row = stmt.executeUpdate(sql);
		if(row != 0)
			return row;
		return 0;
	}
	public void viewFeeDetails() throws Exception
	{
		Statement stmt = (Statement)ConnectionEstablish.con.createStatement();
		
		ResultSet rs = stmt.executeQuery("select * from fee_details");
		
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%10s %20s %15s %15s %15s %20s %20s", "RollNo", "Student Name", "Dept", "Mess Fees", "Hostel Fees", "Amount Paid", "Balance");
		System.out.println();
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");
		
		while(rs.next())
		{
			System.out.format("%10s %20s %15s %15s %15s %20s %20s", rs.getString(5), rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getFloat(4), rs.getFloat(6), rs.getFloat(7));
			System.out.println();
		}
		System.out.println("--------------------------------------------------------------------------------------  ----------------------------------------------------");
		System.out.println();	
	}
	
	void viewMessMenu() throws Exception
	{
		Statement stmt=(Statement)ConnectionEstablish.con.createStatement();
		String menu="select * from mess_menu";
		ResultSet rs=stmt.executeQuery(menu);
		System.out.println("--------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%10s %20s %20s %20s %20s %20s", "Day", "Breakfast", "Morning Snacks", "Lunch", "Evening Snack", "Dinner");
		System.out.println();
		System.out.println("--------------------------------------------------------------------------------------------------------------------");
		while(rs.next())
		{
			System.out.format("%10s %20s %20s %20s %20s %20s", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
			System.out.println();
		}
		System.out.println("--------------------------------------------------------------------------------------------------------------------");
		System.out.println();
	}
	void editFeeDetails() throws Exception
	{
		Statement stmt=(Statement)ConnectionEstablish.con.createStatement();
		
		System.out.print("Enter roll no to update: ");
		String rol = sc.next();
		
		ResultSet rr = stmt.executeQuery("select hostel_fee, mess_fee from fee_details where rollNo = '"+rol+"'");
		float hf = 0, mf = 0;
		while(rr.next()) {
			hf = rr.getFloat(1);
			mf = rr.getFloat(2);
		}
		
		System.out.print("Paid Amount: ");
		float fee = sc.nextFloat();
		float bal = (hf + mf) - fee;
		int row = stmt.executeUpdate("update fee_details set amount_paid = '"+fee+"', balance = '"+bal+"' where rollNo = '"+rol+"'");
		if(row != 0)
			System.out.println("Fee Updated Successfully");
		else
			System.out.println("Updation Failed");
		System.out.println();
	}
}
