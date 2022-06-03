package fsl_tool;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.awt.ItemSelectable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.FilterXmlObject;

public class Expert_EvaluateController implements Initializable {

    //File file;
    String filePath;

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

    String levelFinal = "";

    String checkProblem = "";

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
        } catch (Exception ex) {
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
    private JFXButton btnChoose;

    @FXML
    private TableView<Expert_EvaluateTable> myTable;

    @FXML
    private TableColumn<Expert_EvaluateTable, Integer> colID;

    @FXML
    private TableColumn<Expert_EvaluateTable, String> colEmployeeCode;

    @FXML
    private TableColumn<Expert_EvaluateTable, String> colLevel;

    @FXML
    private TableColumn<Expert_EvaluateTable, Integer> colMrn;

    @FXML
    private TableColumn<Expert_EvaluateTable, String> colProblem;

    @FXML
    private TableColumn<Expert_EvaluateTable, String> colDishTime;

    @FXML
    private TableColumn<Expert_EvaluateTable, Integer> colNumOfDays;

    @FXML
    private TableColumn<Expert_EvaluateTable, String> colStartDate;

    @FXML
    private TableColumn<Expert_EvaluateTable, String> colVisitType;

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
    void CheckProblemWhenPress(KeyEvent event) {

    }

    ObservableList<Expert_EvaluateTable> data = FXCollections.observableArrayList();

    @FXML
    void btnChooseAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Sick leave File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel File", "*.xlsx"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            filePath = selectedFile.getPath();
            try {

                FileInputStream fileIn = new FileInputStream(filePath);
                XSSFWorkbook wb = new XSSFWorkbook(fileIn);
                XSSFSheet sheet = wb.getSheetAt(0);
                Row row;

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    data.add(new Expert_EvaluateTable((int) row.getCell(0).getNumericCellValue(), (int) row.getCell(1).getNumericCellValue(), row.getCell(2).getStringCellValue(), row.getCell(3).getStringCellValue(), (int) row.getCell(4).getNumericCellValue(), row.getCell(5).getStringCellValue(), row.getCell(6).getStringCellValue(), row.getCell(7).getStringCellValue(), row.getCell(8).getStringCellValue()));
                    colID.setCellValueFactory(new PropertyValueFactory<>("id"));
                    colMrn.setCellValueFactory(new PropertyValueFactory<>("mrn"));
                    colProblem.setCellValueFactory(new PropertyValueFactory<>("problem"));
                    colDishTime.setCellValueFactory(new PropertyValueFactory<>("dishTime"));
                    colNumOfDays.setCellValueFactory(new PropertyValueFactory<>("numOfDays"));
                    colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
                    colVisitType.setCellValueFactory(new PropertyValueFactory<>("vissitType"));
                    colEmployeeCode.setCellValueFactory(new PropertyValueFactory<>("EmployeeCode"));
                    colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));

                }

                myTable.setItems(data);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println("please select file");
        }

    }

    @FXML
    void btnSaveAction(ActionEvent event) throws ParseException {
        int index = myTable.getSelectionModel().getSelectedIndex();
        if (myTable.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "please choose file first").showAndWait();
        } else if (index < 0) {
            new Alert(Alert.AlertType.ERROR, "please select row").showAndWait();
        } else {
            //// call Assemblly Method Here ////////////
            // NumDays(numOfDay);
            //aroundWeekEnd(aroundWeek);
            // getDayAfterPay(dayAfterPay);
            // doctorValue(value);
            // checkDiagnoses(diagnosesStatus);
            // visitType(MRNVisitType);
            //  checkMRNNumber(mrnNumber);
            //  checkProblem(checkProblem);
            int mrnTable = myTable.getSelectionModel().getSelectedItem().getMrn();
            String problemTable = myTable.getSelectionModel().getSelectedItem().getProblem();
            String mrnVisitTypefFromTable = myTable.getSelectionModel().getSelectedItem().getVissitType();
            String dish = myTable.getSelectionModel().getSelectedItem().getDishTime();
            String start = myTable.getSelectionModel().getSelectedItem().getStartDate();

            AssemblyMethod(mrnVisitTypefFromTable, checkProblem, aroundWeek, numOfDay, dayAfterPay, value, diagnosesStatus, mrnNumber);

        }
    }

    @FXML
    void onKeyPressEnter(KeyEvent event) {

    }

    @FXML
    private JFXButton btnTest;

    @FXML
    void btnTestAction(ActionEvent event) {

        selectRowFromTV("Ahmed");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //selectLastRowFromDB();
        System.out.println(user);

    }

    /////////////////// Method 1  numOfDay ////////////////////////
    public String NumDays(String Num) {
        this.numOfDay = Num;
        try {

            DB d = new DB();

            int days = myTable.getSelectionModel().getSelectedItem().getNumOfDays();

            PreparedStatement pst = d.con.prepareStatement("SELECT DeservedDays from diagnoses where DiagnosesName = '" + myTable.getSelectionModel().getSelectedItem().getProblem() + "'");
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
        try {
            this.aroundWeek = aroundTheWeekEnd;
            String day = myTable.getSelectionModel().getSelectedItem().getStartDate();
            String convertDay = day.replaceAll("/", "-");
            DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE", Locale.getDefault());
            String daySubString = convertDay.substring(0, 2);
            String MonthSubString = convertDay.substring(3, 5);
            String YearSubString = convertDay.substring(6, 10);

            int d = Integer.parseInt(daySubString);
            int m = Integer.parseInt(MonthSubString);
            int y = Integer.parseInt(YearSubString);

            LocalDate independenceDay = LocalDate.of(y, m, d);
            DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
            System.out.println(dayOfWeek);
            String CHECKDAY = String.valueOf(dayOfWeek);

            // System.out.println(daySubString);
            // System.out.println(MonthSubString);
            //System.out.println(YearSubString);
            if (CHECKDAY.equalsIgnoreCase("Sunday") || CHECKDAY.equalsIgnoreCase("Thursday")) {
                aroundWeek = "Low";
                System.out.println("Arround The Week End : " + aroundWeek);
            } else {
                // new comment
                aroundWeek = "";
                aroundWeek = "Non Conclusive";
                System.out.println("Arround The Week End : " + aroundWeek);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return aroundWeek;
    }

    ///////////////////////   Method 3  dayAfterPay /////////////////////////////
    public String getDayAfterPay(String day) {
        this.dayAfterPay = day;
        String day2 = myTable.getSelectionModel().getSelectedItem().getStartDate();
        String convertDay = day2.replaceAll("/", "-");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE", Locale.getDefault());
        String daySubString = convertDay.substring(0, 2);
        String MonthSubString = convertDay.substring(3, 5);
        String YearSubString = convertDay.substring(6, 10);

        int d = Integer.parseInt(daySubString);

        if (d == 27 || d == 28) {
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
            String prm = myTable.getSelectionModel().getSelectedItem().getProblem();
            String emplCode = myTable.getSelectionModel().getSelectedItem().getEmployeeCode();
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT * from diagnoses where DiagnosesName = '" + prm + "'");
            ResultSet r = pst.executeQuery();
            if (r.next()) {
                Specialty = r.getString("Specialty");
            }

            DB d2 = new DB();
            PreparedStatement pst2 = d2.con.prepareStatement("SELECT * from doctor where employeeCode = '" + emplCode + "'");
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
            String prm = myTable.getSelectionModel().getSelectedItem().getProblem();
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT * from diagnoses where DiagnosesName = '" + prm + "'");
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

    ///////// Method 6  // return normal ot trusted >> in patient /////
    public String visitType(String type) {
        this.MRNVisitType = type;
        String vlu = myTable.getSelectionModel().getSelectedItem().getVissitType();
        // int ind = myTable.getSelectionModel().getSelectedIndex();
        if (vlu.equalsIgnoreCase("in patient") || vlu.equalsIgnoreCase("short stay")) {
            MRNVisitType = "Trusted";
            System.out.println("MRN Visit Type : " + MRNVisitType);
        } else {

            MRNVisitType = "Non Conclusive";
            System.out.println("MRN Visit Type : " + MRNVisitType);
        }
        return MRNVisitType;
    }

    //////// Method 7 check_MRN_Number //////////
    public String checkMRNNumber(String check) {
        int employeeCount = 0;
        try {
            this.mrnNumber = check;
            int mrn = myTable.getSelectionModel().getSelectedItem().getMrn();
            String empCode = myTable.getSelectionModel().getSelectedItem().getEmployeeCode();
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(employee) AS Total from sick_leave where MRN ='" + mrn + "' and employee ='" + empCode + "'");
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

    ////////////// Method 8 Check Problem //////////////////
    public String checkProblem(String prob) {
        this.checkProblem = prob;
        String prm = myTable.getSelectionModel().getSelectedItem().getProblem();
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT * FROM diagnoses WHERE DiagnosesName = '" + prm + "'");
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
        return checkProblem;
    }

    ////////////////////// Assemblly Method ///////////////////////
    public void AssemblyMethod(String inPatient, String prob, String sudayOrThursday, String NumOfDayss, String DayAfter, String issue, String diagnoStatus, String mrnNum) {
        int mrnTable = myTable.getSelectionModel().getSelectedItem().getMrn();
        String problemTable = myTable.getSelectionModel().getSelectedItem().getProblem();
        String mrnVisitTypefFromTable = myTable.getSelectionModel().getSelectedItem().getVissitType();
        String dish = myTable.getSelectionModel().getSelectedItem().getDishTime();
        String start = myTable.getSelectionModel().getSelectedItem().getStartDate();
        DB d = new DB();

        visitType(inPatient);
        checkProblem(prob);
        //this.MRNVisitType = inPatient;
        //this.checkProblem = prob;
        aroundWeekEnd(sudayOrThursday);
        NumDays(NumOfDayss);
        getDayAfterPay(DayAfter);
        doctorValue(issue);
        checkDiagnoses(diagnoStatus);
        checkMRNNumber(mrnNum);

        String dishReplace = dish.replaceAll("/", "-");
        String startReplace = dish.replaceAll("/", "-");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate dishTimeFromTable = LocalDate.parse(dishReplace, formatter);
        LocalDate startDateFromTable = LocalDate.parse(startReplace, formatter);

        int numOfDaysFromTable = myTable.getSelectionModel().getSelectedItem().getNumOfDays();
        // value
        String empCodeFromTable = myTable.getSelectionModel().getSelectedItem().getEmployeeCode();
        if (MRNVisitType.equalsIgnoreCase("Trusted") || MRNVisitType.equalsIgnoreCase("short stay")) {

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
                        String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrnTable + "','" + problemTable + "','" + dishTimeFromTable + "','" + numOfDaysFromTable + "','" + startDateFromTable + "','" + minus + "','" + empCodeFromTable + "','" + "Trusted" + "')";

                        d.stmt.executeUpdate(query);
                    } catch (SQLException ex) {

                        System.out.println(ex.getMessage());
                    }

                    new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();
                    selectRowFromTV("Trusted");
                    saveTableViewIntoEcelFile();
                }
                return null;
            });
            Optional<String> result = dialog.showAndWait();
            return;
        } /////////////////// else 2 /////////////////////
        else if (checkProblem.equalsIgnoreCase("High")) {

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
                        String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrnTable + "','" + problemTable + "','" + dishTimeFromTable + "','" + numOfDaysFromTable + "','" + startDateFromTable + "','" + minus + "','" + empCodeFromTable + "','" + "High" + "')";
                        d.stmt.executeUpdate(query);

                    } catch (SQLException ex) {

                        System.out.println(ex.getMessage());
                    }

                    new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();
                    selectRowFromTV("High");
                    saveTableViewIntoEcelFile();

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
                        String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrnTable + "','" + problemTable + "','" + dishTimeFromTable + "','" + numOfDaysFromTable + "','" + startDateFromTable + "','" + minus + "','" + empCodeFromTable + "','" + "Low" + "')";
                        d.stmt.executeUpdate(query);

                    } catch (SQLException ex) {

                        System.out.println(ex.getMessage());
                    }

                    new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();
                    selectRowFromTV("Low");
                    saveTableViewIntoEcelFile();

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

                        String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrnTable + "','" + problemTable + "','" + dishTimeFromTable + "','" + numOfDaysFromTable + "','" + startDateFromTable + "','" + minus + "','" + empCodeFromTable + "','" + "High" + "')";
                        d.stmt.executeUpdate(query);

                    } catch (SQLException ex) {
                        Logger.getLogger(SickLeaveFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();
                    selectRowFromTV("High");
                    saveTableViewIntoEcelFile();

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
                        String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrnTable + "','" + problemTable + "','" + dishTimeFromTable + "','" + numOfDaysFromTable + "','" + startDateFromTable + "','" + minus + "','" + empCodeFromTable + "','" + "Medium" + "')";

                        d.stmt.executeUpdate(query);
                    } catch (SQLException ex) {
                        Logger.getLogger(SickLeaveFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();
                    selectRowFromTV("Medium");
                    saveTableViewIntoEcelFile();

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

                        String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrnTable + "','" + problemTable + "','" + dishTimeFromTable + "','" + numOfDaysFromTable + "','" + startDateFromTable + "','" + minus + "','" + empCodeFromTable + "','" + "Medium" + "')";
                        d.stmt.executeUpdate(query);

                    } catch (SQLException ex) {
                        Logger.getLogger(SickLeaveFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();
                    selectRowFromTV("Medium");
                    saveTableViewIntoEcelFile();

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

                        String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrnTable + "','" + problemTable + "','" + dishTimeFromTable + "','" + numOfDaysFromTable + "','" + startDateFromTable + "','" + minus + "','" + empCodeFromTable + "','" + "High" + "')";
                        d.stmt.executeUpdate(query);

                    } catch (SQLException ex) {
                        Logger.getLogger(SickLeaveFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();
                    selectRowFromTV("High");
                    saveTableViewIntoEcelFile();

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

                        String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrnTable + "','" + problemTable + "','" + dishTimeFromTable + "','" + numOfDaysFromTable + "','" + startDateFromTable + "','" + minus + "','" + empCodeFromTable + "','" + "High" + "')";
                        d.stmt.executeUpdate(query);

                    } catch (SQLException ex) {
                        Logger.getLogger(SickLeaveFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();
                    selectRowFromTV("High");
                    saveTableViewIntoEcelFile();
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

                        String query = "insert into sick_leave(MRN,Problem,Dish_Time,Num_of_Days,Start_Date,value,employee,level) values ('" + mrnTable + "','" + problemTable + "','" + dishTimeFromTable + "','" + numOfDaysFromTable + "','" + startDateFromTable + "','" + minus + "','" + empCodeFromTable + "','" + "Non Conclusive" + "')";
                        d.stmt.executeUpdate(query);

                    } catch (SQLException ex) {
                        Logger.getLogger(SickLeaveFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    new Alert(Alert.AlertType.INFORMATION, "Data Inserted").showAndWait();
                    selectRowFromTV("Non Conclusive");
                    saveTableViewIntoEcelFile();

                }
                return null;
            });
            Optional<String> result = dialog.showAndWait();
            return;
        }

    }

    /////////  change cell value
    public void selectRowFromTV(String cellValue) {

        String item = myTable.getSelectionModel().getSelectedItem().getLevel();

        myTable.getSelectionModel().getSelectedItem().setLevel(cellValue);
        myTable.refresh();

    }

    //// save table view into excel
    public void saveTableViewIntoEcelFile() {

        try {
            // DB d = new DB();
            //PreparedStatement pst = d.con.prepareStatement("SELECT * FROM employee");
            // ResultSet r = pst.executeQuery();
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("SickLeave Details");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("MRN");
            header.createCell(2).setCellValue("Problem");
            header.createCell(3).setCellValue("Dish Time");
            header.createCell(4).setCellValue("Num Of Days");
            header.createCell(5).setCellValue("Start Date");
            header.createCell(6).setCellValue("Visit Type");
            header.createCell(7).setCellValue("Doctor Code");
            header.createCell(8).setCellValue("Level");

            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.setColumnWidth(3, 256 * 25);
            sheet.setZoom(150);
            int index = 1;

            ObservableList<Expert_EvaluateTable> columns = myTable.getItems();
            for (Expert_EvaluateTable c : columns) {
                XSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(c.getId());
                row.createCell(1).setCellValue(c.getMrn());
                row.createCell(2).setCellValue(c.getProblem());
                row.createCell(3).setCellValue(c.getDishTime());
                row.createCell(4).setCellValue(c.getNumOfDays());
                row.createCell(5).setCellValue(c.getStartDate());
                row.createCell(6).setCellValue(c.getVissitType());
                row.createCell(7).setCellValue(c.getEmployeeCode());
                row.createCell(8).setCellValue(c.getLevel());
                index++;

            }

            String userHomeFolder = System.getProperty("user.home");
            File ExcelFile = new File(userHomeFolder, "Manual Sick Leave.xlsx");
            FileOutputStream fileOutPut = new FileOutputStream(ExcelFile);
            wb.write(fileOutPut);
            fileOutPut.close();
         //   new Alert(Alert.AlertType.INFORMATION, "File has been created").showAndWait();

        } catch (Exception e) {

        }

        /*
                row.createCell(0).setCellValue(c.getId());
                row.createCell(1).setCellValue(c.getMrn());
                row.createCell(2).setCellValue(c.getProblem());
                row.createCell(3).setCellValue(c.getDishTime());
                row.createCell(4).setCellValue(c.getNumOfDays());
                row.createCell(5).setCellValue(c.getStartDate());
                row.createCell(6).setCellValue(c.getVissitType());
                row.createCell(7).setCellValue(c.getEmployeeCode());
                row.createCell(8).setCellValue(c.getLevel());

                index++;
         */
    }

}
