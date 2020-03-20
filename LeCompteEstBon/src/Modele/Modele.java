package Modele;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Modele {
	final static int MINIMUM=100;
	final static int MAXIMUM=900;

private List<Etape> listeEtape;
private GereScores g= new GereScores();
private String sPseudo;
private int iNombreCible;
private int iDureeMax;
private int iDuree;
private ModeJeu mode_jeu;
	


public void nombreATire() {
	Random r= new Random();
	this.iNombreCible=r.nextInt(Modele.MAXIMUM - Modele.MINIMUM + 1) + Modele.MINIMUM;
}
public Modele() {
	this.mode_jeu=ModeJeu.INITIALISER;
	
		nombreATire();
		this.listeEtape=new LinkedList<Etape>();
		this.listeEtape.add(new Etape());
		g.charge();

}	
public void preparer(String pseudo) {
	this.mode_jeu=ModeJeu.PREPARER;
	this.sPseudo=pseudo;
	this.iDuree=0;
}
	
public void jouer(int indexPlaque1,int indexPlaque2,String sOperation) {
	this.mode_jeu=ModeJeu.JOUER;
	this.listeEtape.add(new Etape(this.listeEtape.get(listeEtape.size()-1).getlistePlaques(),indexPlaque1,indexPlaque2,sOperation));
}
public void annuler() {
	if(this.mode_jeu==ModeJeu.JOUER) {
	this.listeEtape.remove(listeEtape.size()-1);
	}
}
public void valider() {
	if(this.mode_jeu==ModeJeu.JOUER) {
	this.listeEtape.get(listeEtape.size()-1).plaquesSuivante();

	}
}
public void supprimer() {
	if(listeEtape.size()-1!=0) {
	this.listeEtape.remove(listeEtape.size()-1);
	}
}
public void proposer() {
	this.mode_jeu=ModeJeu.SCORE;
	int valeur=this.iNombreCible-this.listeEtape.get(listeEtape.size()-1).getlistePlaques().get(this.listeEtape.get(listeEtape.size()-1).getiIndexPremierePlaque());
	this.g.ajouteScore(this.sPseudo, valeur,this.iDureeMax-this.iDuree);
	this.g.enregistre();
}




/** Set and Get **/

public List<Etape> getListeEtape() {
	return listeEtape;
}
public void setListeEtape(List<Etape> listeEtape) {
	this.listeEtape = listeEtape;
}
public int getiNombreCible() {
	return iNombreCible;
}
public void setiNombreCible(int iNombreCible) {
	this.iNombreCible = iNombreCible;
}
public int getiDureeMax() {
	return iDureeMax;
}
public void setiDureeMax(int iDureeMax) {
	this.iDureeMax = iDureeMax;
}
public ModeJeu getMode_jeu() {
	return mode_jeu;
}
public void setMode_jeu(ModeJeu mode_jeu) {
	this.mode_jeu = mode_jeu;
}




}
