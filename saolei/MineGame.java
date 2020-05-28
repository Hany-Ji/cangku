import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;
public class MineGame extends JFrame implements ActionListener{// JFrameҪ��ʾ��Ҫ����setVisable(true);
     JMenuBar bar;
     JMenu fileMenu;
     JMenuItem ����,�м�,�߼�,ɨ��Ӣ�۰�;
     MineArea mineArea=null;
     File Ӣ�۰�=new File("Ӣ�۰�.txt");
     Hashtable hashtable=null;
     ShowRecord showHeroRecord=null;
     MineGame(){
         mineArea=new MineArea(8,8,10,1);//����ͼ�ν���
         add(mineArea,BorderLayout.CENTER);
         bar=new JMenuBar();//��Ӳ˵������˵����˵���
         fileMenu=new JMenu("��Ϸ");
         ����=new JMenuItem("��");
         �м�=new JMenuItem("һ��");
         �߼�=new JMenuItem("����");
         ɨ��Ӣ�۰�=new JMenuItem("ɨ��Ӣ�۰�");  
         Font f=new Font("����",Font.BOLD,20);
         ����.setFont(f);
         �߼�.setFont(f);
         �м�.setFont(f);
         ɨ��Ӣ�۰�.setFont(f);
         fileMenu.setFont(f);
         fileMenu.add(����);
         fileMenu.add(�м�);
         fileMenu.add(�߼�);
         fileMenu.add(ɨ��Ӣ�۰�);
         bar.add(fileMenu);
         setJMenuBar(bar);
         ����.addActionListener(this);
         �м�.addActionListener(this);
         �߼�.addActionListener(this);
         ɨ��Ӣ�۰�.addActionListener(this);
         hashtable=new Hashtable();
         hashtable.put("����","����#"+999+"#����"); //��ӳɼ���ʼ����
         hashtable.put("�м�","�м�#"+999+"#����");
         hashtable.put("�߼�","�߼�#"+999+"#����");
         if(!Ӣ�۰�.exists()) {
            try{ FileOutputStream out=new FileOutputStream(Ӣ�۰�);
                 ObjectOutputStream objectOut=new ObjectOutputStream(out);
                 objectOut.writeObject(hashtable);
                 objectOut.close();
                 out.close();
            }
            catch(IOException e){}
        }
        showHeroRecord=new ShowRecord(this,hashtable);//��� ���µĳɼ�
        setBounds(100,100,480,480);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        validate();
     }
     public void actionPerformed(ActionEvent e){
        if(e.getSource()==����){
              mineArea.initMineArea(9,9,10,1);
              setBounds(100,100,480,580); 
        }
        if(e.getSource()==�м�){
              mineArea.initMineArea(16,16,40,2);
              setBounds(100,100,680,780);
        }
        if(e.getSource()==�߼�){
              mineArea.initMineArea(16,30,99,3);
              setBounds(100,100,1200,780);
        }
        if(e.getSource()==ɨ��Ӣ�۰�){ 
          if(showHeroRecord!=null)
           showHeroRecord.setVisible(true);
        }
        validate();
    }
    public static void main(String args[]){//������Ϸ
        new MineGame();
    }
}
