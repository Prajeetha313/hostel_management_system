import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
public class Student {
	Scanner in=new Scanner(System.in);
	public int validateUser() throws Exception
	{
		System.out.print("Enter your username: ");
		String uname=in.next();
		
		System.out.print("Enter your password: ");
		String pass=in.next();
		
		Statement stmt=(Statement)ConnectionEstablish.con.createStatement();
		
		String sql="select password from student where username='"+uname+"'";
		ResultSet rs=stmt.executeQuery(sql);
		String res="";
		
		while(rs.next())
		{
			res=rs.getString(1);
		}
		
		if(rs!=null)
		{
			if(pass.equals(res))
			{
				System.out.println("Successfully logged in!!!");
				System.out.println();
				
				int flag=0;
				while(flag!=1)
				{
					System.out.println("1. Change password   2. View Noticeboard      3. View Fees   4. Add Complaint");
					System.out.println("5. View Mess Menu    6. Add Vacation Details  7. Exit");
					System.out.print("Enter any operation to perform: ");
					System.out.println();
					int ope=in.nextInt();
					
					switch(ope)
					{
						case 1: if(!changePassword(uname))
									System.out.println("Can't able to change the password");
								break;
								
						case 2: viewNoticeBoard();
								break;
								
						case 3: viewFees(uname);
								break;
								
						case 4: addComplaint();
								break;
								
						case 5: viewMessMenu();
								break;
								
						case 6: int a = addVacationDetails();
								if(a != 0)
									System.out.println("Details added successfully");
								System.out.println();
								break;
								
						default: flag=1;
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
	
	private boolean changePassword(String uName) throws Exception
	{
		Statement stmt = (Statement)ConnectionEstablish.con.createStatement();
		System.out.print("Enter Current Password: ");
		String old_psw = in.next();
		String sql = "select password from student where username = '"+uName+"'";  //admin123
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
				String newpsw = in.next();
				
				System.out.print("Confirm Password: ");
				String cpsw = in.next();
				
				if(newpsw.equals(cpsw))
				{
					String updatePsw = "update student set password = '"+newpsw+"' where username = '"+uName+"'";
					int row = stmt.executeUpdate(updatePsw);
					if(row != 0)
					{
						System.out.println("Password Updated Successfully");
						System.out.println();
						flag = 1;
						return true;
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
			changePassword(uName);
		}
		return false;
	}
	
	void viewNoticeBoard() throws Exception
	{
		Statement stmt=(Statement)ConnectionEstablish.con.createStatement();
		String notice="select * from notice_board";
		ResultSet rs=stmt.executeQuery(notice);
		
		while(rs.next())
		{
			System.out.print("Subject: "+rs.getString(1));
			System.out.print("Content: "+rs.getString(2));
			System.out.println();
		}	
		System.out.println();
	}
	
	void viewFees(String uName) throws Exception
	{
		Statement stmt=(Statement)ConnectionEstablish.con.createStatement();
		String sql="select rollNo from student where username = '"+uName+"'";
		ResultSet rss=stmt.executeQuery(sql);
		
		String rollNo = "";
		while(rss.next())
		{
			rollNo = rss.getString(1);
		}
		
		String fees="select * from fee_details where rollNo = '"+rollNo+"'";
		
		ResultSet rs=stmt.executeQuery(fees);
		
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%10s %20s %15s %15s %15s %20s %20s", "RollNo", "Student Name", "Dept", "Mess Fees", "Hostel Fees", "Amount Paid", "Balance");
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
		
		while(rs.next())
		{
			System.out.format("%10s %20s %15s %15s %15s %20s %20s", rs.getString(5), rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getFloat(4), rs.getFloat(6), rs.getFloat(7));
			System.out.println();
		}
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
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
	
	void addComplaint() throws Exception
	{
		Statement stmt=(Statement)ConnectionEstablish.con.createStatement();
		
		String rollNo=in.next();
		
		String comp1=in.next();
		String comp2 = in.nextLine();
		String comp = comp1+comp2;
		
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		String complaint="insert into complaint_box values('"+rollNo+"','"+comp+"','"+dtf.format(date)+"')";
		
		int count=stmt.executeUpdate(complaint);
		System.out.println(count);
	}
	
	
	int addVacationDetails() throws Exception
	{
		Statement stmt=(Statement)ConnectionEstablish.con.createStatement();
		System.out.print("Roll No: ");
		String rollNo = in.next();
		
		System.out.print("Name of the Student: " );
		String studName=in.next();
		
		System.out.print("Department: ");
		String dept=in.next();
		
		System.out.print("Year of Study: ");
		String year=in.next();
		
		System.out.print("Reason: " );
		String reason1=in.next();
		String reason2 = in.nextLine();
		String reason = reason1+reason2;
		
		System.out.print("Leaving Date: ");
		String dol=in.next();
		
		System.out.print("Arriving Date: ");
		String doa=in.next();
		
		String vac="insert into vacation_details(student_name, department, year_of_study, rollNo, reason, DOL, DOA) values('"+studName+"','"+dept+"',"+"'"+year+"','"+rollNo+"','"+reason+"','"+dol+"','"+doa+"')";
		int count=stmt.executeUpdate(vac);
		return count;
	}
}
