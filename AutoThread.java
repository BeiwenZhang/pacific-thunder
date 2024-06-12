package threadGamev5;

import java.awt.*;
import java.util.Random;


//自动生成敌机线程
public class AutoThread implements Runnable{
    private MyList<Fighter>figlist;
    private MyList<AntiBullet>bullets;

    public AutoThread(MyList<Fighter> figlist, MyList<AntiBullet>bullets) {
        this.figlist = figlist;
        this.bullets=bullets;
    }

    @Override
    public void run() {
        while(true){
//每隔这么多毫秒 就生成一个敌机
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (figlist) {

//准备数据
                Fighter fig= initFigData();//返回值是什么 可以是一个飞机对象 之后刚好加进去
                figlist.add(fig);
//将生成的敌机对象存入队列
                //我们要做的是每生成一个敌机，启动一个线程
                //不断的基于当前敌机的坐标去发射子弹
                AutoGunThread autoGunThread=new AutoGunThread(fig,bullets);//每次生成一个敌机的时候就启动一个子弹的线程
                //把子弹的队列和飞机传进去
                //方法可以在这个类直接写一个新的类，然后一个类里面去创建这个类，一个包含过程
                new Thread(autoGunThread).start();
            }


        }
    }
    Random random = new Random ();
    private Fighter initFigData(){ // 准备敌机所有的数据
        int x = random.nextInt (400); int y = -100;
        int  width = random.nextInt (50)+25;
        int height = random.nextInt(50)+25;
        int speedX=random.nextInt (7)-3;
        int speedY = random.nextInt(1)+1;
        int hp=100;
        boolean islive=true;
        Color color = new Color (random.nextInt(Integer.MAX_VALUE)); // 创建敌机对象 Fighter fighter = new
        Fighter fighter=new Fighter(x,y,width,height,hp,speedX,speedY,islive,color);
        return fighter;}






}

class AutoGunThread implements Runnable{
    private Fighter fig;
    //    private MyList<AntiBullet>bullets;
    private MyList<AntiBullet>bullets;


    public AutoGunThread(Fighter fig, MyList<AntiBullet>bullets) {
        this.fig = fig;
        this.bullets = bullets;
    }

    @Override
    public void run() {
        while(true){
//每隔这么0.3s发射一个子弹
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (bullets) {
                AntiBullet bullet=new AntiBullet(fig.getX()+fig.getWidth()/2, fig.getY()+fig.getHeight(),10,2,true);
                bullets.add(bullet);
//将生成的敌机对象存入队列
                //我们要做的是每生成一个敌机，启动一个线程
                //不断的基于当前敌机的坐标去发射子弹


            }
//准备数据


        }
    }







}
