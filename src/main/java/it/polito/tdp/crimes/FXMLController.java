/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Model;
import it.polito.tdp.crimes.model.vicino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	txtResult.clear();
    	if(this.boxAnno.getValue()!= null) {
    		txtResult.appendText(model.creaGrafo(this.boxAnno.getValue()));
  
    		this.boxMese.setDisable(false);
        	this.boxGiorno.setDisable(false);
        	this.btnSimula.setDisable(false);
        	
        	for (Adiacenza a: model.getVertici(this.boxAnno.getValue())) {
    			for (vicino v: model.getVicini(a)) {
    				txtResult.appendText("\n"+a+v);
    			}
    		}
        	this.boxMese.getItems().addAll(model.getMese());
        	this.boxGiorno.getItems().addAll(model.getMese());
        	
    	}
    	else {
    		txtResult.appendText("Selezionare l'anno correttamente ...");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	Integer anno, mese, giorno, N = null;
    	
    	try {
    		 N= Integer.parseInt(this.txtN.getText());
    		
    	}
    	catch(NumberFormatException e) {
    		this.txtResult.clear();
    		txtResult.appendText("Formato N non corretto\n");
    		e.printStackTrace();
		}
    	
    	if(N<1 || N>10) {
        	this.txtResult.clear();
    		txtResult.appendText("N deve essere compreso tra 1 e 10\n");
    		return;
    	}
    	anno = boxAnno.getValue();
    	mese = boxMese.getValue();
    	giorno = boxGiorno.getValue();
    	
    	if(anno == null || mese == null || giorno == null) {
        	this.txtResult.clear();
    		txtResult.appendText("Seleziona tutti i campi!\n");
    		return;
    	}
    	
    	try {
    		LocalDate.of(anno, mese, giorno);
    	} catch (DateTimeException e) {
        	this.txtResult.clear();
    		txtResult.appendText("Data non corretta\n");
    	}
    	
    	txtResult.appendText("Simulo con " + N + " agenti");
    	txtResult.appendText("\nCRIMINI MAL GESTITI: " + this.model.simula(anno, mese, giorno, N));
    	
    }
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxAnno.getItems().addAll(model.getAnno()); 
    	this.boxMese.setDisable(true);
    	this.boxGiorno.setDisable(true);
    	this.btnSimula.setDisable(true);
    }
}
