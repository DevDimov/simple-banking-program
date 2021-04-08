import java.util.Scanner;

public class SimpleBankingProgramTest {

	public static void main(String[] args) {
		
		SimpleBankingProgram a = new SimpleBankingProgram();
		Scanner sc = new Scanner(System.in);
		int response;
		double amountIn;
		
		do {
		System.out.println("*** Simple Banking Program ***");
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		System.out.println("3. View Transactions");
		System.out.println("4. Exit");
		response = sc.nextInt();
		
		switch (response) {
			case 1:
				System.out.println("Enter amount");
				amountIn = sc.nextDouble();
				a.deposit(amountIn);
				break;	
			case 2:
				System.out.println("Enter amount");
				amountIn = sc.nextDouble();
				a.withdraw(amountIn);
				break;
			case 3:
				a.printBalance();
				a.printTransactions();
				break;
			case 4:
				break;
			default :
				System.out.println("Choose an option from 1 to 4 only" + "\n");
				
		}
		} while (response != 4);
		sc.close();
		System.out.println("Goodbye");
	}
}