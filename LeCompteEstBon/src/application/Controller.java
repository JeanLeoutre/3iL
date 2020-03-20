package application;

import java.time.LocalTime;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Controller {
	private int iTempsMinutes;
    private int iTempsSecondes;
    private Timeline timeline;
    private List<Integer> listPlaques;
    private Integer iIndex1;
    private Integer iIndex2;
    private String sOperation;
	private Modele m=new Modele();
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
		
		if(pseudo.getText().length()>3 || pseudo.getText().length()<8) {
		m.preparer(pseudo.getText());
		coeurJeu.setDisable(false);
        chrono.setDisable(false);
        startTimer();
        this.listPlaques=this.m.getListeEtape().get(this.m.getListeEtape().size()-1).getlistePlaques();
        creationPlaques();
       
		}
		else {
			Alert bteDialog = new Alert(AlertType.INFORMATION);
			bteDialog.setTitle("Information");
			bteDialog.setHeaderText("Le pseudo n'est pas conforme");
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
        coeurJeu.setDisable(true);
        chrono.setDisable(true);
        nbATrouver.setText(Integer.toString(m.getiNombreCible()));
        pseudo.setEditable(true);
        setClock();
        setTimer();
      
	}
	@FXML
	private void boutonAction(ActionEvent e) {
		String sNomBtn=((Button) e.getSource()).getText();
		switch(sNomBtn) {
		case "Annuler":
			m.annuler();
			cleanVariable();
			break;
		case "Valider":
			m.valider();
			textOperation.appendText(this.textCalcul.getText()+"\n");
			this.textCalcul.setText("");
			creationPlaques();
			cleanVariable();
			break;
		case "Proposer":
			m.proposer();
			break;
		case "Supprimer":
			m.supprimer();
			creationPlaques();
			break;
		default:
			break;
		}
	}
	
	public void update() {
		
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
                    timeline.pause();
                    m.proposer();
                    update();
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
}
