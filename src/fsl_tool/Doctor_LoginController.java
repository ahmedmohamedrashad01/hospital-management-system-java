package fsl_tool;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Doctor_LoginController implements Initializable {

    public static String user = "";
    int messageCount = 0;
    public static String name = "";

    //////////////////////////////////
    
    
    
     @FXML
    private FontAwesomeIcon iLogout;

    @FXML
    private FontAwesomeIcon iHome;

    DropShadow dHome = new DropShadow(15, Color.BLUE);
    DropShadow dLogout = new DropShadow(15, Color.RED);
    
       @FXML
    void iHomeClick(MouseEvent event) {
          try {
           // Doctor_MessageController.user = user;
            Parent p = FXMLLoader.load(getClass().getResource("Login.fxml"));
            root.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(Doctor_LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
      @FXML
    void iLogOuClick(MouseEvent event) {
        System.exit(0);
    }
    
   
    @FXML
    void iHomeAction(MouseEvent event) {
        iHome.setEffect(dHome);
    }

      @FXML
    void iLogoutAction(MouseEvent event) {
        iLogout.setEffect(dLogout);
    }

    @FXML
    void iHomeExitAction(MouseEvent event) {
        iHome.setEffect(null);
    }

    @FXML
    void iLogoutExitAction(MouseEvent event) {
        iLogout.setEffect(null);
    }
  
    //////////////////////////////////////////////////////
    
    

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnViewProfile;

    @FXML
    private JFXButton btnCreateSickLeave;

    @FXML
    private JFXButton btnViewStatisticalSheet;

      @FXML
    private FontAwesomeIcon messageIcon;
    
    @FXML
    private Label lblMessageCount;
    
    @FXML
    private JFXButton btnVieMessage;

       @FXML
    void btnVieMessageAction(ActionEvent event) {
          try {
            Doctor_MessageController.user = user;
            Parent p = FXMLLoader.load(getClass().getResource("Doctor_Message.fxml"));
            root.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(Doctor_LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnExit;

    @FXML
    void btnBackAction(ActionEvent event) {
        try {

            Parent p = FXMLLoader.load(getClass().getResource("Login.fxml"));
            root.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(Doctor_LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void btnCreateSickLeaveAction(ActionEvent event) {
        try {
            SickLeaveFormController.user = user;
            Parent p = FXMLLoader.load(getClass().getResource("SickLeaveForm.fxml"));
            root.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(Doctor_LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void btnExitAction(ActionEvent event) {
        System.exit(0);
    }

    

    @FXML
    void btnViewProfileAction(ActionEvent event) {
        try {
            Doctor_View_ProfileController.user = user;
            Parent p = FXMLLoader.load(getClass().getResource("Doctor_View_Profile.fxml"));
            root.getChildren().setAll(p);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void btnViewStatisticalSheetAction(ActionEvent event) {
        try {
            Doctor_Statistical_SheetsController.user = user;
            Parent p = FXMLLoader.load(getClass().getResource("Doctor_Statistical_Sheets.fxml"));
            root.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(Doctor_LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /////////////////////////////
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(user);
        getName();
        msgCount();
        
        if(messageCount != 0){
            DropShadow iconColor = new DropShadow(18, Color.RED);
            messageIcon.setEffect(iconColor);
        }else{
            lblMessageCount.setVisible(false);
        }

    }
    
    //// check message count ////
    public void msgCount(){
        try{
             DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(receiver) as TOTAL FROM notification Where receiver = '"+name+"'");
            ResultSet r = pst.executeQuery();
            if(r.next()){
                messageCount += r.getInt("TOTAL");
            }
            lblMessageCount.setText(String.valueOf(messageCount));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    //////////  select name ///////////
    public void getName(){
        try{
            DB  d = new DB();
            PreparedStatement pst = d.con.prepareStatement("select * from employee where employeeCode = '"+user+"'");
            ResultSet r = pst.executeQuery();
            while(r.next()){
                name = r.getString("Name");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}
