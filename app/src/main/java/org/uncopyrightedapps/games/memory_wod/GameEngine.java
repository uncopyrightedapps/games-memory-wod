package org.uncopyrightedapps.games.memory_wod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class GameEngine {

    private int rowCount;
    private int colCount;
    private Piece pieces[];

    private HashMap piecesForNumber = new HashMap();

    public GameEngine(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;

        if (piecesCount() % 2 != 0) {
            throw new IllegalArgumentException("Piece count cannot be odd");
        }

        pieces = new Piece[piecesCount()];
        int pieceNumber = 0;
        for (int i = 0; i < piecesCount(); i += 2, pieceNumber++) {
            Piece piece = new Piece(pieceNumber);
            pieces[i] = piece;
            pieces[i + 1] = piece.getSibling();

            piecesForNumber.put(pieceNumber, new Piece[]{piece, piece.getSibling()});
        }
    }

    public int rowCount() {
        return rowCount;
    }

    public int colCount() {
        return colCount;
    }

    public int piecesCount() {
        return colCount() * rowCount();
    }

    public boolean isFlipped(int rowIndex, int colIndex) {
        return pieces[getIndex(rowIndex, colIndex)].isFlipped();
    }

    public void flip(int rowIndex, int colIndex) {
        pieces[getIndex(rowIndex, colIndex)].flip();
    }

    public Piece[] getPiecesForNumber(int pieceNumber) {
        return (Piece[]) piecesForNumber.get(pieceNumber);
    }

    public void shuffle() {
        shuffleArray(pieces);
    }

    public Piece[] getPieces() {
        return pieces;
    }

    @Override
    public String toString() {
        return "GameEngine{" +
                "pieces=" + Arrays.toString(pieces) +
                '}';
    }

    private int getIndex(int rowIndex, int colIndex) {
        return (rowIndex * colCount) + colIndex;
    }

    private void shuffleArray(Piece[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Piece a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

}
