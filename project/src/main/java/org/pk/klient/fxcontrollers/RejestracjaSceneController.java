package org.pk.klient.fxcontrollers;

import java.io.EOFException;
import java.io.IOException;

import org.pk.entity.Klient;
import org.pk.klient.util.ConnectionBox;

import com.password4j.BcryptFunction;
import com.password4j.Password;
import com.password4j.types.Bcrypt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RejestracjaSceneController {
	
	private Stage stage;
	private Scene scene;
	private Parent kontener;
	
	@FXML
	private TextField nameField;
	@FXML
	private TextField surnameField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField passwdField;
	@FXML
	private TextField cPasswdField;
	@FXML
	private Label infoLabel;
	
	public void cofnijDoLogowania(ActionEvent zdarzenie) throws IOException {
		kontener = FXMLLoader.load(getClass().getResource("/fxml_files/AppView.fxml"));
		stage = (Stage) ((Node)zdarzenie.getSource()).getScene().getWindow();
		scene = new Scene(kontener);
		stage.setScene(scene);
		stage.show();
	}
	
	public void wyczyscLabele() {
		nameField.clear();
		surnameField.clear();
		emailField.clear();
		passwdField.clear();
		cPasswdField.clear();
	}
	
	public void wykonajRejestracje(ActionEvent zdarzenie) throws IOException, ClassNotFoundException, InterruptedException {
		// Podstawowa walidacja
		if(!passwdField.getText().equals(cPasswdField.getText())) {
			infoLabel.setText("Password fields must match!");
			wyczyscLabele();
			return;
		}
		if(nameField.getText().isEmpty() || surnameField.getText().isEmpty() || emailField.getText().isEmpty()
		   || passwdField.getText().isEmpty() || cPasswdField.getText().isEmpty()) {
			infoLabel.setText("All fields must be filled!");
			wyczyscLabele();
			return;
		}
		if(!emailField.getText().contains("@") || !emailField.getText().contains(".")) {
			infoLabel.setText("Email must be valid!");
			wyczyscLabele();
			return;
		}
		// Walidacja, czy podany adres email istnieje w bazie
		
		ConnectionBox.getInstance().getDoSerwera().writeObject("pobierzEmail()");
		ConnectionBox.getInstance().getDoSerwera().writeObject(emailField.getText());
		ConnectionBox.getInstance().getDoSerwera().flush();
		
		String emailZSerwera="";
		
		try {
			emailZSerwera = (String)ConnectionBox.getInstance().getOdSerwera().readObject();
		}catch(Exception wyjatek) {
			System.out.println("Wyjatek podczas czytania emailu z bazy danych! - Register");
			wyjatek.printStackTrace();
		}
		
		
		if(emailZSerwera.equals(emailField.getText())) {
			infoLabel.setText("This email is already taken!");
			wyczyscLabele();
			return;
		}

		// Szyfrowanie zewnetrzna biblioteka
		BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.Y,10);
		String haslo = Password.hash(passwdField.getText()).with(bcrypt).getResult();
		/*
		System.out.println("TEST BCRYPT: "+haslo);
		System.out.println("CZY ODSZYFROWANIE SIE ZGADZA: " + Password.check(passwdField.getText(), haslo).with(bcrypt));
		*/
		// Operacja w bazie danych poprzez serwer
		Klient nowyKlient = new Klient(nameField.getText(),surnameField.getText(),emailField.getText(),haslo);
		ConnectionBox.getInstance().getDoSerwera().writeObject("stworzKlienta()");
		ConnectionBox.getInstance().getDoSerwera().writeObject(nowyKlient);
		ConnectionBox.getInstance().getDoSerwera().flush();
		
		// Stworzenie konta zakonczone sukcesem, przejscie na scene logowania i zaladowanie komunikatu
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml_files/AppView.fxml"));
		kontener = loader.load();
		
		LoginSceneController menuLogowania = loader.getController();
		menuLogowania.udanaRejestracja("Registration successful!");
		
		stage = (Stage) ((Node)zdarzenie.getSource()).getScene().getWindow();
		scene = new Scene(kontener);
		stage.setScene(scene);
		stage.show();
	}
	
}
