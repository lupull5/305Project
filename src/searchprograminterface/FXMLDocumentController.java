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
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author arnol
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Color x2;
    @FXML
    private Font x1;
    
    private void handleButtonAction(ActionEvent event) throws IOException {

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void searchButtonClicked(ActionEvent event) {
        
    }
    
    @FXML
    private void exitButtonClicked(ActionEvent event) throws IOException {
        Platform.exit();
    }
    
 }
