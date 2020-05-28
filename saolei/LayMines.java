import java.util.*;
import javax.swing.*;
public class LayMines{   
     ImageIcon mineIcon; 
     LayMines() {
          mineIcon=new ImageIcon("mine.jpg");//定义具体图片
     }
     public void layMinesForBlock(Block block[][],int mineCount){
         int row=block.length;
         int column=block[0].length;
         LinkedList<Block> list=new LinkedList<Block>(); //Block换成列表
         for(int i=0;i<row;i++) {
             for(int j=0;j<column;j++)
                list.add(block[i][j]);
         } 
         while(mineCount>0){//埋雷操作
            int size=list.size();             // list返回节点的个数
            int randomIndex=(int)(Math.random()*size);//0-size-1的范围
            Block b=list.get(randomIndex);
            b.setIsMine(true);
            b.setName("雷");
            b.setMineIcon(mineIcon);
            list.remove(randomIndex);        //list删除索引值为randomIndex的节点
            mineCount--;
        } 
        for(int i=0;i<row;i++){
           for(int j=0;j<column;j++){
              if(block[i][j].isMine()){
                 block[i][j].setIsOpen(false);//定义不显示翻开
                 block[i][j].setIsMark(false);//定义不显示标志
              }
              else {//计算不是雷的标签周围雷数
                 int mineNumber=0;
                 for(int k=Math.max(i-1,0);k<=Math.min(i+1,row-1);k++) {
                       for(int t=Math.max(j-1,0);t<=Math.min(j+1,column-1);t++){
                          if(block[k][t].isMine())
                              mineNumber++; 
                       }
                 }
                 block[i][j].setIsOpen(false); //定义不显示翻开
                 block[i][j].setIsMark(false);  //定义不显示标志     
                 block[i][j].setName(""+mineNumber);
                 block[i][j].setAroundMineNumber(mineNumber);
              }
           } 
        }    
    }
}
