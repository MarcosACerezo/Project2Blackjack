package project;

import java.io.File;
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
  private Scanner fileScanner;
  private int[] saveFileValues;


  public SaveGame() throws IOException{
    saveFile = new File(FILE_URL);
    fileScanner = new Scanner(saveFile);
    if(hasGameData()){
      saveFileValues = loadSaveFile();
    }
  }

  private int[] loadSaveFile(){
    int[] nums = new int[3];
    nums[0] = fileScanner.nextInt();
    nums[1] = fileScanner.nextInt();
    nums[2] = fileScanner.nextInt();
    return nums;
  }

  private boolean hasGameData(){
    if(fileScanner.hasNext()){
      return true;
    }
    return false;
  }

  public int[] getFileValues(){
    return saveFileValues;
  }

  public void writeSaveFile(int pWins, int dWins, int bankAmount) throws IOException{
    try(PrintWriter fileWriter = new PrintWriter(saveFile)){
      fileWriter.println(pWins);
      fileWriter.println(dWins);
      fileWriter.println(bankAmount);
    }
  }

  public boolean moneyInBank(){
    if(saveFileValues != null && saveFileValues[2] != 0){
      return true;
    }
    return false;
  } 
}
