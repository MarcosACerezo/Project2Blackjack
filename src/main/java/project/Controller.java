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
  private Gambling wagers;

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
  private Label playerWinsLbl;
  @FXML
  private Label winLbl;
  @FXML
  private Label bankLbl;
  
  @FXML
  private Button yesButton;
  @FXML 
  private Button noButton;
  // @FXML
  // private Button clearButton;
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
    wagers = new Gambling();
    try{
      gameData = new SaveGame();
    }catch(IOException e){
      System.out.println("File not found");
    }
    if(!gameData.hasGameData()){
      noLoadOption();
    }
    winLbl.setVisible(false);
    buttonHbox.getChildren().removeAll(hitButton, standButton);
  }

  //action different than regular black jack, dealer starts with one card, 
  //and player has no cards at the beginning
  @FXML
  void startGame(ActionEvent event) {
    Player.deck.reset();
    player.clearHand();
    dealer.clearHand();
    dealer.hit();
    updateHand(player, playerHandHBox, playerHandLbl);
    updateHand(dealer, dealerHandHBox, dealerHandLbl);
    winLbl.setVisible(false);
    buttonHbox.getChildren().removeAll(playButton);
    buttonHbox.getChildren().addAll(hitButton, standButton);

  }

  @FXML
  void loadOption(){
    playerHandHBox.getChildren().remove(loadGameVBox);
    int[] fileValues = gameData.loadSaveFile();
    player.setWins(fileValues[0]);
    dealer.setWins(fileValues[1]);
    wagers.setBalance(fileValues[2]);
    
    String playerWin =
      String.format("Player Wins: %d", player.getWins());
    String dealerWin = 
      String.format("Dealer Wins: %d", dealer.getWins());
    String bankAmount = 
      String.format("Bank: %d", wagers.getBankAmount());
    playerWinsLbl.setText(playerWin);
    dealerWinsLbl.setText(dealerWin);
    bankLbl.setText(bankAmount);
  }

  //no Operations needed from the file
  @FXML
  void noLoadOption(){
    wagers.setBalance(Gambling.STARTING_AMOUNT);
    String bankAmount = 
      String.format("Bank: %d", wagers.getBankAmount());
    playerHandHBox.getChildren().remove(loadGameVBox);
    bankLbl.setText(bankAmount);
  }

  //not needed because if you select to not load game data, then your information
  //wil be rewritten regardless
  // @FXML
  // void clearSaveData(){
  //   playerHandHBox.getChildren().remove(loadGameVBox);
  //   try{
  //     gameData.clearGameData();
  //   }catch(IOException e ){
  //     System.out.println("File not Found");
  //   }
  // }

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
    String dWin = "Dealer wins!";
    String pWin = "Player wins!";
    String bust = "%d Bust!";

    winLbl.setVisible(true);
    if(player.busted()){
      dealer.win();
      playerHandLbl.setText(String.format(bust, playerHand));
      winLbl.setText(dWin);
    }else if(dealer.busted()){
      player.win();
      dealerHandLbl.setText(String.format(bust, dealerHand));
      winLbl.setText(pWin);
    }else if(dealerHand > playerHand){
      dealer.win();
      winLbl.setText(dWin);
    }else if(playerHand < dealerHand){
      player.win();
      winLbl.setText(pWin);
    }else{
      winLbl.setText("Push! No one wins.");
    }
    //get current win values of dealer and player
    dealerWinsLbl.setText(String.format("Dealer Wins: %d", dealer.getWins()));
    playerWinsLbl.setText(String.format("Player Wins: %d", player.getWins()));

    try{
      gameData.writeSaveFile(
        player.getWins(), dealer.getWins(), wagers.getBankAmount());
    }catch(IOException e){
      System.out.println("File not Found");
    }
    buttonHbox.getChildren().removeAll(hitButton, standButton);
    buttonHbox.getChildren().add(playButton);
  }
}
