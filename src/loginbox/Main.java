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
 * Klasa g��wna wy�wietlaj�ca okno z nazw� u�ytkownika i mo�liwo�ci� wywo�ania logowania
 * 
 * @author Piotr Muzyczuk / muzyczukp@outlook.com
 *  
 */
public class Main extends Application {
	
	private Button btnShowLoginWindow= new Button();;
	private Label lblLoggedIn = new Label();
	
	/**
	 * W metodzie <b>start</b> wy�wietlane jest okno z zarz�dc� <b>VBox</b>, w kt�rym znajduj� si� kontrolki:<br> 
	 * <ul><li><b>lblLoggedIn</b> typu Label pokazuj�c� stan zalogowania (nazwa u�ytkownika i �rodowisko je�li zalogowany) lub informacja o braku zalogowanego u�ytkownika,</li>
	 * <li><b>btnShowLoginWindow</b> typu Button umo�liwiaj�c� wywo�anie okna logowania</li></ul>
	 * @param primaryStage g��wne okno aplikacji
	 */
	@Override
	public void start(Stage primaryStage) {

		lblLoggedIn.setText("Brak zalogowanego u�ytkownika");
		btnShowLoginWindow.setText("Zaloguj si�");
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
	 * @param args standardowe argumenty wywo�ania
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Metoda <b>showLoginWindow</b> odpowiada za wywo�anie okna logowania i odebranie z niego danych. Nast�pnie wprowadza nazw� zalogowanego u�ytkownika oraz �rodowisko do kontrolki lblLoggedIn, gdy logowanie przebiegnie pomy�lnie lub wy�wietla komunikat o b��dzie logowania w razie niepowodzenia w formie Alertu.
	 */
	public void showLoginWindow() {
		
		lblLoggedIn.setText("Brak zalogowanego u�ytkownika");
		Optional<Pair<Environment,String>> result = ( new LogonDialog("Logowanie","Logowanie do systemu TestME") ).showAndWait();
		if(result.isPresent()) {
			result.ifPresent(val -> {
				lblLoggedIn.setText("Zalogowano do �rodowiska "+val.getKey().getName()+" jako "+val.getValue());
			});
			
		}
		else {
			Alert invalidLogin = new Alert(AlertType.WARNING);
			invalidLogin.initStyle(StageStyle.UTILITY);
			invalidLogin.setHeaderText("Uwaga!");
			invalidLogin.setContentText("Logowanie nie powiod�o si�");
			invalidLogin.showAndWait();
		}
		
	}
}
