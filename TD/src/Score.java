import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Score implements Comparable<Score>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sPseudo;
	private int iValeur;
	private int iTemps;
	private String sDate;
	
	public Score(String pseudo, int valeur, int temps) {
		this.sPseudo=pseudo;
		this.iTemps=temps;
		this.iValeur=valeur;
		Date actuelle = new Date();
		 DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			this.sDate = dateFormat.format(actuelle);
	}
	@Override
	public int compareTo(Score s) {
		if(this.iValeur>s.iValeur) {
			return 1;
		}
		if(this.iValeur<s.iValeur) {
			return -1;
		}
		if(this.iValeur==s.iValeur) {
			if(this.iTemps>s.iTemps) {
				return 1;
			}else if(this.iTemps<s.iTemps) {
				return -1;
			}
				return 0;
			
		}
		return 0;
	}
	public String conversionTemps() {
		int secondes;
		int minutes;
		minutes=this.iTemps/60;
		secondes=this.iTemps%60;
		if(secondes<10) {
			return new String(""+minutes+":0"+secondes);
		}
		return new String(""+minutes+":"+secondes);
		
	
	}
	public void affiche() {
		System.out.println(this.sPseudo.toUpperCase()+" = " +this.iValeur+" en "+conversionTemps()+" (le "+this.sDate+")");
	}
	public String getsPseudo() {
		return sPseudo;
	}
	public int getiValeur() {
		return iValeur;
	}
	public int getiTemps() {
		return iTemps;
	}
	public String getsDate() {
		return sDate;
	}

	
}
