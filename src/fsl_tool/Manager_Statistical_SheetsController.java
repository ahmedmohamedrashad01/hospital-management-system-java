package fsl_tool;

import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Manager_Statistical_SheetsController implements Initializable {

    
  
    
    
    
    public static String user = "";
    String doctorCode = "";

    int jan = 0;
    int feb = 0;
    int mar = 0;
    int apr = 0;
    int may = 0;
    int jun = 0;
    int jul = 0;
    int aug = 0;
    int sep = 0;
    int oct = 0;
    int nov = 0;
    int dec = 0;

    int trusted = 0;
    int medium = 0;
    int low = 0;
    int high = 0;
    int normal = 0;

    
    
    ///////////////////////////////////////
    
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
    
    
    
    //////////////////////////////////////////////
    @FXML
    private AnchorPane root;

    @FXML
    private PieChart myPieChart;

    @FXML
    private BarChart<?, ?> myBarChart;

    @FXML
    private JFXComboBox<String> cbx;

    @FXML
    void cbxAction(ActionEvent event) {
        cbxSelectedItem();
        myBarChart.getData().clear();
        myPieChart.getData().clear();
        getAllMonths();
        getMergeOfLevel();

    }

    private ObservableList data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getDoctorName();

    }

    //// CBX 
    public void getDoctorName() {
        ArrayList doctorName = new ArrayList();
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT * FROM employee");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                doctorName.add(r.getString("Name"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        cbx.getItems().addAll(doctorName);

    }

    // CBX SELECT 
    public void cbxSelectedItem() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT * FROM employee WHERE Name = '" + cbx.getSelectionModel().getSelectedItem() + "'");
            ResultSet r = pst.executeQuery();
            if (r.next()) {
                doctorCode = r.getString("employeeCode");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("code is " + doctorCode);
    }

    /////////////////////////////////////
    /////////////////////////////////
    ////////////////////////////////////
    public void getAllMonths() {
        getJanMonth();
        getFebMonth();
        getMarMonth();
        getAprMonth();
        getMayMonth();
        getJunMonth();
        getJulMonth();
        getAugMonth();
        getSepMonth();
        getOctMonth();
        getNovMonth();
        getDecMonth();

        displayBarChart();

    }

    public void getJanMonth() {

        try {

            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(employee) as TOTAL FROM sick_leave Where Month(Start_Date)='01' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                jan = r.getInt("TOTAL");
            }
        } catch (Exception e) {

        }
        System.out.println("COUNT IS : " + jan);

    }

    //// feb ///////
    public void getFebMonth() {

        try {

            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(employee) as TOTAL FROM sick_leave Where Month(Start_Date)='02' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                feb = r.getInt("TOTAL");
            }
        } catch (Exception e) {

        }
        System.out.println("COUNT IS : " + feb);

    }

    ///// march ////
    public void getMarMonth() {

        try {

            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(employee) as TOTAL FROM sick_leave Where Month(Start_Date)='03' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                mar = r.getInt("TOTAL");
            }
        } catch (Exception e) {

        }
        System.out.println("COUNT IS : " + mar);

    }

    //// apr /////////
    public void getAprMonth() {
        try {

            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(employee) as TOTAL FROM sick_leave Where Month(Start_Date)='04' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                apr = r.getInt("TOTAL");
            }
        } catch (Exception e) {

        }
        System.out.println("COUNT IS : " + apr);

    }

    //////////////  may ////////////////////
    public void getMayMonth() {
        try {

            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(employee) as TOTAL FROM sick_leave Where Month(Start_Date)='05' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                may = r.getInt("TOTAL");
            }
        } catch (Exception e) {

        }
        System.out.println("COUNT IS : " + may);

    }

    ////////////// jun ///////////
    public void getJunMonth() {
        try {

            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(employee) as TOTAL FROM sick_leave Where Month(Start_Date)='06' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                jun = r.getInt("TOTAL");
            }
        } catch (Exception e) {

        }
        System.out.println("COUNT IS : " + jun);

    }

    //// jul ////
    public void getJulMonth() {
        try {

            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(employee) as TOTAL FROM sick_leave Where Month(Start_Date)='07' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                jul = r.getInt("TOTAL");
            }
        } catch (Exception e) {

        }
        System.out.println("COUNT IS : " + jul);

    }

    ////  aug ////
    public void getAugMonth() {
        try {

            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(employee) as TOTAL FROM sick_leave Where Month(Start_Date)='08' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                aug = r.getInt("TOTAL");
            }
        } catch (Exception e) {

        }
        System.out.println("COUNT IS : " + aug);

    }

    public void getSepMonth() {
        try {

            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(employee) as TOTAL FROM sick_leave Where Month(Start_Date)='09' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                sep = r.getInt("TOTAL");
            }
        } catch (Exception e) {

        }
        System.out.println("COUNT IS : " + sep);

    }

    //// oct 
    public void getOctMonth() {
        try {

            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(employee) as TOTAL FROM sick_leave Where Month(Start_Date)='10' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                oct = r.getInt("TOTAL");
            }
        } catch (Exception e) {

        }
        System.out.println("COUNT IS : " + oct);

    }

    ///  nov ///
    public void getNovMonth() {
        try {

            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(employee) as TOTAL FROM sick_leave Where Month(Start_Date)='11' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                nov = r.getInt("TOTAL");
            }
        } catch (Exception e) {

        }
        System.out.println("COUNT IS : " + nov);

    }

    ///// dec ///
    public void getDecMonth() {
        try {

            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(employee) as TOTAL FROM sick_leave Where Month(Start_Date)='12' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                dec = r.getInt("TOTAL");
            }
        } catch (Exception e) {

        }
        System.out.println("COUNT IS : " + dec);

    }

    /////// Display BarChart
    public void displayBarChart() {
        XYChart.Series series = new XYChart.Series<>();
        series.getData().addAll(new XYChart.Data<>("Jan", jan));
        series.getData().addAll(new XYChart.Data<>("Feb", feb));
        series.getData().addAll(new XYChart.Data<>("Mar", mar));
        series.getData().addAll(new XYChart.Data<>("Apr", apr));
        series.getData().addAll(new XYChart.Data<>("May", may));
        series.getData().addAll(new XYChart.Data<>("Jun", jun));
        series.getData().addAll(new XYChart.Data<>("Jul", jul));
        series.getData().addAll(new XYChart.Data<>("Aug", aug));
        series.getData().addAll(new XYChart.Data<>("Sep", sep));
        series.getData().addAll(new XYChart.Data<>("Oct", oct));
        series.getData().addAll(new XYChart.Data<>("Nov", nov));
        series.getData().addAll(new XYChart.Data<>("Dec", dec));

        myBarChart.getData().add(series);
    }

    ///////////////////////////////////////
    /////////// PieChart //////////////////
    public void getTrustedLevel() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(level) as TOTAL FROM sick_leave Where level = '" + "Trusted" + "' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                trusted = r.getInt("Total");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(trusted);
    }

    /////  medium ////
    public void getMediumdLevel() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(level) as TOTAL FROM sick_leave Where level = '" + "Medium" + "' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                medium = r.getInt("Total");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(medium);
    }

    ///// LOW //////////
    public void getLowdLevel() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(level) as TOTAL FROM sick_leave Where level = '" + "Low" + "' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                low = r.getInt("Total");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(low);
    }

    ////////////// high //////////////
    public void getHighdLevel() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(level) as TOTAL FROM sick_leave Where level = '" + "High" + "' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                high = r.getInt("Total");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(high);
    }

    /////////// Normal /////////////
    public void getNormaldLevel() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(level) as TOTAL FROM sick_leave Where level = '" + "Non Conclusive" + "' and employee = '" + doctorCode + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                normal = r.getInt("Total");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(normal);
    }

    /////// Merge Level /////////
    public void getMergeOfLevel() {
        getTrustedLevel();
        getMediumdLevel();
        getLowdLevel();
        getHighdLevel();
        getNormaldLevel();

        ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
        pie.add(new PieChart.Data("Trusted", trusted));
        pie.add(new PieChart.Data("High", high));
        pie.add(new PieChart.Data("Medium", medium));
        pie.add(new PieChart.Data("Low", low));
        pie.add(new PieChart.Data("Non Conclusive", normal));
        myPieChart.setData(pie);
        myPieChart.setStartAngle(90);

    }

}
