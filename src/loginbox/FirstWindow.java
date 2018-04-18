package loginbox;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;


/**
 * Klasa opisująca okno, które informuje o statusie zalogowania i umożliwia wywołanie okna logowania
 * 
 * @author Piotr Muzyczuk / muzyczukp@outlook.com
 *
 */
public class FirstWindow {
	
	private Button btnShowLoginWindow= new Button();
	private Label lblLoggedIn = new Label();
	private Stage stage = new Stage();
	
	/**
	 * W <b>konstruktorze</b> tworzone jest okno z zarządcą <b>VBox</b>, w którym znajdują się kontrolki:<br> 
	 * <ul><li><b>lblLoggedIn</b> typu Label pokazująca stan zalogowania (nazwa użytkownika i środowisko jeśli zalogowany) lub informacja o braku zalogowanego użytkownika,</li>
	 * <li><b>btnShowLoginWindow</b> typu Button umożliwiająca wywołanie okna logowania</li></ul>
	 */
	public FirstWindow()
	{
		lblLoggedIn.setText("Brak zalogowanego użytkownika");
		btnShowLoginWindow.setText("Zaloguj się");
		btnShowLoginWindow.setOnAction( e->showLoginWindow() );
		
		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setAlignment(Pos.CENTER);
		VBox.setMargin(btnShowLoginWindow,new Insets(10,10,10,10));
		VBox.setMargin(lblLoggedIn,new Insets(10,10,10,10));
		ObservableList<Node> vList = vBox.getChildren();
		vList.addAll(lblLoggedIn,btnShowLoginWindow);
		
		Scene scene = new Scene(vBox,400,200);
		
		stage.setScene(scene);
		stage.setTitle("Bardzo przydatny system");
		stage.centerOnScreen();
	}
	
	/**
	 * Funkcja odpowiada za wyświetlanie okna utworzonego w konstruktorze
	 */
	public void show()
	{
		stage.show();
	}
	
	/**
	 * Metoda <b>showLoginWindow</b> odpowiada za wywołanie okna logowania i odebranie z niego danych. Następnie wprowadza nazwę zalogowanego użytkownika oraz środowisko do kontrolki lblLoggedIn, gdy logowanie przebiegnie pomyślnie lub wyświetla komunikat o błędzie logowania w razie niepowodzenia w formie Alertu.
	 */
	public void showLoginWindow() {
		
		lblLoggedIn.setText("Brak zalogowanego użytkownika");
		Optional<Pair<Environment,String>> result = ( new LogonDialog("Logowanie","Logowanie do systemu TestME") ).showAndWait();
		if(result.isPresent()) {
			result.ifPresent(val -> {
				lblLoggedIn.setText("Zalogowano do środowiska "+val.getKey().getName()+" jako "+val.getValue());
			});
			
		}
		else {
			Alert invalidLogin = new Alert(AlertType.WARNING);
			invalidLogin.initStyle(StageStyle.UTILITY);
			invalidLogin.setHeaderText("Uwaga!");
			invalidLogin.setContentText("Logowanie nie powiodło się");
			invalidLogin.showAndWait();
		}
		
	}

}
