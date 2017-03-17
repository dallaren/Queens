/**
 * This class implements the logic behind the BDD for the n-queens problem
 * You should implement all the missing methods
 * 
 * @author Stavros Amanatidis
 *
 */

import net.sf.javabdd.*;

public class QueensLogic {
    private int N;
    private int[][] board;
    private BDDFactory factory;
    private BDD True;
    private BDD False;
    private BDD queensBDD;


    public QueensLogic() {
       //constructor
    }

    public int[][] getGameBoard() {
        return board;
    }

    public boolean insertQueen(int column, int row) {

        if (board[column][row] == -1 || board[column][row] == 1) {
            return true;
        }

        board[column][row] = 1;

        return true;
    }

    public void initializeGame(int size) {
        this.N = size;
        this.board = new int[size][size];
        initializeBDD();
    }

    private void initializeBDD() {
        this.factory = JFactory.init(2000000,200000);
        factory.setVarNum(N * N);

        True = factory.one();
        False = factory.zero();
        queensBDD = True;

        addRules();
    }

    //add n-queens rules to the bdd
    private void addRules() {

        atLeastOnePerRow();
        atMostOnePerRow();
        atMostOnePerColumn();
        atMostOnePerNWDiagonal();
        atMostOnePerNEDiagonal();
    }

    private void atLeastOnePerRow() {
        for (int row = 0; row < N; row++) {
            BDD subBDD = False;

            for (int col = 0; col < N; col++) {
                //at least one queen in each row
                //xi or xi+1 or...
                subBDD = subBDD.or(factory.ithVar(getVar(col,row)));
            }

            //this goes for all rows
            queensBDD = queensBDD.and(subBDD);
        }
    }

    private void atMostOnePerRow() {
        for (int row = 0; row < N; row++) {
            BDD subBDD = True;

            for (int col = 0; col < N-1; col++) {
                BDD subsubBDD = True;

                for (int k = col+1; k < N; k++) {
                    //(xij -> !x(i+1)j) and (xij -> !x(i+2)j) and...
                    subsubBDD.and(factory.ithVar(getVar(col,row)).imp(factory.nithVar(getVar(k,row))));
                }

                //make rule for each cell in the row
                subBDD = subBDD.and(subsubBDD);
            }

            //add all rows
            queensBDD = queensBDD.and(subBDD);
        }
    }

    private void atMostOnePerColumn() {
        for (int col = 0; col < N; col++) {
            BDD subBDD = True;

            for (int row = 0; row < N-1; row++) {
                BDD subsubBDD = True;

                for (int k = row+1; k < N; k++) {
                    //(xij -> !xi(j+1)) and (xij -> !xi(j+2) and...
                    subsubBDD.and(factory.ithVar(getVar(col,row)).imp(factory.nithVar(getVar(col,k))));
                }

                //make rule for each cell in the column
                subBDD = subBDD.and(subsubBDD);
            }

            //add all columns
            queensBDD = queensBDD.and(subBDD);
        }
    }

    private void atMostOnePerNWDiagonal() {
        for (int row = 1; row < N; row++) {
            BDD subBDD = True;

            for (int col = 0; col < N-1; col++) {
                BDD subsubBDD = True;

                for (int k = 1; k < Math.min(row+1, N-col); k++) {
                    subsubBDD.and(factory.ithVar(getVar(col,row)).imp(factory.nithVar(getVar(col+k,row-k))));
                }

                //make rule for each cell in the column
                subBDD = subBDD.and(subsubBDD);
            }

            //add all columns
            queensBDD = queensBDD.and(subBDD);
        }
    }

    private void atMostOnePerNEDiagonal() {
        for (int row = 0; row < N-1; row++) {
            BDD subBDD = True;

            for (int col = 0; col < N-1; col++) {
                BDD subsubBDD = True;

                for (int k = 1; k < Math.min(N-row, N-col); k++) {
                    subsubBDD.and(factory.ithVar(getVar(col,row)).imp(factory.nithVar(getVar(col+k,row+k))));
                }

                //make rule for each cell in the column
                subBDD = subBDD.and(subsubBDD);
            }

            //add all columns
            queensBDD = queensBDD.and(subBDD);
        }
    }

    private int getVar(int column, int row) {
        return row*N + column;
    }
}
