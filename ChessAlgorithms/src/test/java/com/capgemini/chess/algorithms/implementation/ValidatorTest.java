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
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_PAWN, from);

		// when
		PawnMoveValidator pawnMoveValidator = new PawnMoveValidator(from, to, board);
		boolean exceptionThrown = false;
		try {
			pawnMoveValidator.validate();
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
			move = pawnMoveValidator.validate();
		} catch (InvalidMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(Piece.WHITE_PAWN, move.getMovedPiece());
	}

	@Test
	public void testPerformMoveInvalidBishopAttackLeapOverForward() {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(3, 3);
		Coordinate to = new Coordinate(5, 5);
		board.setPieceAt(Piece.WHITE_BISHOP, from);
		board.setPieceAt(Piece.BLACK_PAWN, new Coordinate(4, 4));

		// when
		BishopMoveValidator bishopMoveValidator = new BishopMoveValidator(from, to, board);
		boolean exceptionThrown = false;
		try {
			bishopMoveValidator.validate();
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}

		// then
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testPerformMoveInvalidBishopAttackLeapOverBackward() {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(5, 5);
		Coordinate to = new Coordinate(3, 3);
		board.setPieceAt(Piece.WHITE_BISHOP, from);
		board.setPieceAt(Piece.WHITE_PAWN, new Coordinate(4, 4));

		// when
		BishopMoveValidator bishopMoveValidator = new BishopMoveValidator(from, to, board);
		boolean exceptionThrown = false;
		try {
			bishopMoveValidator.validate();
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}

		// then
		assertTrue(exceptionThrown);
	}

	@Test
	public void testPerformMoveBishopCaptureForward() {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(3, 3);
		Coordinate to = new Coordinate(5, 5);
		board.setPieceAt(Piece.WHITE_BISHOP, from);
		board.setPieceAt(Piece.BLACK_PAWN, to);

		// when
		BishopMoveValidator bishopMoveValidator = new BishopMoveValidator(from, to, board);
		Move move = null;
		try {
			move = bishopMoveValidator.validate();
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}

		// then
		assertEquals(MoveType.CAPTURE, move.getType());
		assertEquals(Piece.WHITE_BISHOP, move.getMovedPiece());
	}
	
	@Test
	public void testPerformMoveBishopCaptureBackward() {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(5, 5);
		Coordinate to = new Coordinate(3, 3);
		board.setPieceAt(Piece.WHITE_BISHOP, from);
		board.setPieceAt(Piece.BLACK_PAWN, to);

		// when
		BishopMoveValidator bishopMoveValidator = new BishopMoveValidator(from, to, board);
		Move move = null;
		try {
			move = bishopMoveValidator.validate();
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}

		// then
		assertEquals(MoveType.CAPTURE, move.getType());
		assertEquals(Piece.WHITE_BISHOP, move.getMovedPiece());
	}
	
	
	
	@Test
	public void testPerformMoveKnightAttack() {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(3, 3);
		Coordinate to = new Coordinate(5, 2);
		board.setPieceAt(Piece.WHITE_KNIGHT, from);

		// when
		KnightMoveValidator knightMoveValidator = new KnightMoveValidator(from, to, board);
		Move move = null;
		try {
			move = knightMoveValidator.validate();
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}

		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(Piece.WHITE_KNIGHT, move.getMovedPiece());
	}
	
	@Test
	public void testPerformMoveBishopCapture() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_BISHOP, new Coordinate(5, 0));
		board.setPieceAt(Piece.BLACK_PAWN, new Coordinate(7, 2));
		
		// when
		BoardManager boardManager = new BoardManager(board);
		Move move = boardManager.performMove(new Coordinate(5, 0), new Coordinate(7, 2));
		
		// then
		assertEquals(MoveType.CAPTURE, move.getType());
		assertEquals(Piece.WHITE_BISHOP, move.getMovedPiece());
	}
	
	@Test
	public void testMoveCreatorWithNoSetType() {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(0,0);
		Coordinate to = new Coordinate(1,1);
		MoveCreator moveCreator = new MoveCreator(from,to,board);
		
		
		// when
		Move move = moveCreator.getMove();
		
		// then
		assertNotNull(move);
	}
	
}
