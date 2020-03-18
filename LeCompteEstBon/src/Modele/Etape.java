package Modele;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Etape {
	final static int NBPLAQUES=6;
	final static int NBOPERATION=4;
	private List<Integer> iTabPlaques=new LinkedList<>();
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
			if(this.sTabOperations[i]==sOperation) {
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
		return new String(this.iTabPlaques.get(iIndexPremierePlaque)+" "+this.sTabOperations[this.idOp]+this.iTabPlaques.get(iIndexSecondePlaque)+" = "+this.resultat);
	}
	public void initialisation() {
		Random r= new Random();
		for(int i=0;i<Etape.NBPLAQUES;i++) {
			int iNbAlea= r.nextInt(this.listNombreDispo.size());
			if(!this.listNombreDispo.isEmpty()) {
				System.out.println(this.listNombreDispo.get(iNbAlea));
				this.iTabPlaques.add(this.listNombreDispo.get(iNbAlea));
				this.listNombreDispo.remove(iNbAlea);
			}
		}
	}
	public List<Integer> plaquesSuivante() {
		this.iTabPlaques.remove(iIndexPremierePlaque);
		this.iTabPlaques.remove(iIndexSecondePlaque);
		this.iTabPlaques.add(this.resultat);
		return this.iTabPlaques;
	}
	/**
	public void choixPlaques() {
		Scanner scan=new Scanner(System.in);
		boolean bNotEnd=false;
		
		System.out.println("Veuillez choisir une première plaque parmis les suivantes");
		affichePlaquesRestantes();
		iIndexPremierePlaque= scan.nextInt();
		int iPremierePlaque=this.iTabPlaques.get(iIndexPremierePlaque);
		this.iTabPlaques.remove(iIndexPremierePlaque);
		System.out.println("Veuillez choisir une seconde plaque parmis les suivantes");
		affichePlaquesRestantes();
		iIndexSecondePlaque= scan.nextInt();
		int iSecondePlaque=this.iTabPlaques.get(iIndexSecondePlaque);
		this.iTabPlaques.remove(iIndexSecondePlaque);	
		
	}**/
	public void affichePlaquesRestantes() {
		for(int i : this.iTabPlaques) {
			System.out.print(i+" ");
		}
	}
}
