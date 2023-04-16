import javax.swing.*;
import java.awt.*;

public class Player extends Thread implements Drawable {
    private double playerXPos, playerYPos;
    private double playerHeight, playerWidth;
    private double velX = 0, velY = 0, accX = 0;
    private double gravity = 0.5;
    private GamePanel gamePanel;
    private Image playerImage;
    private PlayerHealthBar healthBar;
    Client client;

    public Player(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.playerXPos = 200;
        this.playerYPos = Sizes.GAME_GROUND_LEVEL - 250;
        this.playerWidth = 95;
        this.playerHeight = 115;
        this.healthBar = new PlayerHealthBar();
        playerImage = new ImageIcon("Images/img_2.png").getImage();

//        client = new Client();
    }

    public double getX() {
        return this.playerXPos;
    }
    public double getY() {
        return this.playerYPos;
    }
    public double getHeight() { return this.playerHeight; }
    public double getWidth() { return this.playerWidth; }
    public void setVelX(double newVelX) {
        this.velX = newVelX;
    }
    public void setAccX(double newAccX) {
        this.accX = newAccX;
    }
    public void jump() {
        this.velY = -10;
    }
    public void takeDamage(int damage){
        healthBar.takeDamage(damage);
    }

    public Rectangle getRectangle() {
        return new Rectangle((int) playerXPos, (int) playerYPos, (int) playerWidth, (int) playerHeight);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(playerImage, (int)this.playerXPos, (int)this.playerYPos, (int)this.playerWidth, (int)this.playerHeight, this.gamePanel);
        healthBar.draw(g);
    }

    public void run() {
        while (playerIsAlive()) {
            this.update();
            gamePanel.repaint();
            this.sleep(10);
        }
    }

    private boolean playerIsAlive() {
        return (healthBar.getCurrentHealth() >= 0);
    }


    void updatePlayerPos (int arr[]){

    }
    public int [] position()
    {
        int[] arr = new int [2];
        arr[0] = (int)this.playerXPos;
        arr[1] = (int)this.playerYPos;
        return arr;
    }
    private void update() {
        if (checkWindowCollision())
            return;
        this.velX += this.accX;
        this.velY += this.gravity;
        this.playerXPos += this.velX;
        this.playerYPos += this.velY;

        if (this.playerYPos >= Sizes.GAME_GROUND_LEVEL - this.playerHeight) {
            this.playerYPos = Sizes.GAME_GROUND_LEVEL - this.playerHeight;
            this.velY = 0;
        }
        if (this.velX < 0 && this.accX == 0) {
            this.velX += 0.1;
        }


    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean checkWindowCollision() {
        return (this.playerXPos + this.playerWidth + this.velX >= Sizes.WINDOW_MAX_WIDTH || this.playerXPos + this.velX <= 0);
    }

}