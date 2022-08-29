import java.util.*;

public class Account {
	
	//using map instead of list to have fast access times when looking up account info
	//the key is a unique user name tied to the account object
	public static HashMap<String, Account> accountList = new HashMap<>(); 
	
	private String username;
	private String password;
	private int accountNumber;
	private double balance;
	private double INTEREST_RATE = 0.015; //percentage in decimal 
	
	//creates a new Account object
	//pre-conditions:
	//username cannot be empty and must be <20 characters and cannot contain ',' character
	//password cannot be empty and must be <20 characters and cannot contain ',' character
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
		this.balance = 0;
		
		//searches for next available account number starting from 1
		int accountNumber = 1;
		for (Account account : Account.accountList.values()) {
			if (account.accountNumber == accountNumber) {
				accountNumber++;
			}
		}
		this.accountNumber = accountNumber;
		
		//add newly created account to the account list
		Account.accountList.put(username, this);
	}
	
	//adds money to account balance, returns true if amount is >0 else false
	public boolean deposit(double amount) {
		if (amount <= 0) {
			return false;
		}
		this.balance += amount;
		return true;
	}
	
	//removes money from account balance. returns true if the account has enough balance to cover withdrawal, else false
	public boolean withdraw (double amount) {
		if (this.balance - amount < 0) {
			return false;
		}
		this.balance -= amount;
		return true;
	}
	
	//transfers balance of amount between accounts, returns true if transfer successful, else false
	public boolean transferFunds(String accountNumber, double amount) {
		//searches to see if there exists an account with matching account username
		Account recipient = Account.accountList.get(accountNumber);
		if ((recipient == null) || this.balance < amount) {
			return false;
		}
		this.withdraw(amount);
		recipient.deposit(amount); //deposits in recipient account balance
		return true;
	}
	
	//applies standard interest rate of INTEREST_RATE to the account balance
	public void applyStandardeInterest() {
		this.deposit(this.balance * this.INTEREST_RATE);
	}
	
	public static void createDefaultAccounts() {
		new Account("admin", "admin");
		new Account("admin2", "admin2");
	}
	
	//maybe make another method that calculates monthly mortgage
	//takes in info such as down payment, house price, APR, interest, etc
	//maybe the down payment could be a portion of the account balance and which user specifies and that is used in the calculation
	//SAVE FOR LATER

	public String getUsername() {
		return username;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public String getPassword() {
		return password;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public double getInterestRate() {
		return this.INTEREST_RATE;
	}
	
}
