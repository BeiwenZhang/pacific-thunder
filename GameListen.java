package threadGamev5;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameListen implements KeyListener {

  MyFighter ourfig;

    public GameListen(MyFighter ourfig) {
        this.ourfig = ourfig;
    }

    //
    @Override
    public void keyTyped(KeyEvent e) {

    }


//按下按键
    @Override
    public void keyPressed(KeyEvent e) {
        int key=e.getKeyCode();
//获取按下的key
      if(key==KeyEvent.VK_UP){
          up();
     //设置
      }
        if(key==KeyEvent.VK_DOWN){
           down();
            //设置
        }
        if(key==KeyEvent.VK_LEFT){
          left();
            //设置
        }
        if(key==KeyEvent.VK_RIGHT){
            right();
            //设置
        }
        if(key==KeyEvent.VK_SPACE){
            ourfig.fire();
            //发射子弹，怎么发？
            //基于当前战机的坐标 生成一个子弹对象
            //将子弹对象加入一个list（固定数据结构）
            //之后在绘制线程 以及移动碰撞等线程中逐一遍历这个子弹list（显示出来之类的）
        }



    }

    public void up(){
        ourfig.setSpeedY(-5);
    }
    public void down(){
        ourfig.setSpeedY(5);
    }
    public void left(){
        ourfig.setSpeedX(-5);
    }
    public void right(){
        ourfig.setSpeedX(5);
    }

//松开按键
    @Override
    public void keyReleased(KeyEvent e) {
        int key=e.getKeyCode();
        if(key==KeyEvent.VK_UP||key==KeyEvent.VK_DOWN){
            ourfig.setSpeedY(0);
            //设置
        }

        if(key==KeyEvent.VK_LEFT||key==KeyEvent.VK_RIGHT){
            ourfig.setSpeedX(0);
            //设置
        }

    }
}
