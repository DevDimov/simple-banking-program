public class Transaction {
	
	private String activity;
	private double amount;
	private double newBalance;
	private String date;
	
	public Transaction(String activity, double amount, double oldBalance, String date) {
		this.activity = activity;
		this.amount = amount;
		this.date = date;
		
		if (activity.equals("Deposit")) {
			this.newBalance = oldBalance + amount;
		}
		if (activity.equals("Withdraw")) {
			this.newBalance = oldBalance - amount;
		}
	}
	
	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getNewBalance() {
		return newBalance;
	}

	public void setBalance(double balance) {
		this.newBalance = balance;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public void printTransactionDetails() {
		System.out.println("Activity: " + getActivity() + ", Amount: " + getAmount() + ", Balance: " + getNewBalance() + ", Date: " + getDate());
	}
}