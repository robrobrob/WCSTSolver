import java.util.ArrayList;
import java.util.Random;

public class Solver {
    boolean speak = false;
    Match perceivedRule;
    Match oldRule;
    ArrayList<Guess> lastPerceivedRule;
    ArrayList<Card> options;
    Random random = new Random();
    final int lookBack;
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    final String ANSI_WHITE = "\u001B[97m";

    //Constructor

    public Solver(){
        lastPerceivedRule = new ArrayList<Guess>();
        perceivedRule = Match.values()[random.nextInt(3)];
        oldRule = perceivedRule;
        lookBack = 2;
    }

	//Helper Methods

	/**
	 * Asks the Solver to choose the correct card from a set of Cards and lets the Solver know whether its last choice was correct.
	 * The Solver will use a perceived rule to choose the correct card from the provided options
	 * @param lastCorrect Was the Solver correct last time
	 * @param card The Card to match to a card within the provided options
	 * @param options The set of Cards to match to
	 * @return the index of the Card (within the set of options) that the Solver chooses to match to
	 */

    public int ask(boolean lastCorrect, Card card, ArrayList<Card> options) {
        this.options = options;
        speakSolverStart();
        int choice = 0;
        if(lastCorrect) { //Correct
            speakCorrect(perceivedRule); //@SPEAK
            lastPerceivedRule.add(new Guess(oldRule,true));
            choice = setChoice(perceivedRule,card,options); //Set Choice
            oldRule = perceivedRule;
            speakSolverEnd(); //@SPEAK
            return choice;
        } else { //Not Correct
            lastPerceivedRule.add(new Guess(oldRule,false));
            if(lastPerceivedRule.size()>2) { //If we are farther in
                speakWrong(perceivedRule); //@SPEAK
                if(!lastPerceivedRule.get(lastPerceivedRule.size()-lookBack).isWasCorrect()) {
                    speakWrongTwoRoundsAgo(lastPerceivedRule); //@SPEAK
                    perceivedRule = deduceRule(lastPerceivedRule); //Deduce
                    choice = setChoice(perceivedRule,card,options); //Set Choice
                    oldRule = perceivedRule;
                    speakSolverEnd(); //@SPEAK
                    return choice;
                } else { //Random guess since we were only wrong once
                    perceivedRule = guessRule(perceivedRule); //Guess
                    choice = setChoice(perceivedRule,card,options); //Set Choice
                    oldRule = perceivedRule;
                    speakSolverEnd(); //@SPEAK
                    return choice;
                }
            } else { //If we are not in far enough
                speakWrongWithExcuse(); //@SPEAK
                perceivedRule = guessRule(perceivedRule); //Guess
                choice = setChoice(perceivedRule,card,options); //Set Choice
                oldRule = perceivedRule;
                speakSolverEnd(); //@SPEAK
                return choice;
            }
        }
    }

    private int setChoice(Match perceivedRule, Card card, ArrayList<Card> options) { //Set Choice
        int choice = 0;
        switch (perceivedRule) {
            case COLOR:
                for (Card c : options) {
                    if (c.getColor() == card.getColor()) {
                        choice = options.indexOf(c);
                    }
                }
                break;
            case SHAPE:
                for (Card c : options) {
                    if (c.getShape() == card.getShape()) {
                        choice = options.indexOf(c);
                    }
                }
                break;
            case NUMBER:
                for (Card c : options) {
                    if (c.getNumber() == card.getNumber()) {
                        choice = options.indexOf(c);
                    }
                }
                break;
        }
        return choice;
    }

    private Match guessRule(Match perceivedRule) { //Guess
        perceivedRule = switch (perceivedRule) {
            case COLOR -> random.nextInt(2) == 1 ? Match.NUMBER : Match.SHAPE;
            case SHAPE -> random.nextInt(2) == 1 ? Match.NUMBER : Match.COLOR;
            case NUMBER -> random.nextInt(2) == 1 ? Match.COLOR : Match.SHAPE;
        };
        speakNewGuessedRule(perceivedRule); //@SPEAK
        return perceivedRule;
    }

    private Match deduceRule(ArrayList<Guess> lastPerceivedRule) { //Deduce
        Match perceivedRule = Match.NUMBER;
        speakWrongOneRoundAgo(lastPerceivedRule); //@SPEAK
        perceivedRule = switch (lastPerceivedRule.get(lastPerceivedRule.size() - (lookBack)).getRule()) {
            case COLOR -> lastPerceivedRule.get(lastPerceivedRule.size() - (lookBack - 1)).getRule() == Match.SHAPE ? Match.NUMBER : Match.SHAPE;
            case SHAPE -> lastPerceivedRule.get(lastPerceivedRule.size() - (lookBack - 1)).getRule() == Match.NUMBER ? Match.COLOR : Match.NUMBER;
            case NUMBER -> lastPerceivedRule.get(lastPerceivedRule.size() - (lookBack - 1)).getRule() == Match.COLOR ? Match.SHAPE : Match.COLOR;
        };
        speakNewRule(perceivedRule); //@SPEAK
        return perceivedRule;
    }

	//Speaking methods

    private void speakSolverStart() {
        System.out.println(ANSI_PURPLE_BACKGROUND + ANSI_WHITE + "Solver" + ANSI_RESET + " {");
    }

    private void speakWrong(Match perceivedRule) {
        System.out.println("Last time the Solver was wrong and the last perceived rule was " + perceivedRule + ".");
    }
    
    private void speakWrongWithExcuse() {
        System.out.println("Last time the Solver was wrong, partially because it is too early to detect what the rule is. Also, the last perceived rule was " + perceivedRule + ".");
        System.out.println("The Solver has not gotten far enough into the test yet to make an informed decision.");
    }

    private void speakWrongTwoRoundsAgo(ArrayList<Guess> lastPerceivedRule) {
        System.out.println("Two rounds ago, the perceived rule was " + lastPerceivedRule.get(lastPerceivedRule.size()-lookBack).getRule() + " and my guess was " + lastPerceivedRule.get(lastPerceivedRule.size()-lookBack).isWasCorrect());
    }

    private void speakWrongOneRoundAgo(ArrayList<Guess> lastPerceivedRule) {
        System.out.println("One round ago, the perceived rule was " + lastPerceivedRule.get(lastPerceivedRule.size()-(lookBack-1)).getRule() + " and my guess was " + lastPerceivedRule.get(lastPerceivedRule.size()-(lookBack-1)).isWasCorrect() + ".");
    }

    private void speakNewRule(Match perceivedRule) {
        System.out.println("The new perceived rule will be " + perceivedRule + ".");
    }

    private void speakNewGuessedRule(Match perceivedRule) {
        System.out.println("The new, guessed rule will be " + perceivedRule + ".");
    }

    private void speakCorrect(Match perceivedRule) {
        System.out.println("Last time the Solver was correct and the last perceived rule was " + perceivedRule + ".");
    }

    private void speakSolverEnd() {
        System.out.print("}\n");
    }
}
