package src.com.mytest;

import java.lang.ref.SoftReference;


public class TestSoftReference {
	 private static class BiggerObject{
	        public int[] values;  
	        public String name;  
	        public BiggerObject(String name){  
	            this.name=name;  
	            values=new int[1024];  
	        }
	    }  
	    public static void main(String[] args) {  
	        int count=10000;//
	        SoftReference[] values=new SoftReference[count];  
	        for(int i=0;i<count;i++){  
	            values[i]=new SoftReference<BiggerObject>(new BiggerObject("Object-"+i));  
	        }    
	        System.out.println(((BiggerObject)(values[values.length-1].get())).name);  
	        for(int i=0;i<10;i++){
	            System.out.println(((BiggerObject)(values[i].get())).name);  
	        }   
	    }   
}
