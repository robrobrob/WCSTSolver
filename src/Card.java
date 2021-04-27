public class Card {
    int number;
    Shape shape;
    Color color;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";


    public Card(int number, Shape shape, Color color) {
        this.number = number;
        this.shape = shape;
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Card{" +
                "number=" + number +
                ", shape=" + shape +
                ", color=" + color +
                '}';
    }

    public static String getAnsiReset() {
        return ANSI_RESET;
    }

    public static String getAnsiRed() {
        return ANSI_RED;
    }

    public static String getAnsiGreen() {
        return ANSI_GREEN;
    }

    public static String getAnsiYellow() {
        return ANSI_YELLOW;
    }

    public static String getAnsiBlue() {
        return ANSI_BLUE;
    }


    public String toGraphicalString() {
        int numCounter = -1;
        String toReturn = "";


        for(int i = 1; i<5; i++) {
            if(numCounter == number) {
                toReturn = toReturn.concat(" ");
            } else {
                switch (color) {
                    case RED -> {
                        toReturn = toReturn.concat(ANSI_RED);
                        toReturn = switch (shape) {
                            case CIRCLE -> toReturn.concat("●");
                            case STAR -> toReturn.concat("★");
                            case TRIANGLE -> toReturn.concat("▲");
                            case CROSS -> toReturn.concat("✚");
                        };
                        toReturn = toReturn.concat(ANSI_RESET);
                    }
                    case GREEN -> {
                        toReturn = toReturn.concat(ANSI_GREEN);
                        toReturn = switch (shape) {
                            case CIRCLE -> toReturn.concat("●");
                            case STAR -> toReturn.concat("★");
                            case TRIANGLE -> toReturn.concat("▲");
                            case CROSS -> toReturn.concat("✚");
                        };
                        toReturn = toReturn.concat(ANSI_RESET);
                    }
                    case BLUE -> {
                        toReturn = toReturn.concat(ANSI_BLUE);
                        toReturn = switch (shape) {
                            case CIRCLE -> toReturn.concat("●");
                            case STAR -> toReturn.concat("★");
                            case TRIANGLE -> toReturn.concat("▲");
                            case CROSS -> toReturn.concat("✚");
                        };
                        toReturn = toReturn.concat(ANSI_RESET);
                    }
                    case YELLOW -> {
                        toReturn = toReturn.concat(ANSI_YELLOW);
                        toReturn = switch (shape) {
                            case CIRCLE -> toReturn.concat("●");
                            case STAR -> toReturn.concat("★");
                            case TRIANGLE -> toReturn.concat("▲");
                            case CROSS -> toReturn.concat("✚");
                        };
                        toReturn = toReturn.concat(ANSI_RESET);
                    }
                }
                numCounter++;
            }
        }

        return toReturn;

    }
}
