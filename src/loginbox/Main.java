package loginbox;
	
import java.util.Optional;

import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.util.Pair;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

/**
 * Klasa g³ówna wyœwietlaj¹ca okno z nazw¹ u¿ytkownika i mo¿liwoœci¹ wywo³ania logowania
 * 
 * @author Piotr Muzyczuk / muzyczukp@outlook.com
 *  
 */
public class Main extends Application {
	
	private Button btnShowLoginWindow= new Button();;
	private Label lblLoggedIn = new Label();
	
	/**
	 * W metodzie <b>start</b> wyœwietlane jest okno z zarz¹dc¹ <b>VBox</b>, w którym znajduj¹ siê kontrolki:<br> 
	 * <ul><li><b>lblLoggedIn</b> typu Label pokazuj¹c¹ stan zalogowania (nazwa u¿ytkownika i œrodowisko jeœli zalogowany) lub informacja o braku zalogowanego u¿ytkownika,</li>
	 * <li><b>btnShowLoginWindow</b> typu Button umo¿liwiaj¹c¹ wywo³anie okna logowania</li></ul>
	 * @param primaryStage g³ówne okno aplikacji
	 */
	@Override
	public void start(Stage primaryStage) {

		lblLoggedIn.setText("Brak zalogowanego u¿ytkownika");
		btnShowLoginWindow.setText("Zaloguj siê");
		btnShowLoginWindow.setOnAction( e->showLoginWindow() );
		
		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setAlignment(Pos.CENTER);
		VBox.setMargin(btnShowLoginWindow,new Insets(10,10,10,10));
		VBox.setMargin(lblLoggedIn,new Insets(10,10,10,10));
		ObservableList<Node> vList = vBox.getChildren();
		vList.addAll(lblLoggedIn,btnShowLoginWindow);
		
		Scene scene = new Scene(vBox,400,200);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Bardzo przydatny system");
		primaryStage.centerOnScreen();
		primaryStage.show();
			
	}
	
	/**
	 * Konstruktor standardowy dla JavaFX
	 * @param args standardowe argumenty wywo³ania
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Metoda <b>showLoginWindow</b> odpowiada za wywo³anie okna logowania i odebranie z niego danych. Nastêpnie wprowadza nazwê zalogowanego u¿ytkownika oraz œrodowisko do kontrolki lblLoggedIn, gdy logowanie przebiegnie pomyœlnie lub wyœwietla komunikat o b³êdzie logowania w razie niepowodzenia w formie Alertu.
	 */
	public void showLoginWindow() {
		
		lblLoggedIn.setText("Brak zalogowanego u¿ytkownika");
		Optional<Pair<Environment,String>> result = ( new LogonDialog("Logowanie","Logowanie do systemu TestME") ).showAndWait();
		if(result.isPresent()) {
			result.ifPresent(val -> {
				lblLoggedIn.setText("Zalogowano do œrodowiska "+val.getKey().getName()+" jako "+val.getValue());
			});
			
		}
		else {
			Alert invalidLogin = new Alert(AlertType.WARNING);
			invalidLogin.initStyle(StageStyle.UTILITY);
			invalidLogin.setHeaderText("Uwaga!");
			invalidLogin.setContentText("Logowanie nie powiod³o siê");
			invalidLogin.showAndWait();
		}
		
	}
}
