import java.util.HashMap;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SimpleBankingProgramGUI extends Application {
	
	SimpleBankingProgram bankingObject = new SimpleBankingProgram();
	
	Button[] activeButton = new Button[1];
	HashMap<String, Double> previousWindowSize = new HashMap<>(2);
	
	public Button createMenuButton(String text) {
		Button button = new Button();       
		button.setText(text);
		button.setFont(Font.loadFont("file:resources/fonts/GiraSans-Regular.ttf", 22));
		button.setMinSize(200,100);
		button.setMaxWidth(1000d);
		button.getStyleClass().add("menu-button");
		return button;
	}
	
	public Button createPrimaryButton(String text) {
		Button button = new Button();       
		button.setText(text);
		button.setPadding(new Insets (0,0,4,0));
		button.setFont(Font.loadFont("file:resources/fonts/GiraSans-Regular.ttf", 22));
		button.setMinSize(200,55);
		button.getStyleClass().add("primary-button");
		return button;
	}
	
	public TextField createTextField() {
		TextField textfield = new TextField();
		textfield.setFont(Font.loadFont("file:resources/fonts/GiraSans-Book.ttf", 80));
		textfield.setAlignment(Pos.CENTER);
		textfield.setPadding(new Insets (0,0,0,0));
		textfield.setMaxSize(375, 125);
		return textfield;
	}
	
	public void changeScene(BorderPane borderPane, HBox hbox, VBox vbox, Button menuButton, Stage stage, Scene scene) {
		borderPane.setTop(hbox);
		borderPane.setCenter(vbox);
		toggleButton(menuButton);
		stage.setHeight(previousWindowSize.get("Height"));
		stage.setWidth(previousWindowSize.get("Width"));
		stage.setScene(scene);
	}
	
	public void addInputValidator(TextField textfield) {
		textfield.textProperty().addListener((observable, oldValue, newValue) -> {
			// digits of length 0 to 4
        	// may be followed by a single dot
        	// and up to two decimal places
        	if (!newValue.matches("\\d{0,4}([\\.]\\d{0,2})?")) {
        		textfield.setText(oldValue);
            }
		});
	}
	
	public void toggleButton(Button button) {
		activeButton[0].getStyleClass().remove("menu-button-active");
		activeButton[0].getStyleClass().add("menu-button");
		button.getStyleClass().add("menu-button-active");
		activeButton[0] = button;
	}
	
	public void showInsufficientFundsError(Alert a) {
       a.setAlertType(AlertType.ERROR);
       double balance = bankingObject.getBalance();
       if (balance == 0) {
    	   a.setContentText("Your balance is 0. Please make a deposit first.");
       }
       else {
    	   a.setHeaderText("Insufficient Funds");
    	   a.setContentText("Withdrawal amount must not be higher than your current balance of " + balance);
       }
       a.show();
    }
	
	public void captureWindowSize(Stage stage) {
		previousWindowSize.put("Height", stage.getHeight());
		previousWindowSize.put("Width", stage.getWidth());
	}
	
	public void start(Stage stage) {
		
		Button depositMenuButton, withdrawMenuButton, activityMenuButton, exitButton;
		HBox menuHBox = new HBox();
		TextField inputDeposit, inputWithdraw;
		Button deposit, withdraw;
		Alert a = new Alert(AlertType.NONE);
		TableView<Transaction> table;
		VBox depositVBox, withdrawVBox;
		BorderPane borderDeposit, borderWithdraw, borderActivity;
		Scene depositScene, withdrawScene, activityScene;
		
		// DEPOSIT SCENE
		
		depositMenuButton = createMenuButton("Deposit");
		withdrawMenuButton = createMenuButton("Withdraw");
		activityMenuButton = createMenuButton("Activity");
		exitButton = createMenuButton("Exit");
		exitButton.setOnAction(e -> stage.close());
		
		menuHBox.setStyle("-fx-background-color: #2D2A27;");
		menuHBox.getChildren().addAll(depositMenuButton, withdrawMenuButton, activityMenuButton, exitButton);
		HBox.setHgrow(depositMenuButton, Priority.ALWAYS);
		HBox.setHgrow(withdrawMenuButton, Priority.ALWAYS);
		HBox.setHgrow(activityMenuButton, Priority.ALWAYS);
		HBox.setHgrow(exitButton, Priority.ALWAYS);
		
		inputDeposit = createTextField();
		addInputValidator(inputDeposit);
		
		deposit = createPrimaryButton("Deposit");
		
		depositVBox = new VBox();
		depositVBox.getChildren().addAll(inputDeposit,deposit);
		depositVBox.setAlignment(Pos.CENTER);
		depositVBox.setSpacing(60);
		
		borderDeposit = new BorderPane();
		borderDeposit.setTop(menuHBox);
		borderDeposit.setCenter(depositVBox);
		
		depositScene = new Scene(borderDeposit);
		depositScene.getStylesheets().add("file:resources/style.css");

		depositMenuButton.setOnAction(e -> {	
			captureWindowSize(stage);
			changeScene(borderDeposit, menuHBox, depositVBox, depositMenuButton, stage, depositScene);
		});
		
		// WITHDRAW SCENE
		
		borderWithdraw = new BorderPane();
		withdrawVBox = new VBox();
		
		withdrawScene = new Scene(borderWithdraw);
		withdrawScene.getStylesheets().add("file:resources/style.css");

		inputWithdraw = createTextField();
		addInputValidator(inputWithdraw);

		withdraw = createPrimaryButton("Withdraw");
		
		withdrawVBox.getChildren().addAll(inputWithdraw,withdraw);
		withdrawVBox.setAlignment(Pos.CENTER);
		withdrawVBox.setSpacing(60);
		
		withdrawMenuButton.setOnAction(e -> {
			captureWindowSize(stage);
			changeScene(borderWithdraw, menuHBox, withdrawVBox, withdrawMenuButton, stage, withdrawScene);
		});
		
		// ACTIVITY SCENE
		
		borderActivity = new BorderPane();
		
		table = new TableView<Transaction>();
		TableColumn<Transaction, String> activityColumn = new TableColumn<>("Activity");
		activityColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("activity"));
		
		TableColumn<Transaction, Double> amountColumn = new TableColumn<>("Amount");
		amountColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("amount"));
		
		TableColumn<Transaction , Double> balanceColumn = new TableColumn<>("Balance");
		balanceColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("newBalance"));
		
		TableColumn<Transaction, String> dateColumn = new TableColumn<Transaction , String>("Date & Time");
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		table.getColumns().addAll(activityColumn, amountColumn, balanceColumn, dateColumn);
		table.getStylesheets().add("file:resources/style.css");
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		borderActivity.setCenter(table);
		
		activityScene = new Scene(borderActivity);
		activityScene.getStylesheets().add("file:resources/style.css");
		
		activityMenuButton.setOnAction(e -> {
			captureWindowSize(stage);
			borderActivity.setTop(menuHBox);
			toggleButton(activityMenuButton);
			stage.setScene(activityScene);
		});
		
		// BUTTON FUNCTIONALITY AND INITIAL VALUES
		
		depositMenuButton.getStyleClass().remove("menu-button");
		depositMenuButton.getStyleClass().add("menu-button-active");
		
		activeButton[0] = depositMenuButton;
		
		deposit.setOnAction(e -> {
			Double amount = Double.valueOf(inputDeposit.getText());
			if (bankingObject.depositAmountIsValid(amount)) {
				bankingObject.deposit(amount);
				table.getItems().add(bankingObject.getLastTransaction());
				inputDeposit.setText("");
			}
		});
		
		withdraw.setOnAction(e -> {
			Double amount = Double.valueOf(inputWithdraw.getText());
			if (bankingObject.withdrawAmountIsValid(amount)) {
				bankingObject.withdraw(amount);
				table.getItems().add(bankingObject.getLastTransaction());
				inputWithdraw.setText("");
			} else {
				showInsufficientFundsError(a);
			}
		});
		
		stage.setTitle("Simple Banking Program");
		stage.setScene(depositScene);
		stage.show();
		stage.setMinWidth(816);
		stage.setMinHeight(600);	
	}
}