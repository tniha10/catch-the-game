public abstract class GameObject {
    protected int x, y;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void draw(java.awt.Graphics g);
    public int getX() { return x; }
    public int getY() { return y; }
}