public class Card {
    int number;
    Shape shape;
    Color color;

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
}
