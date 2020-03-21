package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import Modele.Modele;
import Modele.Score;
import Modele.GereScores;
import Modele.Etape;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller {
	private int iTempsMinutes;
    private int iTempsSecondes;
    private Timeline timeline;
    private List<Integer> listPlaques;
    private Integer iIndex1;
    private Integer iIndex2;
    private String sOperation;
    private int compteurTour=0;
    private List<String> lignes= new LinkedList<String>();
	private Modele m;
	@FXML
	Button boutonScore;
	@FXML
	HBox hboxPlaques;
	@FXML
	TextField pseudo;
	@FXML
	Button boutonJouer;
	@FXML
	Button boutonValider;
	@FXML
	Button boutonAnnuler;
	@FXML
	Button boutonProposer;
	@FXML
	Button boutonSupprimer;
	@FXML
	SplitPane coeurJeu;
	@FXML
	SplitPane chrono;
	@FXML
	Text nbATrouver;
	@FXML
	Text heure;
	@FXML
	TextArea textOperation;
	@FXML
	Label textChrono;
	@FXML
	Text textCalcul;
	@FXML
	public void boutonJouer() {
		
		if(pseudo.toString().length()>=3 || pseudo.toString().length()<=8) {
		m.preparer(pseudo.getText());
		coeurJeu.setDisable(false);
        chrono.setDisable(false);
        startTimer();    
        creationPlaques();
		}
		else {
			Alert bteDialog = new Alert(AlertType.WARNING);
			bteDialog.setTitle("WARNING");
			bteDialog.setHeaderText(null);
			bteDialog.setContentText("Le pseudo doit contenir entre 3 et 8 caractères.");
			bteDialog.showAndWait();
		}
	}
	@FXML
	private void setClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            heure.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }),
            new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
	@FXML
    private void initialize() {
		this.m=new Modele();
        coeurJeu.setDisable(true);
        chrono.setDisable(true);
        nbATrouver.setText(Integer.toString(m.getiNombreCible()));
        pseudo.setEditable(true);
        setClock();
        setTimer();
      
	}
	@SuppressWarnings("unchecked")
	@FXML
	private void boutonAction(ActionEvent e) {
		String sNomBtn=((Button) e.getSource()).getText();
		switch(sNomBtn) {
		case "Annuler":
			if(this.iIndex1!=null && this.iIndex2!=null && this.sOperation!=null) {
			m.annuler();
			cleanVariable();
			this.textCalcul.setText("");
			creationPlaques();
			if(this.lignes.size()>1) {
			this.lignes.remove(this.lignes.size()-1);
			}
			this.textOperation.clear();
			this.textOperation.appendText(this.lignes.toString());
			}else {
				errorPopUp();
			}
			
			break;
		case "Valider":
			this.m.valider();
			creationPlaques();
			this.lignes.add(this.textCalcul.getText()+"\n");
			this.textOperation.clear();
			textOperation.appendText(this.lignes.toString());
			this.textCalcul.setText("");
			compteurTour+=1;
	    	 if(compteurTour==5) {
	    		 m.proposer(iTempsMinutes*60+this.iTempsSecondes);
	    		 finDeJeu();
	    	 }
			cleanVariable();
			break;
		case "Proposer":
			if(this.m.getListeEtape().size()!=1) {
			int temps=iTempsMinutes*60+this.iTempsSecondes;
			m.proposer(temps);
			finDeJeu();
			}
			else {
				errorPopUp();
			}
			break;
		case "Supprimer":
			m.supprimer();
			cleanVariable();
			creationPlaques();
			this.textCalcul.setText("");
			this.lignes.remove(this.lignes.size()-1);
			this.textOperation.clear();
			this.textOperation.appendText(this.lignes.toString());
			this.compteurTour-=1;

			break;
		default:
			break;
		}
	}
	
	private void setTimer() {
        iTempsSecondes = 0;
        iTempsMinutes = 3;
        textChrono.setText(String.format("%02d:%02d", iTempsMinutes, iTempsSecondes));
    }

    private void startTimer() {
        timeline = new Timeline();
       
        KeyFrame keyframe = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if(iTempsSecondes <= 0) {
                    iTempsMinutes--;
                    iTempsSecondes = 59;
                } else {
                    iTempsSecondes--;
                }
                if(iTempsMinutes == 0) {
                	textChrono.setTextFill(Color.RED);
                }
                if(iTempsMinutes == 0 && iTempsSecondes == 0) {
                	finDeJeu();
                    m.proposer(0);
                }
                textChrono.setText(String.format("%02d:%02d", iTempsMinutes, iTempsSecondes));
            }
        });
        textChrono.setTextFill(Color.BLACK);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyframe);
        timeline.play();
    }
    public void creationPlaques() {
    	
    	 this.listPlaques=this.m.getListeEtape().get(this.m.getListeEtape().size()-1).getlistePlaques();
    	 
    	if(!hboxPlaques.getChildren().isEmpty()) {
    		hboxPlaques.getChildren().clear();
    	}
    	for(int i = 0; i <listPlaques.size();i++) {
            Button b = new Button();
            b.setText(""+listPlaques.get(i));
            b.setScaleX(1.5);
            b.setScaleY(1.5);
            final int index = i;
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    actionPlaques(index);
                    b.setDisable(true);
                }
            });

            this.hboxPlaques.getChildren().add(b);
        }
    }
    public void actionPlaques(int index) {
    	if(this.iIndex1==null) {
      this.iIndex1=index; 
    	}
    	else if(this.iIndex1!=null && this.iIndex2==null && this.sOperation!=null) {
    		this.iIndex2=index;
    		this.m.jouer(this.iIndex1, this.iIndex2, this.sOperation);
    		this.textCalcul.setText(this.m.getListeEtape().get(this.m.getListeEtape().size()-1).operationString());
    	}
    }
    public void actionOperation(ActionEvent e) {
    	String sNomBtnOperation=((Button) e.getSource()).getText();
    	this.sOperation=sNomBtnOperation;
    	
    }
    public void cleanVariable() {
		this.iIndex1=null;
		this.iIndex2=null;
		this.sOperation=null;
    }
    @FXML
    public void afficheScores() throws IOException {
    	this.m.getG().export();
    	File htmlFile = new File("score.html");
    	Desktop.getDesktop().browse(htmlFile.toURI());
    	
    }
    public void finDeJeu() {
    	timeline.pause();
    	
    	Alert bteDialog = new Alert(AlertType.INFORMATION);
        bteDialog.setTitle("INFORMATION");
        bteDialog.setHeaderText(null);
        int resultat=this.m.getiNombreCible()-this.m.getListeEtape().get(this.m.getListeEtape().size()-1).getResultat();
        if(resultat<0) {
        	resultat=resultat*-1;
        }
        bteDialog.setContentText("Votre score est : "+resultat);
        bteDialog.showAndWait();
        initialize();
        this.lignes.clear();
        this.compteurTour=0;
        this.textOperation.clear();
        
    }
    public void errorPopUp() {
    	Alert bteDialog = new Alert(AlertType.WARNING);
		bteDialog.setTitle("WARNING");
		bteDialog.setHeaderText(null);
		bteDialog.setContentText("Vous devez faire un calcul pour réaliser cette action");
		bteDialog.showAndWait();
    }
}
