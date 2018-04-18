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
 * Klasa opisuj�ca okienko logowania
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
	 * Konstruktor tworzy customowy dialog z zarz�dc� typu GridPane i kilkalnymi kontrolkami:
	 * <ul><li><b>chbxEnv</b> typu ChoiceBox s�u�aca do wyboru �rodkowiska do zalogowania,</li>
	 * <li><b>cmbUser</b> typu ComboBox s�u��ca do wyboru nazwy u�ytkownika z listy lub wpisania jej dzi�ki ustawieniu atrybutu <i>editable</i> na true,</li>
	 * <li><b>pfieldPass</b> typu PasswordField s�u��ca do wpisania has�a.</li></ul>
	 * <p>Dodatkowo w dialogu znajduj� si� przyciski s�u��ce do potwierdzenia lub zaniechania logowania.</p>
	 * <p>Dzi�ki dodaniu do dialogu <b>setResultContverter</b>-a zwraca on <i>par� �rodowisko, u�ytkownik</i> w przypadku poprawnie podanych po�wiadcze� lub <i>null</i> w przypadku b��dnych.</p>
	 * <p>Dodano tak�e listenery do zmian warto�ci wy�ej wymienionych kontrolek, aby aktualizowa� list� u�ytkownik�w po zmianie �rodowiska oraz aby w��czy� przycisk logowania jedynie, gdy nazwa u�ytkownika i has�o b�d� zawiera�y jakie� warto�ci.</p>
	 * @param title tytu� wy�wietlany na g�rnej belce okna
	 * @param header tekst wy�wietlany w g�rnej cz�ci okna
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
	 * Metoda <b>toggleLogonButton</b> odpowiada za w��czenie lub wy��czenie przycisku logowania. W��cza go, gdy nazwa u�ytkownika i has�o s� niepuste, a wy��cza gdy s� puste.
	 */
	private void toggleLogonButton() {
		if(cmbUser.getValue() == null || pfieldPass.getText() == null || cmbUser.getValue().isEmpty() || pfieldPass.getText().trim().isEmpty())
			dialog.getDialogPane().lookupButton(buttonTypeOk).setDisable(true);
		else
			dialog.getDialogPane().lookupButton(buttonTypeOk).setDisable(false);
	}
	
	/**
	 * Metoda <b>changeEnvironment</b> odpowiada za zmian� listy u�ytkownik�w w kontrolce <b>cmbUser</b>, aby zawiera�a u�ytkownik�w skojarzonych z tym �rodowiskiem. Czy�ci tak�e pole has�a.
	 * @param newVal nowo wybrane �rodowisko
	 */
	private void changeEnvironment(Environment newVal) {
		cmbUser.getSelectionModel().clearSelection();
		cmbUser.setItems(users.getUsersForEnv(newVal.getName()));
		pfieldPass.setText("");
	}
	
	/**
	 * Metoda <b>showAndWait</b> wywo�uje dialog i zwraca jego dane wy�ciowe
	 * @return dane wyj�ciowe customowego dialogu logowania
	 */
	public Optional<Pair<Environment, String>> showAndWait() {
		return dialog.showAndWait();
	}

}
