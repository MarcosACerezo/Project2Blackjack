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
public class Gambling{
  //starting amount if no save file is chosen
  public static final int STARTING_AMOUNT = 1000;
  //amount of currency player has
  private int playerBank;
  private int currentWageredAmount;

  //No stored values
  public Gambling(){
    playerBank = STARTING_AMOUNT;
  }

  //pulling bank amount from saved stats
  public Gambling(int bankAmount){
    playerBank = bankAmount;
  }


  //prompt the user to enter a valid wager
  public boolean setWager(int wager) throws NumberFormatException{
    if(wager > playerBank || wager <= 0){//invalid entry
      return false;//handling responsibility passed to controller
      // errorLbl.setText("Enter an amount you own!");
      // errorLbl.setVisible(true);
      // return -1;
    }
    // errorLbl.setVisible(false);
    playerBank -= wager;
    currentWageredAmount = wager;
    return true;
  }

  public int getBankAmount(){
    return playerBank;
  }

  //only use this method for setting the initial bank balance
  public void setBalance(int amount){
    playerBank = amount;
  }

  public void playerWin(){
    //this might not need to be here
    if(currentWageredAmount < 300){
      playerBank += currentWageredAmount * 1.25;
    }else if(currentWageredAmount < 600){
      playerBank += currentWageredAmount * 1.5;
    }else{
      playerBank += currentWageredAmount * 2;
    }
    currentWageredAmount = -1;//reset the amount after it is used
  }
}
