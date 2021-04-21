public class Guess {
    Match rule;
    boolean wasCorrect;

    public Guess(Match rule, boolean wasCorrect) {
        this.rule = rule;
        this.wasCorrect = wasCorrect;
    }

    public Match getRule() {
        return rule;
    }

    public boolean isWasCorrect() {
        return wasCorrect;
    }
}
