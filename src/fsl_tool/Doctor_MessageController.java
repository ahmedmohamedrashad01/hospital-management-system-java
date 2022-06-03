package fsl_tool;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.controlsfx.control.textfield.TextFields;

public class Doctor_MessageController implements Initializable {

    public static String user = "";
    public static String senderName = "";
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
            Doctor_LoginController.user = user;
              Parent p = FXMLLoader.load(getClass().getResource("Doctor_Login.fxml"));
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
    private JFXTextArea txtOldMessage;

    @FXML
    private JFXTextArea textReceiveNewMessage;

    @FXML
    private TextArea txtNewMeassge;

    @FXML
    private JFXButton btnMessageReceived;

    @FXML
    private JFXButton btnSend;

    @FXML
    private JFXTextField txtName;

    @FXML
    void btnMessageReceivedAction(ActionEvent event) {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("select * from notification where receiver = '"+senderName+"'");
            ResultSet r = pst.executeQuery();
            while(r.next()){
           String query = "insert into message_deleted (sender,receiver,message,date) values ('" + r.getString("sender") + "','" + r.getString("receiver") + "','" + r.getString("message") + "','" + r.getString("date") + "')";
            d.stmt.executeUpdate(query);
            }
            
           
            String query2 = "delete from notification where receiver = '" + senderName + "' ";
            d.stmt.executeUpdate(query2);
            new Alert(Alert.AlertType.INFORMATION, "Done").showAndWait();
            textReceiveNewMessage.clear();
            txtOldMessage.clear();
            getOldMeassges();
            /*
            DB d = new DB();
            String query2 = "delete * from notification where receiver = '" + senderName + "' ";
            d.stmt.executeUpdate(query2);

            
            String query = "insert into message_deleted (sender,receiver,message,date) values ('" + senderName + "','" + txtName.getText().trim() + "','" + txtNewMeassge.getText().trim() + "','" + dt + "')";
            d.stmt.executeUpdate(query);
            new Alert(Alert.AlertType.INFORMATION, "message has been sent").showAndWait();
            txtNewMeassge.clear();

            
            */
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void btnSendAction(ActionEvent event) {
        sendNewMeassge();
       
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(user);
        getSenderName();
        getEmployeesNames();

        getMeassges();
         getOldMeassges();

    }

    ////////// sender name 
    public void getSenderName() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("select * from employee where employeeCode = '" + user + "'");
            ResultSet r = pst.executeQuery();
            if (r.next()) {
                senderName = r.getString("Name");
            }
            System.out.println(senderName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /////////////////////////////////////////////
    /// get employees names 
    public void getEmployeesNames() {
        ArrayList<String> a = new ArrayList<>();
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT * FROM employee");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                a.add(r.getString("Name"));

            }

            TextFields.bindAutoCompletion(txtName, a);

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
    }

    //////////////////////////////////
    public void sendNewMeassge() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String dt = formatter.format(date);

        try {
            if (txtName.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter receiver name").showAndWait();
            } else if (txtName.getText().trim().matches("[0-9]+")) {
                new Alert(Alert.AlertType.ERROR, "please enter reciver name correctlly").showAndWait();
            } else if (txtNewMeassge.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter your message").showAndWait();
            } else {
                DB d = new DB();
                PreparedStatement pst = d.con.prepareStatement("select * from employee where Name = '" + txtName.getText().trim() + "'");
                ResultSet r = pst.executeQuery();
                if (r.next()) {
                    String query2 = "insert into notification (sender,receiver,message,date) values ('" + senderName + "','" + txtName.getText().trim() + "','" + txtNewMeassge.getText().trim() + "','" + dt + "')";
                    d.stmt.executeUpdate(query2);
                    new Alert(Alert.AlertType.INFORMATION, "message has been sent").showAndWait();
                    txtNewMeassge.clear();
                } else {
                    new Alert(Alert.AlertType.ERROR, "receiver name nonexistent").showAndWait();
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    ////////////////////// get new messages /////
    public void getMeassges() {

        ArrayList<String> arr = new ArrayList<>();
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("select * from notification where receiver = '" + senderName + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                //  ahmrashad | 12/3/2020 - 12.00 | ana farah
                arr.add(r.getString("sender") + " : " + r.getString("message") + " // Date : " + r.getString("date") + "\n" + "-------------------------------------------");
            }
            Collections.reverse(arr);
            StringBuilder builder = new StringBuilder();
            for (String value : arr) {

                builder.append(value + "\n");
            }
            String text = builder.toString();
            textReceiveNewMessage.setText(text);

            //     textReceiveNewMessage.setText(r.getString("sender")+" : "+r.getString("message")+"\n"+"--------------------------------------");
            /*
            StringBuilder builder = new StringBuilder();
            for (String value : arr) {
                builder.append(value+"\n");
                builder.append("-------------------------------------------------------"+"\n");
            }
            String text = builder.toString();
            textReceiveNewMessage.setText(text);

             */
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /////////////////////////////////////////
    
     public void getOldMeassges() {

        ArrayList<String> arr = new ArrayList<>();
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("select * from message_deleted where receiver = '" + senderName + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                //  ahmrashad | 12/3/2020 - 12.00 | ana farah
                arr.add(r.getString("sender") + " : " + r.getString("message") + " // Date : " + r.getString("date") + "\n" + "-------------------------------------------");
            }
            Collections.reverse(arr);
            StringBuilder builder = new StringBuilder();
            for (String value : arr) {

                builder.append(value + "\n");
            }
            String text = builder.toString();
            txtOldMessage.setText(text);

         
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    

}
