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
  private PrintWriter fileWriter;
  private Scanner fileScanner;
  private int[] saveFileValues;


  public SaveGame() throws IOException{
    saveFile = new File(FILE_URL);
    fileScanner = new Scanner(saveFile);
    if(hasGameData()){
      saveFileValues = loadSaveFile();
    }
  }

  public int[] loadSaveFile(){
    int[] nums = new int[3];
    nums[0] = fileScanner.nextInt();
    nums[1] = fileScanner.nextInt();
    nums[2] = fileScanner.nextInt();
    return nums;
  }

  public int[] getFileValues(){
    return saveFileValues;
  }

  public void writeSaveFile(int pWins, int dWins, int bankAmount) throws IOException{
    //clear contents of file to not save more information than needed
    fileWriter = new PrintWriter(saveFile);
    fileWriter.println(pWins);
    fileWriter.println(dWins);
    fileWriter.println(bankAmount);
    fileWriter.close();
  }

  public boolean hasGameData(){
    if(fileScanner.hasNext()){
      return true;
    }else{
      return false;
    }
  }

  public boolean moneyInBank(){
    // System.out.println(saveFileValues == null);
    if(saveFileValues != null && saveFileValues[2] != 0){
      return true;
    }
    return false;
  }

  //not needed because if you select to not load game data, then your information
  //wil be rewritten regardless
  // public void clearGameData() throws IOException{
  //   fileWriter = new PrintWriter(saveFile);
  // }
}
