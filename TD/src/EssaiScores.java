import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class EssaiScores {

	public static void main(String[] args) throws IOException {
		List<Score> listeScore=new LinkedList<Score>();
		GereScores gScore = new GereScores();
		
		gScore.ajouteScore("Marcel", 0, 67);
		gScore.ajouteScore("Georges",0, 67);
		gScore.ajouteScore("RENEE", 0, 155);
		gScore.ajouteScore("Georges",2, 84);
		gScore.ajouteScore("Lucien",19, 220);
		gScore.ajouteScore("Marcel", 1, 67);
		gScore.ajouteScore("Georges",4, 67);
		gScore.ajouteScore("RENEE", 6, 155);
		gScore.ajouteScore("Georges",92, 84);
		gScore.ajouteScore("Lucien",10, 220);
		gScore.ajouteScore("Jean",100, 20);
		gScore.enregistre();
		gScore.affiche();
		gScore.export();
		
	}

}
