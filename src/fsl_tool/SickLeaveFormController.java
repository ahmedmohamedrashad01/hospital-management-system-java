package fsl_tool;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.dialog.ProgressDialog;

public class SickLeaveFormController implements Initializable {

    int minus = 0;
    int sqlNumOfDays = 0;

    String numOfDay = "";
    String aroundWeek = ""; 
    String dayAfterPay = "";
    String value = "";
    String Specialty = "";
    String SpecialtyDoctor = "";
    String diagnosesStatus = "";

    String SpecialtyStatus = "";
    String MRNVisitType = "";   
    String mrnNumber = "";

    String level = "";

    String checkProblem = "";

    public static String user = "";

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtMRN;

    @FXML
    private JFXTextField txtNumOfDays;

    @FXML
    private JFXDatePicker txtStartDate;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXDatePicker txtDishTime;

    @FXML
    private JFXTextField txtProblem;

    @FXML
    private FontAwesomeIcon iLogout;

    @FXML
    private FontAwesomeIcon iHome;

    DropShadow dHome = new DropShadow(15, Color.BLUE);
    DropShadow dLogout = new DropShadow(15, Color.RED);

    @FXML
    void iHomeClick(MouseEvent event) {
        try {
            Doctor_MessageController.user = user;
            Parent p = FXMLLoader.load(getClass().getResource("Doctor_Login.fxml"));
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
    private TableView<SickLeaveTable> myTable;

    @FXML
    private TableColumn<SickLeaveTable, Integer> colVisitNumber;

    @FXML
    private TableColumn<SickLeaveTable, String> colVistType;

    @FXML
    private TableColumn<SickLeaveTable, String> colDpt;

    final ObservableList<SickLeaveTable> data = FXCollections.observableArrayList();

    @FXML
    void btnSaveAction(ActionEvent event) {

        /*
        System.out.println("Number Of DAYS : "+NumDays(numOfDay));
         System.out.println("Arround The Week End: "+aroundWeekEnd(aroundWeek));
         System.out.println("Day After Pay : "+getDayAfterPay(dayAfterPay));
          System.out.println("Doctor Value : "+doctorValue(value));
        System.out.println("Diagnoses : "+checkDiagnoses(diagnosesStatus));
        //System.out.println(checkTableView("MRNVisitType: "+MRNVisitType));
        System.out.println("Number Of Sick Leave : "+checkMRNNumber(mrnNumber));

         */
        ////////////////////////////////////////////////
        
         if (txtMRN.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter medical ID").showAndWait();
            } else if (!txtMRN.getText().matches("\\d+")) {
                new Alert(Alert.AlertType.ERROR, "please enter medical ID digits only").showAndWait();

            } else if (txtProblem.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter patient problem").showAndWait();

            } else if (txtProblem.getText().matches("[0-9]+")) {
                
                new Alert(Alert.AlertType.ERROR, "please enter patient problem correctly").showAndWait();

            } else if (txtNumOfDays.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter number of days").showAndWait();
            } else if (!txtNumOfDays.getText().trim().matches("(\\d)+")) {
                new Alert(Alert.AlertType.ERROR, "please enter number of days digits only").showAndWait();

            } else if (txtStartDate.getValue() == null) {
                new Alert(Alert.AlertType.ERROR, "please select sick leave start date").showAndWait();
            } else if (txtStartDate.getValue().isBefore(LocalDate.now())) {
                new Alert(Alert.AlertType.ERROR, "sorry you cant select sick leave start date before today").showAndWait();

            } else if (MRNVisitType.trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please select row").showAndWait();
            }else{
                
                 AssemblyMethod(MRNVisitType, checkProblem, aroundWeek, numOfDay, dayAfterPay, value, diagnosesStatus, mrnNumber);

            }
       
    }

    @FXML
    void onKeyPressEnter(KeyEvent event) {

        // return normal ot trusted >> in patient
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                DB d = new DB();
                PreparedStatement pst = d.con.prepareStatement("SELECT * FROM visit WHERE MRN = '" + txtMRN.getText().trim() + "'");
                ResultSet r = pst.executeQuery();
                while (r.next()) {
                    data.add(new SickLeaveTable(r.getInt("numberOfVisit"), r.getString("VT"), r.getString("department")));
                    colVisitNumber.setCellValueFactory(new PropertyValueFactory<>("numberOfVisit"));
                    colVistType.setCellValueFactory(new PropertyValueFactory<>("VT"));
                    colDpt.setCellValueFactory(new PropertyValueFactory<>("department"));
                    myTable.setItems(data);
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else {
            myTable.getItems().clear();
        }
    }

    @FXML
    void CheckProblemWhenPress(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            try {
                DB d = new DB();
                PreparedStatement pst = d.con.prepareStatement("SELECT * FROM diagnoses WHERE DiagnosesName = '" + txtProblem.getText().trim() + "'");
                ResultSet r = pst.executeQuery();
                if (r.next()) {
                    checkProblem = "Non Conclusive";
                    System.out.println("problem : " + checkProblem);
                } else {
                    checkProblem = "";
                    checkProblem = "High";
                    System.out.println("problem : " + checkProblem);
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayProblemFromSql();
        //System.out.println(user);
        txtDishTime.setValue(LocalDate.now());

    }

    public void displayProblemFromSql() {
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
            TextFields.bindAutoCompletion(txtProblem, a);

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
    }

    ///////// Method 1 //// Number Of Days /////////////////////////
    public String NumDays(String Num) {
        this.numOfDay = Num;
        try {

            DB d = new DB();

            Integer days = Integer.parseInt(txtNumOfDays.getText().trim());

            PreparedStatement pst = d.con.prepareStatement("SELECT DeservedDays from diagnoses where DiagnosesName = '" + txtProblem.getText().trim() + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                sqlNumOfDays = r.getInt("DeservedDays");

            }

            System.out.println("Max Days " + sqlNumOfDays);

            if (days > sqlNumOfDays) {
                numOfDay = "High";

                minus = days - sqlNumOfDays;
                System.out.println("Number Of Days : " + numOfDay);

            } else {

                minus = 0;
                //numOfDay = "";
                numOfDay = "Non Conclusive";
                System.out.println("Number Of Days : " + numOfDay);
            }
            r.close();
            pst.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return Num;
    }

    ///////// Method 2 /// Around Week End ///////////////////////// Doneeeeeeeeeeeeeeeee
    public String aroundWeekEnd(String aroundTheWeekEnd) {
        this.aroundWeek = aroundTheWeekEnd;
        LocalDate day = txtStartDate.getValue(); // or myDatePicker.getValue()
        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE", Locale.getDefault());
        String dayNow = day.format(format);

        if (dayNow.equalsIgnoreCase("Sunday") || dayNow.equalsIgnoreCase("Thursday")) {
            aroundWeek = "Low";
            System.out.println("Arround The Week End : " + aroundWeek);
        } else {
            // new comment
            aroundWeek = "";
            aroundWeek = "Non Conclusive";
            System.out.println("Arround The Week End : " + aroundWeek);
        }

        return aroundWeek;
    }

    ////////  Method 3 //////////////////
    public String getDayAfterPay(String day) {
        this.dayAfterPay = day;
        LocalDate d = txtStartDate.getValue();
        if (d.getDayOfMonth() == 27 || d.getDayOfMonth() == 28) {
            dayAfterPay = "Mid";
            System.out.println("Day After Pay : " + dayAfterPay);
        } else {
            dayAfterPay = "";
            dayAfterPay = "Non Conclusive";
            System.out.println("Day After Pay : " + dayAfterPay);
        }
        return dayAfterPay;
    }

    ////////  Method 4 /////////////////  Doneeeeeeeeeeeeeee
    public String doctorValue(String problem) {
        try {
            this.value = problem;
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT * from diagnoses where DiagnosesName = '" + txtProblem.getText().trim() + "'");
            ResultSet r = pst.executeQuery();
            if (r.next()) {
                Specialty = r.getString("Specialty");
            }

            DB d2 = new DB();
            PreparedStatement pst2 = d2.con.prepareStatement("SELECT * from doctor where employeeCode = '" + user + "'");
            ResultSet r2 = pst2.executeQuery();
            if (r2.next()) {
                SpecialtyDoctor = r2.getString("Specialty");
            }

            if (!Specialty.equalsIgnoreCase(SpecialtyDoctor)) {

                value = "High";
                System.out.println("Doctor Specialty : " + value);
            } else {
                value = "";
                value = "Non Conclusive";
                System.out.println("Doctor Specialty : " + value);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return value;
    }

    ///////  Method 5 /// should be Fourth not Fivth////////////////
    public String checkDiagnoses(String diagnoses) {
        try {

            this.diagnosesStatus = diagnoses;
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT * from diagnoses where DiagnosesName = '" + txtProblem.getText().trim() + "'");
            ResultSet r = pst.executeQuery();
            if (!r.next()) {

                diagnosesStatus = "High";
                System.out.println("Diagnoses Status : " + diagnosesStatus);
            } else {
                diagnosesStatus = "";
                diagnosesStatus = "Non Conclusive";
                System.out.println("Diagnoses Status : " + diagnosesStatus);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return diagnosesStatus;

    }

    /////////////////////////////////////
    ///////// Method 6  // return normal ot trusted >> in patient /////
    /*
    public String checkTableView(String checkVT) {
      
        
        this.MRNVisitType = checkVT;

      
        myTable.setOnMouseClicked((MouseEvent e) -> {

            
            if (e.getButton().equals(MouseButton.PRIMARY)) {
               
                String vlu = myTable.getSelectionModel().getSelectedItem().getVT();
                int ind = myTable.getSelectionModel().getSelectedIndex();
                      if (vlu.equalsIgnoreCase("in patient") || vlu.equalsIgnoreCase("short stay")) {
                    MRNVisitType = "Trusted";
                   
                } else {

                    MRNVisitType = "Normal";
                  
                }

          
                      
                      
             
            }
            
           

        });

        return MRNVisitType;
             
    
    }
     */
    ///////////////  New Fun Table View Test ///////////////////
    ///////// Method 6  // return normal ot trusted >> in patient /////
    @FXML
    void CheckTable(MouseEvent event) {

        String vlu = myTable.getSelectionModel().getSelectedItem().getVT();
        // int ind = myTable.getSelectionModel().getSelectedIndex();
        if (vlu.equalsIgnoreCase("in patient") || vlu.equalsIgnoreCase("short stay")) {
            MRNVisitType = "Trusted";
            System.out.println("MRN Visit Type : " + MRNVisitType);
        } else {

            MRNVisitType = "Non Conclusive";
            System.out.println("MRN Visit Type : " + MRNVisitType);
        }

    }

    //////////////////////////////
    //////// Method 7 check_MRN_Number //////////
    public String checkMRNNumber(String check) {
        int employeeCount = 0;
        try {
            this.mrnNumber = check;
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(employee) AS Total from sick_leave where MRN ='" + txtMRN.getText().trim() + "' and employee ='" + user + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                employeeCount = r.getInt("Total");
            }
            if (employeeCount >= 10) {
                // System.out.println(employeeCount);
                mrnNumber = "Medium";
                System.out.println("Number Of Sick Leave : " + mrnNumber);
            } else {
                //  System.out.println(employeeCount);
                mrnNumber = "";
                mrnNumber = "Non Conclusive";
                System.out.println("Number Of Sick Leave : " + mrnNumber);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return mrnNumber;
    }

    ////  check Problem Method 
    ////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    /////////////////////////  Assembly_Method 8 /////////////////////////////////////
    //////////////////////////////////////////////////////////////
    /////////////////////////////////////////
    public void AssemblyMethod(String inPatient, String prob, String sudayOrThursday, String NumOfDayss, String DayAfter, String issue, String diagnoStatus, String mrnNum) {

        try {

            DB d = new DB();
            this.MRNVisitType = inPatient;
            this.checkProblem = prob;
            /////////////////////////////

            ///////////////////////////
            aroundWeekEnd(sudayOrThursday);
            NumDays(NumOfDayss);
            getDayAfterPay(DayAfter);
            doctorValue(issue);
            checkDiagnoses(diagnoStatus);
            checkMRNNumber(mrnNum);
            
            int mrn = Integer.parseInt(txtMRN.getText().trim());
            int noOfDays = Integer.parseInt(txtNumOfDays.getText().trim());
            
             if (inPatient.equalsIgnoreCase("Trusted") || inPatient.equalsIgnoreCase("short stay")) {

                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Confirmation Dialog");
                dialog.setHeaderText("Do You Want To Continue?");
                ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));
                ProgressBar progressBar_1 = new ProgressBar(0.99);
                progressBar_1.setPrefSize(200, 50);
                progressBar_1.setStyle("-fx-accent: green;");
                grid.add(new Label("Trusted : "), 0, 1);
                grid.add(progressBar_1, 1, 1);
                Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
                loginButton.setDisable(false);
                dialog.getDialogPane().setContent(grid);
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        try {
                            String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrn + "','" + txtProblem.getText().trim() + "','" + txtDishTime.getValue() + "','" + noOfDays + "','" + txtStartDate.getValue() + "','" + minus + "','" + user + "','" + "Trusted" + "')";

                            d.stmt.executeUpdate(query);
                        } catch (SQLException ex) {

                            System.out.println(ex.getMessage());
                        }

                        new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();
                        txtMRN.setText("");
                        txtProblem.setText("");

                        txtNumOfDays.setText("");
                        txtStartDate.setValue(null);
                        myTable.getItems().clear();

                    }
                    return null;
                });
                Optional<String> result = dialog.showAndWait();
                return;
            } /////////////////// else 2 /////////////////////
            else if (prob.equalsIgnoreCase("High")) {

                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Confirmation Dialog");
                dialog.setHeaderText("Do You Want To Continue?");
                ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));
                ProgressBar progressBar_1 = new ProgressBar(0.99);
                progressBar_1.setPrefSize(200, 50);
                progressBar_1.setStyle("-fx-accent: red;");
                grid.add(new Label("High : "), 0, 1);
                grid.add(progressBar_1, 1, 1);
                Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
                loginButton.setDisable(false);
                dialog.getDialogPane().setContent(grid);
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        try {
                            String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrn + "','" + txtProblem.getText().trim() + "','" + txtDishTime.getValue() + "','" + noOfDays + "','" + txtStartDate.getValue() + "','" + minus + "','" + user + "','" + "High" + "')";
                            d.stmt.executeUpdate(query);

                        } catch (SQLException ex) {

                            System.out.println(ex.getMessage());
                        }

                        new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();
                        txtMRN.setText("");
                        txtProblem.setText("");

                        txtNumOfDays.setText("");
                        txtStartDate.setValue(null);
                        myTable.getItems().clear();
                    }
                    return null;
                });
                Optional<String> result = dialog.showAndWait();
                return;
            } ///////////////// else 3 ////////////////
            else if (aroundWeek.equalsIgnoreCase("Low")) {

                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Confirmation Dialog");
                dialog.setHeaderText("Do You Want To Continue?");
                ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));
                ProgressBar progressBar_1 = new ProgressBar(0.99);
                progressBar_1.setPrefSize(200, 50);
                progressBar_1.setStyle("-fx-accent: yellow;");
                grid.add(new Label("Low : "), 0, 1);
                grid.add(progressBar_1, 1, 1);
                Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
                loginButton.setDisable(false);
                dialog.getDialogPane().setContent(grid);
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        try {
                            String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrn + "','" + txtProblem.getText().trim() + "','" + txtDishTime.getValue() + "','" + noOfDays + "','" + txtStartDate.getValue() + "','" + minus + "','" + user + "','" + "Low" + "')";
                            d.stmt.executeUpdate(query);

                        } catch (SQLException ex) {

                            System.out.println(ex.getMessage());
                        }

                        new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();
                        txtMRN.setText("");
                        txtProblem.setText("");

                        txtNumOfDays.setText("");
                        txtStartDate.setValue(null);
                        myTable.getItems().clear();
                    }
                    return null;
                });
                Optional<String> result = dialog.showAndWait();
                return;
            } ////////////// else 4 ////////////////////
            else if (numOfDay.equalsIgnoreCase("High") && aroundWeek.equalsIgnoreCase("Non Conclusive") && value.equalsIgnoreCase("Non Conclusive") && diagnosesStatus.equalsIgnoreCase("Non Conclusive") && mrnNumber.equalsIgnoreCase("Non Conclusive") && dayAfterPay.equalsIgnoreCase("Non Conclusive")) {

                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Confirmation Dialog");
                dialog.setHeaderText("Do You Want To Continue?");
                ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));
                ProgressBar progressBar_1 = new ProgressBar(0.99);
                progressBar_1.setPrefSize(200, 50);
                progressBar_1.setStyle("-fx-accent: red;");
                grid.add(new Label("High : "), 0, 1);
                grid.add(progressBar_1, 1, 1);
                Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
                loginButton.setDisable(false);
                dialog.getDialogPane().setContent(grid);
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        try {

                            String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrn + "','" + txtProblem.getText().trim() + "','" + txtDishTime.getValue() + "','" + noOfDays + "','" + txtStartDate.getValue() + "','" + minus + "','" + user + "','" + "High" + "')";
                            d.stmt.executeUpdate(query);

                        } catch (SQLException ex) {
                            Logger.getLogger(SickLeaveFormController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();
                        txtMRN.setText("");
                        txtProblem.setText("");

                        txtNumOfDays.setText("");
                        txtStartDate.setValue(null);
                        myTable.getItems().clear();
                    }
                    return null;
                });
                Optional<String> result = dialog.showAndWait();

                return;

            } //////////// else 5 ////////////////////
            else if (numOfDay.equalsIgnoreCase("Non Conclusive") && aroundWeek.equalsIgnoreCase("Non Conclusive") && value.equalsIgnoreCase("Non Conclusive") && diagnosesStatus.equalsIgnoreCase("Non Conclusive") && mrnNumber.equalsIgnoreCase("Non Conclusive") && dayAfterPay.equalsIgnoreCase("Mid")) {

                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Confirmation Dialog");
                dialog.setHeaderText("Do You Want To Continue?");
                ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));
                ProgressBar progressBar_1 = new ProgressBar(0.99);
                progressBar_1.setPrefSize(200, 50);
                progressBar_1.setStyle("-fx-accent: orange;");
                grid.add(new Label("Medium : "), 0, 1);
                grid.add(progressBar_1, 1, 1);
                Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
                loginButton.setDisable(false);
                dialog.getDialogPane().setContent(grid);
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        try {
                            String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrn + "','" + txtProblem.getText().trim() + "','" + txtDishTime.getValue() + "','" + noOfDays + "','" + txtStartDate.getValue() + "','" + minus + "','" + user + "','" + "Medium" + "')";

                            d.stmt.executeUpdate(query);
                        } catch (SQLException ex) {
                            Logger.getLogger(SickLeaveFormController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();
                        txtMRN.setText("");
                        txtProblem.setText("");

                        txtNumOfDays.setText("");
                        txtStartDate.setValue(null);
                        myTable.getItems().clear();
                    }
                    return null;
                });
                Optional<String> result = dialog.showAndWait();
                return;
            } ////////////// else 6 //////////////////
            else if (aroundWeek.equalsIgnoreCase("Non Conclusive") && value.equalsIgnoreCase("Non Conclusive") && diagnosesStatus.equalsIgnoreCase("Non Conclusive") && mrnNumber.equalsIgnoreCase("Medium") && dayAfterPay.equalsIgnoreCase("Non Conclusive")) {

                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Confirmation Dialog");
                dialog.setHeaderText("Do You Want To Continue?");
                ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));
                ProgressBar progressBar_1 = new ProgressBar(0.99);
                progressBar_1.setPrefSize(200, 50);
                progressBar_1.setStyle("-fx-accent: orange;");
                grid.add(new Label("Medium : "), 0, 1);
                grid.add(progressBar_1, 1, 1);
                Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
                loginButton.setDisable(false);
                dialog.getDialogPane().setContent(grid);
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        try {

                            String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrn + "','" + txtProblem.getText().trim() + "','" + txtDishTime.getValue() + "','" + noOfDays + "','" + txtStartDate.getValue() + "','" + minus + "','" + user + "','" + "Medium" + "')";
                            d.stmt.executeUpdate(query);

                        } catch (SQLException ex) {
                            Logger.getLogger(SickLeaveFormController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();
                        txtMRN.setText("");
                        txtProblem.setText("");

                        txtNumOfDays.setText("");
                        txtStartDate.setValue(null);
                        myTable.getItems().clear();
                    }
                    return null;
                });
                Optional<String> result = dialog.showAndWait();
                return;
            } ////////////////// else 7 /////////////////////
            else if (aroundWeek.equalsIgnoreCase("Non Conclusive") && value.equalsIgnoreCase("Non Conclusive") && diagnosesStatus.equalsIgnoreCase("Non Conclusive") && mrnNumber.equalsIgnoreCase("Medium") && dayAfterPay.equalsIgnoreCase("Mid")) {

                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Confirmation Dialog");
                dialog.setHeaderText("Do You Want To Continue?");
                ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));
                ProgressBar progressBar_1 = new ProgressBar(0.99);
                progressBar_1.setPrefSize(200, 50);
                progressBar_1.setStyle("-fx-accent: red;");
                grid.add(new Label("High : "), 0, 1);
                grid.add(progressBar_1, 1, 1);
                Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
                loginButton.setDisable(false);
                dialog.getDialogPane().setContent(grid);
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        try {

                            String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrn + "','" + txtProblem.getText().trim() + "','" + txtDishTime.getValue() + "','" + noOfDays + "','" + txtStartDate.getValue() + "','" + minus + "','" + user + "','" + "High" + "')";
                            d.stmt.executeUpdate(query);

                        } catch (SQLException ex) {
                            Logger.getLogger(SickLeaveFormController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();
                        txtMRN.setText("");
                        txtProblem.setText("");

                        txtNumOfDays.setText("");
                        txtStartDate.setValue(null);
                        myTable.getItems().clear();
                    }
                    return null;
                });
                Optional<String> result = dialog.showAndWait();
                return;
            } ////////////////////// else 8//////////////////
            else if (aroundWeek.equalsIgnoreCase("Non Conclusive") && value.equalsIgnoreCase("High") && diagnosesStatus.equalsIgnoreCase("Non Conclusive") && mrnNumber.equalsIgnoreCase("Non Conclusive") && dayAfterPay.equalsIgnoreCase("Non Conclusive")) {

                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Confirmation Dialog");
                dialog.setHeaderText("Do You Want To Continue?");
                ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));
                ProgressBar progressBar_1 = new ProgressBar(0.99);
                progressBar_1.setPrefSize(200, 50);
                progressBar_1.setStyle("-fx-accent: red;");
                grid.add(new Label("High : "), 0, 1);
                grid.add(progressBar_1, 1, 1);
                Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
                loginButton.setDisable(false);
                dialog.getDialogPane().setContent(grid);
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        try {

                            String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrn + "','" + txtProblem.getText().trim() + "','" + txtDishTime.getValue() + "','" + noOfDays + "','" + txtStartDate.getValue() + "','" + minus + "','" + user + "','" + "High" + "')";
                            d.stmt.executeUpdate(query);

                        } catch (SQLException ex) {
                            Logger.getLogger(SickLeaveFormController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();

                        txtMRN.setText("");
                        txtProblem.setText("");

                        txtNumOfDays.setText("");
                        txtStartDate.setValue(null);
                        myTable.getItems().clear();
                    }
                    return null;
                });
                Optional<String> result = dialog.showAndWait();
                return;
            } ////////////////// else  9 ////////////////
            else {

                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Confirmation Dialog");
                dialog.setHeaderText("Do You Want To Continue?");
                ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));
                ProgressBar progressBar_1 = new ProgressBar(0.99);
                progressBar_1.setPrefSize(200, 50);
                progressBar_1.setStyle("-fx-accent: green;");
                grid.add(new Label("Non Conclusive"), 0, 1);
                grid.add(progressBar_1, 1, 1);
                Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
                loginButton.setDisable(false);
                dialog.getDialogPane().setContent(grid);
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        try {

                            String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrn + "','" + txtProblem.getText().trim() + "','" + txtDishTime.getValue() + "','" + noOfDays + "','" + txtStartDate.getValue() + "','" + minus + "','" + user + "','" + "Non Conclusive" + "')";
                            d.stmt.executeUpdate(query);

                        } catch (SQLException ex) {
                            Logger.getLogger(SickLeaveFormController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();

                        txtMRN.setText("");
                        txtProblem.setText("");

                        txtNumOfDays.setText("");
                        txtStartDate.setValue(null);
                        myTable.getItems().clear();
                    }
                    return null;
                });
                Optional<String> result = dialog.showAndWait();
                return;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /////////////////
    public void gridPaneDisplay() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Confirmation Dialog");
        dialog.setHeaderText("Do You Want To Continue?");
        ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        ProgressBar progressBar_1 = new ProgressBar(0.99);
        progressBar_1.setPrefSize(200, 50);
        progressBar_1.setStyle("-fx-accent: red;");
        grid.add(new Label("Value : "), 0, 1);
        grid.add(progressBar_1, 1, 1);
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(false);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                new Alert(Alert.AlertType.INFORMATION, "Save").showAndWait();
            }
            return null;
        });
        Optional<String> result = dialog.showAndWait();
    }
}
