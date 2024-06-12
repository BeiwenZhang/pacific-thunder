package threadGamev5;

import java.awt.*;
import java.awt.image.BufferedImage;


public class ShowThread implements Runnable{
    private MyList<Fighter> figlist;
    private Graphics g;
    private MyFighter ourfig;
    private MyList<AntiBullet> bullets;
    //创建构造方法


    public ShowThread(MyFighter ourfig, MyList<Fighter> figlist, Graphics g, MyList<AntiBullet> bullets) {
        this.ourfig=ourfig;
        this.figlist = figlist;
        this.g = g;
        this.bullets=bullets;
    }

    @Override
    //基本思路，把数据取出来然后不停的画，所以需要一个数组
    public void run() {
        while(true){

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //每30msfor循环之中取一次，专用写法谢在外面
            BufferedImage buffimg=new BufferedImage(500,800,BufferedImage.TYPE_INT_ARGB);
            Graphics bfg=buffimg.getGraphics();
            //解决闪的问题。为什么闪？闪的原因是要逐一画太多的东西；
            //如果搞一个bufferedimage，在缓冲图片上获取画笔，这个画笔
            //去画你想要的东西；最后再用窗体上获得的画笔去把缓冲图片画出来
            //绘制背景板
            bfg.setColor(new Color(238,238,238));
            bfg.fillRect(0,0,500,800);
            ourfig.show(bfg);
            //之后写一个接口，把数据都定义在接口里面；
            //之后只要在接口里面改数据就可以了
            //画我方子弹
            MyList<Bullet> bulletlist=ourfig.getBulletlist();

            for(int i=0;i<bulletlist.index();i++){



                if(bulletlist.get(i)!=null){
                    if(bulletlist.get(i).isIslive())    {bulletlist.get(i).show(bfg);}}


                //figlist去get可以拿到数组里面对应的fighter，再调用show方法
            }

//画敌方子弹
            //这个就不是对象去获取，而是直接传进来，因为在GameUI里面写了
            synchronized (bullets) {
                for(int i=0;i<bullets.index();i++){
                    if(bullets.get(i)!=null){
                        if(bullets.get(i).isIslive())  { bullets.get(i).show(bfg);}

                    }
                }




//绘制敌方战机
                for(int i=0;i<figlist.index();i++){
                    Fighter fig=figlist.get(i);
                    if(fig!=null){
                        if(fig.isIslive())   {figlist.get(i).show(bfg);}
                        //figlist去get可以拿到数组里面对应的fighter，再调用show方法
                    }}
                g.drawImage(buffimg,0,0,null);

            }


        }

    }
}
