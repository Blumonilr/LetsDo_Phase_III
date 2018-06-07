package YingYingMonster.LetsDo_Phase_III.model;

public enum CommitResult {
    passed("passed"), rejected("rejected");

    private String  val;
    private double accuracy;

    public String getVal() {
        return val;
    }

    public double getAccuracy() {

        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    CommitResult(String val) {

        this.val = val;
    }

    public boolean equals(CommitResult commitResult) {
        return this.val.equals(commitResult.getVal());
    }
}
