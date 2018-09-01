package src.com.test;

import java.lang.ref.WeakReference;

public class TestWeakReference {
    private static class BiggerObject{
        public int[] values;  
        public String name;  
        public BiggerObject(String name){  
            this.name=name;  
            values=new int[1024];  
        }  
    }  
    public static void main(String[] args) {  
        int count=100;
        WeakReference[] values=new WeakReference[count];  
        for(int i=0;i<count;i++){  
            values[i]=new WeakReference<BiggerObject>(new BiggerObject("Object-"+i));  
        }    
        System.out.println(((BiggerObject)(values[values.length-1].get())).name);  
        System.gc();
        for(int i=0;i<10;i++){  
            System.out.println(((BiggerObject)(values[i].get())).name);  
        }   
    }   
}  
