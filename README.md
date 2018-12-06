### User

The `User` class is responsible for managing all aspects of the customer's personal information, as outlined below.

* The customer's first name.
* The customer's last name.
* The customer's PIN.
* The customer's date of birth.
* The customer's telephone number.
* The customer's street address.
* The customer's city.
* The customer's state.
* The customer's postal code.

Generally, the `User` class should have the ability to retrieve and modify each of the above pieces of information. Exceptions to this general rule of thumb are outlined below.

##* In order to modify the PIN, a customer must first enter his or her existing PIN before choosing a new one.
##* A customer should not be permitted to modify his or her first name, last name, or date of birth.

### BankAccount

The `BankAccount` class is responsible for managing all aspects of the customer's account information, as outlined below.

##* The system-generated account number.
##* The user associated with the account (i.e., an instance of the `User` class).
* The current account balance.

The `BankAccount` class should have the ability to deposit and withdraw money, as well as transfer funds to another account. That being said, customers should be prohibited from doing any of the following.

* Depositing a dollar amount that is less than or equal to $0.00.
* Withdrawing a dollar amount that is less than or equal to $0.00.
* Withdrawing a dollar amount that is greater than the account balance.
* Transferring a dollar amount that is less than or equal to $0.00.
* Transferring a dollar amount that is greater than the sending account balance.

##* Transferring funds to non-existent accounts.

### Database

The `Database` class is responsible for interfacing between the fixed width file that serves as our database and the `ATM` class. It should contain basic CRUD (create, read, update, delete) operations.

* Create new accounts from user-provided information.
* Retrieve existing accounts.
* Update existing accounts.
* Delete existing accounts.

Including in this repository is a file called `accounts-db.txt`. This is a fixed-width file, meaning each field is assigned a specific number of characters. The account number, for example, is 9 characters wide and it is the very first field. This means that the first 9 characters of each line correspond to the account number. Full field definitions and descriptions are outlined below.

| Field | Description |
| --- | --- |
| Account Number |  0-9      9 characters wide, consisting only of numbers (i.e., 100000001). Account numbers should be system-generated and assigned during account creation. |
| PIN            |  9-13     4 characters wide, consisting only of numbers (i.e., 1234). |
| Balance        |  13-28    15 characters wide (including the decimal point)
| Name           |  28-48    35 characters wide (formatted as Last, First). Last names are 20 characters wide, first names are 15 characters wide |
                    48-63 
| Date of Birth  |  63-71    8 characters wide (format: YYYYMMDD). |
| Phone Number   |  71-81    10 characters wide (format: ##########). |
| Street Address |  81-111   30 characters wide (i.e., 1776 Raritan Road). |
| City           |  111-141  30 characters wide (i.e., Scotch Plains). |
| State          |  141-143  2 characters wide (i.e., NJ). |
| Postal Code    |  143-148  5 characters wide (i.e., 07076). |
| Account Status |  148-149  1 character wide (Y for open accounts, N for closed accounts). |

### ATM

The `ATM` class is responsible for managing the interaction between the customer and ATM. Most of the logic will be implemented in this class. It should meet the specifications outlined below.

* Display a simple main menu.
   - Open account
   - Login
   - Quit
* Display a more complex submenu after logging in.
   - Deposit funds       
   - Withdraw funds      
   - Transfer funds      
   - View balance        
   - View personal info  
   - Update personal info
   - Close account       
   - Logout              
* Respond accordingly to each of the menu options.

The expected inputs for a program like this will understandably vary. It is your responsibility to handle this. Your program needs to be able to handle anything a customer might throw at it. Simply put, it should never crash.

### Tester

The `Tester` class is responsible only for starting the program. This is where your `main` method should be written. A working version, with all features correctly implemented, is available in JAR form. You can download it from this repository and run it using the command below.
```
java -jar enhanced-atm.jar
```

## Deadline

Your Canvas submission is due at or before 11:59pm on TBD.

### Submission Requirements

All that is required for submission is the URL to your [GitHub](https://github.com/) repository for this problem set.
