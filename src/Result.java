public class Result {
    int numCorrect;
    int maxNumCorrectInARow;
    int numWrong;
    int maxNumWrongInARow;
    int numValidRuns;
    int totalCards;

    public Result(int numCorrect, int maxNumCorrectInARow, int numWrong, int maxNumWrongInARow, int numValidRuns) {
        this.numCorrect = numCorrect;
        this.maxNumCorrectInARow = maxNumCorrectInARow;
        this.numWrong = numWrong;
        this.maxNumWrongInARow = maxNumWrongInARow;
        this.numValidRuns = numValidRuns;
    }

    @Override
    public String toString() {
        totalCards = numCorrect + numWrong;
        return "Result{" +
                " Number of times Solver performed correct sorting operations = " + numCorrect +
                ", Number of times Solver was correct in a row = " + maxNumCorrectInARow +
                ", Number of times Solver performed incorrect sorting operations = " + numWrong + "\n" +
                ", Number of times Solver was incorrect in a row = " + maxNumWrongInARow +
                ", Number of Valid Runs completed = " + numValidRuns +
                ", Number of Cards seen = " + totalCards +
                '}';
    }
}
