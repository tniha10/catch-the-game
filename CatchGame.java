import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CatchGame extends JFrame {
    Player player;
    ArrayList<FallingItem> items = new ArrayList<>();
    Inventory<String> inventory = new Inventory<>();
    int score = 0;
    int lives = 3;

    public CatchGame() {
        setTitle("Catch Game");
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        player = new Player(170, 500);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.move(-15);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.move(15);
                }
            }
        });

        // Timer to refresh screen
        javax.swing.Timer timer = new javax.swing.Timer(30, e -> repaint());
        timer.start();

        // Start item generator in background thread
        new Thread(() -> {
            while (lives > 0) {
                try {
                    Thread.sleep(1000);
                    FallingItem item = new FallingItem((int) (Math.random() * 360), 0);
                    items.add(item);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void paint(Graphics g) {
        Image offscreen = createImage(getWidth(), getHeight());
        Graphics bg = offscreen.getGraphics();

        bg.setColor(Color.WHITE);
        bg.fillRect(0, 0, getWidth(), getHeight());

        player.draw(bg);

        for (int i = 0; i < items.size(); i++) {
            FallingItem item = items.get(i);
            item.fall();
            item.draw(bg);

            if (player.catchItem(item)) {
                inventory.addItem("Item");
                score++;
                items.remove(i);
                i--;
            } else if (item.getY() > 600) {
                lives--;
                items.remove(i);
                i--;
            }
        }

        bg.setColor(Color.BLACK);
        bg.drawString("Score: " + score, 10, 50);
        bg.drawString("Lives: " + lives, 10, 70);

        if (lives <= 0) {
            bg.drawString("Game Over!", 150, 300);
            try {
                if (score < 2) throw new GameException("Better luck next time!");
            } catch (GameException e) {
                bg.drawString(e.getMessage(), 120, 320);
            }
        }

        g.drawImage(offscreen, 0, 0, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CatchGame().setVisible(true));
    }
}