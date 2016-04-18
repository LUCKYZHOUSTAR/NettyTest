package NIO;

import java.nio.IntBuffer;

public class BufferTest {

	public static void main(String[] args) {
		
		//创建指定长度的缓冲区
		IntBuffer buff=IntBuffer.allocate(10);
		int[] array=new int[]{3,5,1};
		//使用数组来创建一个缓冲区视图
		buff=buff.wrap(array);
		//使用数组的某一个区间来创建视图
		buff=buff.wrap(array, 0, 2);
		
		//对缓冲区某个位置上面进行元素修改
		buff.put(0, 7);
		//遍历缓冲区中的数据
		System.out.println("缓冲区中的数据如下:");
		for(int i=0;i<buff.limit();i++){
			System.out.println(buff.get()+"\t");
		}
		
		//遍历数组中元素，结果表明缓冲区的修改，也会直接影响到原数组的数据
		for(int a:array){
			System.out.println(a+"\t");
		}
		
		buff.flip();//对缓冲区进行反转
		buff.clear();
		
		//赋值一个新的缓冲区
		IntBuffer buff2=buff.duplicate();
		System.out.println(buff2);
	}
}
