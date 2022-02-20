import java.util.Scanner;

import Models.User;

public class Starter {
	
	public static  User start(){
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Insert Name: ");
	    String name = scan.next();
		
		System.out.println("Insert Nickname:");
	    String nick = scan.next();
	    
	    return new User(name, nick);
	}

}
