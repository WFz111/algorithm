package test;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        Random random = new Random();
        try {
            FileReader fi=new FileReader(new File(System.getProperty("user.dir")+"/algorithms/data/synthetic_test2.txt"));
            bufferedReader = new BufferedReader(fi);
            FileWriter fw= new FileWriter(new File(System.getProperty("user.dir")+"/algorithms/data/synthetic_test3.txt"));
            bufferedWriter = new BufferedWriter(fw);

            String line = "";
            while ((line=bufferedReader.readLine())!=null){
                bufferedWriter.write(line);//写‘48’
                bufferedWriter.newLine();//换行
                line = bufferedReader.readLine();//读练习类型
                bufferedWriter.write(line);//写练习类型
                bufferedWriter.newLine();//换行
                line = bufferedReader.readLine();//读分数
                double[] score = Arrays.stream(line.split(",")).mapToDouble(Double::parseDouble).toArray();
                for (int i=0; i<score.length; i=i+4){
                    double temp = (score[i]+score[i+1]+score[i+2]+score[i+3])/4;
                    int d;
                    if (temp>=80){
//                        d = random.nextInt(2)+1;
                        d = 1;
                    }else if (temp<80 && temp>=70){
//                        d = random.nextInt(2)+2;
                        d = 2;
                    }else {
                        d = 3;
                    }
                    if (i==44){
                        bufferedWriter.write(d+"");
                    }else {
                        bufferedWriter.write(d+",");
                    }
                }//for
                bufferedWriter.newLine();
                bufferedWriter.write(line);//写分数
                bufferedWriter.newLine();

                bufferedWriter.flush();
            }//while
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
