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
  private FileWriter fWriter;
  private Scanner fileScanner;


  public SaveGame() throws IOException{
    saveFile = new File(FILE_URL);
    fWriter = new FileWriter(FILE_URL, true);
    fileScanner = new Scanner(saveFile);
  }

  public int[] loadSaveFile(){
    int[] nums = new int[2];
    nums[0] = fileScanner.nextInt();
    nums[1] = fileScanner.nextInt();
    return nums;
  }

  public void writeSaveFile(){

  }

  public boolean hasGameData(){
    if(fileScanner.hasNext()){
      return true;
    }else{
      return false;
    }
  }

  public void clearGameData() throws IOException{
    fWriter = new FileWriter(FILE_URL, false);
  }
}
