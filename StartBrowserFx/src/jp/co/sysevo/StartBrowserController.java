package jp.co.sysevo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;

public class StartBrowserController extends AnchorPane implements Initializable  {

	Stage stage = null;
	WebEngine engine;

	private static int SERVER_PORT = 10080;
	private static int SERVER_MAX_CONNECTING = 10;

	private boolean isActive = false;

	@FXML
	private Button start;
	@FXML
	private Button end;

	public StartBrowserController(Stage stage) {
		this.stage = stage;
		loadFxml();
	}

	private void loadFxml() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartBrowser.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void show() {
		Scene scene = stage.getScene();
		if (scene == null) {
        	scene = new Scene(this);
            stage.setScene(scene);
        } else {
        	stage.getScene().setRoot(this);
        }
		this.stage.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		this.stage.show();
	}

	@FXML
	private void start(ActionEvent event) {
		System.out.println("START SERVICE start");
		try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT, SERVER_MAX_CONNECTING)){
			serverSocket.setReuseAddress(true);
			serverSocket.setSoTimeout(1000);
			if (isActive) {
				System.out.println("ALREADY ACTIVE");
			} else {
				System.out.println("ACCEPT WAITING start");
				Socket socket = serverSocket.accept();
				System.out.println("ACCEPT WAITING end");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("START SERVICE end");
	}
	@FXML
	private void end(ActionEvent event) {
		System.out.println("STOP SERVICE start");
		isActive = false;
		System.out.println("STOP SERVICE end");
	}


//	@FXML
//	private void browse(ActionEvent event) {
//		System.out.println("xxx");
//		final String identifier = "https://accounts.google.com/ServiceLogin?service=mail#identifier";
//		final String password = "https://accounts.google.com/ServiceLogin?service=mail#password";
//		Platform.runLater(new Task<Void>() {
//
//			@Override
//			protected Void call() throws Exception {
//				System.out.println("yyy_a");
//				WebView view = new WebView();
//				engine = view.getEngine();
//				engine.load(identifier);
//				System.out.println("yyy_b");
//				return null;
//			}
//		});
//		System.out.println("zzz");
//	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
