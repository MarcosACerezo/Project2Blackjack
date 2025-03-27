package project;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends ImageView{
  public static final String[] FACES = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "K", "Q"};
  private static final int HEIGHT = 130;
  private String face;

  public Card(String face){
    this.face = face;
    setImage(new Image(face + ".png"));
    setFitHeight(HEIGHT);
    setPreserveRatio(true);
  }

  public String getFace(){
    return face;
  }

  public int valueOf(){
    switch(face){
      case "A":
      return 1;
      case "2":
        return 2;
      case "3":
        return 3;
      case "4":
        return 4;
      case "5":
        return 5;
      case "6":
        return 6;
      case "7":   
        return 7;
      case "8":
        return 8;
      case "9":
        return 9;
      case "10", "J", "Q", "K":
        return 10;
      default: 
        return -1;
    }
  }
}
