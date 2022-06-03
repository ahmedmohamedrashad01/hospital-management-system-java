
package fsl_tool;


public class SickLeaveTable {
    
    int numberOfVisit;
    String VT;
    String department;

    public int getNumberOfVisit() {
        return numberOfVisit;
    }

    public void setNumberOfVisit(int numberOfVisit) {
        this.numberOfVisit = numberOfVisit;
    }

    public String getVT() {
        return VT;
    }

    public void setVT(String VT) {
        this.VT = VT;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public SickLeaveTable(int numberOfVisit, String VT, String department) {
        this.numberOfVisit = numberOfVisit;
        this.VT = VT;
        this.department = department;
    }
    
}
