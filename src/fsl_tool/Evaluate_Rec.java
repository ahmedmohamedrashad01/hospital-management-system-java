
package fsl_tool;

public class Evaluate_Rec {
    
    int id ;
    int mrn;
    String problem;
    int numOfDays;
    int value;
    String level;

    public Evaluate_Rec(int id, int mrn, String problem, int numOfDays, int value, String level) {
        this.id = id;
        this.mrn = mrn;
        this.problem = problem;
        this.numOfDays = numOfDays;
        this.value = value;
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

    public int getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(int numOfDays) {
        this.numOfDays = numOfDays;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

   
}
