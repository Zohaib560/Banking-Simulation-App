import java.util.*;

public class BankMain {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Account.createDefaultAccounts(); //loads in default accounts
		boolean isExit = false;
		do {
			BankOperations.displayLoginScreen();
			char action = Character.toUpperCase(input.nextLine().charAt(0));
			//if user just presses enter and tries not to pick an action then action would be /n
			
			switch(action) {
				case 'A': //create account
					BankOperations.createAccount(input);
					break;
				case 'B': //login
					Account account = BankOperations.login(input);
					if (account != null) BankOperations.runOperations(input, account);
					//runOperations is very similar to what is her in main but just located
					//in a different method for ease of readability.
					break;
				case 'C': //exit
					isExit = true;
					break;
				default:
					System.out.println("\n================================================");
					System.out.println("Please enter a valid option.");
			}
		} while (!isExit);
		input.close();
		System.out.println("\n================================================");
		System.out.println("Thank you for using BankApp, you have sucessfully exited the app.");
	}

}
