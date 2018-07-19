package com.capgemini.chess.algorithms.implementation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.CoordinateOutOfBoundsException;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class ValidatorTest {
	@Test
	public void testPerformExceptionOnBackwardPawnMove() {
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
	public void testPerformMoveOnForwardPawnMove() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(1, 1);
		Coordinate to = new Coordinate(1, 2);
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_PAWN, from);

		// when
		PawnMoveValidator pawnMoveValidator = new PawnMoveValidator(from, to, board);
		Move move = null;

		move = pawnMoveValidator.validate();

		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(Piece.WHITE_PAWN, move.getMovedPiece());
	}

	@Test
	public void testPerformFirstMoveOnForwardWhitePawnMove() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(1, 1);
		Coordinate to = new Coordinate(1, 3);
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_PAWN, from);

		// when
		PawnMoveValidator pawnMoveValidator = new PawnMoveValidator(from, to, board);
		Move move = null;

		move = pawnMoveValidator.validate();

		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(Piece.WHITE_PAWN, move.getMovedPiece());
	}

	@Test
	public void testPerformFirstMoveOnForwardBlackPawnMove() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(1, 6);
		Coordinate to = new Coordinate(1, 4);
		Board board = new Board();
		board.setPieceAt(Piece.BLACK_PAWN, from);

		// when
		PawnMoveValidator pawnMoveValidator = new PawnMoveValidator(from, to, board);
		Move move = null;

		move = pawnMoveValidator.validate();

		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(Piece.BLACK_PAWN, move.getMovedPiece());

	}

	@Test
	public void testPerformCaptureOnBlackPawnMove() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(1, 6);
		Coordinate to = new Coordinate(2, 5);
		Board board = new Board();
		board.setPieceAt(Piece.BLACK_PAWN, from);
		board.setPieceAt(Piece.WHITE_KNIGHT, to);

		// when
		PawnMoveValidator pawnMoveValidator = new PawnMoveValidator(from, to, board);
		Move move = null;

		move = pawnMoveValidator.validate();

		assertEquals(MoveType.CAPTURE, move.getType());
		assertEquals(Piece.BLACK_PAWN, move.getMovedPiece());

	}

	@Test
	public void testPerformInvalidFirstMoveOnForwardBlackPawnMoveLeapOver() {
		// given
		Coordinate from = new Coordinate(1, 6);
		Coordinate to = new Coordinate(1, 4);
		Board board = new Board();
		board.setPieceAt(Piece.BLACK_PAWN, from);
		board.setPieceAt(Piece.WHITE_KNIGHT, new Coordinate(1, 5));

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
	public void testPerformInvalidFirstMoveOnForwardWhitePawnMoveLeapOver() {
		// given
		Coordinate from = new Coordinate(1, 1);
		Coordinate to = new Coordinate(1, 3);
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_PAWN, from);
		board.setPieceAt(Piece.WHITE_KNIGHT, new Coordinate(1, 2));

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
	public void testPerformMoveOnForwardBlackPawnMove() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(1, 6);
		Coordinate to = new Coordinate(1, 5);
		Board board = new Board();
		board.setPieceAt(Piece.BLACK_PAWN, from);

		// when
		PawnMoveValidator pawnMoveValidator = new PawnMoveValidator(from, to, board);
		Move move = null;

		move = pawnMoveValidator.validate();

		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(Piece.BLACK_PAWN, move.getMovedPiece());
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
	public void testPerformMoveBishopCaptureForward() throws InvalidMoveException {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(3, 3);
		Coordinate to = new Coordinate(5, 5);
		board.setPieceAt(Piece.WHITE_BISHOP, from);
		board.setPieceAt(Piece.BLACK_PAWN, to);

		// when
		BishopMoveValidator bishopMoveValidator = new BishopMoveValidator(from, to, board);
		Move move = null;

		move = bishopMoveValidator.validate();

		// then
		assertEquals(MoveType.CAPTURE, move.getType());
		assertEquals(Piece.WHITE_BISHOP, move.getMovedPiece());
	}

	@Test
	public void testPerformMoveBishopCaptureBackward() throws InvalidMoveException {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(5, 5);
		Coordinate to = new Coordinate(3, 3);
		board.setPieceAt(Piece.WHITE_BISHOP, from);
		board.setPieceAt(Piece.BLACK_PAWN, to);

		// when
		BishopMoveValidator bishopMoveValidator = new BishopMoveValidator(from, to, board);
		Move move = null;

		move = bishopMoveValidator.validate();

		// then
		assertEquals(MoveType.CAPTURE, move.getType());
		assertEquals(Piece.WHITE_BISHOP, move.getMovedPiece());
	}

	@Test
	public void testPerformMoveKnightAttackHorizontal() throws InvalidMoveException {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(3, 3);
		Coordinate to = new Coordinate(5, 2);
		board.setPieceAt(Piece.WHITE_KNIGHT, from);

		// when
		KnightMoveValidator knightMoveValidator = new KnightMoveValidator(from, to, board);
		Move move = null;

		move = knightMoveValidator.validate();

		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(Piece.WHITE_KNIGHT, move.getMovedPiece());
	}

	@Test
	public void testPerformMoveKnightAttackVertical() throws InvalidMoveException {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(3, 3);
		Coordinate to = new Coordinate(4, 5);
		board.setPieceAt(Piece.WHITE_KNIGHT, from);

		// when
		KnightMoveValidator knightMoveValidator = new KnightMoveValidator(from, to, board);
		Move move = null;

		move = knightMoveValidator.validate();

		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(Piece.WHITE_KNIGHT, move.getMovedPiece());
	}

	@Test
	public void testPerformMoveKnightCaptureHorizontal() throws InvalidMoveException {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(3, 3);
		Coordinate to = new Coordinate(5, 2);
		board.setPieceAt(Piece.BLACK_KNIGHT, from);
		board.setPieceAt(Piece.WHITE_PAWN, to);

		// when
		KnightMoveValidator knightMoveValidator = new KnightMoveValidator(from, to, board);
		Move move = null;

		move = knightMoveValidator.validate();

		// then
		assertEquals(MoveType.CAPTURE, move.getType());
		assertEquals(Piece.BLACK_KNIGHT, move.getMovedPiece());
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
		Coordinate from = new Coordinate(0, 0);
		Coordinate to = new Coordinate(1, 1);
		MoveCreator moveCreator = new MoveCreator(from, to, board);

		// when
		Move move = moveCreator.getMove();

		// then
		assertNotNull(move);
	}

	@Test
	public void testMoveCreatorWithSetAttack() {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(0, 0);
		Coordinate to = new Coordinate(1, 1);
		MoveCreator moveCreator = new MoveCreator(from, to, board);
		moveCreator.setAttack();

		// when
		Move move = moveCreator.getMove();

		// then
		assertEquals(from, move.getFrom());
		assertEquals(to, move.getTo());
		assertEquals(MoveType.ATTACK, move.getType());
	}

	@Test
	public void testMoveCreatorWithSetCapture() {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(0, 0);
		Coordinate to = new Coordinate(1, 1);
		MoveCreator moveCreator = new MoveCreator(from, to, board);
		moveCreator.setCapture();

		// when
		Move move = moveCreator.getMove();

		// then
		assertEquals(from, move.getFrom());
		assertEquals(to, move.getTo());
		assertEquals(MoveType.CAPTURE, move.getType());
	}

	@Test
	public void testMoveCreatorWithSetEnPassant() {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(0, 0);
		Coordinate to = new Coordinate(1, 1);
		MoveCreator moveCreator = new MoveCreator(from, to, board);
		moveCreator.setEnPassant();

		// when
		Move move = moveCreator.getMove();

		// then
		assertEquals(from, move.getFrom());
		assertEquals(to, move.getTo());
		assertEquals(MoveType.EN_PASSANT, move.getType());
	}

	@Test
	public void testMoveCreatorWithSetCastling() {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(0, 0);
		Coordinate to = new Coordinate(1, 1);
		MoveCreator moveCreator = new MoveCreator(from, to, board);
		moveCreator.setEnPassant();

		// when
		Move move = moveCreator.getMove();

		// then
		assertEquals(from, move.getFrom());
		assertEquals(to, move.getTo());
		assertEquals(MoveType.EN_PASSANT, move.getType());
	}

	@Test
	public void testPerformMoveInvalidIndexOutOfBound() throws InvalidMoveException {
		// given
		BoardManager boardManager = new BoardManager();

		// when
		boolean exceptionThrown = false;
		try {
			boardManager.performMove(new Coordinate(8, 6), new Coordinate(7, 6));
		} catch (CoordinateOutOfBoundsException e) {
			exceptionThrown = true;
		}

		// then
		assertTrue(exceptionThrown);
	}

	@Test
	public void testPerformMoveKingCaptureToLeft() throws InvalidMoveException {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(3, 3);
		Coordinate to = new Coordinate(4, 3);
		board.setPieceAt(Piece.BLACK_KING, from);
		board.setPieceAt(Piece.WHITE_PAWN, to);

		// when
		KingMoveValidator kingMoveValidator = new KingMoveValidator(from, to, board);
		Move move = null;

		move = kingMoveValidator.validate();

		// then
		assertEquals(MoveType.CAPTURE, move.getType());
		assertEquals(Piece.BLACK_KING, move.getMovedPiece());
	}

	@Test
	public void testPerformMoveCastlingLeftWhite() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_KING, new Coordinate(4, 0));
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(0, 0));

		// when
		BoardManager boardManager = new BoardManager(board);
		Move move = boardManager.performMove(new Coordinate(4, 0), new Coordinate(2, 0));

		// then
		assertEquals(MoveType.CASTLING, move.getType());
		assertEquals(Piece.WHITE_KING, move.getMovedPiece());
	}

	@Test
	public void testPerformMoveCastlingLeftBlack() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(Piece.BLACK_KING, new Coordinate(4, 7));
		board.setPieceAt(Piece.BLACK_ROOK, new Coordinate(0, 7));

		// when
		BoardManager boardManager = new BoardManager(board);
		Move move = boardManager.performMove(new Coordinate(4, 7), new Coordinate(2, 7));

		// then
		assertEquals(MoveType.CASTLING, move.getType());
		assertEquals(Piece.BLACK_KING, move.getMovedPiece());
	}

	@Test
	public void testPerformMoveRookAttackLeftHorizontal() throws InvalidMoveException {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(3, 3);
		Coordinate to = new Coordinate(0, 3);
		board.setPieceAt(Piece.WHITE_ROOK, from);

		// when
		RookMoveValidator rookMoveValidator = new RookMoveValidator(from, to, board);
		Move move = null;

		move = rookMoveValidator.validate();

		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(Piece.WHITE_ROOK, move.getMovedPiece());
	}

	@Test
	public void testPerformMoveQueenAttackLeftHorizontal() throws InvalidMoveException {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(3, 3);
		Coordinate to = new Coordinate(0, 3);
		board.setPieceAt(Piece.WHITE_QUEEN, from);

		// when
		QueenMoveValidator queenMoveValidator = new QueenMoveValidator(from, to, board);
		Move move = null;

		move = queenMoveValidator.validate();

		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(Piece.WHITE_QUEEN, move.getMovedPiece());
	}

	@Test
	public void testPerformMoveRookAttackForwardVertical() throws InvalidMoveException {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(0, 0);
		Coordinate to = new Coordinate(0, 3);
		board.setPieceAt(Piece.WHITE_ROOK, from);

		// when
		RookMoveValidator rookMoveValidator = new RookMoveValidator(from, to, board);
		Move move = null;

		move = rookMoveValidator.validate();

		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(Piece.WHITE_ROOK, move.getMovedPiece());
	}

	@Test
	public void testPerformMoveInvalidAttackBackwardVerticalLeapOver() throws InvalidMoveException {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(0, 3);
		Coordinate to = new Coordinate(0, 0);
		board.setPieceAt(Piece.WHITE_ROOK, from);
		board.setPieceAt(Piece.BLACK_KNIGHT, new Coordinate(0, 1));

		// when
		RookMoveValidator rookMoveValidator = new RookMoveValidator(from, to, board);
		boolean exceptionThrown = false;
		try {
			rookMoveValidator.validate();
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}

		// then
		assertTrue(exceptionThrown);
	}

	@Test
	public void testPerformMoveInvalidAttackBackwardHorizontalLeapOver() throws InvalidMoveException {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(3, 0);
		Coordinate to = new Coordinate(0, 0);
		board.setPieceAt(Piece.WHITE_ROOK, from);
		board.setPieceAt(Piece.BLACK_KNIGHT, new Coordinate(1, 0));

		// when
		RookMoveValidator rookMoveValidator = new RookMoveValidator(from, to, board);
		boolean exceptionThrown = false;
		try {
			rookMoveValidator.validate();
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}

		// then
		assertTrue(exceptionThrown);
	}

	@Test
	public void testPerformMoveInvalidAttackForwardHorizontalLeapOver() throws InvalidMoveException {
		// given
		Board board = new Board();
		Coordinate from = new Coordinate(0, 0);
		Coordinate to = new Coordinate(3, 0);
		board.setPieceAt(Piece.WHITE_ROOK, from);
		board.setPieceAt(Piece.BLACK_KNIGHT, new Coordinate(1, 0));

		// when
		RookMoveValidator rookMoveValidator = new RookMoveValidator(from, to, board);
		boolean exceptionThrown = false;
		try {
			rookMoveValidator.validate();
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}

		// then
		assertTrue(exceptionThrown);
	}

	@Test
	public void testPerformAttackWhileEnPassantIsPossible() throws InvalidMoveException {
		// given
		Board board = new Board();
		BoardManager boardManager = new BoardManager(board);

		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(Piece.WHITE_PAWN, new Coordinate(1, 4));
		board.setPieceAt(Piece.BLACK_PAWN, new Coordinate(2, 6));
		boardManager.performMove(new Coordinate(2, 6), new Coordinate(2, 4));

		// when
		PawnMoveValidator pawnMoveValidator = new PawnMoveValidator(new Coordinate(1, 4), new Coordinate(1, 5), board);
		Move move = pawnMoveValidator.validate();

		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(Piece.WHITE_PAWN, move.getMovedPiece());
	}

	private Move createDummyMove(Board board) {

		Move move = new Move();

		if (board.getMoveHistory().size() % 2 == 0) {
			board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(0, 0));
			move.setMovedPiece(Piece.WHITE_ROOK);
		} else {
			board.setPieceAt(Piece.BLACK_ROOK, new Coordinate(0, 0));
			move.setMovedPiece(Piece.BLACK_ROOK);
		}
		move.setFrom(new Coordinate(0, 0));
		move.setTo(new Coordinate(0, 0));
		move.setType(MoveType.ATTACK);
		board.setPieceAt(null, new Coordinate(0, 0));
		return move;
	}

}
