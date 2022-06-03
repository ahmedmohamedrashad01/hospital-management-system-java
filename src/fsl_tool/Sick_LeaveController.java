package fsl_tool;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

public class Sick_LeaveController implements Initializable {

    public static String user = "";
    
    
    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtMRN;

    @FXML
    private JFXTextField txtOrderPhuscian;

    @FXML
    private JFXTextField txtNumOfDays;

    @FXML
    private JFXDatePicker txtStartDate;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXComboBox<String> cbxProblem;

    @FXML
    private JFXComboBox<String> cbxDay;

    @FXML
    private JFXDatePicker txtDishTime;

    @FXML
    private JFXComboBox<String> cbxStatus;

    @FXML
    void btnSaveAction(ActionEvent event) {
     

    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(user);
        txtDishTime.setValue(LocalDate.now());
        txtStartDate.setValue(LocalDate.now());
        txtStartDate.setDisable(true);
        diagnosesName();
        displayCBX();

    }

    public void diagnosesName() {
        ArrayList<String> a = new ArrayList<>();
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT * FROM diagnoses");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                a.add(r.getString("DiagnosesName"));

            }
            
            HashSet<String> set = new HashSet<>(a);
            a.clear();
            a.addAll(set);

            cbxProblem.getItems().addAll(a);

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
    }

    public void displayCBX() {
        cbxDay.getItems().addAll("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        cbxStatus.getItems().addAll("Partial", "Complete");

    }
    
    public void check(){
        int sqlNumOfDays = 0;
        int minus = 0;

        try {

            if (txtMRN.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter MRN").showAndWait();
            }else if(!txtMRN.getText().matches("\\d+")){
             new Alert(Alert.AlertType.ERROR, "please enter MRN digits only!!!").showAndWait();
            
            }else if (cbxStatus.getValue() == null) {
                new Alert(Alert.AlertType.ERROR, "please select status").showAndWait();
            } else if (cbxProblem.getValue() == null) {
                new Alert(Alert.AlertType.ERROR, "please select problem").showAndWait();

            } else if (txtOrderPhuscian.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter order_physcian").showAndWait();
            } else if (txtNumOfDays.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter number of days").showAndWait();

            } else if (!txtNumOfDays.getText().matches("[0-9]+")) {
                new Alert(Alert.AlertType.ERROR, "please enter number of days digits only").showAndWait();

            } else if (txtStartDate.getValue() == null) {
                new Alert(Alert.AlertType.ERROR, "please select start date").showAndWait();
            } else if (cbxDay.getValue() == null) {
                new Alert(Alert.AlertType.ERROR, "please select day").showAndWait();
            } else {

                
                DB d = new DB();
                Integer MRN = Integer.parseInt(txtMRN.getText());
                Integer days = Integer.parseInt(txtNumOfDays.getText());
                
                PreparedStatement pst = d.con.prepareStatement("SELECT DeservedDays from diagnoses where DiagnosesName = '" + cbxProblem.getSelectionModel().getSelectedItem() + "'");
                ResultSet r = pst.executeQuery();
                if (r.next()) {
                    sqlNumOfDays += r.getInt("DeservedDays");
                    if (days > sqlNumOfDays) {
                        /// HIGH
                        minus = days - sqlNumOfDays;
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("Look, a Confirmation Dialog");
                        alert.setContentText("number of days greater than number of days on database\n do you want to continue?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            
                            /// 
                            
                            d.query = "insert into sick_leave (MRN,Status,Problem,Dish_Time,Order_Physcian,Num_of_Days,Start_Date,value,employee) values ('" +MRN+ "','" + cbxStatus.getSelectionModel().getSelectedItem() + "','" + cbxProblem.getSelectionModel().getSelectedItem() + "','" + txtDishTime.getValue() + "','" + txtOrderPhuscian.getText().trim() + "','" + days + "','" + txtStartDate.getValue() + "' , '" + minus + "','"+user+"')";
                            d.stmt.executeUpdate(d.query);
                              new Alert(Alert.AlertType.INFORMATION, "new sick leave has been created").showAndWait();
                            
                            /*
                            System.out.println("Ok");
                            System.out.println(minus);
                             */

                        } else {
                            new Alert(Alert.AlertType.INFORMATION, "Canceled").showAndWait();
                            
                        }
                    }
                }

                /*
                LocalDate day = LocalDate.now(); // or myDatePicker.getValue()
                DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE", Locale.getDefault());
                String dayNow = day.format(format);
                System.out.println(dayNow);
                 */
 /*
             DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT medical_ID from new_sick where medical_ID = '"+txtMedical.getText().trim()+"'");
            ResultSet r = pst.executeQuery();
            if(r.next()){
                 new Alert(Alert.AlertType.ERROR, "medical ID already exist").showAndWait();                 
          
            }else{
                
                
            Integer days = Integer.parseInt(txtNumOfDays.getText());
            String query2 = "insert into new_sick (name,medical_ID,number_of_days,diagnous,age,date) values ('"+txtName.getText().trim()+"','"+txtMedical.getText().trim()+"','"+days+"','"+txtDiagnous.getText().trim()+"','"+txtAge.getText().trim()+"','"+txtDate.getValue()+"')";
            d.stmt.executeUpdate(query2);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Message");
            a.setContentText("patient data has been added");
            a.showAndWait();
            }
            
                 */
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
    }
}
