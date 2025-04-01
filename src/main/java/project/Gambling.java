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

  public boolean validWager(int wager){
    if(wager > playerBank || wager <= 0){//invalid entry
      return false;//handling responsibility passed to controller
    }
    return true;
  }

  //prompt the user to enter a valid wager
  public void setWager(int wager) throws NumberFormatException{
    playerBank -= wager;
    currentWageredAmount = wager;
  }

  public int getBankAmount(){
    return playerBank;
  }

  //only use this method for setting the initial bank balance
  public void setBalance(int amount){
    playerBank = amount;
  }

  public void playerWin(){
    if(currentWageredAmount < 300){
      playerBank += currentWageredAmount * 1.25;
    }else if(currentWageredAmount < 600){
      playerBank += currentWageredAmount * 1.5;
    }else{
      playerBank += currentWageredAmount * 2;
    }
    currentWageredAmount = -1;//reset the amount after it is used
  }

  public void push(){
    playerBank += currentWageredAmount;
    currentWageredAmount = -1;
  }

  public boolean noMoney(){
    if(playerBank == 0){
      return true;
    }
    return false;
  }
}
