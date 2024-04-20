package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLUtils {
	public static void loadFXML(String fxmlPath, Node sourceNode) throws IOException{
		//Create a method to manage switching pages with buttons
		FXMLLoader loader = new FXMLLoader(FXMLUtils.class.getResource(fxmlPath));
		Parent root = loader.load();
		
		Stage stage = (Stage) sourceNode.getScene().getWindow();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(FXMLUtils.class.getResource("View/application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}
