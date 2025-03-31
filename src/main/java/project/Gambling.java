package project;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class Gambling extends VBox{
  private String title = "Please Select a wager";
  //starting amount if no save file is chosen
  private final int STARTING_AMOUNT = 1000;
  private int playerBank;
  private Label errorLabel;
  private TextField amountWagered;
  private Button wagerButton;

  /**
  * Name: Marcos Cerezo
  * Username: Cerema01
  */
  //No stored values
  public Gambling(){
    errorLabel = new Label();
    wagerButton = new Button("Ready!");
    
    playerBank = STARTING_AMOUNT;
    setAlignment(Pos.CENTER);
    getChildren().addAll(new Label(title), amountWagered = new TextField(), wagerButton, errorLabel);
    errorLabel.setVisible(false);
    
    wagerButton.setOnAction(event  ->{
      setWager(Integer.parseInt(amountWagered.getText()));
      System.out.println("Hi there");
    });
  }

  //pulling bank amount from saved stats
  public Gambling(int bankAmount){
    errorLabel = new Label();
    wagerButton = new Button("Ready!");
    
    playerBank = bankAmount;
    setAlignment(Pos.CENTER);
    getChildren().addAll(new Label(title), amountWagered = new TextField(), wagerButton, errorLabel);
    errorLabel.setVisible(false);
    wagerButton.setOnAction(event  ->{
      setWager(Integer.parseInt(amountWagered.getText()));
    });
  }

  //if the player enters an invalid int as the wager the method will return -1 to 
  //prompt the user to enter a valid wager
  public int setWager(int wager){
    if(wager > playerBank || wager <= 0){
      errorLabel.setVisible(true);
      errorLabel.setText("Please select a valid wager");
      return -1;
    }else{
      errorLabel.setVisible(false);
      playerBank -= wager;
      return wager;
    }
  }

  public int getBankAmount(){
    return playerBank;
  }
}
