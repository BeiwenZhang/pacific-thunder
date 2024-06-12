package threadGamev5;



public class MoveThread implements  Runnable{
    private MyList<Fighter> figlist=null;
    //移动的方法，把那些逐一取出来
//除此之外 我方战机，敌方战机都需要传进来
    private MyList<AntiBullet>bullets;
    private MyFighter ourfig;

    public MoveThread(MyList<Fighter> figlist, MyList<AntiBullet> bullets, MyFighter ourfig) {
        this.figlist = figlist;
        this.bullets = bullets;
        this.ourfig = ourfig;
    }

    @Override
    public void run() {

        while(true){

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //移动玩家战机
            ourfig.move(figlist);

            //移动玩家子弹
            MyList<Bullet> bulletlist=ourfig.getBulletlist();

            synchronized (bulletlist) {
                for (int i = 0; i < bulletlist.index(); i++) {
                    if(bulletlist.get(i)!=null){
                        if(bulletlist.get(i).isIslive()) { bulletlist.get(i).move(bulletlist);}

                    }}
            }

            synchronized (figlist) {
                //移动敌机
                for(int i=0;i<figlist.index();i++){
                    Fighter fig=figlist.get(i);
                    if(fig!=null){

                        if(fig.isIslive())  {figlist.get(i).move(figlist);}//取出来执行move方法，这个时候move里面每次执行到y的一定数目就从队列里面拿走
                    }}


            }

            synchronized (bullets) {
                //移动敌机子弹

                for (int i=0;i<bullets.index();i++){

                    if(bullets.get(i)!=null){                    if(bullets.get(i).isIslive()) {  bullets.get(i).move(bullets);}

                    }}


            }


        }
    }
}
