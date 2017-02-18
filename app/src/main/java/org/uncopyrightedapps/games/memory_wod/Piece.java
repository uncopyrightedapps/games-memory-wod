package org.uncopyrightedapps.games.memory_wod;

public class Piece {
    private boolean flipped = false;
    private Piece sibling;
    private int pieceNumber;

    public Piece(int pieceNumber) {
        this.pieceNumber = pieceNumber;
    }

    public void flip() {
        flipped = !flipped;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public Piece getSibling() {
        if (sibling == null) {
            sibling = new Piece(pieceNumber);
        }
        return sibling;
    }

    public int getPieceNumber() {
        return pieceNumber;
    }

    @Override
    public String toString() {
        return "Piece{" +
                 pieceNumber  +
                ", " + (flipped ? "flipped" : "!flipped") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece piece = (Piece) o;

        return pieceNumber == piece.pieceNumber;
    }

    @Override
    public int hashCode() {
        return pieceNumber;
    }
}
