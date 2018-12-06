/**
 * Just like last time, the ATM class is responsible for managing all
 * of the user interaction. This means login procedures, displaying the
 * menu, and responding to menu selections. In the enhanced version, the
 * ATM class will have the added responsibility of interfacing with the
 * Database class to write and read information to and from the database.
 * 
 * Most of the functionality for this class should have already been
 * implemented last time. You can always reference my Github repository
 * for inspiration (https://github.com/rwilson-ucvts/java-sample-atm).
 */
import java.util.Scanner;

public class ATM {
	Scanner input = new Scanner(System.in);
	
	public void primaryMenu() {
		Database db = new Database();
		
		ATM atm = new ATM();		
		
		System.out.print("Open Account: [1]\nLogin:        [2]\nExit:         [3]\n\nPlease choose an action: ");
		int response = Integer.parseInt(atm.makeNumber(input.nextLine(), "0"));
		System.out.print("\n");
		
		switch (response) {
			case 1: // Create Account
				
				// Input Details
				System.out.print("              First Name: ");
				String firstName = (input.nextLine() + "                ").substring(0, 15);
				System.out.print("\n               Last Name: ");
				String lastName = (input.nextLine() + "                     ").substring(0, 20);
				long dob;
				do {
					System.out.print("\nDate of Birth (YYYYMMDD): ");
					dob = (long) Integer.parseInt(atm.makeNumber(input.nextLine(), "0"));
				} while (Long.toString(dob).length() != 8);
				long phoneNum;
				do {
					System.out.print("\n  Phone Num (##########): ");
					phoneNum = Long.parseLong(atm.makeNumber(input.nextLine(), "0"));
				} while (Long.toString(phoneNum).length() != 10);
				System.out.print("\n          Street Address: ");
				String address = (input.nextLine() + "                              ").substring(0, 30);
				System.out.print("\n                    City: ");
				String city = (input.nextLine() + "                                 ").substring(0, 30);
				System.out.print("\n                   State: ");
				String state = (input.nextLine() + "   ").substring(0, 2);
				String postalCode = "0";
				do {
					System.out.print("\n             Postal Code: ");
					postalCode = atm.makeNumber(input.nextLine(), "0");
				} while (postalCode.length() != 5);
				long acntNum = db.maxAcntNum() + 1;
				long pin;
				do {
					System.out.print("\n              Pin (####): ");
					pin = (long) Integer.parseInt(atm.makeNumber(input.nextLine(), "0"));
				} while (Long.toString(pin).length() != 4);
				
				// Add to database
				db.create(acntNum, pin, (double) 0, lastName, firstName, dob, phoneNum, address, city, state, postalCode);
				
				System.out.printf("\n\nYour Account information (May be trucated):\n\nNumber: %s\nPin: %d\nLast Name: %s\nFirst Name: %s\nDat of Birth: %d\nPhone: %d\nAddress: %s\nCity: %s\nState: %s\nPostal Code: %s\n\n\n\n", acntNum, pin, lastName, firstName, dob, phoneNum, address, city, state, postalCode);
				
				// Wait for them
				System.out.println("\nContinue: [ENTER]");
				input.nextLine();
				System.out.println("\n\n********************\n\n");
				
				// Move on to subMenu
				atm.subMenu(acntNum);
				break;
				
				
			case 2: 
				String tempAcntNum;
				
				// Get acntNum
				do {
					System.out.print("Account Number: ");
					tempAcntNum = input.nextLine();
					
					// check for bad inputs
					tempAcntNum = atm.makeNumber(tempAcntNum, "0000");
					if (db.retrieveNum("balance", (long) Integer.parseInt(String.valueOf(tempAcntNum))) == -1) {
						System.out.println("This Account Number doesn't exist");
						tempAcntNum = "Null";
					}
				} while (tempAcntNum.length() != 9);
				acntNum = (long) Integer.parseInt(String.valueOf(tempAcntNum));
				
				// Get and check pin
				long tempPin = 0;
				do {
					System.out.print("\nAccount Pin: ");
					tempPin = (long) Integer.parseInt(atm.makeNumber(input.nextLine(), "0"));
					if (db.retrieveNum("pin", acntNum) != (double) tempPin) {
						tempPin = 0;
					}
				} while (tempPin == 0);

				if (db.retrieveString("open", acntNum).equals("N")) {
					System.out.println("\nThis account exists, but it is closed. Would you like to reopen it?\n\nYes [1]\nNo  [2]\n");
					int open;
					do {
						System.out.print("Selection: ");
						open = Integer.parseInt(atm.makeNumber(input.nextLine(), "0"));
					} while (open > 2 || open < 1);
					if (open == 1) {
						db.update(acntNum, "open", "Y");
						System.out.println("\nContinue: [ENTER]");
						input.nextLine();
						System.out.println("\n\n********************\n\n");
					} else {
						System.out.println("\nContinue: [ENTER]");
						input.nextLine();
						System.out.println("\n\n********************\n\n");
						break;
					}

				}
				System.out.println("\n\n********************\n\n");
				atm.subMenu(acntNum);
				break;
				
				
			case 3:
				System.out.println("Thank you, have a good day :)");
				return;
				
				
			default:
				System.out.println("Oops! That wasn't an option\n\n");
		}
		primaryMenu();
	}
	
	public void subMenu(long acntNum) {
		Database db = new Database();
		ATM atm = new ATM();
		BankAccount BA = new BankAccount();
		
		int response;
		System.out.print("Welcome to the Inva Sterrs Bank. What would you like to do?\n\nDeposit funds:       [1]\nWithdraw funds:      [2]\nTransfer funds:      [3]\nView balance:        [4]\nView personal info:  [5]\nUpdate personal info:[6]\nClose account:       [7]\nLogout:              [8]");
		do {
			System.out.print("\n\nPlease choose an action: ");
			response = Integer.parseInt(atm.makeNumber(input.nextLine(), "9"));
		} while (response > 8 || response < 1);
		System.out.println("\n\n********************\n\n");

		switch (response) {
			case 1:
				BA.deposit(acntNum);
				break;
			case 2:
				BA.withdraw(acntNum);
				break;
			case 3:
				long recipient;
				do {
					System.out.println("Account Number of Recipient: ");
					recipient = input.nextLong();
				} while (Long.toString(recipient).length() != 9);
				BA.transfer(acntNum, recipient);
				break;
			case 4:
				System.out.print("Your Current Balance: $" + String.valueOf(db.retrieveNum("balance", acntNum)));
				break;
			case 5:
				// Very ugly but it works
				System.out.printf("Your Account information:\n\nLast Name: %s\nFirst Name: %s\nDOB: %d\nPhone: %d\nAddress: %s\nCity: %s\nState: %s\nPostal Code: %s\n\n\n\n", db.retrieveString("lastName", acntNum), db.retrieveString("firstName", acntNum), (long) db.retrieveNum("dob", acntNum), (long) db.retrieveNum("phoneNum", acntNum), db.retrieveString("address", acntNum), db.retrieveString("city", acntNum), db.retrieveString("state", acntNum), db.retrieveString("postalCode", acntNum));
				break;
			case 6:
				int choice;
				System.out.print("\n\nPin:             [1]\nLast Name:       [2]\nFirst Name:      [3]\nDate of Birth:   [4]\nPhone Number:    [5]\nStreet Address:  [6]\nCity:            [7]\nState:           [8]\nPostal Code:     [9]");
				do {
					System.out.print("\n\nPlease choose a field to update: ");
					choice = Integer.parseInt(atm.makeNumber(input.nextLine(), "0"));
				} while (choice > 9 || choice < 1);
				String field = "0";
				String value = "0";
				switch (choice) {
					case 1:
						field = "pin";
						long oldPin;
						
						// Check old Pin
						boolean wrongPin = true; 
						while (wrongPin) {
							do {
								System.out.print("Old Pin: ");
								oldPin = (long) Integer.parseInt(atm.makeNumber(input.nextLine(), "0"));
							} while (Long.toString(oldPin).length() != 4);
							if ((oldPin == db.retrieveNum("pin", acntNum))) {
								wrongPin = false;
							} else {
								System.out.println("Incorrect PIN entered");
							}
						}
						
						// Set new Pin
						do {
							System.out.print("New Pin: ");
							value = atm.makeNumber(input.nextLine(), "0");
						} while (value.length() != 4);
						break;
					case 2:
						field = "lastName";
						System.out.print("Last Name: ");
						value = (input.nextLine() + "                ").substring(0, 15);
						break;
					case 3:
						field = "firstName";
						System.out.print("First Name: ");
						value = (input.nextLine() + "                     ").substring(0, 20);
						break;
					case 4:
						field = "dob";
						do {
							System.out.print("Date of Birth (YYYYMMDD): ");
							value = atm.makeNumber(input.nextLine(), "0");
						} while (value.length() != 8);
						break;
					case 5:
						field = "phoneNum";
						do {
							System.out.print("\nPhone Num (##########): ");
							value = atm.makeNumber(input.nextLine(), "0");
						} while (value.length() != 10);
						break;
					case 6:
						field = "address";
						System.out.print("Date of Birth (YYYYMMDD): ");
						value = (input.nextLine() + "                              ").substring(0, 30);
						break;
					case 7:
						field = "city";
						System.out.print("City: ");
						value = (input.nextLine() + "                                 ").substring(0, 30);
						break;
					case 8:
						field = "state";
						System.out.print("State: ");
						value = (input.nextLine() + "   ").substring(0, 2);
						break;
					case 9:
						field = "postalCode";
						do {
							System.out.print("\nPostal Code: ");
							value = atm.makeNumber(input.nextLine(), "0");
						} while (value.length() != 5);
						break;
					default:
						break;
				}
				db.update(acntNum, field, value);
				System.out.println("Information Updated");
				break;
			case 7:
				System.out.println("Are you sure you want to close your account?\nYes: [1]\nNo:  [2]");
				int close;
				do {
					System.out.print("Selection:");
					close = Integer.parseInt(atm.makeNumber(input.nextLine(), "0"));
				} while (close > 2 || close < 1);
				switch (close) {
					case 1: 
						db.update(acntNum, "open", "N");
						System.out.println("Account Closed");
						break;
					case 2:
						System.out.println("Account Closure Cancelled");
						break;
					default:
						System.out.println("Invalid Input");
						System.out.println("Account Closure Cancelled");
						break;
				}
				break;
			case 8:
				System.out.println("Are you sure you want to log out?\nYes: [1]\nNo:  [2]");
				int logOut; 
				do {
					System.out.print("Selection: ");
					logOut= Integer.parseInt(atm.makeNumber(input.nextLine(), "0"));
				} while (logOut > 2 || logOut < 1);
				switch (logOut) {
					case 1: 
						System.out.println("Logging out...\n\n********************\n\n");
						return;
					case 2:
						break;
					default:
						System.out.println("Invalid Input");
						break;
				}
				break;
			default:
				break;
				
				
		}
		System.out.println("\nContinue: [ENTER]");
		input.nextLine();
		System.out.println("\n\n********************\n\n");
		
		atm.subMenu(acntNum);
	}
	
	// Simply takes in a string that is supposed to be a number and makes sure it doesn't cause an error
	public String makeNumber(String input, String valIfWrong) {
		for (int i = 0; i < input.length(); i++) {
			input = (input.charAt(i) >= '0' && input.charAt(i) <= '9') ? input : valIfWrong;
		}
		if (input.equals("")) {
			input = valIfWrong;
		}
		return input;
	}
}