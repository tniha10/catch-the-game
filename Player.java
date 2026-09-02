import java.awt.*;

public class Player extends GameObject {
    public Player(int x, int y) {
        super(x, y);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 60, 20);
    }

    public void move(int dx) {
        x += dx;
        if (x < 0) x = 0;
        if (x > 340) x = 340;
    }

    public boolean catchItem(FallingItem item) {
        Rectangle playerRect = new Rectangle(x, y, 60, 20);
        Rectangle itemRect = new Rectangle(item.getX(), item.getY(), 20, 20);
        return playerRect.intersects(itemRect);
    }
}