package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

import objects.Game;
import objects.GameCheckBox;
import objects.Save;

public class MRRGoalSaveLoad {

	public static Save save = new Save();
	public static Set<GameCheckBox> gameListCheckbox = new TreeSet<GameCheckBox>();
	
	public static void saveGoals() {
		try(FileOutputStream fileOut = new FileOutputStream("save.ser");
	        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(save);
 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void loadGoals() {
		try(FileInputStream fileIn = new FileInputStream("save.ser");
	        ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            save = (Save) objectIn.readObject();
            
          //build GameCheckBox list from gameList
            for(Game game : save.getGameList()) {
            	gameListCheckbox.add(new GameCheckBox(game));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
