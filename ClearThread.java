package threadGamev5;

public class ClearThread implements Runnable{
    private MyList<Fighter> figlist;
    private MyFighter myfighter;
    private MyList<AntiBullet> bullets;

    public ClearThread(MyList<Fighter> figlist, MyFighter myfighter, MyList<AntiBullet> bullets) {
        this.figlist = figlist;
        this.myfighter = myfighter;
        this.bullets = bullets;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

 figlist.resize();
            bullets.resize();
            myfighter.getBulletlist().resize();



        }
    }
}
