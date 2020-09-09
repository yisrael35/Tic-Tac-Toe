package yisraelbar.com.tic_tac_toe;

    public class players {
    private	String type;
    private  int numOfWin;

      public players (String s){
         this.type=s;
         this.numOfWin=0;
      }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getNumOfWin() {
        return numOfWin;
    }
    public void setNumOfWin(int numOfWin) {
        this.numOfWin = numOfWin;
    }





}
