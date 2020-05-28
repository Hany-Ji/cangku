import javax.swing.ImageIcon;
public class Block {
     String name;            //名字,比如"雷"或数字
     int aroundMineNumber;         //周围雷的数目
     ImageIcon mineIcon;     //雷的图标
     boolean isMine=false;   //是否是雷
     boolean isMark=false; //是否被标记
     boolean isOpen=false;   //是否被挖开
     public void setName(String name) { 
         this.name=name; 
     }
     /**
     * @param n
     */
    public void setAroundMineNumber(int n) { 
         aroundMineNumber=n;
     }
     public int getAroundMineNumber() {
         return aroundMineNumber;
     }
     public String getName() { 
         return name;  
     }
     public boolean isMine() {
         return isMine;
     } 
     public void setIsMine(boolean b) {
         isMine=b;
     }
     public void setMineIcon(ImageIcon icon){
         mineIcon=icon;
     }
     public ImageIcon getMineicon(){
         return mineIcon;
     }
     public boolean getIsOpen() {
         return isOpen;
     } 
     public void setIsOpen(boolean p) {
         isOpen=p;
     }
     public boolean getIsMark() {
         return isMark;
     } 
     public void setIsMark(boolean m) {
         isMark=m;
     }
}
