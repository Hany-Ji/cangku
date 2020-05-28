import javax.swing.*;
import java.awt.*;
public class BlockView extends JPanel{ 
     JLabel blockNameOrIcon; //用来显示Block对象的name、number和mineIcon属性
     JButton blockCover;     //用来遮挡blockNameOrIcon.
     CardLayout card;        //卡片式布局
     BlockView(){
        card=new CardLayout();
        setLayout(card);
        blockNameOrIcon=new JLabel("",JLabel.CENTER);
        blockNameOrIcon.setHorizontalTextPosition(AbstractButton.CENTER);
        blockNameOrIcon.setVerticalTextPosition(AbstractButton.CENTER); 
        blockCover=new JButton(); 
        add("cover",blockCover);
        add("view",blockNameOrIcon);
     }
     public void giveView(Block block){
        if(block.isMine){
           blockNameOrIcon.setText(block.getName());
           blockNameOrIcon.setIcon(block.getMineicon());
        }
        else {
           int n=block.getAroundMineNumber();
           if(n>=1)
             blockNameOrIcon.setText(""+n);
           else
             blockNameOrIcon.setText(" ");
        }
     }
     public void seeBlockNameOrIcon(){//看见标签/遮盖下面的东西
        card.show(this,"view");
        validate();
     }
     public void seeBlockCover(){//看见按钮/遮盖的东西
        card.show(this,"cover");
        validate();
     }
     public JButton getBlockCover(){
        return blockCover;
     } 
}
