package threadGamev5;

import java.awt.*;

//这个类是飞机，需要写属性和方法

public class Fighter extends FlyObject {
    //参数声明
    private int x,y,width,height,hp,speedX,speedY;
    private boolean islive=true;
    //用private封装起来，这样的话调用的时候必须使用set，get函数去传
    //不能直接用对象去调用属性
    private Color color;

    public Fighter(int x, int y, int width, int height, int hp, int speedX, int speedY, boolean islive,Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hp = hp;
        this.speedX = speedX;
        this.speedY = speedY;
        this.islive = islive;
        this.color=color;}

//封装方法


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public boolean getIslive() {
        return islive;
    }

    public void isIslive(boolean islive) {
        this.islive = islive;
    }

    //绘制方法
    public void show(Graphics g){
        g.setColor(color);
        g.fillRect(x,y,width,height);
    }


    //移动方法(只写敌方战机即可）
    public  void  move(MyList<Fighter> figlist){
        if(x<0||x>(500-width)){
            speedX=-speedX;
        }
        if(y>800){
            //直接用旗帜
            this.setIslive(false);
        }
        x+=speedX;

        y+=speedY;
    }

    //检测碰撞方法(还没写）
//同时，需要两个子类去继承这个，之后自己写


}
class Bullet extends FlyObject {
    int x,y,width=10,height=14,speedX,speedY,id;
    Color color=Color.RED;
    private boolean islive=true;

    public Bullet(int x, int y, int speedY, int id,boolean islive){
        this.x = x;
        this.y = y;

        this.id=id;
        this.speedY = speedY;

        this.islive = islive;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void show(Graphics g){
        g.setColor(color);
        g.fillPolygon(new int[]{x - width / 2, x + width / 2, x}, new int[]{y, y, y - height},3);
    }


    public  void  move(MyList<Bullet> bulletlist){

        if(y<0){
            //从队列中移除
            this.setIslive(false);
        }


        y+=speedY;
    }
}


//anti

class AntiBullet extends FlyObject {
    Color color=Color.BLUE;

    int x,y,width=10,height=14,speedX,speedY,id;

    private boolean islive=true;

    public AntiBullet(int x, int y, int speedY, int id, boolean islive) {
        this.x = x;
        this.y = y;
        this.speedY = speedY;
        this.id = id;
        this.islive = islive;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void show(Graphics g){
        g.setColor(color);
        g.fillPolygon(new int[]{x - width / 2, x + width / 2, x}, new int[]{y, y, y + height},3);



    }

    public  void  move(MyList<AntiBullet> bullets){

        if(y>800){
            //从队列中移除
            this.setIslive(false);
        }


        y+=speedY;
    }

}

