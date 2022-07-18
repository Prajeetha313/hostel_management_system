import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Secretary {
	Scanner in=new Scanner(System.in);
	public int validateUser() throws Exception
	{
		System.out.print("Enter your username: ");
		String uname=in.next();
		
		System.out.print("Enter your password: ");
		String pass=in.next();
		
		Statement stmt=(Statement)ConnectionEstablish.con.createStatement();
		
		String sql="select password from secretary where username='"+uname+"'";
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
					System.out.println("1. Change password   2. View Noticeboard   3. View Fees       4. Edit Mess Menu");
					System.out.println("5. View Mess Menu    6. Edit Fee Details   7. Approve Leave   8. Exit");
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
								
						case 3: viewFeeDetails();
								break;
				
						case 4: 
								break;
								
						case 5: viewMessMenu();
								break;
						
						case 6: editFeeDetails();
								break;
								
						case 7: approveLeave();
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
		String sql = "select password from secretary where username = '"+uName+"'";  //admin123
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
					String updatePsw = "update secretary set password = '"+newpsw+"' where username = '"+uName+"'";
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
		String rol = in.next();
		
		ResultSet rr = stmt.executeQuery("select hostel_fee, mess_fee from fee_details where rollNo = '"+rol+"'");
		float hf = 0, mf = 0;
		while(rr.next()) {
			hf = rr.getFloat(1);
			mf = rr.getFloat(2);
		}
		
		System.out.print("Paid Amount: ");
		float fee = in.nextFloat();
		float bal = (hf + mf) - fee;
		int row = stmt.executeUpdate("update fee_details set amount_paid = '"+fee+"', balance = '"+bal+"' where rollNo = '"+rol+"'");
		if(row != 0)
			System.out.println("Fee Updated Successfully");
		else
			System.out.println("Updation Failed");
		System.out.println();
	}
	
	void approveLeave() throws Exception
	{
		Statement stmt=(Statement)ConnectionEstablish.con.createStatement();
		
		viewLeaveDetails();
		System.out.print("1. Approve Leave  2. Delete from vacation details: ");
		int r = in.nextInt();
		if(r == 1)
		{
			System.out.print("Enter Roll no: ");
			String rol = in.next();
			
			System.out.print("Leave Approved or not : (Enter yes/no): ");
			String res = in.next();
			
			int row = stmt.executeUpdate("update vacation_details set approved = '"+res+"' where rollNo = '"+rol+"'");
			if(row != 0)
				System.out.println("Updated Successfully");
		}
		else
		{
			System.out.println("1. RollNo   2. DOA (Enter Choice): ");
			int num = in.nextInt();
			if(num == 1)
			{
				String roll = in.next();
				stmt.executeUpdate("delete from vacation_details where rollNo = '"+roll+"'");
				System.out.println("Record Deleted");
			}
			else
			{
				String DOA = in.next();
				int rrr = stmt.executeUpdate("delete from vacation_details where doa = '"+DOA+"'");
				System.out.println(rrr+" records deleted");
			}
		}
	}
	
	void viewLeaveDetails() throws Exception
	{
		Statement stmt=(Statement)ConnectionEstablish.con.createStatement();
		
		ResultSet rs = stmt.executeQuery("select * from vacation_details order by dol");
		
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%10s %20s %15s %15s %15s %20s %20s %20s", "RollNo", "Student Name", "Department", "Year of Study", "Reason for leave", "DOL", "DOA", "Approved");
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
		
		while(rs.next())
		{
			System.out.format("%10s %20s %15s %15s %15s %20s %20s %20s", rs.getString(4), rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
			System.out.println();
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
	}
	
}
