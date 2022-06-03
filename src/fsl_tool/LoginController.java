package fsl_tool;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoginController implements Initializable {
 ///  Export Data From Database to Excel File
    @FXML
    private JFXButton btnExport;
    @FXML
    void btnExportAction(ActionEvent event) {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT * FROM employee");
            ResultSet r = pst.executeQuery();
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Employees Details");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("employee Code");
            header.createCell(2).setCellValue("Name");
            header.createCell(3).setCellValue("Department");
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.setColumnWidth(3, 256 * 25);
            sheet.setZoom(150);
            int index = 1;
            while (r.next()) {
                XSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(r.getInt("ID"));
                row.createCell(1).setCellValue(r.getString("employeeCode"));
                row.createCell(2).setCellValue(r.getString("Name"));
                row.createCell(3).setCellValue(r.getString("Department"));
                index++;
            }
            String userHomeFolder = System.getProperty("user.home");
            File ExcelFile = new File(userHomeFolder, "Employees Details.xlsx");
            FileOutputStream fileOutPut = new FileOutputStream(ExcelFile);
            wb.write(fileOutPut);
            fileOutPut.close();
            new Alert(Alert.AlertType.INFORMATION, "File has been created").showAndWait();
            pst.close();
            r.close();
        } catch (Exception e) {

        }

    }

    @FXML
    private AnchorPane root;

    @FXML
    private ImageView myBackground;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXPasswordField txtPass;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnCreateUser;

    @FXML
    void btnCreateUserAction(ActionEvent event) {

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("login as administrator");
        dialog.setHeaderText("please enter administator password");

// Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField password = new PasswordField();
        password.setPromptText("Enter Administrator Password");

        grid.add(new Label("Password : "), 0, 1);
        grid.add(password, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> password.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                if (password.getText().trim().equalsIgnoreCase("123456")) {
                    try {
                        Parent p = FXMLLoader.load(getClass().getResource("CreateUser.fxml"));
                        root.getChildren().setAll(p);
                    } catch (IOException ex) {

                        new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Password is incorrect!!!").showAndWait();

                }

            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            System.out.println(", Password=" + password.getText());
        });

    }

    @FXML
    void btnLoginAction(ActionEvent event) {

        try {

            if (txtID.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter your ID").showAndWait();
            } else if (txtID.getText().trim().equalsIgnoreCase("Admin") && txtPass.getText().trim().equalsIgnoreCase("123456")) {
                Parent p = FXMLLoader.load(getClass().getResource("Admin_Login.fxml"));
                root.getChildren().setAll(p);
            } else if (!txtID.getText().matches("[D|E|M]{1}(\\d{4})")) {
                new Alert(Alert.AlertType.ERROR, "please enter user name correctly").showAndWait();

            } else if (txtPass.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter your password").showAndWait();
            } else {
                DB d = new DB();
                PreparedStatement pst = d.con.prepareStatement("SELECT employeeCode,Password FROM user_account WHERE employeeCode = '" + txtID.getText().trim() + "' and Password = '" + txtPass.getText().trim() + "'");
                ResultSet r = pst.executeQuery();
                if (r.next()) {

                    if (txtID.getText().charAt(0) == 'D') {
                        Doctor_LoginController.user = txtID.getText().trim();
                        Parent p = FXMLLoader.load(getClass().getResource("Doctor_Login.fxml"));
                        root.getChildren().setAll(p);
                    } else if (txtID.getText().charAt(0) == 'E') {
                        Expert_LoginController.user = txtID.getText().trim();
                        Parent p = FXMLLoader.load(getClass().getResource("Expert_Login.fxml"));
                        root.getChildren().setAll(p);
                    } else {
                        Manager_LoginController.user = txtID.getText().trim();
                        Parent p = FXMLLoader.load(getClass().getResource("Manager_Login.fxml"));
                        root.getChildren().setAll(p);
                    }

                } else {
                    new Alert(Alert.AlertType.ERROR, "user name or password is incorrect").showAndWait();

                }

                //  "SELECT employeeCode,Password FROM user_account WHERE employeeCode = '" + txtID.getText().trim() + "' and Password = '" + txtPass.getText().trim() + "'"
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DB d = new DB();
        

    }

}
