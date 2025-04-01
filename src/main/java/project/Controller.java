package project;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
* Name: Marcos Cerezo
* Username: Cerema01
*/
public class Controller {
  private final String GAME_OVER 
  = "Game Over. :(";
  private Player player;
  private Player dealer;
  private SaveGame gameData;
  private Gambling bet;
  private String titleString = "Please set a bet amount";


  //gambling controls and containers
  private VBox betVBox;
  private Label title;
  private TextField desiredBet;
  private Button betBtn;
  private Label errorLbl;

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
 

  public void initWagerUI(){
    betVBox = new VBox();
    betVBox.setAlignment(Pos.CENTER);
    betVBox.setMinWidth(300);
    title = new Label(titleString);
    desiredBet = new TextField();
    betBtn = new Button("Place Bet");
    errorLbl = new Label();
    betVBox.getChildren().addAll(title, desiredBet, betBtn, errorLbl);

    betBtn.setOnAction(event -> {
      try{
        errorLbl.setVisible(false);
        int betAmount = Integer.parseInt(desiredBet.getText());
        if(bet.validWager(betAmount)){
          betPlaced(betAmount);
        }else{
          errorLbl.setText("Please enter an amount you own");
          errorLbl.setVisible(true);
        }
      }catch(NumberFormatException e){
        errorLbl.setText("Integer not entered");
        errorLbl.setVisible(true);
      }
    });
  }

  @FXML
  void initialize(){
    player = new Player();
    dealer = new Player();
    bet = new Gambling();
    //Here we are handling three possibilities
    //1 file not foind
    //2 game file does not have any data
    //3 game file bank value is 0
    playButton.setDisable(true);
    try{
      gameData = new SaveGame();
    }catch(IOException e){
      System.out.println("File not found");
    }
    if(!gameData.moneyInBank()){
      noLoadOption();
    }
    winLbl.setVisible(false);
    buttonHbox.getChildren().removeAll(hitButton, standButton);
    initWagerUI();
  }

  //action different than regular black jack, dealer starts with one card, 
  //and player has no cards at the beginning
  @FXML
  void startGame(ActionEvent event) {
    Player.deck.reset();
    player.clearHand();
    dealer.clearHand();
    // dealer.hit();Don't want the dealer to hit until after
    updateHand(player, playerHandHBox, playerHandLbl);
    updateHand(dealer, dealerHandHBox, dealerHandLbl);
    winLbl.setVisible(false);
    buttonHbox.getChildren().removeAll(playButton);
    buttonHbox.getChildren().addAll(hitButton, standButton);
    playerHandHBox.getChildren().add(betVBox);
  }

  public void betPlaced(int amount){
    bet.setWager(amount);
    String bankAmount = 
      String.format("Bank: %d", bet.getBankAmount());

    playerHandHBox.getChildren().remove(betVBox);
    dealer.hit();
    updateHand(player, playerHandHBox, playerHandLbl);
    updateHand(dealer, dealerHandHBox, dealerHandLbl);
    bankLbl.setText(bankAmount);
  }

  @FXML
  void loadOption(){
    int[] fileValues = gameData.getFileValues();
    playerHandHBox.getChildren().remove(loadGameVBox);
    player.setWins(fileValues[0]);
    dealer.setWins(fileValues[1]);
    bet.setBalance(fileValues[2]);
    
    String playerWin =
      String.format("Player Wins: %d", player.getWins());
    String dealerWin = 
      String.format("Dealer Wins: %d", dealer.getWins());
    String bankAmount = 
      String.format("Bank: %d", bet.getBankAmount());
    playerWinsLbl.setText(playerWin);
    dealerWinsLbl.setText(dealerWin);
    bankLbl.setText(bankAmount);
    playButton.setDisable(false);
  }

  //no Operations needed from the file
  @FXML
  void noLoadOption(){
    bet.setBalance(Gambling.STARTING_AMOUNT);
    String bankAmount = 
      String.format("Bank: %d", bet.getBankAmount());
    playerHandHBox.getChildren().remove(loadGameVBox);
    bankLbl.setText(bankAmount);
    playButton.setDisable(false);
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
      bet.playerWin();
      dealerHandLbl.setText(String.format(bust, dealerHand));
      winLbl.setText(pWin);
    }else if(dealerHand > playerHand){
      dealer.win();
      winLbl.setText(dWin);
    }else if(playerHand < dealerHand){
      player.win();
      bet.playerWin();
      winLbl.setText(pWin);
    }else{
      winLbl.setText("Push! No one wins.");
      bet.push();
    }
    //get current win values of dealer and player
    dealerWinsLbl.setText(String.format("Dealer Wins: %d", dealer.getWins()));
    playerWinsLbl.setText(String.format("Player Wins: %d", player.getWins()));
    bankLbl.setText(String.format("Bank: %d", bet.getBankAmount()));

    try{
      gameData.writeSaveFile(
        player.getWins(), dealer.getWins(), bet.getBankAmount());
    }catch(IOException e){
      System.out.println("File not Found");
    }
    buttonHbox.getChildren().removeAll(hitButton, standButton);
    buttonHbox.getChildren().add(playButton);
    if(bet.noMoney()){
      playButton.setDisable(true);
      winLbl.setText(GAME_OVER);
    }
  }
}
