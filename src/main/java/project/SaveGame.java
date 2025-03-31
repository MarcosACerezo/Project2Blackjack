package project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Name: Marcos Cerezo
 * Username: Cerema01
 */
public class SaveGame{
  private final String FILE_URL = "saveGame.txt";
  private File saveFile;
  private PrintWriter fileWriter;
  private Scanner fileScanner;


  public SaveGame() throws IOException{
    saveFile = new File(FILE_URL);
    fileScanner = new Scanner(saveFile);
  }

  public int[] loadSaveFile(){
    int[] nums = new int[2];
    nums[0] = fileScanner.nextInt();
    nums[1] = fileScanner.nextInt();
    return nums;
  }

  public void writeSaveFile(int pWins, int dWins) throws IOException{
    fileWriter = new PrintWriter(saveFile);
    fileWriter.println(pWins);
    fileWriter.println(dWins);
    fileWriter.close();

  }

  public boolean hasGameData(){
    if(fileScanner.hasNext()){
      return true;
    }else{
      return false;
    }
  }

  //not needed because if you select to not load game data, then your information
  //wil be rewritten regardless
  // public void clearGameData() throws IOException{
  //   fileWriter = new PrintWriter(saveFile);
  // }
}
