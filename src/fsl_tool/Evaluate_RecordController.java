package fsl_tool;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
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
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Evaluate_RecordController implements Initializable {

    public static String user = "";

    @FXML
    private AnchorPane root;

    @FXML
    private FontAwesomeIcon iLogout;

    @FXML
    private FontAwesomeIcon iHome;

    DropShadow dHome = new DropShadow(15, Color.BLUE);
    DropShadow dLogout = new DropShadow(15, Color.RED);

    @FXML
    void iHomeClick(MouseEvent event) {
        try {
            Expert_LoginController.user = user;
            Parent p = FXMLLoader.load(getClass().getResource("Expert_Login.fxml"));
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

    ////////////////////////////////////
    @FXML
    private JFXTextField txtMrn;

    @FXML
    private TableView<Evaluate_Rec> tableView;

    @FXML
    private TableColumn<Evaluate_Rec, Integer> colId;

    @FXML
    private TableColumn<Evaluate_Rec, Integer> colMrn;

    @FXML
    private TableColumn<Evaluate_Rec, String> colProblem;

    @FXML
    private TableColumn<Evaluate_Rec, Integer> colNumOfDays;

    @FXML
    private TableColumn<Evaluate_Rec, Integer> colValue;

    @FXML
    private TableColumn<Evaluate_Rec, String> colLevel;

    @FXML
    private JFXComboBox<String> cbx;

    @FXML
    private JFXButton btnUpdate;

    ObservableList<Evaluate_Rec> data = FXCollections.observableArrayList();

    /*
    
    
   

     */
    @FXML
    void keyRel(KeyEvent event) {
        if (txtMrn.getText().trim().length() == 4) {
            try {
                DB d = new DB();
                PreparedStatement pst = d.con.prepareStatement("select * from sick_leave where MRN = '" + txtMrn.getText().trim() + "'");
                ResultSet r = pst.executeQuery();
                while (r.next()) {
                    /// create java class first 
                    /// insert into table view
                    data.addAll(new Evaluate_Rec(r.getInt("ID"), r.getInt("MRN"), r.getString("Problem"), r.getInt("Num_of_Days"), r.getInt("value"), r.getString("level")));
                    colId.setCellValueFactory(new PropertyValueFactory<>("id"));
                    colMrn.setCellValueFactory(new PropertyValueFactory<>("mrn"));
                    colProblem.setCellValueFactory(new PropertyValueFactory<>("problem"));
                    colNumOfDays.setCellValueFactory(new PropertyValueFactory<>("numOfDays"));
                    colValue.setCellValueFactory(new PropertyValueFactory<>("value"));
                    colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));

                }
                tableView.setItems(data);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            tableView.getItems().clear();
        }
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
        try {
            int index = tableView.getSelectionModel().getSelectedIndex();
            if (txtMrn.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "please enter MRN first").showAndWait();
            } else if (index < 0) {
                new Alert(Alert.AlertType.ERROR, "please select row!!!").showAndWait();
            } else if (cbx.getValue() == null) {
                new Alert(Alert.AlertType.ERROR, "please select level!!!").showAndWait();
            } else {
                int i = tableView.getSelectionModel().getSelectedItem().getId();
                int mr = tableView.getSelectionModel().getSelectedItem().getMrn();
                String prob = tableView.getSelectionModel().getSelectedItem().getProblem();
                int numDays = tableView.getSelectionModel().getSelectedItem().getNumOfDays();
                int val = tableView.getSelectionModel().getSelectedItem().getValue();
                String lev = tableView.getSelectionModel().getSelectedItem().getLevel();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                String dt = formatter.format(date);
                
                DB d = new DB();
                PreparedStatement pst = d.con.prepareStatement("update sick_leave set level = '" + cbx.getValue() + "' where ID = '" + i + "'");
                pst.executeUpdate();
                String query = "insert into level (MRN,problem,num_of_days,value,old_level,new_level,user,date) values ('" + mr + "','" + prob + "','" + numDays + "','" + val + "','" + lev + "','" + cbx.getValue() + "','" + user + "','"+dt+"')";
                d.stmt.executeUpdate(query);
                new Alert(Alert.AlertType.INFORMATION, "Level has been changed").showAndWait();
                tableView.getItems().clear();
                getDataFromSql();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    ////////////////////////////////////
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(user);
        cbx.getItems().addAll("Trusted", "High", "Medium", "Low","Non Conclusive");
        txtMrn.setFocusTraversable(false);
    }

    public void getDataFromSql() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("select * from sick_leave where MRN = '" + txtMrn.getText().trim() + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                /// create java class first 
                /// insert into table view
                data.addAll(new Evaluate_Rec(r.getInt("ID"), r.getInt("MRN"), r.getString("Problem"), r.getInt("Num_of_Days"), r.getInt("value"), r.getString("level")));
                colId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colMrn.setCellValueFactory(new PropertyValueFactory<>("mrn"));
                colProblem.setCellValueFactory(new PropertyValueFactory<>("problem"));
                colNumOfDays.setCellValueFactory(new PropertyValueFactory<>("numOfDays"));
                colValue.setCellValueFactory(new PropertyValueFactory<>("value"));
                colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));

            }
            tableView.setItems(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
