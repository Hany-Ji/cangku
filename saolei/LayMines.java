import java.util.*;
import javax.swing.*;
public class LayMines{   
     ImageIcon mineIcon; 
     LayMines() {
          mineIcon=new ImageIcon("mine.jpg");//�������ͼƬ
     }
     public void layMinesForBlock(Block block[][],int mineCount){
         int row=block.length;
         int column=block[0].length;
         LinkedList<Block> list=new LinkedList<Block>(); //Block�����б�
         for(int i=0;i<row;i++) {
             for(int j=0;j<column;j++)
                list.add(block[i][j]);
         } 
         while(mineCount>0){//���ײ���
            int size=list.size();             // list���ؽڵ�ĸ���
            int randomIndex=(int)(Math.random()*size);//0-size-1�ķ�Χ
            Block b=list.get(randomIndex);
            b.setIsMine(true);
            b.setName("��");
            b.setMineIcon(mineIcon);
            list.remove(randomIndex);        //listɾ������ֵΪrandomIndex�Ľڵ�
            mineCount--;
        } 
        for(int i=0;i<row;i++){
           for(int j=0;j<column;j++){
              if(block[i][j].isMine()){
                 block[i][j].setIsOpen(false);//���岻��ʾ����
                 block[i][j].setIsMark(false);//���岻��ʾ��־
              }
              else {//���㲻���׵ı�ǩ��Χ����
                 int mineNumber=0;
                 for(int k=Math.max(i-1,0);k<=Math.min(i+1,row-1);k++) {
                       for(int t=Math.max(j-1,0);t<=Math.min(j+1,column-1);t++){
                          if(block[k][t].isMine())
                              mineNumber++; 
                       }
                 }
                 block[i][j].setIsOpen(false); //���岻��ʾ����
                 block[i][j].setIsMark(false);  //���岻��ʾ��־     
                 block[i][j].setName(""+mineNumber);
                 block[i][j].setAroundMineNumber(mineNumber);
              }
           } 
        }    
    }
}
