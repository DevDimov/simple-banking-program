import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SimpleBankingProgram {
	private double balance;
	private ArrayList<Transaction> transactions = new ArrayList<>();
	
	public SimpleBankingProgram(){
		balance = 0;
	}
	
	public Boolean depositAmountIsValid(double amount) {
		if (amount > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean withdrawAmountIsValid(double amount) {
		if (amount <= balance && amount > 0) {
			return true;
		}
		else if (amount > balance) {
			System.out.println("Insufficient funds");
			return false;
		}
		System.out.println("Invalid amount");
		return false;
	}
	
	public void deposit(double amountIn) {
		if (depositAmountIsValid(amountIn)) {
			logTransaction("Deposit", amountIn, getBalance(), logDateTime());
			setBalance(balance + amountIn);
			System.out.println("Deposit successful");
		}
		else {
			System.out.println("Invalid amount");
		}
	}
	
	public void withdraw(double amountIn) {
		if (withdrawAmountIsValid(amountIn)) {	
			logTransaction("Withdraw", amountIn, getBalance(), logDateTime());
			setBalance(balance - amountIn);
			System.out.println("Withdrawal successful");
		}
	}
	
	public void printBalance() {
		System.out.println("Current balance: " + balance);
	}
	
	public void printTransactions() {
		System.out.println("Latest transactions");
		for (int i=0; i<transactions.size(); i++) {	
			transactions.get(i).printTransactionDetails();
		}
		System.out.println();
	}
	
	public Double getBalance() {
		return balance;
	}
	
	public void setBalance(double amount) {
		this.balance = amount;
	}
	
	public Transaction getLastTransaction() {
		return transactions.get(transactions.size() - 1);
	}
	
	public Transaction logTransaction(String activity, double amount, double oldBalance, String date) {
		Transaction transaction = new Transaction(activity, amount, oldBalance, date);
		transactions.add(transaction);
		return transaction;
	}
	
	public String logDateTime() {
		LocalDateTime dateObj = LocalDateTime.now();
		DateTimeFormatter formatDateObj = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss");
		String formattedDate = dateObj.format(formatDateObj);
		return formattedDate;
	}
}