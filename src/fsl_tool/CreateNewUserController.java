package fsl_tool;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.controlsfx.control.textfield.TextFields;

public class CreateNewUserController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtEmpCode;

    @FXML
    private JFXTextField txtName;

      @FXML
    private FontAwesomeIcon iLogout;

    @FXML
    private FontAwesomeIcon iHome;

    DropShadow dHome = new DropShadow(15, Color.BLUE);
    DropShadow dLogout = new DropShadow(15, Color.RED);
    
       @FXML
    void iHomeClick(MouseEvent event) {
          try {
          
            Parent p = FXMLLoader.load(getClass().getResource("Admin_Login.fxml"));
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
    private JFXButton btnSave;

   

    @FXML
    void btnSaveAction(ActionEvent event) {
        String pass = "111111";
        try {

            if (txtEmpCode.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter employee code").showAndWait();
            } else if (!txtEmpCode.getText().matches("[D|E|M]{1}(\\d{4})")) {
                new Alert(Alert.AlertType.ERROR, "please enter employee code correctly").showAndWait();

            } else if (txtName.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter employee name").showAndWait();
            } else if (txtAuto.getText().trim().isEmpty()) {

                new Alert(Alert.AlertType.ERROR, "please enter department").showAndWait();
            } else {
                DB d = new DB();
                PreparedStatement pst = d.con.prepareStatement("SELECT employeeCode FROM employee WHERE employeeCode = '" + txtEmpCode.getText().trim() + "'");
                ResultSet r = pst.executeQuery();
                if (!r.next()) {

                    String query2 = "insert into employee (employeeCode,Name,Department) values ('" + txtEmpCode.getText().trim() + "','" + txtName.getText().trim() + "','" + txtAuto.getText().trim() + "')";
                    d.stmt.executeUpdate(query2);

                    String query3 = "insert into user_account (employeeCode,Password) values ('" + txtEmpCode.getText().trim() + "','" + pass + "')";
                    d.stmt.executeUpdate(query3);

                    new Alert(Alert.AlertType.INFORMATION, "user account has been created").showAndWait();
                    Parent p = FXMLLoader.load(getClass().getResource("Admin_Login.fxml"));
                    root.getChildren().setAll(p);
                } else {
                    new Alert(Alert.AlertType.ERROR, "employee code already exist!!!").showAndWait();
                }

                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    
    
      @FXML
    private JFXTextField txtAuto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        displayDPTFromSql();
        
    }

    public void displayDPTFromSql() {
        ArrayList<String> a = new ArrayList<>();
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT * FROM employee");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                a.add(r.getString("Department"));

            }

            HashSet<String> set = new HashSet<>(a);
            a.clear();
            a.addAll(set);
            TextFields.bindAutoCompletion(txtAuto, a);
           

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
    }

}
