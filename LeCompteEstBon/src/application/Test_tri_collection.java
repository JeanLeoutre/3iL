package application;

import java.util.LinkedList;
import java.util.List;

public class Test_tri_collection {
	public static void main(String[] args) {
		List<Score> listeScore =new LinkedList<Score>();
		listeScore.add(new Score("Jean",3,65));
		for(Score s:listeScore) {
			s.affiche();
		}
	}
}
