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
        int choice = 0;
        if(lastCorrect) { //Correct
            System.out.println("Correct - Last perceived rule: " + perceivedRule);
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
            System.out.println("Old rule: " + oldRule);
            System.out.println("choice: " + choice);
            return choice;
        } else { //Not Correct
            System.out.println("Wrong - Last perceived Rule: " + perceivedRule);
            lastPerceivedRule.add(new Guess(oldRule,false));
            if(lastPerceivedRule.size()>2) { //If we are farther in
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
                    System.out.println("choice: " + choice);
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
//OTHER VARIANT
    public int ask(boolean lastCorrect, Card card, ArrayList<Card> options) {
        this.options = options;
        //System.out.println("Options:" + options);
        int choice = 0;
        if(lastCorrect) { //Correct
            //System.out.println("Correct - Last perceived rule: " + perceivedRule);
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
            System.out.println("Old rule: " + oldRule);
            System.out.println("choice: " + choice);
            return choice;
        } else { //Not Correct
            System.out.println("Wrong - Last perceived Rule: " + perceivedRule);
            lastPerceivedRule.add(new Guess(oldRule,false));
            if(lastPerceivedRule.size()>2) { //If we are farther in
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
                    System.out.println("choice: " + choice);
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
