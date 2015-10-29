package SayWhat;

import java.io.Serializable;
import java.util.Comparator;

public class FajlbaTopLista implements Serializable{
	

	private static final long serialVersionUID = 1L;
	String name = ""; //név és pontszám név_pontszám formátumban
	int score = 1;
	public static Comparator<FajlbaTopLista> ScoreBased = new Comparator<FajlbaTopLista>(){

		@Override
		public int compare(FajlbaTopLista egyik, FajlbaTopLista masik) {
			// TODO Auto-generated method stub
			
			int elso = egyik.score;
			int masodik = masik.score;
			
			return masodik-elso;
		}
		
		
		
		
	};
	
	
	public int getscore(){
		
		return score;
	}
	

	
}


