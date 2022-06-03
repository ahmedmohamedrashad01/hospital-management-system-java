package fsl_tool;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Admin_LoginController implements Initializable {

        @FXML
    private FontAwesomeIcon iLogout;

    @FXML
    private FontAwesomeIcon iHome;

    DropShadow dHome = new DropShadow(30, Color.BLUE);
    DropShadow dLogout = new DropShadow(30, Color.RED);
    
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
    
    
       @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnCrateUser;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnExit;

   

    @FXML
    private TableView<DisplayEmployees> myTable;

    @FXML
    private TableColumn<DisplayEmployees, Integer> colID;

    @FXML
    private TableColumn<DisplayEmployees, String> colCode;

    @FXML
    private TableColumn<DisplayEmployees, String> colName;

    @FXML
    private TableColumn<DisplayEmployees, String> colDPT;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private FontAwesomeIcon IconSearch;

    @FXML
    private JFXButton btnEdit;

    DropShadow SearchEntered = new DropShadow(70, Color.RED);

    @FXML
    void IconSearchEntered(MouseEvent event) {
        IconSearch.setEffect(SearchEntered);
    }

    @FXML
    void IconSearchExit(MouseEvent event) {
        IconSearch.setEffect(null);
    }

    @FXML
    void IconSearchAction(MouseEvent event) {

        try {
            if (txtSearch.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter employee code to start search").showAndWait();

            } else {
                myTable.getItems().clear();
                DB d = new DB();
                PreparedStatement pst = d.con.prepareStatement("select * from employee where employeeCode = '" + txtSearch.getText().trim() + "'");
                ResultSet r = pst.executeQuery();
                if (!r.next()) {
                    new Alert(Alert.AlertType.ERROR, "employee code does not exist").showAndWait();

                } else {
                    data.add(new DisplayEmployees(r.getInt("ID"), r.getString("employeeCode"), r.getString("Name"), r.getString("Department")));
                    colID.setCellValueFactory(new PropertyValueFactory<>("id"));
                    colCode.setCellValueFactory(new PropertyValueFactory<>("employeeCode"));
                    colName.setCellValueFactory(new PropertyValueFactory<>("name"));
                    colDPT.setCellValueFactory(new PropertyValueFactory<>("dpt"));

                    myTable.setItems(data);
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void btnBackAction(ActionEvent event) {

    }

    @FXML
    void btnCrateUserAction(ActionEvent event) {
         try {
            Parent p = FXMLLoader.load(getClass().getResource("CreateNewUser.fxml"));
            root.getChildren().setAll(p);
        } catch (IOException ex) {
             System.out.println(ex.getMessage());
        }
    }

    @FXML
    void btnDeleteAction(ActionEvent event) {
           try {
            int index = myTable.getSelectionModel().getSelectedIndex();
            if (index < 0) {
                new Alert(Alert.AlertType.ERROR, "please select row to delete it").showAndWait();

            } else {
                String employeeCode = myTable.getSelectionModel().getSelectedItem().getEmployeeCode();
                String name = myTable.getSelectionModel().getSelectedItem().getName();
                String dpt = myTable.getSelectionModel().getSelectedItem().getDpt();
                int i = myTable.getSelectionModel().getSelectedItem().getId();
                DB d = new DB();
                PreparedStatement pst = d.con.prepareStatement("delete from employee where id = '" + i + "'");
                pst.executeUpdate();
                PreparedStatement pst2 = d.con.prepareStatement("delete from user_account where employeeCode = '" + employeeCode + "'");
                pst2.executeUpdate();
                myTable.getItems().remove(index);
                new Alert(Alert.AlertType.INFORMATION, "user account has been deleted").showAndWait();

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void btnEditAction(ActionEvent event) {
            int index = myTable.getSelectionModel().getSelectedIndex();

        if (index < 0) {
            new Alert(Alert.AlertType.ERROR, "please select row to edit employee information").show();

        } else {
           
            Edit_UserController.idFromEmployee = myTable.getSelectionModel().getSelectedItem().getId();
            getUserAccount();
            Edit_UserController.employeeCodeGUI = myTable.getSelectionModel().getSelectedItem().getEmployeeCode();
            Edit_UserController.name = myTable.getSelectionModel().getSelectedItem().getName();
            Edit_UserController.dpt = myTable.getSelectionModel().getSelectedItem().getDpt();
          
            try {
                Parent p = FXMLLoader.load(getClass().getResource("Edit_User.fxml"));
                root.getChildren().setAll(p);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    void btnExitAction(ActionEvent event) {
       
    }
   
    
    
   
    
    @FXML
    void iLogOutClick(MouseEvent event) {
         try{
            Parent p = FXMLLoader.load(getClass().getResource("Login.fxml"));
            root.getChildren().setAll(p);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
   
    
    final ObservableList<DisplayEmployees> data = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayEmployees();
    }
    public void displayEmployees() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("select * from employee");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                data.add(new DisplayEmployees(r.getInt("ID"), r.getString("employeeCode"), r.getString("Name"), r.getString("Department")));
            }
            colID.setCellValueFactory(new PropertyValueFactory<>("id"));
            colCode.setCellValueFactory(new PropertyValueFactory<>("employeeCode"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colDPT.setCellValueFactory(new PropertyValueFactory<>("dpt"));
            myTable.setItems(data);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
     public void getUserAccount(){
            try{
                String  employeeCode = myTable.getSelectionModel().getSelectedItem().getEmployeeCode();

                 DB d = new DB();
                PreparedStatement pst = d.con.prepareStatement("SELECT * FROM user_account WHERE employeeCode = '" + employeeCode + "'");
                ResultSet r = pst.executeQuery();
                if(r.next()){
                    Edit_UserController.idFromUserAccount = r.getInt("ID");
                }
                
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    
}



