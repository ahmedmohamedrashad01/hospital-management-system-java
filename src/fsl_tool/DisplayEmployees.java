
package fsl_tool;


public class DisplayEmployees {
    int id ;
    String employeeCode;
    String name;
    String dpt;

    public DisplayEmployees(int id, String employeeCode, String name, String dpt) {
        this.id = id;
        this.employeeCode = employeeCode;
        this.name = name;
        this.dpt = dpt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDpt() {
        return dpt;
    }

    public void setDpt(String dpt) {
        this.dpt = dpt;
    }
    
            
}
