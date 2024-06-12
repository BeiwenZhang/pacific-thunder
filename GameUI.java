package threadGamev5;

import javax.swing.*;
import java.awt.*;


public class GameUI extends JFrame {
//现在想想怎么加锁？上次我们是对希望对num加锁，但是由于不行最后是对object加锁的
    //只要object是和需要上锁的资源同步就可以
    //需要上锁的资源是队列们

    MyList<Fighter>figlist=new MyList<>();//注意 这个就是敌方战机
    //存储战机的队列（已经查了java里面有这种队列，也有数组类型）
    //声明方式差不多就这个样子 <>里面是存储对象，后面的是取的名字
    MyFighter ourfig=  new MyFighter(200,500,200,60,0,0,0,true,Color.black);
    GameListen gls=new GameListen(ourfig);
    MyList<AntiBullet>bullets=new MyList<>();//敌方子弹

    public  GameUI(){
        initUI();//初始化界面

        //启动线程
        initWorkThreads();

    }


    public void initUI(){
        setTitle("飞机大战V1.0");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,800);
        setLocationRelativeTo(null);//居中
        setResizable(false);
        setVisible(true);
        addKeyListener(gls);
    }


    public void  initWorkThreads(){
        //启动绘制线程
        ShowThread std=new ShowThread(ourfig,figlist, getGraphics(),bullets);
        new Thread(std).start();
        System.out.println("测试222");
        //移动线程
        MoveThread mtd=new MoveThread(figlist,bullets,ourfig);
        new Thread(mtd).start();
        System.out.println("测试444");
        //碰撞线程
        //自动生成敌方线程
        AutoThread atd=new AutoThread(figlist,bullets);
        new Thread(atd).start();
        System.out.println("测试333");

        CollisionThread ctd=new CollisionThread(figlist,ourfig,bullets);
        new Thread(ctd).start();
        System.out.println("碰撞线程");

   /*     ClearThread ctd2=new ClearThread(figlist,ourfig,bullets);
        new Thread(ctd2).start();
        System.out.println("清理线程");*/
    }

    public  static void main(String[] args){

        new GameUI();
    }

}
