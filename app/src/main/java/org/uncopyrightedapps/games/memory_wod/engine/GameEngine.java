package org.uncopyrightedapps.games.memory_wod.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameEngine implements Serializable {

    private int flippedCount;
    private GameType gameType;
    private int rowCount;
    private int colCount;
    private Piece pieces[];
    private int numberOfTries;
    private List<Integer> flippedPieces;

    private HashMap piecesForNumber = new HashMap();

    public GameEngine(int rowCount, int colCount, GameType gameType) {
        this.gameType = gameType;
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

        flippedPieces = new ArrayList<Integer>();
        flippedCount = 0;

        numberOfTries = 0;
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
        return isFlipped(getIndex(rowIndex, colIndex));
    }

    public boolean isFlipped(int position) {
        return pieces[position].isFlipped();
    }

    public void flip(int rowIndex, int colIndex) {
        flip(getIndex(rowIndex, colIndex));
    }

    public void flip(int position) {
        Piece piece = pieces[position];

        if (!piece.isFlipped()) {
            piece.flip();
            flippedPieces.add(position);
        }

        if (numberOfPiecesFlippedIs(2)) {
            numberOfTries++;
        }
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

    public boolean numberOfPiecesFlippedIs(int count) {
        return flippedPieces.size() == count;
    }

    public boolean matchNotFound() {
        if (numberOfPiecesFlippedIs(2)) {
            Piece piece1 = pieces[flippedPieces.get(0)];
            Piece piece2 = pieces[flippedPieces.get(1)];
            boolean notFound = piece1.getPieceNumber() != piece2.getPieceNumber();
            if (!notFound) {
                flippedCount++;
            }
            return notFound;
        }
        return false;
    }

    public List<Integer> resetFlippedPieces() {
        ArrayList<Integer> positions = new ArrayList<>(flippedPieces);
        for (int position : positions) {
            pieces[position].flip();
        }
        clearFlippedPieces();
        return positions;
    }

    public void clearFlippedPieces() {
        flippedPieces.clear();
    }

    public void reset() {
        for (Piece piece : pieces) {
            piece.reset();
        }
        clearFlippedPieces();
        shuffle();
        flippedCount = 0;
        numberOfTries = 0;
    }

    public boolean gameOver() {
        return flippedCount == piecesCount() / 2;
    }

    public int getNumberOfTries() {
        return numberOfTries;
    }

    public GameType getGameType() {
        return gameType;
    }
}

