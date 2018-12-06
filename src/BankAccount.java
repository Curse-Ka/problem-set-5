/**
 * Just like last time, the BankAccount class is primarily responsible
 * for depositing and withdrawing money. In the enhanced version, there
 * will be the added requirement of transfering funds between accounts.
 * 
 * Most of the functionality for this class should have already been
 * implemented last time. You can always reference my Github repository
 * for inspiration (https://github.com/rwilson-ucvts/java-sample-atm).
 */

import java.util.Scanner;

public class BankAccount {
	Scanner input = new Scanner(System.in);
	
	Database db = new Database();
	
	ATM atm = new ATM();
	
	private long acntNum;
	private User user;
	private double balance;
	
	public void acntNumSet() {
		
	}
	
	public void withdraw(long acntNum) {
		double value;
		double balance = db.retrieveNum("balance", acntNum);
		boolean gettingWithdraw = true;
		do {
			System.out.print("Withdraw Amount: ");
			value = (double) Double.parseDouble(atm.makeNumber(input.nextLine(), "0"));
			if (value > balance) {
				System.out.println("Insufficient Funds.\nWithdrawl: " + value + "\nBalance: " + balance + "\n");
			} else if (value < 0) {
				System.out.println("Invalid value entered: " + value + "\n");
			} else {
				gettingWithdraw = false;
			}
		} while (gettingWithdraw);
		
		balance -= value;
		db.update(acntNum, "balance", Double.toString(balance));
		
		System.out.printf("Amount withdrawn: $%.2f\nAccount Balance: $%.2f\n\n", value, db.retrieveNum("balance", acntNum));
		return;
	}
	
	public void deposit(long acntNum) {
		double value;
		double balance = db.retrieveNum("balance", acntNum);
		boolean gettingDeposit = true;
		do {
			System.out.print("Deposit Amount: ");
			value = (double) Double.parseDouble(atm.makeNumber(input.nextLine(), "0"));
			if (value < 0) {
				System.out.println("Invalid value entered: " + value + "\n");
			} else if (Double.toString(balance + value).length() > 15) {
				System.out.println("Account Balance would exceed $999,999,999,999.99");
			} else {
				gettingDeposit = false;
			}
		} while (gettingDeposit);
		
		balance += value;
		
		db.update(acntNum, "balance", Double.toString(balance));
		
		System.out.printf("Amount deposited: $%.2f\nAccount Balance: $%.2f\n\n", value, db.retrieveNum("balance", acntNum));
		return;
	}
	
	public void transfer(long sender, long recipient) {
		double value;
		boolean gettingTransfer = true;
		double balanceSender = db.retrieveNum("balance", sender);
		double balanceRecipient = db.retrieveNum("balance", recipient);
		if (balanceRecipient == -1) {
			System.out.println("Account does not exist");
			return;
		}
		
		do {
			System.out.print("Transfer Amount: ");
			value = (double) Integer.parseInt(atm.makeNumber(input.nextLine(), "0"));
			if (value > balanceSender) {
				System.out.println("Insufficient Funds.\nTransfer: " + value + "\nBalance: " + balanceSender + "\n");
			} else if (value < 0) {
				System.out.println("Invalid value entered: " + value + "\n");
			} else {
				gettingTransfer = false;
			}
		} while (gettingTransfer);
		
		balanceSender -= value;
		balanceRecipient += value;
		
		db.update(sender, "balance", Double.toString(balanceSender));
		db.update(recipient, "balance", Double.toString(balanceRecipient));
		
		System.out.printf("Amount transferred: $%.2f\nAccount Balance: $%.2f\n\n", value, db.retrieveNum("balance", sender));
		return;
	}
}