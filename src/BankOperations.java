import java.text.NumberFormat;
import java.util.*;

public class BankOperations {
	//this is a static class for all banking operations.
	//it handles user input and displays proper output in the console.
	//it validates user output and ensures that the Account class always receives valid data.
	
	private static NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

	
	public static void displayLoginScreen() {
		System.out.println("\n================================================");
		System.out.println("Welcome, please select an option using the letters");
		System.out.println("A. Create An Account");
		System.out.println("B. Login");
		System.out.println("C. Exit");
		System.out.print("Action: ");
	}
	
	public static void displayHomeScreen(Account account) {
		System.out.println("\n================================================");
		System.out.println("Welcome, " + account.getUsername());
		System.out.println("Account Number: " + account.getAccountNumber() + "\n");
		System.out.println("Please select an option using the letters");
		System.out.println("A. View My Balance");
		System.out.println("B. Deposit Money");
		System.out.println("C. Withdraw Money");
		System.out.println("D. Transfer Funds");
		System.out.println("E. Add Account Interest Using Standard Rate");
		System.out.println("F. Logout");
		System.out.print("Action: ");
		
	}
	
	public static void createAccount(Scanner input) {
		System.out.println("\nEnter Username: ");
		String username = input.nextLine();
		if (Account.accountList.containsKey(username)) {
			System.out.println("Username is already taken. Please choose a different username and try again.");
			return;
		} else if (username.contains(",") || username.length() > 20) {
			System.out.println("A username cannot contain ',' and has to be <21 characters. Please choose a different username and try again.");
			return;
		}
		
		System.out.println("Enter Password: ");
		String password = input.nextLine();
		if (password.contains(",") || password.length() > 20) {
			System.out.println("A password cannot contain ',' and has to be <21 characters. Please choose a different password and try again.");
			return;
		}
		new Account(username, password);
		System.out.println("Your account was sucessfully created.");
		return;
	}
	
	public static Account login(Scanner input) {
		System.out.println("\nEnter Username: ");
		String username = input.nextLine();
		System.out.println("Enter Password: ");
		String password = input.nextLine();
		
		boolean doesAccExist = Account.accountList.containsKey(username);
		boolean isCorrectPassword = Account.accountList.get(username).getPassword().equals(password);
		if (doesAccExist && isCorrectPassword) {
			return Account.accountList.get(username);
		}
		System.out.println("The username or password is incorrect. Please try again.");
		return null;
	}
	
	public static void viewBalance(Account account) {
		System.out.println("\nYour Current Balance: " + BankOperations.dollarFormat.format(account.getBalance()));

	}
	
	public static void deposit(Scanner input, Account account) {
		System.out.println("\nDeposit Amount: ");
		try {
			double amount = input.nextDouble();
			input.nextLine(); //used to get rid of the trailing /n in input
			if (account.deposit(amount)) {
				System.out.println("You have successfully deposited: " + BankOperations.dollarFormat.format(amount));
			} else {
				System.out.println("You can only deposit money that is >0 in value.");
			}
		} 
		catch (Exception e) { //if user does not enter a double amount
			System.out.println("Please enter a valid dollar amount.");
		}
	}
	
	public static void withdraw(Scanner input, Account account) {
		System.out.println("\nWithdraw Amount: ");
		try {
			double amount = input.nextDouble();
			input.nextLine(); //used to get rid of the trailing /n in input
			if (account.withdraw(amount)) {
				System.out.println("You have successfully withdrawn: " + BankOperations.dollarFormat.format(amount));
			} else {
				System.out.println("You can't withdraw more money that is avaliable in your balance.");
			}
		} 
		catch (Exception e) { //if user does not enter a double amount
			System.out.println("Please enter a valid dollar amount.");
		}
	}
	
	public static void transferFunds(Scanner input, Account account) {
		System.out.println("\nEnter a username to transfer money to: ");
		String recipient = input.nextLine();
		System.out.println("Enter an amount to transfer: ");
		double amount = 0;
		try {
			amount = input.nextDouble();
			input.nextLine(); //used to get rid of the trailing /n in input
		}
		catch (Exception e) {
			System.out.println("Please enter a valid dollar amount.");
		}
		
		if (account.transferFunds(recipient, amount)) {
			System.out.println("Funds sucessfully transfered.");
		}
		else {
			System.out.println("Transfer Failed. Either the recipient does not exist or you do not have enough funds to transfer.");
		}
	}
	
	public static void applyInterest(Account account) {
		account.applyStandardeInterest();
		System.out.println("\nSucessfully applied interest to account balance with standard rate of " + account.getInterestRate());
	}
	
	//operates all login functionality after an account has logged in
	public static void runOperations(Scanner input, Account account) {
		boolean isLogout = false;
		do {
			BankOperations.displayHomeScreen(account);
			char action = Character.toUpperCase(input.nextLine().charAt(0));
			
			switch (action){
				case 'A': //view balance
					BankOperations.viewBalance(account);
					break;
				case 'B': //deposit
					BankOperations.deposit(input, account);
					break;
				case 'C': //withdraw
					BankOperations.withdraw(input, account);
					break;
				case 'D': //transfer funds
					BankOperations.transferFunds(input, account);
					break;
				case 'E': //add interest
					BankOperations.applyInterest(account);
					break;
				case 'F': //logout
					isLogout = true;
					break;
				default:
					System.out.println("\n================================================");
					System.out.println("Please enter a valid option.");
			}
		} while(!isLogout);
	}
	
}
