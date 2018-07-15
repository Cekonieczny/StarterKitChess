package com.capgemini.chess.algorithms.implementation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class ValidatorTest {
	@Test
	public void testThrowExceptionOnBackwardPawnMove() {
		// given
		Coordinate from = new Coordinate(1, 2);
		Coordinate to = new Coordinate(1, 1);
		Move move = null;
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_PAWN, from);

		// when
		PawnMoveValidator pawnMoveValidator = new PawnMoveValidator(from, to, board);
		boolean exceptionThrown = false;
		try {
			move = pawnMoveValidator.validation();
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}

		// then
		assertTrue(exceptionThrown);
	}

	@Test
	public void testReturnMoveOnForwardPawnMove() {
		// given
		Coordinate from = new Coordinate(1, 1);
		Coordinate to = new Coordinate(1, 2);
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_PAWN, from);

		// when
		PawnMoveValidator pawnMoveValidator = new PawnMoveValidator(from, to, board);
		Move move = null;
		try {
			move = pawnMoveValidator.validation();
		} catch (InvalidMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		// then
		assertEquals(MoveType.ATTACK,move.getType());
		assertEquals(Piece.WHITE_PAWN,move.getMovedPiece());
	}
}
