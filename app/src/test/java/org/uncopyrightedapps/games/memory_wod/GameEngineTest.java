package org.uncopyrightedapps.games.memory_wod;

import org.junit.Test;
import org.uncopyrightedapps.games.memory_wod.engine.GameEngine;
import org.uncopyrightedapps.games.memory_wod.engine.Piece;

import static org.junit.Assert.*;

public class GameEngineTest {
    @Test
    public void canBeInitializedWithNumberOfRowsAndColumns() throws Exception {
        GameEngine engine = new GameEngine(4, 8, null);

        assertTrue("Incorrect row count", engine.rowCount() == 4);
        assertTrue("Incorrect columns count", engine.colCount() == 8);
        assertTrue("Incorrect pieces count", engine.piecesCount() == 32);
    }

    @Test
    public void numberOfPriecesCannotBeOdd() throws Exception {
        try {
            new GameEngine(3, 3, null);
            fail("Constructor should have throw IllegalArgumentException on odd piece count");
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void piecesAreNotFlippedAfterInitialization() throws Exception {
        GameEngine engine = new GameEngine(4, 8, null);
        for (int r = 0; r < engine.rowCount(); r++) {
            for (int c = 0; c < engine.colCount(); c++) {
                assertFalse("Piece shouldn't be flipped", engine.isFlipped(r, c));
            }
        }
    }

    @Test
    public void pieceIsFlippedAfterFlipActions() throws Exception {
        GameEngine engine = new GameEngine(4, 8, null);

        engine.flip(1, 1);
        assertTrue("Piece should be flipped", engine.isFlipped(1, 1));

        engine.flip(1, 1);
        assertTrue("Piece shouldn't be flipped", !engine.isFlipped(1, 1));
    }

    @Test
    public void thereAreTwoPiecesForEachPieceNumber() throws Exception {
        GameEngine engine = new GameEngine(4, 8, null);
        for (int i = 0; i < engine.piecesCount() / 2; i++) {
            Piece piece[] = engine.getPiecesForNumber(i);
            assertEquals("There number of piece count for a piece number is invalid", 2, piece.length);

            assertEquals("Invalid piece number", i, piece[0].getPieceNumber());
            assertEquals("Invalid piece number", i, piece[1].getPieceNumber());

            assertEquals("Invalid sibling", piece[1], piece[0].getSibling());
            assertEquals("Invalid sibling", piece[0], piece[1].getSibling());
        }
    }

    @Test
    public void shuffleChangesTheOrderOfPieces() throws Exception {
        GameEngine engine = new GameEngine(4, 8, null);
        engine.shuffle();

        Piece[] pieces = engine.getPieces();
        for (int i = 0; i < engine.piecesCount(); i += 2) {
            assertNotEquals(pieces[i].getPieceNumber(), pieces[i + 1].getPieceNumber());
        }
    }

    @Test
    public void testToString() throws Exception {
        GameEngine engine = new GameEngine(4, 8, null);
        System.out.println(engine.toString());

        engine.shuffle();
        System.out.println(engine.toString());
    }
}