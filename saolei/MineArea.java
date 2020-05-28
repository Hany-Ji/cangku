import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MineArea extends JPanel implements ActionListener,MouseListener{//������ӻ�ͼ��,��ʾ״̬��true
     JButton reStart;
     Block [][] block;
     BlockView [][] blockView;
     LayMines lay;
     int row,colum,mineCount,markMount;//�����������������Լ����׸������û������ı����
     ImageIcon mark;
     int grade; 
     JPanel pCenter,pNorth;//��������飬��Ƭ�ֲ���+��ʱ��������������
     JTextField showTime,showMarkedMineCount,task; //��ʾ��ʱ�Լ������
     Timer time;  //��ʱ��
     int spendTime=0;
     Record record;
     public MineArea(int row,int colum,int mineCount,int grade) {
         reStart=new JButton("���¿�ʼ");
         mark=new ImageIcon("mark.jpg");  //̽�ױ��
         task=new JTextField(10);
        
         time=new Timer(1000,this);
         showTime=new JTextField(8);
         Font f=new Font("����",Font.BOLD,20);
         reStart.setFont(f);
         task.setFont(f);
         showMarkedMineCount=new JTextField(8);
         showTime.setHorizontalAlignment(JTextField.CENTER);//�ı������ʽ
         showMarkedMineCount.setHorizontalAlignment(JTextField.CENTER);
         showMarkedMineCount.setFont(new Font("����",Font.BOLD,20));//�ı���ʽ�����壬���壬�ֺ�
         showTime.setFont(new Font("����",Font.BOLD,20));         
         pCenter=new JPanel();
         pNorth=new JPanel();
         lay=new LayMines();             
         initMineArea(row,colum,mineCount,grade); //��ʼ������,�������LayMines()
         reStart.addActionListener(this);
         //task=new JTextField("�Ѷȣ�����");
         pNorth.add(showMarkedMineCount);
         pNorth.add(reStart);
         pNorth.add(showTime);
         pNorth.add(task);
         setLayout(new BorderLayout());
         add(pNorth,BorderLayout.NORTH);
         add(pCenter,BorderLayout.CENTER);
        
    }
    public void initMineArea(int row,int colum,int mineCount,int grade){
       pCenter.removeAll();
       spendTime=0;
       markMount=mineCount;
       this.row=row;
       this.colum=colum;
       this.mineCount=mineCount; 
       this.grade=grade; 
       block=new Block[row][colum];
       for(int i=0;i<row;i++){
         for(int j=0;j<colum;j++)
              block[i][j]=new Block();
       }
       lay.layMinesForBlock(block,mineCount);    
       blockView=new BlockView[row][colum];
       pCenter.setLayout(new GridLayout(row,colum));
       for(int i=0;i<row;i++) {
          for(int j=0;j<colum;j++) {
               blockView[i][j]=new BlockView(); 
               blockView[i][j].giveView(block[i][j]); //��block[i][j]�ṩ��ͼ
               pCenter.add(blockView[i][j]);
               blockView[i][j].getBlockCover().addActionListener(this);
               blockView[i][j].getBlockCover().addMouseListener(this);
               blockView[i][j].seeBlockCover();//��ʾcover
               blockView[i][j].getBlockCover().setEnabled(true);//��ť�Ƿ���Ӧ
               blockView[i][j].getBlockCover().setIcon(null);//���ð�ťĬ��ͼ��
          }
       }
      showMarkedMineCount.setText("��ǣ�"+markMount); 
      validate();
      switch(grade){
      case 1: task.setText("�Ѷȣ���");
              break;
      case 2: task.setText("�Ѷȣ�һ��");
              break;
      case 3: task.setText("�Ѷȣ�����");
              break;
   }
     
         }
    
    
   public void setRow(int row){
       this.row=row;
   }
   public void setColum(int colum){
       this.colum=colum;
   }
   public void setMineCount(int mineCount){
       this.mineCount=mineCount;
   }
   public void setGrade(int grade) {
       this.grade=grade;
   }
   public void actionPerformed(ActionEvent e) {
        if(e.getSource()!=reStart&&e.getSource()!=time) {
          time.start();  
          int m=-1,n=-1; 
          for(int i=0;i<row;i++) {
             for(int j=0;j<colum;j++) {
               if(e.getSource()==blockView[i][j].getBlockCover()){
                  m=i;
                  n=j;
                  break;
               }
             }
          }
          if(block[m][n].isMine()) { //�������
             for(int i=0;i<row;i++) {
                for(int j=0;j<colum;j++) {
                   blockView[i][j].getBlockCover().setEnabled(false);//������Ӧ
                   if(block[i][j].isMine())
                      blockView[i][j].seeBlockNameOrIcon(); //��ʾ�����ף����Ƿ�����Ƭ������
                }
             }
             time.stop();
             spendTime=0;
             markMount=mineCount;
          }
         else { 
             show(m,n);          //����������show����
          }
      }
      if(e.getSource()==reStart) {
         initMineArea(row,colum,mineCount,grade);
      }
      if(e.getSource()==time){
         spendTime++;
         showTime.setText("��ʱ��"+spendTime);
      }
      inquireWin();//ʱ�̼���Ƿ�ͨ��
    }
    
    public void show(int m,int n) {
      if(block[m][n].getAroundMineNumber()>0&&block[m][n].getIsOpen()==false){
          blockView[m][n].seeBlockNameOrIcon();
          block[m][n].setIsOpen(true);
          return;
      }
      else if(block[m][n].getAroundMineNumber()==0&&block[m][n].getIsOpen()==false){
          blockView[m][n].seeBlockNameOrIcon();
          block[m][n].setIsOpen(true);
          for(int k=Math.max(m-1,0);k<=Math.min(m+1,row-1);k++) {
             for(int t=Math.max(n-1,0);t<=Math.min(n+1,colum-1);t++)
                 show(k,t);//Ƕ�׺���
          } 
      }      
    }
    public void mousePressed(MouseEvent e){ //�������ӵı��
        JButton source=(JButton)e.getSource();
        for(int i=0;i<row;i++) {
            for(int j=0;j<colum;j++) {
              if(e.getModifiers()==InputEvent.BUTTON3_MASK&&
                 source==blockView[i][j].getBlockCover()){
                 if(block[i][j].getIsMark()) {
                        source.setIcon(null);
                        block[i][j].setIsMark(false);
                        markMount=markMount+1;
                        showMarkedMineCount.setText("��ǣ�"+markMount);
                 }
                 else{
                        source.setIcon(mark);
                        block[i][j].setIsMark(true);
                        markMount=markMount-1;
                        showMarkedMineCount.setText("��ǣ�"+markMount);
                 }
              }    
            }
        }
   }
   public void inquireWin(){//ʱ�̸��£����û�д򿪵ķ�����Ϊ����������ʾͨ��
        int number=0;
        for(int i=0;i<row;i++) {
            for(int j=0;j<colum;j++) {
              if(block[i][j].getIsOpen()==false)
                number++;
            }
        }
        if(number==mineCount){
           time.stop();
           record=new Record();
           switch(grade){
              case 1: record.setGrade("����");
                      break;
              case 2: record.setGrade("�м�");
                      break;
              case 3: record.setGrade("�߼�");
                      break;
           }
          record.setTime(spendTime);
          record.setVisible(true); 
        }
            
   
   }
   public void mouseReleased(MouseEvent e){}//��갴�����ͷ��Ǳ�����������A�㲻�ɿ��ƶ���B�㣬ȡ��A��ĵ��Ч��
   public void mouseEntered(MouseEvent e){}//������ȥ�ı���ɫ
   public void mouseExited(MouseEvent e){}//����Ƴ����ʱ�����֮ǰЧ��
   public void mouseClicked(MouseEvent e){}//���������¼�ʱ������Ч��
} 
