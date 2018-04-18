package loginbox;

import javafx.util.Pair;
import java.util.Optional;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;

/**
 * Klasa opisująca okienko logowania
 * @author muzyc
 *
 */
public class LogonDialog {
	
	private Dialog <Pair<Environment,String>> dialog = new Dialog<>();
	private Users users = new Users();
	
	private ChoiceBox<Environment> chbxEnv = new ChoiceBox<Environment>();
	private ComboBox<String> cmbUser = new ComboBox<String>();
	private PasswordField pfieldPass = new PasswordField();
	private ButtonType buttonTypeOk = new ButtonType("Logon",ButtonData.OK_DONE);
	
	/**
	 * Konstruktor tworzy customowy dialog z zarządcą typu GridPane i kilkalnymi kontrolkami:
	 * <ul><li><b>chbxEnv</b> typu ChoiceBox służaca do wyboru środkowiska do zalogowania,</li>
	 * <li><b>cmbUser</b> typu ComboBox służąca do wyboru nazwy użytkownika z listy lub wpisania jej dzięki ustawieniu atrybutu <i>editable</i> na true,</li>
	 * <li><b>pfieldPass</b> typu PasswordField służąca do wpisania hasła.</li></ul>
	 * <p>Dodatkowo w dialogu znajdują się przyciski służące do potwierdzenia lub zaniechania logowania.</p>
	 * <p>Dzięki dodaniu do dialogu <b>setResultContverter</b>-a zwraca on <i>parę środowisko, użytkownik</i> w przypadku poprawnie podanych poświadczeń lub <i>null</i> w przypadku błędnych.</p>
	 * <p>Dodano także listenery do zmian wartości wyżej wymienionych kontrolek, aby aktualizować listę użytkowników po zmianie środowiska oraz aby włączyć przycisk logowania jedynie, gdy nazwa użytkownika i hasło będą zawierały jakieś wartości.</p>
	 * @param title tytuł wyświetlany na górnej belce okna
	 * @param header tekst wyświetlany w górnej części okna
	 */
	public LogonDialog(String title,String header){
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		
		ImageView imageView = new ImageView();
		Image image = new Image("images/padlock.png");
		imageView.setImage(image);
		dialog.setGraphic(imageView);
		
		Label lblEnv = new Label("Środowisko:");
		Label lblUser = new Label("Użytkownik:");
		Label lblPass = new Label("Hasło:");
		chbxEnv.setItems(FXCollections.observableArrayList(new Environment("Deweloperskie"),new Environment("Testowe"),new Environment("Produkcyjne")));
		chbxEnv.getSelectionModel().selectFirst();
		cmbUser.setItems(users.getUsersForEnv("Deweloperskie"));
		cmbUser.setEditable(true);
		
		GridPane grid = new GridPane();
		grid.add(lblEnv, 1, 1);
		grid.add(chbxEnv, 2, 1);
		
		grid.add(lblUser, 1, 2);
		grid.add(cmbUser, 2, 2);
		
		grid.add(lblPass, 1, 3);
		grid.add(pfieldPass, 2, 3);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20,10,20,10));
		dialog.getDialogPane().setContent(grid);
		
		
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		dialog.getDialogPane().lookupButton(buttonTypeOk).setDisable(true);
		ButtonType buttonTypeCancel = new ButtonType("Anuluj",ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
		
		dialog.setResultConverter(dialogButton -> {
			if(dialogButton == buttonTypeOk) {
				if(users.isPassCorrect(chbxEnv.getValue().getName(), cmbUser.getValue(), pfieldPass.getText()))
					return new Pair<Environment,String>(chbxEnv.getValue(),cmbUser.getValue());
			}
			return null;
		});
		
		cmbUser.valueProperty().addListener((observable,oldVal,newVal) -> toggleLogonButton());
		pfieldPass.textProperty().addListener((observable,oldVal,newVal) -> toggleLogonButton());
		chbxEnv.valueProperty().addListener((observable,oldVal,newVal) -> changeEnvironment(newVal));
	}
	
	/**
	 * Metoda <b>toggleLogonButton</b> odpowiada za włączenie lub wyłączenie przycisku logowania. Włącza go, gdy nazwa użytkownika i hasło są niepuste, a wyłącza gdy są puste.
	 */
	private void toggleLogonButton() {
		if(cmbUser.getValue() == null || pfieldPass.getText() == null || cmbUser.getValue().isEmpty() || pfieldPass.getText().trim().isEmpty())
			dialog.getDialogPane().lookupButton(buttonTypeOk).setDisable(true);
		else
			dialog.getDialogPane().lookupButton(buttonTypeOk).setDisable(false);
	}
	
	/**
	 * Metoda <b>changeEnvironment</b> odpowiada za zmianę listy użytkowników w kontrolce <b>cmbUser</b>, aby zawierała użytkowników skojarzonych z tym środowiskiem. Czyści także pole hasła.
	 * @param newVal nowo wybrane środowisko
	 */
	private void changeEnvironment(Environment newVal) {
		cmbUser.getSelectionModel().clearSelection();
		cmbUser.setItems(users.getUsersForEnv(newVal.getName()));
		pfieldPass.setText("");
	}
	
	/**
	 * Metoda <b>showAndWait</b> wywołuje dialog i zwraca jego dane wyjściowe
	 * @return dane wyjściowe customowego dialogu logowania
	 */
	public Optional<Pair<Environment, String>> showAndWait() {
		return dialog.showAndWait();
	}

}
