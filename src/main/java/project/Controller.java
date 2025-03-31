package project;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * Name: Marcos Cerezo
 * Username: Cerema01
 */
public class Controller {
  private Player player;
  private Player dealer;
  private SaveGame gameData;

  @FXML
  private VBox loadGameVBox;
  @FXML
  private HBox buttonHbox;
  @FXML
  private HBox dealerHandHBox; 
  @FXML
  private HBox playerHandHBox;

  @FXML
  private Label dealerHandLbl;
  @FXML
  private Label dealerWinsLbl;
  @FXML
  private Label playerHandLbl;
  @FXML
  private Label playerWinsLlbl;
  @FXML
  private Label winLabel;
  
  @FXML
  private Button yesButton;
  @FXML 
  private Button noButton;
  @FXML
  private Button clearButton;
  @FXML
  private Button hitButton;
  @FXML
  private Button playButton;
  @FXML
  private Button standButton;
 

  @FXML
  void initialize(){
    player = new Player();
    dealer = new Player();
    try{
      gameData = new SaveGame();
    }catch(IOException e){
      System.out.println("Not enough storage to create this file");
    }
    if(!gameData.hasGameData()){
      noLoadOption();
    }
    winLabel.setVisible(false);
    buttonHbox.getChildren().removeAll(hitButton, standButton);
  }

  @FXML
  void startGame(ActionEvent event) {
    Player.deck.reset();
    player.clearHand();
    dealer.clearHand();
    dealer.hit();
    updateHand(player, playerHandHBox, playerHandLbl);
    updateHand(dealer, dealerHandHBox, dealerHandLbl);
    winLabel.setVisible(false);
    buttonHbox.getChildren().removeAll(playButton);
    buttonHbox.getChildren().addAll(hitButton, standButton);
  }

  @FXML
  void loadOption(){
    playerHandHBox.getChildren().remove(loadGameVBox);
    int[] fileValues = gameData.loadSaveFile();
    dealer.setWins(fileValues[0]);
    player.setWins(fileValues[1]);
    String playerWin = String.format("Player Wins: %d", player.win() - 1);
    String dealerWin = String.format("Dealer Wins: %d", dealer.win() - 1);
    playerWinsLlbl.setText(playerWin);
    dealerWinsLbl.setText(dealerWin);
  }

  //no Operations needed from the file
  @FXML
  void noLoadOption(){
    playerHandHBox.getChildren().remove(loadGameVBox);
  }

  @FXML
  void clearSaveData(){
    playerHandHBox.getChildren().remove(loadGameVBox);
    try{
      gameData.clearGameData();
    }catch(IOException e ){
      System.out.println("Not enough memory to write into the file");
    }
  }

  @FXML
  void hit(ActionEvent event) {
    player.hit();
    updateHand(player, playerHandHBox, playerHandLbl);
    if(player.busted()){
      endGame();
    }
  }


  @FXML
  void stand(ActionEvent event) {
    while(!dealer.stand(player.valueOfHand())){
      dealer.hit();
      updateHand(dealer, dealerHandHBox, dealerHandLbl);
    }
    endGame();
  }

  

  public void updateHand(Player p, HBox handBox, Label handValue){
    handBox.getChildren().setAll(p.getHand());
    handValue.setText(p.valueOfHand() + "");
  }

  public void endGame(){
    int playerHand= player.valueOfHand();
    int dealerHand = dealer.valueOfHand();

    String dealerWin = String.format("Dealer Wins: %d", dealer.win());
    String dWin = "Dealer wins!";
    String playerWin = String.format("Player Wins: %d", player.win());
    String pWin = "Player wins!";

    String bust = "%d Bust!";


    winLabel.setVisible(true);
    if(player.busted()){
      playerHandLbl.setText(String.format(bust, playerHand));
      dealerWinsLbl.setText(dealerWin);
      winLabel.setText(dWin);
    }else if(dealer.busted()){
      dealerHandLbl.setText(String.format(bust, dealerHand));
      playerWinsLlbl.setText(playerWin);
      winLabel.setText(pWin);
    }else if(dealerHand > playerHand){
      dealerWinsLbl.setText(dealerWin);
      winLabel.setText(dWin);
    }else if(playerHand < dealerHand){
      playerWinsLlbl.setText(playerWin);
      winLabel.setText(pWin);
    }else{
      winLabel.setText("Push! No one wins.");
    }
    buttonHbox.getChildren().removeAll(hitButton, standButton);
    buttonHbox.getChildren().add(playButton);
  }



}
