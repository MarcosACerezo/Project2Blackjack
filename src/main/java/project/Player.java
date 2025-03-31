package project;

import java.util.ArrayList;

/**
 * Name: Marcos Cerezo
 * Username: Cerema01
 */
public class Player {
  public static Deck deck = new Deck();
  private ArrayList<Card> hand;
  private int wins;

  public Player(){
    hand = new ArrayList<>();
    wins = 0;
  }

  public ArrayList<Card> getHand(){
    return hand;
  }


  public int valueOfHand(){
    int handTotal = 0;
    //counter to hande whether aces are counted as 1 or 11
    int aceTotal = 0;
    for(Card card: hand){
      if(card.getFace().equalsIgnoreCase("A")){
        aceTotal++;
      }
      handTotal += card.valueOf();
    }
    while(handTotal > 21 && aceTotal > 0){
      handTotal = handTotal - 10;
      aceTotal--;
    }
    return handTotal;
  }

  public void clearHand(){
    hand.clear();
  }

  //algorithm for whether or not the dealer should stand
  public boolean stand(int otherPlayerValue){

    if(busted() || valueOfHand() > otherPlayerValue){
      return true;
    }else if((valueOfHand() == otherPlayerValue) && otherPlayerValue >= 16){
      double randomSelector = Math.random();
      if(randomSelector > .5){
        return true;
      }else{
        return false;
      }
    }else{
      return false;
    }
  }

  public boolean busted(){
    if(valueOfHand() > 21){
      return true;
    }else{
      return false;
    }
  }

  public void hit(){
    hand.add(deck.dealCard());
  }

  public void setWins(int wins){
    this.wins = wins;
  }

  public int getWins(){
    return wins;
  }

  public int win(){
    wins++;
    return wins;
  }


}
