import java.util.ArrayList;
import java.util.Random;

public class Game {
    ArrayList<Card> deck = new ArrayList<Card>();
    int numCardsInDeck;
    int numRunsThroughDeck;
    int numCardsCorrectTillChange;
    Random random = new Random();
    ArrayList<Card> options;
    Solver solver;

    int numCorrect;
    int numCorrectInARow;
    int maxNumCorrectInARow;
    int numWrong;
    int numWrongInARow;
    int maxNumWrongInARow;
    int numValidRuns;
    boolean correct;
    int choice;
    Card toSort;
    Match rule;
    Result result;

	final String ANSI_RESET = "\u001B[0m";
	final String ANSI_WHITE = "\u001B[97m";
	final String ANSI_GREY_BACKGROUND = "\u001B[48;5;242m";
	final String ANSI_CYAN_BACKGROUND = "\u001B[46m";

    public Game(int numCardsInDeck, int numRunsThroughDeck, int numCardsCorrectTillChange, boolean standardOptions) {
        this.numCardsInDeck = numCardsInDeck;
        this.numRunsThroughDeck = numRunsThroughDeck;
        this.numCardsCorrectTillChange = numCardsCorrectTillChange;
        options = new ArrayList<Card>();
        options.add(new Card(0,Shape.TRIANGLE,Color.RED));
        options.add(new Card(1,Shape.STAR,Color.GREEN));
        options.add(new Card(2,Shape.CROSS,Color.YELLOW));
        options.add(new Card(3,Shape.CIRCLE, Color.BLUE));
        deck = generateDeck(numCardsInDeck);
        numCorrect = 0;
        numCorrectInARow = 0;
        maxNumCorrectInARow = 0;
        numWrong = 0;
        numWrongInARow = 0;
        maxNumWrongInARow = 0;
        numValidRuns = 0;
        correct = false;
        choice = 0;
        rule = Match.NUMBER;
        result = test(standardOptions);
        System.out.println(result);
    }


    /**
     * Returns a deck of Cards of size i
     *
     * @param i The size of the deck to generate
     * @return  A deck of Cards with a size of i
     */
    private ArrayList<Card> generateDeck(int i) {
        ArrayList<Card> nDeck = new ArrayList<Card>();
        for (int x = 0; x<i;x++){
            nDeck.add(new Card(random.nextInt(3),Shape.values()[random.nextInt(4)],Color.values()[random.nextInt(4)]));
        }
        return nDeck;
    }


    /**
     * Plays the game and tests a solver, returning the Results of the test. Either plays with the standard option cards or with randomly generated ones each time.
     *
     * @param standardOptions   Whether to use the standard option cards or use randomly generated ones
     * @return 					The Results of the test
     */
    private Result test(boolean standardOptions) {
        rule = Match.values()[random.nextInt(3)];
        if (standardOptions){
            solver = new Solver(options);
            for (int z  = 0; z<numRunsThroughDeck; z++) { //Run through the deck
                for (int i = 0; i < deck.size(); i++) { //Run through the cards in the deck
                    ArrayList<Integer> possibilities = new ArrayList<Integer>();
                    for (int y = 0;y<deck.size();y++) { //Generate the possible cards to chose from, so as to not choose the same card as the last card
                        if (y != i) {
                            possibilities.add(y);
                        }
                    }
                    if(z == 0 && i == 0) { //If this is the first card of the test
                        correct = false;
                    }
                    toSort = deck.get(possibilities.get(random.nextInt(possibilities.size()))); //Get the card to be sorted
                    System.out.println("---- " + ANSI_GREY_BACKGROUND + ANSI_WHITE + "Game" + ANSI_RESET + " { Times through deck: " + z + " Card in Deck: "+ Math.addExact(i,1) + " } ----");
                    choice = solver.ask(correct,toSort); //Ask the Solver to sort a card and give tell it if the last guess was correct
                    if (matchesRule(choice, options, toSort, rule)) { //If the solver was correct
						correct = true;
                        numWrongInARow = 0;
                        numCorrect++;
                        numCorrectInARow++;
                        if(numCorrectInARow > maxNumCorrectInARow) { //If the numCorrectInARow is higher than the highest it has ever previously been
                        	maxNumCorrectInARow = numCorrectInARow;
						}
                        if(numCorrectInARow > 10) {
                            if(random.nextInt(2) == 1) {
                                rule = getNewRule(rule);
                                System.out.println(ANSI_CYAN_BACKGROUND + ANSI_WHITE + "Tester" + ANSI_RESET +" { Valid Run Completed - Rule Change }");
                                numValidRuns++;
                                if(numValidRuns > 8) {
                                    break;
                                }
                            }
                        }
                    } else {
						correct = false;
                        numCorrectInARow = 0;
                        numWrong++;
                        numWrongInARow++;
						if(numWrongInARow > maxNumWrongInARow) {
							maxNumWrongInARow = numWrongInARow;
						}
                    }
                    System.out.println("//// " + ANSI_GREY_BACKGROUND + ANSI_WHITE + "Game" + ANSI_RESET + " { END TURN } ////");
                }
                if (numValidRuns > 8) {
					System.out.println("//// " + ANSI_GREY_BACKGROUND + ANSI_WHITE + "Game" + ANSI_RESET + " { END TURN } ////");
                    break;
                }
            }
        } else { //Generate options each time
            System.out.println("VARIANT");
            solver = new Solver();
            for(int z  = 0; z<numRunsThroughDeck; z++) {
                for (int i = 0; i < deck.size(); i++) {
                    ArrayList<Integer> possibilities = new ArrayList<Integer>();
                    for(int y = 0;y<deck.size();y++) {
                        if(y != i) {
                            possibilities.add(y);
                        }
                    }
                    if(z == 0 && i == 0) {
                        correct = false;
                    }
                    toSort = deck.get(possibilities.get(random.nextInt(possibilities.size())));
                    System.out.println("---- " + ANSI_GREY_BACKGROUND + ANSI_WHITE + "Game" + ANSI_RESET + " { Times through deck: " + z + " Card in Deck: "+ Math.addExact(i,1) + " } ----");
                    choice = solver.ask(correct,toSort,options);
                    if(matchesRule(choice, options, toSort, rule)){
                        numWrongInARow = 0;
                        numCorrect++;
                        numCorrectInARow++;
						if(numCorrectInARow > maxNumCorrectInARow) {
							maxNumCorrectInARow = numCorrectInARow;
						}
                        if(numCorrectInARow > 10) {
                            if(random.nextInt(2) == 1) {
                                rule = getNewRule(rule);
								System.out.println(ANSI_CYAN_BACKGROUND + ANSI_WHITE + "Tester" + ANSI_RESET +" { Valid Run Completed - Rule Change }");
                                numValidRuns++;
                                if(numValidRuns > 8) {
                                    break;
                                }
                            }
                        }
                    } else {
                        numCorrectInARow = 0;
                        numWrong++;
                        numWrongInARow++;
						if(numWrongInARow > maxNumWrongInARow) {
							maxNumWrongInARow = numWrongInARow;
						}
                    }
					System.out.println("//// " + ANSI_GREY_BACKGROUND + ANSI_WHITE + "Game" + ANSI_RESET + " { END TURN } ////");
                }
                if(numValidRuns > 8) {
					System.out.println("//// " + ANSI_GREY_BACKGROUND + ANSI_WHITE + "Game" + ANSI_RESET + " { END TURN } ////");
                    break;
                }
            }

        }

        return new Result(numCorrect,maxNumCorrectInARow,numWrong,maxNumWrongInARow,numValidRuns);
    }

	/**
	 * Returns a new Match rule for the test to use. Takes a Match rule and excludes it from the selection process.
	 *
	 * @param iRule The Match rule to exclude
	 * @return		The new, randomly generated Match rule
	 */

	private Match getNewRule(Match iRule) {
        Match nRule;
        if(iRule == Match.COLOR) {
            if(random.nextInt(2) == 1){
                nRule = Match.NUMBER;
            } else {
                nRule = Match.SHAPE;
            }
        } else if(iRule == Match.NUMBER) {
            if(random.nextInt(2) == 1){
                nRule = Match.COLOR;
            } else {
                nRule = Match.SHAPE;
            }
        } else if(iRule == Match.SHAPE) {
            if(random.nextInt(2) == 1){
                nRule = Match.NUMBER;
            } else {
                nRule = Match.COLOR;
            }
        } else {
            nRule = Match.COLOR;
        }
        return nRule;
    }

	/**
	 * Tests if the given choice of Card within the options fits the Match rule, for the provided card to sort
	 * @param iChoice	The choice from the iOptions array of Cards
	 * @param iOptions	The array of Cards to use as matching options
	 * @param card		The Card to sort
	 * @param iRule		The Match rule being used
	 * @return			If the choice was correct
	 */
    private boolean matchesRule(int iChoice, ArrayList<Card> iOptions, Card card, Match iRule) {
        boolean right = false;
        final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
        final String ANSI_RED_BACKGROUND = "\u001B[41m";

        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_WHITE + "Tester" + ANSI_RESET + " {");
		System.out.println(" Rule: " + iRule);
        System.out.println(" Options to Match from: ");
        iOptions.forEach(cards -> System.out.print(" " +cards.toGraphicalString()));
        System.out.println(
                "\n" + " Card to Sort: " + card.toGraphicalString());
        System.out.println(
                " Solver matched to: " +
                        iOptions.get(iChoice).toGraphicalString());

        switch (iRule) {
            case COLOR:
                if(iOptions.get(iChoice).getColor() == toSort.getColor()){
                    right = true;
                    System.out.println(
                            ANSI_GREEN_BACKGROUND + ANSI_WHITE + " Result: Correct " + ANSI_RESET);
                }
                break;
            case SHAPE:
                if(iOptions.get(iChoice).getShape() == toSort.getShape()){
                    right = true;
                    System.out.println(
                            ANSI_GREEN_BACKGROUND + ANSI_WHITE + " Result: Correct " + ANSI_RESET);
                }
                break;
            case NUMBER:
                if(iOptions.get(iChoice).getNumber() == toSort.getNumber()){
                    right = true;
                    System.out.println(
                            ANSI_GREEN_BACKGROUND + ANSI_WHITE + " Result: Correct " + ANSI_RESET);
                }
                break;
        }
        if (!right) {
            System.out.println(
                    ANSI_RED_BACKGROUND + ANSI_WHITE + " Result: Incorrect " + ANSI_RESET);
        }
		System.out.println("}");
        return right;
    }

}
