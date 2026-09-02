import java.awt.*;

public class FallingItem extends GameObject {
    public FallingItem(int x, int y) {
        super(x, y);
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, 20, 20);
    }

    public void fall() {
        y += 5;
    }
}