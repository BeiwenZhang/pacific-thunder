package threadGamev5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyList<E> {
    //E通配符，表示一个元素；之后创建的时候底层还是需要使用object
    //如果不加泛型的话vector也可以存入任何
    private Object[] valueArray = {};// size 为 0

    private int index = 0;
    private int size;//size就是真实长度-其实还index是一个概念
    private int length;
    private static final int defaultLength = 10;
    private int nullCount;
    //这些属性都是全局变量

    // 构造方法+
    public MyList() {
        length = defaultLength;
        valueArray = new Object[length];//直接实例化
        size = 0;
        index = 0;

        nullCount = length;
    }

    /**
     * @param initLength
     */
    //不同构造方法，传一个容量的
    public MyList(int initLength) {
        if (initLength <= 0) {//防止出现不合法的情况，保证传的参数正确
            throw new IllegalArgumentException("长度不合法！初始化失败！");
        }//自己学习到的报错形式

        length = initLength;
        valueArray = new Object[length];
        size = 0;
        index = 0;
        nullCount = length;
    }

    //也可以传一个list进来，遍历，把数据传进来就可以
    public MyList(List list) {

    }


    public int size() {
        return size;
    }

    public int length() {//注意 真正遍历的时候什么最保险？length
        //除了扩容以外length是比较稳定 更容易保证数据安全问题
        return length;
    }

    // add方法
    public synchronized void add(E e) {//添加object类型
        if (index == length) {//如果已经是满的，length=下标index
            grow();
        }
        valueArray[index++] = e;//可能越界，所以必须扩容
        //index是什么概念？指向目前最后一个元素的下一个，所以和size一样
        size++;
        nullCount--;
    }


    //加一个数组,所以泛型
    public void addAll(MyList<? extends E> list) {//标准写法，如果是吃不准会传什么泛型，就MyList[E]
        //更加改进的话就加一个继承，也就是说只要声明一个object类，之后任何都是object的子类，不会报错
        if (index + list.length >= length) {//则需要扩容，但是不一定扩容一次不够
            int minLength = index + list.length;//大了多少，最小需要扩容的长度，
            //index是现在有的长度（下标），length是现在已经申请了储存空间的那个确定的长度
            grow(minLength);
        }
        int count = 0;
        for (int i = index; i < valueArray.length; i++) {
            if (list.get(i - index) != null) {//也可能传进去的数组不是满的，考虑有空的情况
                valueArray[i] = list.get(i - index);//e是从0开始计数
                count++;
            }
        }//全部存进去了，index加过去更新，必须一个count来做计数器以求准确（i不行，i仅仅在循环内有效
        index += count;
        size += count;
        nullCount = nullCount + list.nullCount;
    }


    private synchronized void grow() {
        int oldLength = length;
        int needLendth = (oldLength >> 1);
        int newLength = oldLength + needLendth;
        //计算一个新长度，1.5倍扩容
        //>>1就是右移一位，除以二去余数的意思（如果是八进制>>就是除以八）
        length = newLength;
        //复制旧的数组过去
        valueArray = Arrays.copyOf(valueArray, newLength);//复制数组直接赋回原值
        nullCount += needLendth;//有了上面的方法，下面几行手动复制数组元素的代码就不再需要
    /*    Object[] newArray = new Object[length];//声明一个新数组
        for (int i = 0; i < oldLength; i++) {
            newArray[i] = valueArray[i];
        }//拷贝数组赋值
        valueArray=newArray;//替换引用，newArray的地址赋给valueArray（还记得今天学的，数组本来就是一个首地址的概念       */
    }

//！！！！扩容需要加锁 待完成


    private void grow(int minlength) {//精确扩容
        int oldLength = length;
        int newLength = minlength;//！！！感觉有问题 应该还要加上length
        length = newLength;
        Object[] newArray = new Object[length];
        for (int i = 0; i < oldLength; i++) {
            newArray[i] = valueArray[i];
        }
        valueArray = newArray;
        nullCount = nullCount + (minlength - length);
    }


    public synchronized void remove(int index) {//把之前写的报异常删掉 自己写一个
        if (checkIndexAndElementIsNull(index)) {
            valueArray[index] = null;//对应这个去掉就可以了
            size--;
            nullCount++;
        }

    }

    /**
     * 整理为null的位置（碎片）
     * 但是是否调用是使用者的选择，可以在线程中定时整理，然后加锁
     * 重整操作的思路：从前往后遍历，如果发现其中一个元素为空，就把后面第一个不为空的元素往前填补，逐一进行
     * <p>
     * public synchronized void resize(E[] e){
     * // 重排列数据
     * // 重新更新index / size
     * int i=0;
     * for(;i<e.length;i++){
     * if(e[i]==null){
     * int j;
     * for(j=i;e[j]!=null;j++);
     * e[i]=e[j];
     * }
     * <p>
     * }
     * //更新一下属性即可
     * for(index=0;e[index]==null;index++);//直到index指向该有的位置
     * size=index;
     * //注意 只要是数组的话只有取到length这一个数据
     * // index和size是一个意思，如果针对数组这个结构的话必须遍历才能获得
     * }
     */

    //宇哥的resize方法，因为加了nullCount，这个方法好像变得不用那般遍历？
    public synchronized void resize() {
        Object[] reSizeArray = Arrays.copyOf(valueArray, index);//一个需要导包的方法，顾名思义就是复制数组的
        Object[] countArray = new Object[length - nullCount];
        //注意数组底层有length这个属性 没有size这个属性
        int count = 0;
        for (int i = 0; i < reSizeArray.length; i++) {
            Object data = reSizeArray[i];
            if (data != null) {
                countArray[count++] = data;//也就是实现了把数组里面不空的元素全都取出来
            }
        }
        valueArray = countArray;
        length = countArray.length;//因为数组里存在两种元素 总长是length，除了有存元素的部分以外都是nullCount部分
        //所以这个时候是满的
        size = countArray.length;
        index = countArray.length;
        nullCount = 0;
    }


    //清空方法
    public synchronized void clear() {
        length = defaultLength;
        valueArray = new Object[length];//重新定义一个新数组
        size = 0;
        nullCount = length;
        index = 0;
    }

    //get方法
    public synchronized E get(int i) {
        if (checkIndexAndElementIsNull(i)) {

            E value = (E) valueArray[i];//最底层用的还是object数组，为了改成泛型我们强制转换
            //其实E就是一个通配符，之后写好了这个类以后使用时候就就直接把E换成目标元素即可

            return value;
        } else {
            return null;
        }
    }

    public synchronized boolean checkIndexAndElementIsNull(int index) {//因为要去检查长度 坚持是否为空 所以加锁很有必要
        if (index >= length) {
            System.err.println(index + ">=" + length);//有的时候不需要报异常，报err不取这个数就够了
            return false;
        }
        if (index >= this.index) {
            System.err.println("index element is Not Element");
            return false;
        }
        Object data = valueArray[index];
        if (data == null) {
            System.err.println("index element is null");
            return false;
        }
        return true;//经过这三重判断都没有问题就可以返回TRUE去取值

    }

//打印数组信息的方法,直接调用这个return不能打印出来，要之后println才行 毕竟会return string

    public void print() {
        System.out.println("MyArrayList{" +
                "elementData=" + Arrays.toString(valueArray) +
                ",size=" + size +
                ",length=" + length +
                ",index=" + index +
                ",nullCount=" + nullCount +
                '}');

    }



    //main函数怎么快速写？直接main+回车
    public static void main(String[] args) {
        MyList<String> list = new MyList<>(15);
        for (int i = 0; i < 10; i++) {
            list.add(i + " ");

        }
        list.print();

        for (int i = 0; i < 10; i += 2) {
            list.remove(i);
        }
        list.print();
        list.resize();
        list.print();
        String m = list.get(0);
        System.out.println(m);
        MyList<String> racoo = new MyList<>(5);
        racoo.add("look");
        racoo.add("at");
        racoo.add("little");
        racoo.add("cutie");

        racoo.add("sheldon");
        racoo.add("!!!!!!");
        racoo.print();
        list.addAll(racoo);
        list.print();
    }

    public int index() {
        return index;
    }
}


