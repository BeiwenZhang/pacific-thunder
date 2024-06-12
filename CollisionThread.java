package threadGamev5;


//现在清心，好好把这个文件看一下自己的版本应该做哪些改进，分几步？
//应该比较重要的是一个是1.把所有的Arraylist全部改成MyList
//2.碰撞线程的话 改写的在里面写 之后启动碰撞线程就可以了
//3、现在写一个所有的父类FlyObject————然后用其他的去继承
//敌机与我方战机 如果碰撞的话会怎么样？？注意一下这个只是模板，跟着写必不可取
//考虑一下导入图片，有哪些功能呢？目前先把碰撞写出来，最朴素的往往有最好的效果
//我方子弹碰到敌方战机 敌方战机会死；我方子弹碰到敌方子弹，敌方子弹会死；


public class CollisionThread implements Runnable {
    private MyList<Fighter> figlist;
    private MyFighter myfighter;
    private MyList<AntiBullet> bullets;

    public CollisionThread(MyList<Fighter> figlist, MyFighter myfighter, MyList<AntiBullet> bullets) {
        this.figlist = figlist;
        this.myfighter = myfighter;
        this.bullets = bullets;
    }

    @Override
    public void run() {//现在就几层循环就可以啦
//跟着写就好
        while (true) {
            //sleep是为什么？
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //我方子弹和敌方战机
            synchronized (figlist) {//目前是多线程 能少用下标就少用下标，先get一下，用局部变量去判断；上锁和数组越界没关系；很多时候index改成index更稳妥 因为index就是;ength-nullCount
                //但是很多时候index早就存到后面去了，所以用index可以保证不会漏掉元素，之后加上！=null的判断
                //另外ctrl+D可以复制这一行东西
                //Ctrl+Alt+L可以实现格式化
                //另外如果说提高代码能力可以考虑把全部的线程放在同一个线程里去写，自己重新按照思路写一边， 可以避免数组越界问题

                for (int i = 0; i < myfighter.getBulletlist().index(); i++) {//改成index，最新的下标
                    Bullet mybullet = myfighter.getBulletlist().get(i);

                    if (mybullet != null) {
                        for (int j = 0; j < figlist.index(); j++) {
                            //对照写的 动态数组不越界
                            Fighter fighter = figlist.get(j);
                            if (fighter != null) {
                                if (isCollision(mybullet, fighter)) {//每次使用get的时候都需要去判断一次


                                    fighter.setIslive(false);//这个知道 死循环不太清楚 应该没写吧

                                    break;
                                }
                            }
                        }
                    }


                    for (int j = 0; j < bullets.index(); j++) {
                        AntiBullet anti = bullets.get(j);
                        if(anti!=null&&mybullet!=null){
                            if (isCollision(mybullet, anti)) {

                                mybullet.setIslive(false);
                                anti.setIslive(false);

                                break;
                            }}

                    }


                }
            }
            //我方战机和敌方战机
            synchronized (figlist) {

                for (int j = 0; j < figlist.index(); j++) {
                    Fighter fig=figlist.get(j);
                    if(fig!=null){
                        if (isCollision(myfighter, fig)) {


                            figlist.get(j).setIslive(false);

                            break;
                        }
                    }}

            }
            //我方战机 敌方子弹

            synchronized (bullets) {
                for (int j = 0; j < bullets.index(); j++) {
                    AntiBullet anti = bullets.get(j);
                    if(anti!=null){
                        if (isCollision(myfighter, anti)) {

                            anti.setIslive(false);

                            break;//必须跳出这一层循环
                        }
                    }}
            }
            //现在最后一起清空一下live false的东西（包括我方子弹-敌方战机-敌方子弹）
            synchronized (bullets) {//敌方子弹
                for (int i = 0; i < bullets.index(); i++) {
                    if (bullets.get(i) != null) {


                        if (!bullets.get(i).isIslive()) {
                            bullets.remove(i);
                            i--;
                        }
                    }
                }
            }

            synchronized (myfighter.getBulletlist()) {//我方子弹
                for (int i = 0; i < myfighter.getBulletlist().index(); i++) {
                    if (myfighter.getBulletlist().get(i) != null) {
                        if (!myfighter.getBulletlist().get(i).isIslive()) {
                            myfighter.getBulletlist().remove(i);
                            i--;
                        }
                    }
                }}

                synchronized (figlist) {//敌方子弹
                    for (int i = 0; i < figlist.index(); i++) {
                        Fighter fig=figlist.get(i);
                        if(fig!=null){
                            if (!fig.isIslive()) {
                                figlist.remove(i);
                                i--;
                            }
                        }}
                }

          /*  if(bullets.index()==2147483645){bullets.resize();}
            if(figlist.index()==2147483645){figlist.resize();}
            if(myfighter.getBulletlist().index()==2147483645){myfighter.getBulletlist().resize();}*/

            }

        }



    public boolean isCollision(FlyObject b1, FlyObject b2) {
        //非常经典的判断两张图片是否相交
        if (b1.getX() + b1.getWidth() < b2.getX() || b2.getX() + b2.getWidth() < b1.getX() || b1.getY() + b1.getHeight() < b2.getY() || b2.getY() + b2.getHeight() < b1.getY()) {
            return false;
        }
        return true;
    }
}
