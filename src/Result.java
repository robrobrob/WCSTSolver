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
                "numCorrect=" + numCorrect +
                ", maxNumCorrectInARow=" + maxNumCorrectInARow +
                ", numWrong=" + numWrong +
                ", maxNumWrongInARow=" + maxNumWrongInARow +
                ", numValidRuns=" + numValidRuns +
                ", totalCards=" + totalCards +
                '}';
    }
}
