package threadGamev5;

import java.awt.*;


public class MyFighter extends Fighter {

    private MyList<Bullet>bulletlist=new MyList<>();
    //现在需要的就是把这个队列传到各个线程里面去，按照惯例get方法


    public MyList<Bullet> getBulletlist() {
        return bulletlist;
    }

    public MyFighter(int x, int y, int width, int height, int hp, int speedX, int speedY, boolean islive, Color color) {
        super(x, y, width, height, hp, speedX, speedY, islive, color);
    }//继承的话不传构造方法会报错
    //继承是继承了所有东西 包括属性和方法
    public void move(MyList<Fighter> figlist){//方法重写，必须参数一样
//复习重载是什么。重载是一个子类去继承父类，然后在子类里面去写多个同名方法，参数不同，之后调用的时候
        //根据给的参数不同执行不同的方法
        if(getX()>=450||getX()<=10) {setX(450);}
        if(getY()>=750||getY()<=10) {setY(750);}

        setX(getX()+getSpeedX());

        setY(getY()+getSpeedY());


    }

    public void fire(){
        for(int i=0;i<5;i++){
            Bullet bullet=  new Bullet(this.getX()+getWidth()/2-i*11+20,this.getY()+i*3,-10,1,true);
            bulletlist.add(bullet);

        }

    }

}
