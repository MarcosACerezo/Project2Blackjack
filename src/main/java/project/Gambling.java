package project;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
  * Name: Marcos Cerezo
  * Username: Cerema01
  */
public class Gambling extends VBox{
  //starting amount if no save file is chosen
  private final int STARTING_AMOUNT = 1000;
  //JavaFX Controls
  private Label errorLbl;
  private TextField amountWagered;
  private Button wagerButton;

  private String title = "Please Select a wager";
  //amount of currency player has
  private int playerBank;
  
  public void initUI(){
    setAlignment(Pos.CENTER);
    setMinWidth(300);
    errorLbl = new Label("");
    wagerButton = new Button("Ready!");
    amountWagered = new TextField();
    //adding all elements
    getChildren().addAll(
      new Label(title), amountWagered , wagerButton, errorLbl);
    errorLbl.setVisible(false);
    //button error handling
    wagerButton.setOnAction(event  ->{
      try{
        setWager(Integer.parseInt(amountWagered.getText()));
      }catch(NumberFormatException e){
        errorLbl.setVisible(true);
        errorLbl.setText("Incorrect int format");
      }
    });
  }
  
  //No stored values
  public Gambling(){
    playerBank = STARTING_AMOUNT;
    initUI();
  }

  //pulling bank amount from saved stats
  public Gambling(int bankAmount){
    playerBank = bankAmount;
    initUI();
  }


  //prompt the user to enter a valid wager
  public int setWager(int wager) throws NumberFormatException{
    if(wager > playerBank || wager <= 0){//invalid entry
      errorLbl.setText("Enter an amount you own!");
      errorLbl.setVisible(true);
      return -1;
    }
    errorLbl.setVisible(false);
    playerBank -= wager;
    return wager;
  }

  public int getBankAmount(){
    return playerBank;
  }

  public void setBankAmount(int amount){
    setBankAmount(amount);
  }
}
