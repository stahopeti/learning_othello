package teszt;

import java.io.File;

public class mana {
	
	public static void main(String[] args){
	//Ez a "main" f�ggv�ny, itt j�n l�tre a f�men�.
		File f = new File("kutyacica.txt");
		System.out.println(f.getPath());
		GUIllermo ez = new GUIllermo();
		
		ez.setVisible(true);
		
		
	}
	
}
