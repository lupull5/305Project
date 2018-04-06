/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchprograminterface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author arnol
 */
public class FXMLHomePageController implements Initializable {
    private void handleButtonAction(ActionEvent event) throws IOException {


    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void startButtonClicked(ActionEvent event) throws IOException {
        System.out.println("Loading program...");
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }
    
    @FXML
    private void exitButtonClicked(ActionEvent event) throws IOException {
        Platform.exit();
    }

}
