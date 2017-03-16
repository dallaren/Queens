/**
 * This class implements the logic behind the BDD for the n-queens problem
 * You should implement all the missing methods
 * 
 * @author Stavros Amanatidis
 *
 */

import net.sf.javabdd.*;

public class QueensLogic {
    private int size;
    private int[][] board;
    private BDDFactory factory;
    private BDD True;
    private BDD False;
    private BDD queensBDD;


    public QueensLogic() {
       //constructor
    }

    public void initializeGame(int size) {
        this.size = size;
        this.board = new int[size][size];
    }

    private void initializeBDD() {
        this.factory = JFactory.init(2000000,200000);
        factory.setVarNum(size*size);

        True = factory.one();
        False = factory.zero();

        queensBDD = True;
        addRules();
    }

    //add n-queens rules to the bdd
    private void addRules() {
        //at least one per row rule

        //at most one per row rule

        //at most one per column rule

        //at most one per NW diagonal rule

        //at most one per NE diagonal rule

    }

    public int[][] getGameBoard() {
        return board;
    }

    public boolean insertQueen(int column, int row) {

        if (board[column][row] == -1 || board[column][row] == 1) {
            return true;
        }
        
        board[column][row] = 1;
        
        // put some logic here..
      
        return true;
    }
}
