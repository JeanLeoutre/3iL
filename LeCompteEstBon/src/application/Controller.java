package application;

import java.time.LocalTime;

import Modele.Modele;
import Modele.Score;
import Modele.GereScores;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Controller {
	
	private Modele m=new Modele();
	@FXML
	TextField pseudo;
	@FXML
	Button boutonJouer;
	@FXML
	SplitPane coeurJeu;
	@FXML
	SplitPane chrono;
	@FXML
	Text nbATrouver;
	@FXML
	Text heure;
	
	@FXML
	public void boutonJouer() {
		
		if(pseudo.getText().length()>3 || pseudo.getText().length()<8) {
		m.preparer(pseudo.getText());
		
		}
		else {
			Alert bteDialog = new Alert(AlertType.INFORMATION);
			bteDialog.setTitle("Information");
			bteDialog.setHeaderText("Le pseudo n'est pas conforme");
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
    }
	
	public void update() {
		
	}
	
}
