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

    /*
        generateDeck creates a deck of size i
     */
    private ArrayList<Card> generateDeck(int i) {
        ArrayList<Card> nDeck = new ArrayList<Card>();
        for(int x = 0; x<i;x++){
            nDeck.add(new Card(random.nextInt(3),Shape.values()[random.nextInt(4)],Color.values()[random.nextInt(4)]));
        }
        return nDeck;
    }
    /*
    test either generates options or uses the standard options
    */
    private Result test(boolean standardOptions) {
        rule = Match.values()[random.nextInt(3)];
        if(standardOptions){
            solver = new Solver(options);
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
					System.out.println("---- Game{ z:" + z + " i:"+ i +" Card to Sort: " + toSort + "} ----");
                    System.out.println("Options: " + options);
                    System.out.println("Game{ Using Rule: " + rule + " }");
                    choice = solver.ask(correct,toSort);
                    if(matchesRule(choice, options, toSort, rule)){
						System.out.println("Game{ Correct }");
						correct = true;
                        numWrongInARow = 0;
                        numCorrect++;
                        numCorrectInARow++;
                        System.out.println("numCorrectInARow:" + numCorrectInARow);
                        if(numCorrectInARow > maxNumCorrectInARow) {
                        	maxNumCorrectInARow = numCorrectInARow;
						}
                        if(numCorrectInARow > 10) {
                            if(random.nextInt(2) == 1) {
                                rule = getNewRule(rule);
                                System.out.println("RULE CHANGE");
                                numValidRuns++;
                                if(numValidRuns > 8) {
                                    break;
                                }
                            }
                        }
                    } else {
						System.out.println("Game{ Wrong }");
						correct = false;
                        numCorrectInARow = 0;
                        numWrong++;
                        numWrongInARow++;
                        System.out.println("numWrongInARow:" + numWrongInARow);
						if(numWrongInARow > maxNumWrongInARow) {
							maxNumWrongInARow = numWrongInARow;
						}
                    }
                }
                if(numValidRuns > 8) {
                    break;
                }
            }
        } else { //generate options each time
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
                }
                if(numValidRuns > 8) {
                    break;
                }
            }
        }

        return new Result(numCorrect,maxNumCorrectInARow,numWrong,maxNumWrongInARow,numValidRuns);
    }

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

    private boolean matchesRule(int iChoice, ArrayList<Card> iOptions, Card card, Match iRule) {
        boolean right = false;
        switch (iRule) {
            case COLOR:
                if(iOptions.get(iChoice).getColor() == toSort.getColor()){
                    right = true;
                }
                break;
            case SHAPE:
                if(iOptions.get(iChoice).getShape() == toSort.getShape()){
                    right = true;
                }
                break;
            case NUMBER:
                if(iOptions.get(iChoice).getNumber() == toSort.getNumber()){
                    right = true;
                }
                break;
        }
        return right;
    }

}
