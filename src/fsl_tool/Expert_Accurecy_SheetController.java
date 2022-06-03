package fsl_tool;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Expert_Accurecy_SheetController implements Initializable {

    int countMed = 0;
    int countLow = 0;
    int countHigh = 0;
    int countTrus = 0;

    int SickLeaveLevel = 0;
    int SickLeaveLevel1 = 0;

    int non_Con = 0;
    ////////////  count as total from sivk_leave - count as total from level * 100/100
    int newLevel = 0;
    /////////// Month //////////////
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

    /////////////////////////////////
    int count = 0;

    ////
    int trusted = 0;
    int medium = 0;
    int low = 0;
    int high = 0;
    int normal = 0;

    /////////////  
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
            Expert_LoginController.user = user;
            Parent p = FXMLLoader.load(getClass().getResource("Expert_Login.fxml"));
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

    @FXML
    private AnchorPane root;

    @FXML
    private PieChart myPieChart;

    @FXML
    private BarChart<?, ?> myBarChart;

    @FXML
    private BarChart<?, ?> myBarChart1;

    @FXML
    private PieChart myPieChart1;

    private ObservableList data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getMed();

        System.out.println(user);
        //getPercentageFromNewLevel();
        getMergeOfLevelPercentageUpdated();
        getMergeOfLevel();
        displayBarChartLast();
        getAllMonths();

    }

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

    /// JAN //////
    public void getJanMonth() {

        try {

            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(user) as TOTAL FROM level Where Month(date)='01' and user = '" + user + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                jan = r.getInt("TOTAL");
            }
            jan = jan / newLevel * 100;
        } catch (Exception e) {

        }
        System.out.println("COUNT IS : " + jan);

    }

    //// feb ///////
    public void getFebMonth() {

        try {

            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(user) as TOTAL FROM level Where Month(date)='02' and user = '" + user + "'");
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
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(user) as TOTAL FROM level Where Month(date)='03' and user = '" + user + "'");
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
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(user) as TOTAL FROM level Where Month(date)='04' and user = '" + user + "'");
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
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(user) as TOTAL FROM level Where Month(date)='05' and user = '" + user + "'");
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
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(user) as TOTAL FROM level Where Month(date)='06' and user = '" + user + "'");
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
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(user) as TOTAL FROM level Where Month(date)='07' and user = '" + user + "'");
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
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(user) as TOTAL FROM level Where Month(date)='08' and user = '" + user + "'");
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
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(user) as TOTAL FROM level Where Month(date)='09' and user = '" + user + "'");
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
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(user) as TOTAL FROM level Where Month(date)='10' and user = '" + user + "'");
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
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(user) as TOTAL FROM level Where Month(date)='11' and user = '" + user + "'");
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
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(user) as TOTAL FROM level Where Month(date)='12' and user = '" + user + "'");
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
        //apr = apr / (newLevel * 100);
        // System.out.println("percentage of april" + apr);
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
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL FROM level Where new_level = '" + "Trusted" + "' and user = '" + user + "'");
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
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL FROM level Where new_level = '" + "Medium" + "' and user = '" + user + "'");
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
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL FROM level Where new_level = '" + "Low" + "' and user = '" + user + "'");
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
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL FROM level Where new_level = '" + "High" + "' and user = '" + user + "'");
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
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL FROM level Where old_level = '" + "Non Conclusive" + "' and user = '" + user + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                normal = r.getInt("Total");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(normal);
    }

    ///////// non conclusive 
    public void getNonConclusiveSick() {
        //getNormaldLevel();
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(level) as TOTAL FROM sick_leave where level = '" + "Non Conclusive" + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                non_Con += r.getInt("TOTAL");
            }

            System.out.println("Non Con " + non_Con);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /////// Merge Level /////////
    public void getMergeOfLevel() {

        getTotalFromSickLeave1();
        getNonConclusiveSick();

        int i = non_Con;
        ObservableList<PieChart.Data> pie = FXCollections.observableArrayList();
        pie.add(new PieChart.Data("Total Of Sick leave", SickLeaveLevel1));
        pie.add(new PieChart.Data("Total Of Non Conclusive", i));

        myPieChart.setData(pie);
        myPieChart.setStartAngle(180);

        pie.forEach(data
                -> data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty(), ""
                        )
                )
        );

    }

    /// Percntage for bar chart from level table first graph
    public void getPercentageFromNewLevel() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL FROM level");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                newLevel += r.getInt("TOTAL");
            }

            System.out.println("new level full value " + newLevel);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ///////////////// PieChart Percentage Of Updated Sick Leave /////////////
    public void getTrustedLevelPercentageUpdated() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL FROM level Where new_level = '" + "Trusted" + "'");
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
    public void getMediumdLevelPercentageUpdated() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL FROM level Where new_level = '" + "Medium" + "'");
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
    public void getLowdLevelPercentageUpdated() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL FROM level Where new_level = '" + "Low" + "'");
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
    public void getHighdLevelPercentageUpdated() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL FROM level Where new_level = '" + "High" + "'");
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
    public void getNormaldLevelPercentageUpdated() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL FROM level Where new_level = '" + "Non Conclusive" + "'");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                normal = r.getInt("Total");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(normal);
    }

    /////// Merge Level PieChart Updated Sick Leave /////////
    public void getMergeOfLevelPercentageUpdated() {
        getTotalFromSickLeave();

        getPercentageFromNewLevel();

        getTrustedLevelPercentageUpdated();
        getMediumdLevelPercentageUpdated();
        getLowdLevelPercentageUpdated();
        getHighdLevelPercentageUpdated();
        getNormaldLevelPercentageUpdated();

        /*
        int t = ((trusted * 100) / newLevel);
        int h = ((high * 100) / newLevel);
        int m = ((medium * 100) / newLevel);
        int l = ((low * 100) / newLevel);
        int n = ((normal * 100) / newLevel);
        
         */
        int updated = ((newLevel * 100) / SickLeaveLevel);
        // int min = SickLeaveLevel - newLevel ;
        int p = 100 - updated;

        ObservableList<PieChart.Data> pie1 = FXCollections.observableArrayList();

        pie1.add(new PieChart.Data("Percentage Of not Updated Sick Leave", p));
        pie1.add(new PieChart.Data("Percentage Of Updated Sickleave ", updated));
        //  pie1.add(new PieChart.Data("Medium", m));
        // pie1.add(new PieChart.Data("Low", l));
        // pie1.add(new PieChart.Data("Normal", n));
        myPieChart1.setData(pie1);
        myPieChart1.setStartAngle(180);

        pie1.forEach(data
                -> data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty(), "%"
                        )
                )
        );

    }

    public void getTotalFromSickLeave() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(level) as TOTAL from sick_leave");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                SickLeaveLevel += r.getInt("TOTAL");
            }
            System.out.println("total sick : " + SickLeaveLevel);
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }

    }

    public void getTotalFromSickLeave1() {
        try {
            DB d = new DB();
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(level) as TOTAL from sick_leave");
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                SickLeaveLevel1 += r.getInt("TOTAL");
            }
            System.out.println("total sick : " + SickLeaveLevel);
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }

    }

    //////////  med1
    public void getMed() {
        try {
            DB d = new DB();
            // PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL from leave where old_level = '"+"Normal"+"' and new_level = '"+"Medium"+"'");
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL FROM level Where new_level ='" + "Medium" + "' and old_level = '" + "Non Conclusive" + "'");

            ResultSet r = pst.executeQuery();
            while (r.next()) {
                countMed += r.getInt("TOTAL");
            }
            System.out.println("total getMed : " + countMed);
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
    }

    //////////////////////////////
    //////////  low2
    public void getLow() {
        try {
            DB d = new DB();
            // PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL from leave where old_level = '"+"Normal"+"' and new_level = '"+"Medium"+"'");
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL FROM level Where new_level ='" + "Low" + "' and old_level = '" + "Non Conclusive" + "'");

            ResultSet r = pst.executeQuery();
            while (r.next()) {
                countLow += r.getInt("TOTAL");
            }
            System.out.println("total getMed : " + countLow);
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
    }
    
    
    
     ////////////// high3
     public void getHigh() {
        try {
            DB d = new DB();
            // PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL from leave where old_level = '"+"Normal"+"' and new_level = '"+"Medium"+"'");
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL FROM level Where new_level ='" + "High" + "' and old_level = '" + "Non Conclusive" + "'");

            ResultSet r = pst.executeQuery();
            while (r.next()) {
                countHigh += r.getInt("TOTAL");
            }
            System.out.println("total getMed : " + countHigh);
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
    }
     
      ////////////// high3
     public void getTrus() {
        try {
            DB d = new DB();
            // PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL from leave where old_level = '"+"Normal"+"' and new_level = '"+"Medium"+"'");
            PreparedStatement pst = d.con.prepareStatement("SELECT COUNT(new_level) as TOTAL FROM level Where new_level ='" + "Trusted" + "' and old_level = '" + "Non Conclusive" + "'");

            ResultSet r = pst.executeQuery();
            while (r.next()) {
                countTrus += r.getInt("TOTAL");
            }
            System.out.println("total getMed : " + countTrus);
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
    }
     

    ///////////Last BarChart 
    public void displayBarChartLast() {
        getLow();
        getMed();
        getHigh();
        getTrus();

        //apr = apr / (newLevel * 100);
        // System.out.println("percentage of april" + apr);
        XYChart.Series series = new XYChart.Series<>();
        series.getData().addAll(new XYChart.Data<>("Low", countLow));
        series.getData().addAll(new XYChart.Data<>("Medium", countMed));
        series.getData().addAll(new XYChart.Data<>("High", countHigh));
        series.getData().addAll(new XYChart.Data<>("Trusted", countTrus));

        myBarChart1.getData().add(series);
    }

}
