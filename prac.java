package pracexthash;

import java.util.ArrayList;
import java.util.Scanner;

//division function is mod 16
class Directory{
    String data;
    Block b;
    Directory(String data, Block b){
        this.data=data;
        this.b=b;
    }
}
class Block{
    ArrayList<Integer> keys;
    int block;
    Block() {
        this.keys=new ArrayList<>();
    }
}
public class prac {
    static int dir=2;
    static int keys[]=new int[20];
    static int n, index, flag=0,capacity,GD,LD;
    
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter capacity of bucket:");
        capacity=sc.nextInt();
        System.out.println("Enter number of keys:");
        n=sc.nextInt();
        //System.out.println("Enter global depth:");
        //GD=sc.nextInt();
        //System.out.println("Enter local depth:");
        //LD=sc.nextInt();
        System.out.println("Enter keys:");
        for(int i=0;i<n;i++){
            System.out.println("Enter "+" key"+(i+1) + ":") ;
            keys[i]=sc.nextInt();
            ArrayList<Directory> d =new ArrayList<>();
            for(int j=0;j<Math.pow(2,dir);j++){
                Block b=new Block();
                String format="%"+dir+"s";
                d.add(new Directory(String.format(format, Integer.toBinaryString(j)).replace(" ", "0"), b));
                b.block=dir;
            }
            int xy = i + 1;
            insert(keys,d,xy);
            while(flag==1){
            if(d.get(index).b.block==dir){
                directory_doubling(d);
                //display(d);
            }
            if(d.get(index).b.block!=dir){
                block_separation(d);
                //display(d);
            }
            }
            if(flag==0)
            {
            	display(d);
            }
            dir = 2;
            index =0;
        }
        //directory
        
    }
    static void display(ArrayList<Directory> d){
        //System.out.println(dir);
        System.out.println(d.size());
        for(int j=0;j<d.size();j++){
            Directory x=d.get(j);
            System.out.print(x.data+"-> ");
            for(int i=0;i<x.b.keys.size();i++){
                System.out.print(x.b.keys.get(i)+" ");
            }
            //System.out.print("  "+x.b.block);
            System.out.println();
        }
        System.out.println();
    }
    
    static void insert(int[] keys, ArrayList<Directory> d,int xy){
        flag=0;
        for(int i=0;i<xy;i++){
            int h=keys[i]%16;
            String bin=String.format("%4s", Integer.toBinaryString(h)).replace(" ", "0");
            for(int j=0;j<d.size();j++){
                Directory x=d.get(j);
                if(bin.endsWith(x.data)){
                    if(x.b.keys.size()<capacity)
                        x.b.keys.add(keys[i]);
                    else{
                        i=999;
                        index=j;
                        flag=1;
                        break;
                    }      
                }
            }
        }
    }
    
    static void directory_doubling(ArrayList<Directory> d){
        dir++;String format="%"+dir+"s";
        d.removeAll(d);
        int ff = (int)Math.pow(2, dir)/2;
        for(int i=0;i<Math.pow(2,dir)/2;i++){
            if(i!=index){
                Block b=new Block();
                d.add(new Directory(String.format(format, Integer.toBinaryString(i)).replace(" ", "0"), b));
                d.add(new Directory(String.format(format, Integer.toBinaryString(i+ff)).replace(" ", "0"), b));
                b.block=dir-1;
            } 
            else{
                Block b1=new Block();
                d.add(new Directory(String.format(format, Integer.toBinaryString(i)).replace(" ", "0"), b1));
                b1.block=dir;
                Block b2=new Block();
                d.add(new Directory(String.format(format, Integer.toBinaryString(i+ff)).replace(" ", "0"), b2));
                b2.block=dir;
            }
        }
        insert(keys,d,n);
    }

    private static void block_separation(ArrayList<Directory> d) {
        for(int i=0;i<d.size();i++){
            d.get(i).b.keys.clear();
        }
        if(index%2!=0){
            Block b1=new Block();
            d.get(index).b=b1;
            b1.block=dir;
            Block b2=new Block();
            d.get(index-1).b=b2;
            b2.block=dir;
            insert(keys, d,n);
        }
        else{
            Block b1=new Block();
            d.get(index).b=b1;
            b1.block=dir;
            Block b2=new Block();
            d.get(index+1).b=b2;
            b2.block=dir;
            insert(keys, d,n);
        }
        
    }
    
}
