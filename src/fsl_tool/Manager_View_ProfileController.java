
package fsl_tool;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;


public class Manager_View_ProfileController implements Initializable {
 public static String user = "";

    
        @FXML
    private FontAwesomeIcon iLogout;

    @FXML
    private FontAwesomeIcon iHome;

    DropShadow dHome = new DropShadow(15, Color.BLUE);
    DropShadow dLogout = new DropShadow(15, Color.RED);
    
       @FXML
    void iHomeClick(MouseEvent event) {
          try {
           Manager_LoginController.user = user;
            Parent p = FXMLLoader.load(getClass().getResource("Manager_Login.fxml"));
            root.getChildren().setAll(p);
        } catch (IOException ex) {
              System.out.println(ex.getMessage()); 
        
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
    
    
    
    
    @FXML
    private AnchorPane root;

    @FXML
    private Label lblName;

    @FXML
    private JFXTextField txtEmpCode;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXTextField txtDPT;

    
    
  

    @FXML
    private FontAwesomeIcon eye;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    void btnSaveAction(ActionEvent event) {

        if (txtPassword.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "please enter your password").showAndWait();

        } else {

            try {
                DB d = new DB();
                String query = "UPDATE user_account SET Password = '" + txtPassword.getText().trim() + "' where employeeCode = '" + user + "'";

                d.stmt.executeUpdate(query);
                Manager_LoginController.user = user;
                new Alert(Alert.AlertType.INFORMATION, "password has been changed").showAndWait();
                Parent p = FXMLLoader.load(getClass().getResource("Manager_Login.fxml"));
                root.getChildren().setAll(p);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

    }

    @FXML
    void eyeClick(MouseEvent event) {

    }

    @FXML
    void eyeEnter(MouseEvent event) {

    }

    @FXML
    void eyeExit(MouseEvent event) {

    }

   

   

    @FXML
    void iLogOutClick(MouseEvent event) {

    }

   

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
          getNameFromSQL();
        displayEmployeeData();
    }

    public void getNameFromSQL() {
        try {
            DB d = new DB();
            java.sql.PreparedStatement pst = d.con.prepareStatement("select * from employee where employeeCode = '" + user + "'");
            ResultSet r = pst.executeQuery();
            if (r.next()) {
                lblName.setText("View Profile Of Doctor " + r.getString("Name"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void displayEmployeeData() {
        try {
            DB d = new DB();
            java.sql.PreparedStatement pst = d.con.prepareStatement("select * from employee where employeeCode = '" + user + "'");
            ResultSet r = pst.executeQuery();
            if (r.next()) {
                txtName.setText(r.getString("Name"));
                txtEmpCode.setText(r.getString("employeeCode"));
                txtDPT.setText(r.getString("Department"));
            }

            java.sql.PreparedStatement pst2 = d.con.prepareStatement("select * from user_account where employeeCode = '" + user + "'");
            ResultSet r2 = pst2.executeQuery();
            if (r2.next()) {
                txtPassword.setText(r2.getString("Password"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
       
    
}
