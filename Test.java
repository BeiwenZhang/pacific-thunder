package threadGamev5;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        User user=new User();
        //在使用user.setAge(100)的时候，可以在main函数那边throw上自己写的异常；也可以around with try/catch
        int age=new Scanner(System.in).nextInt();
        try {//try catch到底有什么用？try是检测异常发生
            user.setAge(age);
        } catch (AgeCheckException e) {//catch块进行匹配异常，做对应的处理-如果发生了这个异常，给出对应的处理，让程序去正常执行
            throw new RuntimeException(e);//AgeCheckException只是继承了Exception类，没有继承运行时异常类，但是throw的话仍然是throw运行时异常

        }

        User user2=new User();
         try {
            user2.setAge(age);
        } catch (AgeCheckException e) {
            e.printStackTrace();//也是常用的处理错误方法，信息栈
        }
//finally代码块 无论上面是否执行 我这里都会最终执行；finally里面的代码常常用于解锁
         //比如说如果程序卡住了，我们就在finally里面设定一个计时器，多少秒之后就出来;
         //这一串代码就是无论如何都会执行，测试的时候如果把那个CheckAge的异常改成空指针异常，在这个异常后面的代码都会中断不能执行，但是finally语句必定执行
         finally{
             System.out.println("finnaly!! ");
         }
        int num=new Scanner(System.in).nextInt();
        user.setWeight(48);//会发现这样写就不会报错，点开非法参数异常的源代码会发现继承的是 运行时异常 RuntimeException
        //空指针，也是运行时异常；——运行时异常可以不强制抛出，但是继承Exception的异常类必须在运行的时候就强制抛出
        //所谓运行时异常就是在运行的时候才可能出现的异常，在写代码的时候根本就检测不出来
        //还记得什么要抛吗？线程里面的sleep，继承的直接是exception
    }


}
class User{
    private int age,Weight;
    public void setAge(int age) throws AgeCheckException {
        if(age<0){//用封装的方法来测试这个age是否合法
            //抛一个自带的异常
            throw new AgeCheckException("年龄不合法");
            //之前抛的异常：非法参数异常
            //现在改成自己写的异常
        }
        this.age=age;
    }
//如果是不合法参数异常
    public void setWeight(int Weight) {
        if(Weight<0){

            throw new IllegalArgumentException("体重不合法");
          //非法参数异常

        }
        this.Weight=Weight;
    }


}
//自己写一个异常类,看了那些异常类的源代码，一般来说有的是一些构造方法，时候调用的时候直接throw，括号里写自己需要的参数（多态）
class AgeCheckException extends Exception{
    AgeCheckException(){
        super();//调用父类的构造方法
    }
    AgeCheckException(String msg){
 super();
    }
}
