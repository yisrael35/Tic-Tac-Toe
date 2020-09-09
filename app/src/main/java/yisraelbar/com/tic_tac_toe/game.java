package yisraelbar.com.tic_tac_toe;

public class game {


    private players x;
    private players o;
    private String []bord;
    private	int countNumOfTurn;
    public game () {
        this.countNumOfTurn =0;
        x=new players("x");
        o=new players("o");
        bord=new String [9];
        for (int i = 0; i < bord.length; i++) {
            this.bord [i]="";
        }
    }

    public int getXNumOfWin() {
        return x.getNumOfWin();
    }

    public void setXNumOfWinPlusOne() {
        x.setNumOfWin(getXNumOfWin()+1);
    }
    public void setXNumOfWin(int num){
        x.setNumOfWin(num);
    }
    public int getONumOfWin() {
        return o.getNumOfWin();
    }

    public void setONumOfWinPlusOne() {
        o.setNumOfWin(getONumOfWin()+1);
    }
    public void setONumOfWin(int num){
        o.setNumOfWin(num);
    }
//check if the choice is valid
    public boolean checkIfValid(int spot){
        if (bord[spot].equals("")){
            return  true;
        }
        return  false;
    }

    public String[] getBord() {
        return bord;
    }

    public void setBord(int spot, String s) {
        this.bord [spot]= s;
    }
    public void setBord(String [] s){
        this.bord=s;
    }

    public int getCountNumOfTurn() {
        return countNumOfTurn;
    }

    public void setCountNumOfTurn() {
        ++this.countNumOfTurn ;
    }
//check if x/o is won
    public boolean checkIfWin(String []bord, String s) {
        if ((bord[0].equals(s)&& bord[1].equals(s)&&bord[2].equals(s))||
                (bord[3].equals(s)&& bord[4].equals(s)&&bord[5].equals(s))||
                (bord[6].equals(s)&& bord[7].equals(s)&&bord[8].equals(s))||
                (bord[0].equals(s)&& bord[3].equals(s)&&bord[6].equals(s))||
                (bord[1].equals(s)&& bord[4].equals(s)&&bord[7].equals(s))||
                (bord[2].equals(s)&& bord[5].equals(s)&&bord[8].equals(s))||
                (bord[0].equals(s)&& bord[4].equals(s)&&bord[8].equals(s))||
                (bord[2].equals(s)&& bord[4].equals(s)&&bord[6].equals(s))
        ) {
            return true;
        }
        return false;
    }
//check how many position available
    public int howManyPositionAvailable(String [] bord) {
        int temp=0;
        for (int i = 0; i < bord.length; i++) {
            if(bord[i].equals("")) {
                temp++;
            }
        }
        return temp;
    }
//return array with all the position available
    public int [] placesAvailable(String [] bord) {
        int temp[]=new int[howManyPositionAvailable(bord)];
        for (int i = 0,j=0; i < bord.length; i++) {
            if (bord[i].equals("")) {
                temp[j]=i;
                j++;
            }
        }
        return temp;
    }

  //difficulty 3 -its a easy level
    public int bestNextPlay3() {
        for (int i = 0; i < bord.length; i++) {
            if (bord[i].equals("")) {
                String[] js1 = copyTheArray(bord);
                if (helperBestNext3(js1, true, i)) {
                    return i;
                }
            }
        }
        int temp1=-1,counter=0;
        while (temp1==-1 && counter<9) {
            if (bord[counter].equals("")) {
                temp1=counter;
            }
            counter++;
        }
        return temp1;
    }

//clear the bord
    public void clear(){
        this.countNumOfTurn =0;
        bord=new String [9];
        for (int i = 0; i < bord.length; i++) {
            this.bord [i]="";
        }
    }
//copy the bord
    private String[] copyTheArray(String[] justForCheck) {
        String [] js1=new String[9] ;
        for (int j = 0; j < js1.length; j++) {
            js1[j]=justForCheck[j];
        }
        return js1;
    }
   //difficulty 3
    private boolean helperBestNext3 (String [] justForCheck, boolean myturn, int index) {
        //computer wins
        if (myturn && checkIfWin(justForCheck, "o")) {
            return true;
        }
        //computer lose
        if (!myturn && checkIfWin(justForCheck,"x")) {
            return false;
        }
        //check if all the spot are been full
        int temp=0;
        for (int i = 0; i < justForCheck.length; i++) {
            if (justForCheck[i].equals("")) {
                temp++;
            }
        }
        //its a tie
        if (temp==0) {
            return false;
        }
        if (myturn) {
            justForCheck [index]="o";
            int [] allPosition= placesAvailable(justForCheck);
            for (int i = 0; i < allPosition.length; i++) {
                return helperBestNext3((justForCheck), false, allPosition[i]);
            }
        }
        if (!myturn) {
            justForCheck [index]="x";
            int [] allPosition= placesAvailable(justForCheck);
            for (int i = 0; i < allPosition.length; i++) {
                return helperBestNext3((justForCheck), true, allPosition[i]);
            }
        }
        return false;
    }

    //difficulty 2 its a medium level
    public int bestNextPlay2() {
        for (int i = 0; i < bord.length; i++) {
            if (bord[i].equals("")) {
                String[] js1 = copyTheArray(this.bord);
                lessMoves=100;
                if (helperBestNext2(js1, true, i,true)) {
                    return i;
                }
            }
        }
        int temp1=-1,counter=0;
        while (temp1==-1 && counter<9) {
            if (bord[counter].equals("")) {
                temp1=counter;
            }
            counter++;
        }
//        System.out.println("computer can loos");
        return temp1;
    }
    //difficulty 2
    private int lessMoves=100, temp1=0;
    private boolean helperBestNext2 (String [] justForCheck, boolean myturn, int index,boolean newMove) {
        if (newMove) {
            temp1=0;
        }
        if (temp1>=lessMoves) {
            return false;
        }

        //computer wins
        if (myturn && checkIfWin(justForCheck, "o")) {
            if (temp1<lessMoves) {
                lessMoves=temp1;
            }
            return true;
        }
        //computer lose
        if (!myturn && checkIfWin(justForCheck,"x")) {

            return false;
        }
        //check if all the spot are been full
        int temp=0;
        for (int i = 0; i < justForCheck.length; i++) {
            if (justForCheck[i].equals("")) {
                temp++;
            }
        }
        //its a tie
        if (temp==0) {
            return false;

        }
        if (myturn) {
            justForCheck [index]="o";
            int [] allPosition= placesAvailable(justForCheck);
            for (int i = 0; i < allPosition.length; i++) {
                ++temp1;
                return helperBestNext2((justForCheck), false, allPosition[i],false);
            }
        }
        if (!myturn) {
            justForCheck [index]="x";
            int [] allPosition= placesAvailable(justForCheck);
            for (int i = 0; i < allPosition.length; i++) {
                ++temp1;
                return helperBestNext2((justForCheck), true, allPosition[i],false);
            }
        }
        return false;
    }

    //difficulty 1
    //check if you can lost/win in one play
    public boolean goingToLooseTo(String s) {
//        ="x";
        if (//1
                        (bord[0].equals("")&& bord[1].equals(s)&&bord[2].equals(s))||
                        (bord[0].equals("")&& bord[3].equals(s)&&bord[6].equals(s))||
                        (bord[3].equals("")&& bord[4].equals(s)&&bord[5].equals(s))||
                        (bord[6].equals("")&& bord[7].equals(s)&&bord[8].equals(s))||
                        (bord[1].equals("")&& bord[4].equals(s)&&bord[7].equals(s))||
                        (bord[2].equals("")&& bord[5].equals(s)&&bord[8].equals(s))||
                        (bord[0].equals("")&& bord[4].equals(s)&&bord[8].equals(s))||
                        (bord[2].equals("")&& bord[4].equals(s)&&bord[6].equals(s))||
                        //2
                        (bord[0].equals(s)&& bord[1].equals("")&&bord[2].equals(s))||
                        (bord[3].equals(s)&& bord[4].equals("")&&bord[5].equals(s))||
                        (bord[6].equals(s)&& bord[7].equals("")&&bord[8].equals(s))||
                        (bord[0].equals(s)&& bord[3].equals("")&&bord[6].equals(s))||
                        (bord[1].equals(s)&& bord[4].equals("")&&bord[7].equals(s))||
                        (bord[2].equals(s)&& bord[5].equals("")&&bord[8].equals(s))||
                        (bord[0].equals(s)&& bord[4].equals("")&&bord[8].equals(s))||
                        (bord[2].equals(s)&& bord[4].equals("")&&bord[6].equals(s))||
                        //3
                        (bord[0].equals(s)&& bord[1].equals(s)&&bord[2].equals(""))||
                        (bord[3].equals(s)&& bord[4].equals(s)&&bord[5].equals(""))||
                        (bord[6].equals(s)&& bord[7].equals(s)&&bord[8].equals(""))||
                        (bord[0].equals(s)&& bord[3].equals(s)&&bord[6].equals(""))||
                        (bord[1].equals(s)&& bord[4].equals(s)&&bord[7].equals(""))||
                        (bord[2].equals(s)&& bord[5].equals(s)&&bord[8].equals(""))||
                        (bord[0].equals(s)&& bord[4].equals(s)&&bord[8].equals(""))||
                        (bord[2].equals(s)&& bord[4].equals(s)&&bord[6].equals(""))
        ) {
            return true;
        }
        return false;
    }
//check all the cell if one them can cause win/lose in one play
    public int block(String s) {
        int toReturn=0;
//        String s="x";
        if ((bord[0].equals("")&& bord[4].equals(s)&&bord[8].equals(s))||
                (bord[0].equals("")&& bord[3].equals(s)&&bord[6].equals(s))||
                (bord[0].equals("")&& bord[1].equals(s)&&bord[2].equals(s))) {
            toReturn=0;
        }
        else if ((bord[1].equals("")&& bord[4].equals(s)&&bord[7].equals(s))||
                (bord[0].equals(s)&& bord[1].equals("")&&bord[2].equals(s))) {
            toReturn=1;
        }
        else if ((bord[2].equals("")&& bord[5].equals(s)&&bord[8].equals(s))||
                (bord[2].equals("")&& bord[4].equals(s)&&bord[6].equals(s))||
                (bord[0].equals(s)&& bord[1].equals(s)&&bord[2].equals(""))) {
            toReturn=2;
        }
        else if((bord[3].equals("")&& bord[4].equals(s)&&bord[5].equals(s))||
                (bord[0].equals(s)&& bord[3].equals("")&&bord[6].equals(s))) {
            toReturn =3;
        }else if ((bord[3].equals(s)&& bord[4].equals("")&&bord[5].equals(s))||
                (bord[1].equals(s)&& bord[4].equals("")&&bord[7].equals(s))||
                (bord[0].equals(s)&& bord[4].equals("")&&bord[8].equals(s))||
                (bord[2].equals(s)&& bord[4].equals("")&&bord[6].equals(s))) {
            toReturn=4;
        }
        else if((bord[2].equals(s)&& bord[5].equals("")&&bord[8].equals(s))||
                (bord[3].equals(s)&& bord[4].equals(s)&&bord[5].equals(""))) {
            toReturn=5;
        }
        else if ((bord[6].equals("")&& bord[7].equals(s)&&bord[8].equals(s))||
                (bord[0].equals(s)&& bord[3].equals(s)&&bord[6].equals(""))||
                (bord[2].equals(s)&& bord[4].equals(s)&&bord[6].equals(""))) {
            toReturn=6;
        }
        else if ((bord[6].equals(s)&& bord[7].equals("")&&bord[8].equals(s))||
                (bord[1].equals(s)&& bord[4].equals(s)&&bord[7].equals(""))) {
            toReturn=7;
        }
        else if ((bord[6].equals(s)&& bord[7].equals(s)&&bord[8].equals(""))||
                (bord[2].equals(s)&& bord[5].equals(s)&&bord[8].equals(""))||
                (bord[0].equals(s)&& bord[4].equals(s)&&bord[8].equals(""))) {
            toReturn=8;
        }
        return toReturn;
    }

    public int difficultyLevel(int level ){
        int blockCell=0,toReturn=0;
            switch (level){
              //level 0 is the most difficult, check if you can win in one play
                //if not it's check if you can lose in one play, and if not -find the best play
                case 0:
                    if (goingToLooseTo("o")){
                        blockCell=block("o");
                        return blockCell;
                    }
                    if (goingToLooseTo("x")){
                        blockCell=block("x");
                        return blockCell;
                    }
                        toReturn = bestNextPlay2();
                        break;
                        //level 1 is the second best just check for the bast play in the less moves
                case 1:
                    toReturn = bestNextPlay2();
                    break;
                    //level 2 is the easy one, just doing a good move
                case 2:
                    toReturn =bestNextPlay3();
                    break;
                    //randomly choose a play
                case 3:
                    toReturn=randomPlay();
                    break;
                default:
            }
        return toReturn;
    }
//random choose available cell
    private int randomPlay(){
        int howMenyPositionAvailable= howManyPositionAvailable(bord);
        int [] placesAvailable= placesAvailable(bord);
        int rand= (int) (Math.random()*howMenyPositionAvailable);
        rand=placesAvailable[rand];
        return  rand;
    }

}
