package project;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Name: Marcos Cerezo
 * Username: Cerema01
 */
public class Card extends ImageView{
  public static final String[] SUITES = {"H", "S", "C", "D"};
  public static final String[] FACES = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "K", "Q"};
  private static final int HEIGHT = 130;
  private String face;

  public Card(String face, String suite){
    this.face = face;
    Image image = new Image(getClass().getResource("/project/moreCards/" + suite + face + ".png").toExternalForm());
    setImage(image);
    setFitHeight(HEIGHT);
    setPreserveRatio(true);
  }

  public String getFace(){
    return face;
  }

  public int valueOf(){
    try{
      return Integer.parseInt(face);
    }catch(NumberFormatException e){
      switch(face){
        case "A":
          return 11;
        case "J", "Q", "K":
          return 10;
        default:
          return -1;
      }
    }
  }
}
