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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CreateUserController implements Initializable {
    
    @FXML
    private AnchorPane root;
    
    @FXML
    private JFXTextField txtEmpCode;
    
    @FXML
    private JFXTextField txtName;
    
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
    private JFXButton btnSaveNewUser;
    
    @FXML
    private JFXButton btnSaveAfterEdit;
    
    @FXML
    private JFXButton btnEdit;
    
    @FXML
    private JFXButton btnBack;
    
    @FXML
    private JFXButton btnExit;
    
    @FXML
    private JFXTextField txtSearch;
    
    @FXML
    private FontAwesomeIcon IconSearch;
    
    @FXML
    private JFXComboBox<String> cbxDpt;
    
    @FXML
    private JFXTextField txtDPTCode;
    
    @FXML
    void IconSearchAction(MouseEvent event) {
        System.out.println("Clicked");
    }
    
    @FXML
    void btnBackAction(ActionEvent event) {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("Login.fxml"));
            root.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(CreateUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void btnEditAction(ActionEvent event) {
        int index = myTable.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            new Alert(Alert.AlertType.ERROR, "please select row first").showAndWait();
            
        } else {
            btnSaveAfterEdit.setDisable(false);
            btnSaveNewUser.setDisable(true);
            int id = myTable.getSelectionModel().getSelectedItem().getId();
            txtEmpCode.setText(myTable.getSelectionModel().getSelectedItem().getEmployeeCode());     
            txtName.setText(myTable.getSelectionModel().getSelectedItem().getName());
            cbxDpt.setValue(myTable.getSelectionModel().getSelectedItem().getDpt());
        }
    }
    
    @FXML
    void btnExitAction(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    void btnSaveAfterEdit(ActionEvent event) {
        int id = myTable.getSelectionModel().getSelectedItem().getId();
        try{
            if (txtEmpCode.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter employee code").showAndWait();
            } else if (!txtEmpCode.getText().matches("[D|E|M]{1}(\\d{4})")) {
                new Alert(Alert.AlertType.ERROR, "please enter employee code correctly").showAndWait();
                
            } else if (txtName.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter employee name").showAndWait();
            } else if (cbxDpt.getValue() == null) {
                
                new Alert(Alert.AlertType.ERROR, "please select department").showAndWait();
            }else{
                 DB d = new DB();
                 d.query = "UPDATE employee SET employeeCode = '" + txtDPTCode.getText().trim() + "' , Name = '" + txtName.getText().trim() + "' , Department = '" + cbxDpt.getSelectionModel().getSelectedItem() + "' where ID = '" + id + "'";
                d.stmt.executeUpdate(d.query);
                new Alert(Alert.AlertType.INFORMATION, "user information has been updated").showAndWait();
                myTable.getItems().clear();
               displayEmployees();

                txtName.clear();
               txtDPTCode.clear();
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    void btnSaveNewUserAction(ActionEvent event) {
        String pass = "111111";
        try {
            
            if (txtEmpCode.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter employee code").showAndWait();
            } else if (!txtEmpCode.getText().matches("[D|E|M]{1}(\\d{4})")) {
                new Alert(Alert.AlertType.ERROR, "please enter employee code correctly").showAndWait();
                
            } else if (txtName.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter employee name").showAndWait();
            } else if (cbxDpt.getValue() == null) {
                
                new Alert(Alert.AlertType.ERROR, "please select department").showAndWait();
            } else {
                DB d = new DB();
                PreparedStatement pst = d.con.prepareStatement("SELECT employeeCode FROM employee WHERE employeeCode = '" + txtEmpCode.getText().trim() + "'");
                ResultSet r = pst.executeQuery();
                if (!r.next()) {
                    
                    String query2 = "insert into employee (employeeCode,Name,Department) values ('" + txtEmpCode.getText().trim() + "','" + txtName.getText().trim() + "','" + cbxDpt.getValue() + "')";
                    d.stmt.executeUpdate(query2);
                    
                    String query3 = "insert into user_account (employeeCode,Password) values ('" + txtEmpCode.getText().trim() + "','" + pass + "')";
                    d.stmt.executeUpdate(query3);
                    
                    new Alert(Alert.AlertType.INFORMATION, "user account has been created").showAndWait();
                    myTable.getItems().clear();
                    displayEmployees();
                    
                    cbxDpt.setSelectionModel(null);
                    
                } else {
                    new Alert(Alert.AlertType.ERROR, "employee code already exist!!!").showAndWait();
                    
                }

                //  "SELECT employeeCode,Password FROM user_account WHERE employeeCode = '" + txtID.getText().trim() + "' and Password = '" + txtPass.getText().trim() + "'"
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    @FXML
    void cbxDptAction(ActionEvent event) {

        /*
        try {

            //  "SELECT * FROM employee WHERE employeeCode LIKE 'A%' "
           
           
            int max = r.getInt("maximum");
                    PreparedStatement pst2 = d.con.prepareStatement("SELECT * from employee WHERE ID = '"+max+"' ");
                     ResultSet r2 = pst2.executeQuery();
                    while(r2.next()){
                      System.out.println(r.getString("Name"));
                    }
           
           
            
            int lastId = 0;

            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT max(ID) as maximum FROM doctor");
            ResultSet r = pst.executeQuery();
            while (r.next()){
                lastId = r.getInt("maximum");
                System.out.println(lastId);
                
              
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
         */
    }
    
    final ObservableList<DisplayEmployees> data = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // setItems();
        displayEmployees();
        displayDPTFromSql();
    }

    //// display all items into cbx
    public void setItems() {
        ObservableList dptItems = FXCollections.observableArrayList();
        dptItems.add("D");
        dptItems.add("E");
        dptItems.add("M");
        cbxDpt.getItems().addAll(dptItems);
        
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
            
            cbxDpt.getItems().addAll(a);
            
        } catch (SQLException ex) {
            
            System.out.println(ex.getMessage());
        }
    }
}
