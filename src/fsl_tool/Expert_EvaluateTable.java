
package fsl_tool;

import java.util.Date;


public class Expert_EvaluateTable {
    int id;
    int mrn;
    String problem;
    String dishTime;
    int numOfDays;
    String startDate;
    String vissitType;
    String EmployeeCode;
    String level;

    public Expert_EvaluateTable(int id, int mrn, String problem, String dishTime, int numOfDays, String startDate, String vissitType, String EmployeeCode, String level) {
        this.id = id;
        this.mrn = mrn;
        this.problem = problem;
        this.dishTime = dishTime;
        this.numOfDays = numOfDays;
        this.startDate = startDate;
        this.vissitType = vissitType;
        this.EmployeeCode = EmployeeCode;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMrn() {
        return mrn;
    }

    public void setMrn(int mrn) {
        this.mrn = mrn;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDishTime() {
        return dishTime;
    }

    public void setDishTime(String dishTime) {
        this.dishTime = dishTime;
    }

    public int getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(int numOfDays) {
        this.numOfDays = numOfDays;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getVissitType() {
        return vissitType;
    }

    public void setVissitType(String vissitType) {
        this.vissitType = vissitType;
    }

    public String getEmployeeCode() {
        return EmployeeCode;
    }

    public void setEmployeeCode(String EmployeeCode) {
        this.EmployeeCode = EmployeeCode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

  
   
}
