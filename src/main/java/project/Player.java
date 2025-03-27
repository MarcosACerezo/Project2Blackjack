package project;

import java.util.ArrayList;

public class Player {
  private static Deck deck;
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
    for(Card card: hand){
      handTotal = card.valueOf();
    }
    return handTotal;
  }

  public void clearHand(){
    hand.clear();
  }

  public boolean stand(int otherPlayerValue){
    if(valueOfHand() > otherPlayerValue){
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

  public int win(){
    wins++;
    return wins;
  }


}
