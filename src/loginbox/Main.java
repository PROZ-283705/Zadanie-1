package loginbox;
	
import java.util.Optional;

import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.util.Pair;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;


public class Main extends Application {
	
	Button btnShowLoginWindow;
	Label lblLoggedIn = new Label();
	
	@Override
	public void start(Stage primaryStage) {

		lblLoggedIn.setText("Brak zalogowanego u¿ytkownika");
		btnShowLoginWindow = new Button();
		btnShowLoginWindow.setText("Zaloguj siê");
		btnShowLoginWindow.setOnAction( e->showLoginWindow() );
		
		VBox vBox = new VBox();
		vBox.setSpacing(10);
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
	
	public static void main(String[] args) {
		launch(args);
	}
	
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
