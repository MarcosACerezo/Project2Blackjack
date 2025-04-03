package project;

import java.util.ArrayList;
import java.util.Random;

/**
 * Name: Marcos Cerezo
 * Username: Cerema01
 */
public class Deck {
  private ArrayList<Card> cards;
  private Random random;

  public Deck(){
    cards = new ArrayList<>();
    random = new Random();
    //new deck with 52 cards
    newDeck();
  }

  public Card dealCard(){
    //don't need - 1 because nextInt excludes the range max
    int maxDeckIndex = cards.size();
    int selectedCard = random.nextInt(maxDeckIndex);
    Card chosenCard = cards.get(selectedCard);
    cards.remove(selectedCard);
    return chosenCard;
  }

  public void reset(){
    cards.clear();
    newDeck();
  }

  public void newDeck(){
    for(String suite: Card.SUITES){
      for(String face: Card.FACES){
        cards.add(new Card(face, suite));
      }
    }
  }
}
