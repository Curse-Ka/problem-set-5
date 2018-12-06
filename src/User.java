/**
 * Just like last time, the User class is responsible for retrieving
 * (i.e., getting), and updating (i.e., setting) user information.
 * This time, though, you'll need to add the ability to update user
 * information and display that information in a formatted manner.
 * 
 * Most of the functionality for this class should have already been
 * implemented last time. You can always reference my Github repository
 * for inspiration (https://github.com/rwilson-ucvts/java-sample-atm).
 */

public class User {
	private String firstName;
	private String lastName;
	private long pin;
	private long dob;
	private long phoneNum;
	private String address;
	private String city;
	private String state;
	private long postalCode;
	
	Database db = new Database();
	
	public User(String firstName, String lastName, long pin, long dob, long phoneNum, String address, String city, String state, long postalCode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.pin = pin;
		this.dob = dob;
		this.phoneNum = phoneNum;
		this.address = address;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
	}
	
	// Getters	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public long getPin() {
		return pin;
	}
	public long getDob() {
		return dob;
	}
	public long getPhoneNum() {
		return phoneNum;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public long getPostalCode() {
		return postalCode;
	}
	
	// Setters
	public void setFirstName(String in) {
		firstName = in;
		return;
	}
	public void setLastName(String in) {
		lastName = in;
		return;
	}
	public long setPin(long oldPin, long acntNum) {
		if (!(oldPin == db.retrieveNum("pin", acntNum))) {
			System.out.println("Incorrect PIN entered");
			oldPin = -1;
		}
		return oldPin;
	}
	public void setDob(long in) {
		dob = in;
		return;
	}
	public void setPhoneNum(long in) {
		phoneNum = in;
		return;
	}
	public void setAddress(String in) {
		address = in;
		return;
	}
	public void setCity(String in) {
		city = in;
		return;
	}
	public void setState(String in) {
		state = in;
		return;
	}
	public void setPostalCode(long in) {
		postalCode = in;
		return;
	}
	
}