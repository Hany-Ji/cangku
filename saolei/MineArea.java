import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MineArea extends JPanel implements ActionListener,MouseListener{//定义可视化图形,显示状态是true
     JButton reStart;
     Block [][] block;
     BlockView [][] blockView;
     LayMines lay;
     int row,colum,mineCount,markMount;//雷区的行数、列数以及地雷个数和用户给出的标记数
     ImageIcon mark;
     int grade; 
     JPanel pCenter,pNorth;//界面分两块，卡片分布块+计时，启动，计数栏
     JTextField showTime,showMarkedMineCount,task; //显示用时以及标记数
     Timer time;  //计时器
     int spendTime=0;
     Record record;
     public MineArea(int row,int colum,int mineCount,int grade) {
         reStart=new JButton("重新开始");
         mark=new ImageIcon("mark.jpg");  //探雷标记
         task=new JTextField(10);
        
         time=new Timer(1000,this);
         showTime=new JTextField(8);
         Font f=new Font("宋体",Font.BOLD,20);
         reStart.setFont(f);
         task.setFont(f);
         showMarkedMineCount=new JTextField(8);
         showTime.setHorizontalAlignment(JTextField.CENTER);//文本对齐格式
         showMarkedMineCount.setHorizontalAlignment(JTextField.CENTER);
         showMarkedMineCount.setFont(new Font("宋体",Font.BOLD,20));//文本格式：字体，黑体，字号
         showTime.setFont(new Font("宋体",Font.BOLD,20));         
         pCenter=new JPanel();
         pNorth=new JPanel();
         lay=new LayMines();             
         initMineArea(row,colum,mineCount,grade); //初始化雷区,见下面的LayMines()
         reStart.addActionListener(this);
         //task=new JTextField("难度：初级");
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
               blockView[i][j].giveView(block[i][j]); //给block[i][j]提供视图
               pCenter.add(blockView[i][j]);
               blockView[i][j].getBlockCover().addActionListener(this);
               blockView[i][j].getBlockCover().addMouseListener(this);
               blockView[i][j].seeBlockCover();//显示cover
               blockView[i][j].getBlockCover().setEnabled(true);//按钮是否响应
               blockView[i][j].getBlockCover().setIcon(null);//设置按钮默认图标
          }
       }
      showMarkedMineCount.setText("标记："+markMount); 
      validate();
      switch(grade){
      case 1: task.setText("难度：简单");
              break;
      case 2: task.setText("难度：一般");
              break;
      case 3: task.setText("难度：困难");
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
          if(block[m][n].isMine()) { //如果踩雷
             for(int i=0;i<row;i++) {
                for(int j=0;j<colum;j++) {
                   blockView[i][j].getBlockCover().setEnabled(false);//不可响应
                   if(block[i][j].isMine())
                      blockView[i][j].seeBlockNameOrIcon(); //显示所有雷（不是翻开卡片操作）
                }
             }
             time.stop();
             spendTime=0;
             markMount=mineCount;
          }
         else { 
             show(m,n);          //见本类后面的show方法
          }
      }
      if(e.getSource()==reStart) {
         initMineArea(row,colum,mineCount,grade);
      }
      if(e.getSource()==time){
         spendTime++;
         showTime.setText("用时："+spendTime);
      }
      inquireWin();//时刻检查是否通关
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
                 show(k,t);//嵌套函数
          } 
      }      
    }
    public void mousePressed(MouseEvent e){ //计数棋子的标记
        JButton source=(JButton)e.getSource();
        for(int i=0;i<row;i++) {
            for(int j=0;j<colum;j++) {
              if(e.getModifiers()==InputEvent.BUTTON3_MASK&&
                 source==blockView[i][j].getBlockCover()){
                 if(block[i][j].getIsMark()) {
                        source.setIcon(null);
                        block[i][j].setIsMark(false);
                        markMount=markMount+1;
                        showMarkedMineCount.setText("标记："+markMount);
                 }
                 else{
                        source.setIcon(mark);
                        block[i][j].setIsMark(true);
                        markMount=markMount-1;
                        showMarkedMineCount.setText("标记："+markMount);
                 }
              }    
            }
        }
   }
   public void inquireWin(){//时刻跟新，检查没有打开的方块数为地雷数，表示通关
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
              case 1: record.setGrade("初级");
                      break;
              case 2: record.setGrade("中级");
                      break;
              case 3: record.setGrade("高级");
                      break;
           }
          record.setTime(spendTime);
          record.setVisible(true); 
        }
            
   
   }
   public void mouseReleased(MouseEvent e){}//鼠标按键被释放是被触发，点在A点不松开移动到B点，取消A点的点击效果
   public void mouseEntered(MouseEvent e){}//鼠标放上去改变颜色
   public void mouseExited(MouseEvent e){}//光标移除组件时，解除之前效果
   public void mouseClicked(MouseEvent e){}//发生单击事件时被触发效果
} 
