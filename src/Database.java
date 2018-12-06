/**
 * This class will serve as the intermediary between our ATM program and
 * the database of BankAccounts. It'll be responsible for fetching accounts
 * when users try to login, as well as updating those accounts after any
 * changes are made.
 */

import java.io.*;

public class Database {
	
	File file = new File("/Users/William/Desktop/APCSA/pset5/accounts-db.txt");
	
	public void create(long acntNum, long pin, double balance, String lastName, String firstName, long dob, long phoneNum, String address, String city, String state, String postalCode){ 
		String a = String.format("%-9s", acntNum);
		String b = String.format("%-4d", pin);
		String c = String.format("%-15f", balance);
		String d = String.format("%-20s", lastName);
		String e = String.format("%-15s", firstName);
		String f = String.format("%-8s", dob);
		String g = String.format("%-10d", phoneNum);
		String h = String.format("%-30s", address);
		String i = String.format("%-30s", city);
		String j = String.format("%-2s", state);
		String k = String.format("%-5s", postalCode);
		String l = "Y";
		String record = a + b + c + d + e + f + g + h + i + j + k + l;

		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			String wholeDB = "";
			String line = "";
			while ((line = br.readLine()) != null) {
				wholeDB = wholeDB + line + "\n";
			}
			wholeDB += record;
			
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(wholeDB);
		    fileWriter.close();
		    br.close();
		}
		catch (IOException ex) {
		      ex.printStackTrace(); 
		}
	}
	
	public String retrieveString(String field, long acntNum) {
		String value = "-1";
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); 

			String line;
			while ((line = br.readLine()) != null) {
				if (line.substring(0,9).equals(Long.toString(acntNum))) {
					switch (field) {
						case "firstName":
							value = line.substring(48, 63);
							break;
						case "lastName":
							value = line.substring(28, 48);
							break;
						case "address":
							value = line.substring(81, 111);
							break;
						case "city":
							value = line.substring(111, 141);
							break;
						case "state":
							value = line.substring(141, 143);
							break;
						case "postalCode":
							value = line.substring(143, 148);
							break;
						case "open":
							value = line.substring(148);
							break;
						default:
							System.out.println("Error from datbase.");
							value = "Bad Attempt";
							break;
					}
				}
			}
			br.close();
		}
		catch (IOException e) {
		      e.printStackTrace(); 
		}
		
		return value;
	}
	
	public double retrieveNum(String field, long acntNum) {		
		double value = -1;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			
			String line;
			while ((line = br.readLine()) != null) {
				if (line.substring(0,9).equals(Long.toString(acntNum))) {
					switch (field) {
						case "pin":
							value = Double.valueOf(line.substring(9, 13));
							break;
						case "balance":
							value = Double.valueOf(line.substring(13, 28));
							break;
						case "dob":
							value = Double.valueOf(line.substring(63, 71));
							break;
						case "phoneNum":
							value = Double.valueOf(line.substring(71, 81));
							break;
						default:
							System.out.println("Error from datbase.");
							break;
					}
				}
			}
			br.close();
		}
		catch (IOException e) {
		      e.printStackTrace(); 
		}
		
		return value;
	}
	
	public void update(long acntNum, String field, String value) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			String before = "";
			String after = "";
			
			// info about position
			int start = 0;
			int end = 0;
			switch (field) {
				case "pin":
					start = 9;   end = 13;
					break;
				case "balance":
					start = 13;  end = 28;
					break;
				case "lastName":
					start = 28;  end = 48;
					break;
				case "firstName":
					start = 48;  end = 63;
					break;
				case "dob":
					start = 63;  end = 71;
					break;
				case "phoneNum":
					start = 71;  end = 81;
					break;
				case "address":
					start = 81;  end = 111;
					break;
				case "city":
					start = 111; end = 141;
					break;
				case "state":
					start = 141; end = 143;
					break;
				case "postalCode":
					start = 143; end = 148;
					break;
				case "open":
					start = 149; end = 150;
					break;
				default:
					System.out.println("Wrong input method");
					break;
			}
		
		
			boolean updated = false;
			String line;
			while ((line = br.readLine()) != null) {
				if (line.substring(0,9).equals(Long.toString(acntNum))) {
					if (!field.equals("open")) {
						value = line.substring(0, start) + String.format("%-" + (end - start) + "s", value) + line.substring(end, 149) + "\n";
					} else {
						value = line.substring(0, 148) + value;
					}
					updated = true;
				} else {
					if (!updated) {
						before = before + line + "\n";
					} else {
						after = after + line + "\n";
					}
				}
			}
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(before + value + after);
		    fileWriter.close();
		    br.close();
		}
		catch (IOException e) {
		      e.printStackTrace(); 
		}
	}
	
	public void delete(long acntNum) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			String wholeDB = "";
			
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.substring(0,9).equals(Long.toString(acntNum))) {
					wholeDB = wholeDB + line + "\n";
				} 
			}
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(wholeDB);
		    fileWriter.close();
		    br.close();
		}
		catch (IOException e) {
		      e.printStackTrace(); 
		}
	}
	
	public long maxAcntNum(){
		long maxNum = 0;
		try {			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			
			while ((line = br.readLine()) != null) {
				if (Long.valueOf(line.substring(0, 9)) > maxNum) {
					maxNum = (long) Long.valueOf(line.substring(0, 9));
				}
			}
			br.close();
		}
		catch (IOException e) {
		      e.printStackTrace(); 
		}
		return maxNum;
	}
}