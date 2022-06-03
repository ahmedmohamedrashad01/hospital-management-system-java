package fsl_tool;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class Expert_LoginController implements Initializable {

    public static String user = "";

    int messageCount = 0;
    public static String name = "";

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
    private JFXButton btnEvaluate;

    @FXML
    void btnEvaluateAction(ActionEvent event) {
        try {
            Expert_EvaluateController.user = user;
            Parent p = FXMLLoader.load(getClass().getResource("Expert_Evaluate.fxml"));
            root.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(Expert_LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private JFXButton btnViewProfile;

    @FXML
    private JFXButton btnCreateSickLeave;

    @FXML
    private JFXButton btnViewStatisticalSheet;

    @FXML
    private JFXButton btnExpert_Accurecy_Sheet;

    @FXML
    private JFXButton btnBack;

    @FXML
    void btnBackAction(ActionEvent event) {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("Login.fxml"));
            root.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(Expert_LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void Expert_Accurecy_SheetAction(ActionEvent event) {
        try {
            Expert_Accurecy_SheetController.user = user;
            Parent p = FXMLLoader.load(getClass().getResource("Expert_Accurecy_Sheet.fxml"));
            root.getChildren().setAll(p);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void btnCreateSickLeaveAction(ActionEvent event) {

    }

    @FXML
    void btnExitAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void btnLoginAction(ActionEvent event) {

        //  Expert_Message
        try {
            Expert_MessageController.user = user;
            Parent p = FXMLLoader.load(getClass().getResource("Expert_Message.fxml"));
            root.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(Expert_LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void btnViewProfileAction(ActionEvent event) {
        try {
            Expert_View_ProfileController.user = user;
            Parent p = FXMLLoader.load(getClass().getResource("Expert_View_Profile.fxml"));
            root.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(Expert_LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void btnViewStatisticalSheetAction(ActionEvent event) {
        try {
            Evaluate_RecordController.user = user;
            Parent p = FXMLLoader.load(getClass().getResource("Evaluate_Record.fxml"));
            root.getChildren().setAll(p);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private FontAwesomeIcon messageIcon;
    @FXML
    private Label lblMessageCount;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(user);
        getName();
        msgCount();

        if (messageCount != 0) {
            DropShadow iconColor = new DropShadow(18, Color.RED);
            messageIcon.setEffect(iconColor);
        } else {
            lblMessageCount.setVisible(false);
        }
    }

    public void msgCount() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(receiver) as TOTAL FROM notification Where receiver = '" + name + "'");
            ResultSet r = pst.executeQuery();
            if (r.next()) {
                messageCount += r.getInt("TOTAL");
            }
            lblMessageCount.setText(String.valueOf(messageCount));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //////////  select name ///////////
    public void getName() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("select * from employee where employeeCode = '" + user + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                name = r.getString("Name");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
