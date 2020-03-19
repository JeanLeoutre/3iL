package Modele;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Etape {
	final static int NBPLAQUES=6;
	final static int NBOPERATION=4;
	private List<Integer> listePlaques=new LinkedList<>();
	private int iIndexPremierePlaque;
	private int iIndexSecondePlaque;
	private String[] sTabOperations= {"-","+","*","/"};
	private int idOp;
	private boolean calculOK;
	private int resultat;
	private List<Integer> listNombreDispo =  new LinkedList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,25,50,75,100));
	
	public Etape() {
		initialisation();
	}
	
	public Etape(List<Integer> oldTabPlaques,int iIndexPlaque1, int iIndexPlaque2,String sOperation) {
		this.iIndexPremierePlaque=iIndexPlaque1;
		this.iIndexSecondePlaque=iIndexPlaque2;
		
		for(int i=0;i<this.sTabOperations.length;i++) {
			if(this.sTabOperations[i].equals(sOperation)) {
				this.idOp=i;
			}
		}
		if(iIndexPlaque1!=iIndexPlaque2) {
		switch(sOperation) {
		case "-":
			this.resultat=oldTabPlaques.get(iIndexPlaque1)-oldTabPlaques.get(iIndexPlaque2);
			break;
		case "+":
			this.resultat=oldTabPlaques.get(iIndexPlaque1)+oldTabPlaques.get(iIndexPlaque2);
			break;
		case "*":
			this.resultat=oldTabPlaques.get(iIndexPlaque1)*oldTabPlaques.get(iIndexPlaque2);
			break;
		case "/":
			this.resultat=oldTabPlaques.get(iIndexPlaque1)%oldTabPlaques.get(iIndexPlaque2);
			if(resultat!=0) {
				this.calculOK=false;
				throw new IllegalArgumentException("Opération impossible");
			}else {
				
			this.resultat=oldTabPlaques.get(iIndexPlaque1)/oldTabPlaques.get(iIndexPlaque2);
			}
			break;
			default:
				break;
		}
		if(this.resultat<0) {
			this.calculOK=false;
			throw new IllegalArgumentException("Opération impossible");
			
		}
		else {
			this.calculOK=true;
		}
		}
			
	}
	public boolean calculOK() {
		return this.calculOK;
	}
	public String operationString() {
		return new String(this.listePlaques.get(iIndexPremierePlaque)+" "+this.sTabOperations[this.idOp]+this.listePlaques.get(iIndexSecondePlaque)+" = "+this.resultat);
	}
	public void initialisation() {
		Random r= new Random();
		for(int i=0;i<Etape.NBPLAQUES;i++) {
			int iNbAlea= r.nextInt(this.listNombreDispo.size());
			if(!this.listNombreDispo.isEmpty()) {
				this.listePlaques.add(this.listNombreDispo.get(iNbAlea));
				this.listNombreDispo.remove(iNbAlea);
			}
		}
	}
	public List<Integer> plaquesSuivante() {
		this.listePlaques.remove(iIndexPremierePlaque);
		this.listePlaques.remove(iIndexSecondePlaque);
		this.listePlaques.add(this.resultat);
		return this.listePlaques;
	}
	
	public void affichePlaquesRestantes() {
		for(int i : this.listePlaques) {
			System.out.print(i+" ");
		}
	}

	public List<Integer> getlistePlaques() {
		return listePlaques;
	}

	public void setlistePlaques(List<Integer> listePlaques) {
		this.listePlaques = listePlaques;
	}

	public boolean isCalculOK() {
		return calculOK;
	}

	public void setCalculOK(boolean calculOK) {
		this.calculOK = calculOK;
	}

	public int getResultat() {
		return resultat;
	}

	public void setResultat(int resultat) {
		this.resultat = resultat;
	}

	public int getiIndexPremierePlaque() {
		return iIndexPremierePlaque;
	}

	public void setiIndexPremierePlaque(int iIndexPremierePlaque) {
		this.iIndexPremierePlaque = iIndexPremierePlaque;
	}

	public int getiIndexSecondePlaque() {
		return iIndexSecondePlaque;
	}

	public void setiIndexSecondePlaque(int iIndexSecondePlaque) {
		this.iIndexSecondePlaque = iIndexSecondePlaque;
	}

	public int getIdOp() {
		return idOp;
	}

	public void setIdOp(int idOp) {
		this.idOp = idOp;
	}
	
	
}
