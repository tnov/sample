package jp.dip.fission.SampleRestApplication.tools;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class BlindBrowserMain extends Application {

	private static String GMAIL_LOGIN = "https://accounts.google.com/ServiceLogin?service=mail#identifier";

	@Override
	public void init() throws Exception {
		super.init();
		System.out.println("init");
	}

	@Override
	public void stop() throws Exception {
		System.out.println("stop");
		super.stop();
	}

	public static void main(String[] args) {
		System.out.println("main s");
		Application.launch(args);
		System.out.println("main e");
	}

	@Override
	public void start(Stage stage) throws Exception {
		System.out.println("start");
		WebView webView = new WebView();
		stage.setScene(new Scene(webView));
		WebEngine engine = webView.getEngine();
		engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				if (newValue == State.SUCCEEDED) {
					engine.getDocument().getElementById("Email").setNodeValue("test");
				}
			}
		});
		engine.load(GMAIL_LOGIN);
		Platform.exit();
	}

}
