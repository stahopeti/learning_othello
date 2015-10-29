package teszt;

import java.io.File;

public class mana {
	
	public static void main(String[] args){
	//Ez a "main" függvény, itt jön létre a fõmenü.
		File f = new File("kutyacica.txt");
		System.out.println(f.getPath());
		GUIllermo ez = new GUIllermo();
		
		ez.setVisible(true);
		
		
	}
	
}
