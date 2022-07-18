import java.util.*;
public class HMS {

	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		ConnectionEstablish c = new ConnectionEstablish();
		Admin a = new Admin();
		Student s = new Student();
		Secretary sy = new Secretary();
		
		c.getConnection();
	
		System.out.println("----------------------------------------------------");
		System.out.println("1. Administrator ----- 2. Secretary ----- 3. Student");
		System.out.println("-------------- Choose the Login type: --------------");
		
		int op = sc.nextInt();
		switch(op)
		{
			case 1: int number = a.validateUser();
					if(number == 0)
						System.exit(0);
					break;
					
			case 2: int number2 = sy.validateUser();
					if(number2== 0)
						System.exit(0);
					break;
					
			case 3: int number1 = s.validateUser();
					if(number1== 0)
						System.exit(0);
					break;
		}
		sc.close();
	}

}
