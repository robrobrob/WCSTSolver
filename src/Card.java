public class Card implements Comparable<Card> {
    int number;
    Shape shape;
    Color color;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

	//Constructor

    public Card(int number, Shape shape, Color color) {
        this.number = number;
        this.shape = shape;
        this.color = color;
    }

    //Getters

    public int getNumber() {
        return number;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

	public static String getANSIReset() {
		return ANSI_RESET;
	}

	public static String getANSIRed() {
		return ANSI_RED;
	}

	public static String getANSIGreen() {
		return ANSI_GREEN;
	}

	public static String getANSIYellow() {
		return ANSI_YELLOW;
	}

	public static String getANSIBlue() {
		return ANSI_BLUE;
	}

	//Helper methods

	/**
	 * Returns a string of a number of colored shapes, meaning to represent the card graphically
	 * @return a graphical String representing the Card
	 */

	public String toGraphicalString() {
        int numCounter = -1;
        String toReturn = "";
        for(int i = 1; i<5; i++) {
            if(numCounter == number) {
                toReturn = toReturn.concat(" ");
            } else {
                toReturn = appendANSIFromColor(toReturn,color);
                toReturn = switch (shape) {
                    case CIRCLE -> toReturn.concat("●");
                    case STAR -> toReturn.concat("★");
                    case TRIANGLE -> toReturn.concat("▲");
                    case CROSS -> toReturn.concat("✚");
                };
                toReturn = toReturn.concat(getANSIReset());
                numCounter++;
            }
        }
        return toReturn;
    }

	/**
	 * Returns a String with an ANSI color code appended to it, given a String to begin with and a Color to append
	 * @param string the String to append the ANSI color code to
	 * @param color the Color of the ANSI color code
	 * @return a String with a ANSI color code appended to it, matching the specified Color
	 */

	public String appendANSIFromColor(String string, Color color) {
        return string + switch(color) {
            case RED -> getANSIRed();
            case GREEN -> getANSIGreen();
            case BLUE -> getANSIBlue();
            case YELLOW -> getANSIYellow();
        };
    }

	//Overridden methods

	@Override
	public String toString() {
		return "Card{" +
				"number=" + number +
				", shape=" + shape +
				", color=" + color +
				'}';
	}

	@Override
	public int compareTo(Card c) { //Return 0 if they match, otherwise return -1
		if((this.getNumber() == c.getNumber()) && (this.getShape() == c.getShape()) && (this.getColor() == c.getColor())) {
			return 0;
		}
		return -1;
	}
}
