package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TextField data;
    @FXML
    private TextField error;
    @FXML
    private TextArea TextAreaPalindrom;
    @FXML
    private TextField recivied;
    @FXML
    ComboBox<String> parity;
    @FXML
    TextField error1;
    @FXML
    TextField error11;
    @FXML
    TextField error111;
    Hamming HamnigInstance;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        parity.getItems().addAll("ODD","EVEN");
    }
    @FXML
    void findTrans(){
        TextAreaPalindrom.clear();
        HamnigInstance=new Hamming(data.getText(),Hamming.Parity.valueOf(parity.getValue()));
        //HamnigInstance (01001101,even)
        int splt=Integer.parseInt(error1.getText());
        //splt=(4)
        //check HamnigInstance 8,16,etc
        if (HamnigInstance.isValid(data.getText())){
            recivied.setText(HamnigInstance.GetTransData());

            char [] data1=HamnigInstance.getData().toCharArray();
            int a=0;
            for (int i=0;i<data1.length/splt;i++){

                String hamp="";
                for(int j=0;j<splt;j++){
                    hamp+=data1[a];
                    a++;
                }
                HamnigInstance.setData(hamp);
                System.out.println(hamp);
                TextAreaPalindrom.appendText(HamnigInstance.GetTransData()+"  Error bit at ("+ HamnigInstance.getErrorBit(HamnigInstance.GetTransData(),HamnigInstance.getParity())  +")\n");
                for (int k=0;k<HamnigInstance.getPs().size();k++){
                    TextAreaPalindrom.appendText(HamnigInstance.getPs().get(k)+"\n");
                }
                System.out.println(HamnigInstance.isValidRecievedData(HamnigInstance.GetTransData()));
            }
            error.setText(String.valueOf(HamnigInstance.GetPerceOfTheCorrectErorSingnnnal()+"%"));
            error11.setText(String.valueOf(HamnigInstance.GetPercOffffCorrrrrErrrrorDoouubbleee()+"%"));

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Data");
            alert.setContentText("The data you entered is invalid");
            alert.showAndWait();
        }


    }
    @FXML
    void finde(){
        HamnigInstance=new Hamming(data.getText(),Hamming.Parity.valueOf(parity.getValue()));
        error111.setText(String.valueOf(HamnigInstance.getErrorBit(recivied.getText(), HamnigInstance.getParity())));
    }
}