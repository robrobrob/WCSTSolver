import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Solver {
    Match perceivedRule;
    Match oldRule;
    ArrayList<Guess> lastPerceivedRule;
    ArrayList<Card> options;
    Random random = new Random();
    final int lookBack;
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    final String ANSI_WHITE = "\u001B[97m";

    public Solver(){
        lastPerceivedRule = new ArrayList<Guess>();
        perceivedRule = Match.values()[random.nextInt(3)];
        oldRule = perceivedRule;
        lookBack = 2;
        //System.out.println("Initial options: " + options);
    }

    public Solver(ArrayList<Card> options) {
        lastPerceivedRule = new ArrayList<Guess>();
        perceivedRule = Match.values()[random.nextInt(3)];
        oldRule = perceivedRule;
        lookBack = 2;
        this.options = options;
    }

    public int ask(boolean lastCorrect, Card card) {
        //System.out.println("Options:" + options);
        System.out.println(ANSI_PURPLE_BACKGROUND + ANSI_WHITE + "Solver" + ANSI_RESET + " {");
        int choice = 0;
        if(lastCorrect) { //Correct
            System.out.println("Last time the Solver was correct and the last perceived rule was " + perceivedRule + ".");
            lastPerceivedRule.add(new Guess(oldRule,true));
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
            oldRule = perceivedRule;
            //System.out.println("Old rule: " + oldRule);
            //System.out.println("choice: " + choice);
            System.out.print("}\n");
            return choice;
        } else { //Not Correct
            lastPerceivedRule.add(new Guess(oldRule,false));
            if(lastPerceivedRule.size()>2) { //If we are farther in
                System.out.println("Last time the Solver was wrong and the last perceived rule was " + perceivedRule + ".");
                if(!lastPerceivedRule.get(lastPerceivedRule.size()-lookBack).isWasCorrect()) {
                    System.out.println("Two rounds ago, the perceived rule was "
                            + lastPerceivedRule.get(lastPerceivedRule.size()-lookBack).getRule()
                            + " and my guess was " + lastPerceivedRule.get(lastPerceivedRule.size()-lookBack).isWasCorrect());
                    switch (lastPerceivedRule.get(lastPerceivedRule.size() - (lookBack)).getRule()) {
                        case COLOR:
                            System.out.println("One round ago, the perceived rule was "
                                    + lastPerceivedRule.get(lastPerceivedRule.size()-(lookBack-1)).getRule()
                                    + " and my guess was " + lastPerceivedRule.get(lastPerceivedRule.size()-(lookBack-1)).isWasCorrect() + ".");
                            if (lastPerceivedRule.get(lastPerceivedRule.size() - (lookBack-1)).getRule() == Match.SHAPE) {
                                perceivedRule = Match.NUMBER;
                                System.out.println("The new perceived rule will be " + perceivedRule);
                            } else {
                                perceivedRule = Match.SHAPE;
                                System.out.println("The new perceived rule will be " + perceivedRule);
                            }
                            break;
                        case SHAPE:
                            System.out.println("One round ago, the perceived rule was "
                                    + lastPerceivedRule.get(lastPerceivedRule.size()-(lookBack-1)).getRule()
                                    + " and my guess was " + lastPerceivedRule.get(lastPerceivedRule.size()-(lookBack-1)).isWasCorrect() + ".");
                            if (lastPerceivedRule.get(lastPerceivedRule.size() - (lookBack-1)).getRule() == Match.NUMBER) {
                                perceivedRule = Match.COLOR;
                                System.out.println("The new perceived rule will be " + perceivedRule + ".");
                            } else {
                                perceivedRule = Match.NUMBER;
                                System.out.println("The new perceived rule will be " + perceivedRule + ".");
                            }
                            break;
                        case NUMBER:
                            System.out.println("One round ago, the perceived rule was "
                                    + lastPerceivedRule.get(lastPerceivedRule.size()-(lookBack-1)).getRule()
                                    + " and my guess was " + lastPerceivedRule.get(lastPerceivedRule.size()-(lookBack-1)).isWasCorrect() + ".");
                            if (lastPerceivedRule.get(lastPerceivedRule.size() - (lookBack-1)).getRule() == Match.COLOR) {
                                perceivedRule = Match.SHAPE;
                                System.out.println("The new perceived rule will be " + perceivedRule + ".");
                            } else {
                                perceivedRule = Match.COLOR;
                                System.out.println("The new perceived rule will be " + perceivedRule + ".");
                            }
                            break;
                    }
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
                    oldRule = perceivedRule;
                    //System.out.println("choice: " + choice);
                    System.out.print("}\n");
                    return choice;
                } else { //Random guess since we were only wrong once
                    switch (perceivedRule) {
                        case COLOR:
                            if (random.nextInt(2) == 1) {
                                perceivedRule = Match.NUMBER;
                                System.out.println("The new, guessed perceived rule will be " + perceivedRule + ".");
                            } else {
                                perceivedRule = Match.SHAPE;
                                System.out.println("The new, guessed perceived rule will be " + perceivedRule + ".");
                            }
                            break;
                        case SHAPE:
                            if (random.nextInt(2) == 1) {
                                perceivedRule = Match.NUMBER;
                                System.out.println("The new, guessed perceived rule will be " + perceivedRule + ".");
                            } else {
                                perceivedRule = Match.COLOR;
                                System.out.println("The new, guessed perceived rule will be " + perceivedRule + ".");
                            }
                            break;
                        case NUMBER:
                            if (random.nextInt(2) == 1) {
                                perceivedRule = Match.COLOR;
                                System.out.println("The new, guessed perceived rule will be " + perceivedRule + ".");
                            } else {
                                perceivedRule = Match.SHAPE;
                                System.out.println("The new, guessed perceived rule will be " + perceivedRule + ".");
                            }
                            break;
                    }
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
                    oldRule = perceivedRule;
                    //System.out.println("choice: " + choice);
                    System.out.print("}\n");
                    return choice;
                }
            } else { //If we are not in far enough
                System.out.println("Last time the Solver was wrong, partially because it is too early to detect what the rule is. Also, the last perceived rule was " + perceivedRule + ".");
                System.out.println("The Solver has not gotten far enough into the test yet to make an informed decision.");
                switch (perceivedRule) {
                    case COLOR:
                        if (random.nextInt(2) == 1) {
                            perceivedRule = Match.NUMBER;
                            System.out.println("The new, guessed perceived rule will be " + perceivedRule + ".");
                        } else {
                            perceivedRule = Match.SHAPE;
                            System.out.println("The new, guessed perceived rule will be " + perceivedRule + ".");
                        }
                        break;
                    case SHAPE:
                        if (random.nextInt(2) == 1) {
                            perceivedRule = Match.NUMBER;
                            System.out.println("The new, guessed perceived rule will be " + perceivedRule + ".");
                        } else {
                            perceivedRule = Match.COLOR;
                            System.out.println("The new, guessed perceived rule will be " + perceivedRule + ".");
                        }
                        break;
                    case NUMBER:
                        if (random.nextInt(2) == 1) {
                            perceivedRule = Match.COLOR;
                            System.out.println("The new, guessed perceived rule will be " + perceivedRule + ".");
                        } else {
                            perceivedRule = Match.SHAPE;
                            System.out.println("The new, guessed perceived rule will be " + perceivedRule + ".");
                        }
                        break;
                }
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
                oldRule = perceivedRule;
                //System.out.println("choice: " + choice);
                System.out.print("}\n");
                return choice;
            }
        }
    }
//OTHER VARIANT
    public int ask(boolean lastCorrect, Card card, ArrayList<Card> options) {
        this.options = options;
        //System.out.println("Options:" + options);
        int choice = 0;
        if(lastCorrect) { //If the Solver was correct last time
            System.out.println("Last time the Solver was correct and the last perceived rule was " + perceivedRule + ".");
            lastPerceivedRule.add(new Guess(oldRule,true));
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
            oldRule = perceivedRule;
            //System.out.println("Old rule: " + oldRule);
            //System.out.println("choice: " + choice);
            return choice;
        } else { //If the Solver was not correct last time
            System.out.println("Wrong - Last perceived Rule: " + perceivedRule);
            lastPerceivedRule.add(new Guess(oldRule,false));
            if(lastPerceivedRule.size()>2) { //If the Solver is farther enough that they can make the an informed selection of a Rule
                if(!lastPerceivedRule.get(lastPerceivedRule.size()-lookBack).isWasCorrect()) {
                    System.out.println("Two rounds ago, Perceived Rule was: "
                            + lastPerceivedRule.get(lastPerceivedRule.size()-lookBack).getRule()
                            + " and it was: " + lastPerceivedRule.get(lastPerceivedRule.size()-lookBack).isWasCorrect());
                    switch (lastPerceivedRule.get(lastPerceivedRule.size() - (lookBack)).getRule()) {
                        case COLOR:
                            System.out.println("One round ago, Perceived Rule was: "
                                    + lastPerceivedRule.get(lastPerceivedRule.size()-(lookBack-1)).getRule()
                                    + " and it was: " + lastPerceivedRule.get(lastPerceivedRule.size()-(lookBack-1)).isWasCorrect());
                            if (lastPerceivedRule.get(lastPerceivedRule.size() - (lookBack-1)).getRule() == Match.SHAPE) {
                                perceivedRule = Match.NUMBER;
                                System.out.println("New perceived rule: " + perceivedRule);
                            } else {
                                perceivedRule = Match.SHAPE;
                                System.out.println("New perceived rule: " + perceivedRule);
                            }
                            break;
                        case SHAPE:
                            System.out.println("One round ago, Perceived Rule was: "
                                    + lastPerceivedRule.get(lastPerceivedRule.size()-(lookBack-1)).getRule()
                                    + " and it was: " + lastPerceivedRule.get(lastPerceivedRule.size()-(lookBack-1)).isWasCorrect());
                            if (lastPerceivedRule.get(lastPerceivedRule.size() - (lookBack-1)).getRule() == Match.NUMBER) {
                                perceivedRule = Match.COLOR;
                                System.out.println("New perceived rule: " + perceivedRule);
                            } else {
                                perceivedRule = Match.NUMBER;
                                System.out.println("New perceived rule: " + perceivedRule);
                            }
                            break;
                        case NUMBER:
                            System.out.println("One round ago, Perceived Rule was: "
                                    + lastPerceivedRule.get(lastPerceivedRule.size()-(lookBack-1)).getRule()
                                    + " and it was: " + lastPerceivedRule.get(lastPerceivedRule.size()-(lookBack-1)).isWasCorrect());
                            if (lastPerceivedRule.get(lastPerceivedRule.size() - (lookBack-1)).getRule() == Match.COLOR) {
                                perceivedRule = Match.SHAPE;
                                System.out.println("New perceived rule: " + perceivedRule);
                            } else {
                                perceivedRule = Match.COLOR;
                                System.out.println("New perceived rule: " + perceivedRule);
                            }
                            break;
                    }
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
                    oldRule = perceivedRule;
                    //System.out.println("choice: " + choice);
                    return choice;
                } else {
                    switch (perceivedRule) {
                        case COLOR:
                            if (random.nextInt(2) == 1) {
                                perceivedRule = Match.NUMBER;
                                System.out.println("New perceived rule: " + perceivedRule);
                            } else {
                                perceivedRule = Match.SHAPE;
                                System.out.println("New perceived rule: " + perceivedRule);
                            }
                            break;
                        case SHAPE:
                            if (random.nextInt(2) == 1) {
                                perceivedRule = Match.NUMBER;
                                System.out.println("New perceived rule: " + perceivedRule);
                            } else {
                                perceivedRule = Match.COLOR;
                                System.out.println("New perceived rule: " + perceivedRule);
                            }
                            break;
                        case NUMBER:
                            if (random.nextInt(2) == 1) {
                                perceivedRule = Match.COLOR;
                                System.out.println("New perceived rule: " + perceivedRule);
                            } else {
                                perceivedRule = Match.SHAPE;
                                System.out.println("New perceived rule: " + perceivedRule);
                            }
                            break;
                    }
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
                    oldRule = perceivedRule;
                    System.out.println("choice: " + choice);
                    return choice;
                }
            } else { //If we are not in far enough
                System.out.println("Not far enough");
                switch (perceivedRule) {
                    case COLOR:
                        if (random.nextInt(2) == 1) {
                            perceivedRule = Match.NUMBER;
                            System.out.println("New perceived rule: " + perceivedRule);
                        } else {
                            perceivedRule = Match.SHAPE;
                            System.out.println("New perceived rule: " + perceivedRule);
                        }
                        break;
                    case SHAPE:
                        if (random.nextInt(2) == 1) {
                            perceivedRule = Match.NUMBER;
                            System.out.println("New perceived rule: " + perceivedRule);
                        } else {
                            perceivedRule = Match.COLOR;
                            System.out.println("New perceived rule: " + perceivedRule);
                        }
                        break;
                    case NUMBER:
                        if (random.nextInt(2) == 1) {
                            perceivedRule = Match.COLOR;
                            System.out.println("New perceived rule: " + perceivedRule);
                        } else {
                            perceivedRule = Match.SHAPE;
                            System.out.println("New perceived rule: " + perceivedRule);
                        }
                        break;
                }
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
                oldRule = perceivedRule;
                System.out.println("choice: " + choice);
                return choice;
            }
        }
    }
}
