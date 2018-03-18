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
 * Klasa opisuj¹ca okienko logowania
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
	 * Konstruktor tworzy customowy dialog z zarz¹dc¹ typu GridPane i kilkalnymi kontrolkami:
	 * <ul><li><b>chbxEnv</b> typu ChoiceBox s³u¿aca do wyboru œrodkowiska do zalogowania,</li>
	 * <li><b>cmbUser</b> typu ComboBox s³u¿¹ca do wyboru nazwy u¿ytkownika z listy lub wpisania jej dziêki ustawieniu atrybutu <i>editable</i> na true,</li>
	 * <li><b>pfieldPass</b> typu PasswordField s³u¿¹ca do wpisania has³a.</li></ul>
	 * <p>Dodatkowo w dialogu znajduj¹ siê przyciski s³u¿¹ce do potwierdzenia lub zaniechania logowania.</p>
	 * <p>Dziêki dodaniu do dialogu <b>setResultContverter</b>-a zwraca on <i>parê œrodowisko, u¿ytkownik</i> w przypadku poprawnie podanych poœwiadczeñ lub <i>null</i> w przypadku b³êdnych.</p>
	 * <p>Dodano tak¿e listenery do zmian wartoœci wy¿ej wymienionych kontrolek, aby aktualizowaæ listê u¿ytkowników po zmianie œrodowiska oraz aby w³¹czyæ przycisk logowania jedynie, gdy nazwa u¿ytkownika i has³o bêd¹ zawiera³y jakieœ wartoœci.</p>
	 * @param title tytu³ wyœwietlany na górnej belce okna
	 * @param header tekst wyœwietlany w górnej czêœci okna
	 */
	public LogonDialog(String title,String header){
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		
		ImageView imageView = new ImageView();
		Image image = new Image(getClass().getResourceAsStream("images/padlock.png"));
		imageView.setImage(image);
		dialog.setGraphic(imageView);
		
		Label lblEnv = new Label("Œrodowisko:");
		Label lblUser = new Label("U¿ytkownik:");
		Label lblPass = new Label("Has³o:");
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
	 * Metoda <b>toggleLogonButton</b> odpowiada za w³¹czenie lub wy³¹czenie przycisku logowania. W³¹cza go, gdy nazwa u¿ytkownika i has³o s¹ niepuste, a wy³¹cza gdy s¹ puste.
	 */
	private void toggleLogonButton() {
		if(cmbUser.getValue() == null || pfieldPass.getText() == null || cmbUser.getValue().isEmpty() || pfieldPass.getText().trim().isEmpty())
			dialog.getDialogPane().lookupButton(buttonTypeOk).setDisable(true);
		else
			dialog.getDialogPane().lookupButton(buttonTypeOk).setDisable(false);
	}
	
	/**
	 * Metoda <b>changeEnvironment</b> odpowiada za zmianê listy u¿ytkowników w kontrolce <b>cmbUser</b>, aby zawiera³a u¿ytkowników skojarzonych z tym œrodowiskiem. Czyœci tak¿e pole has³a.
	 * @param newVal nowo wybrane œrodowisko
	 */
	private void changeEnvironment(Environment newVal) {
		cmbUser.getSelectionModel().clearSelection();
		cmbUser.setItems(users.getUsersForEnv(newVal.getName()));
		pfieldPass.setText("");
	}
	
	/**
	 * Metoda <b>showAndWait</b> wywo³uje dialog i zwraca jego dane wyœciowe
	 * @return dane wyjœciowe customowego dialogu logowania
	 */
	public Optional<Pair<Environment, String>> showAndWait() {
		return dialog.showAndWait();
	}

}
