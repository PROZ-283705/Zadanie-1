package loginbox;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Klasa główna wyświetlająca okno z nazwą użytkownika i możliwością wywołania logowania
 * 
 * @author Piotr Muzyczuk / muzyczukp@outlook.com
 *  
 */
public class Main extends Application {
	
	
	/**
	 * W metodzie <b>start</b> tworzona jest klasa reprezentująca okienko używane do wywoływania mechanizmu logowania, a następnie rzeczone okienko jest wyświetlane.
	 * @param primaryStage standardowy parametr typu Stage dla JavaFX
	 */
	@Override
	public void start(Stage primaryStage) {

		FirstWindow first = new FirstWindow();
		first.show();
	}
	
	/**
	 * Konstruktor standardowy dla JavaFX
	 * @param args standardowe argumenty wywo�ania
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
