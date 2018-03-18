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


public class LogonDialog {
	
	Dialog <Pair<Environment,String>> dialog = new Dialog<>();
	Users users = new Users();
	
	ChoiceBox<Environment> chbxEnv = new ChoiceBox<Environment>();
	ComboBox<String> cmbUser = new ComboBox<String>();
	PasswordField pfieldPass = new PasswordField();
	ButtonType buttonTypeOk = new ButtonType("Logon",ButtonData.OK_DONE);
	
	LogonDialog(String title,String header){
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		
		ImageView imageView = new ImageView();
		Image image = new Image(getClass().getResourceAsStream("images/padlock.png"));
		imageView.setImage(image);
		dialog.setGraphic(imageView);
		
		dialog.setResizable(true);
		
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
	
	public Optional<Pair<Environment, String>> showAndWait() {
		return dialog.showAndWait();
	}
	
	private void toggleLogonButton() {
		if(cmbUser.getValue() == null || pfieldPass.getText() == null || cmbUser.getValue().isEmpty() || pfieldPass.getText().trim().isEmpty())
			dialog.getDialogPane().lookupButton(buttonTypeOk).setDisable(true);
		else
			dialog.getDialogPane().lookupButton(buttonTypeOk).setDisable(false);
	}
	
	private void changeEnvironment(Environment newVal) {
		cmbUser.getSelectionModel().clearSelection();
		cmbUser.setItems(users.getUsersForEnv(newVal.getName()));
		pfieldPass.setText("");
	}

}
