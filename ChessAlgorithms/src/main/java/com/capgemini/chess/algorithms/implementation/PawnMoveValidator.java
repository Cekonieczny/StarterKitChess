package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class PawnMoveValidator extends MoveValidator {
	private Coordinate from;
	private Coordinate to;
	private Board board;
	private static final int WHITE_PAWN_INITIAL_Y = 1;
	private static final int BLACK_PAWN_INITIAL_Y = 6;

	public PawnMoveValidator(Coordinate from, Coordinate to, Board board) {
		this.from = from;
		this.to = to;
		this.board = board;
	}

	@Override
	public Move validate() throws InvalidMoveException {
		MoveCreator moveCreator = new MoveCreator(from, to, board);

		if ((board.getPieceAt(from) == Piece.WHITE_PAWN && movesForward())
				|| (board.getPieceAt(from) == Piece.BLACK_PAWN && !movesForward())) {
			if (checkEnPassant()) {
				moveCreator.setEnPassant();
				return moveCreator.getMove();
			} else if (normalAttackValidation() || firstAttackValidation()) {
				moveCreator.setAttack();
				return moveCreator.getMove();
			} else if (capturePawnValidation()) {
				moveCreator.setCapture();
				return moveCreator.getMove();
			}
		}
		throw new InvalidMoveException();
	}

	private boolean firstAttackValidation() {
		int verticalMoveDistance = to.getY() - from.getY();

		if (isPawnAtInitialPosition(from) && from.getX() == to.getX() && Math.abs(verticalMoveDistance) == 2) {
			if (verticalMoveDistance < 0) {
				if (thisFieldIsOccupied(to, board)
						|| thisFieldIsOccupied(new Coordinate(to.getX(), to.getY() - 1), board)) {
					return false;
				}
			} else if (verticalMoveDistance > 0) {
				if (thisFieldIsOccupied(to, board)
						|| thisFieldIsOccupied(new Coordinate(to.getX(), to.getY() + 1), board)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	private boolean normalAttackValidation() {
		if (Math.abs(from.getY() - to.getY()) == 1 && from.getX() == to.getX()) {
			if (thisFieldIsOccupied(to, board)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean capturePawnValidation() {
		if (Math.abs(from.getY() - to.getY()) == 1 && (Math.abs(from.getX() - to.getX()) == 1)) {
			if (thisFieldIsOccupied(to, board)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	private boolean movesForward() {
		if (to.getY() > from.getY()) {
			return true;
		}
		return false;
	}

	private boolean isPawnAtInitialPosition(Coordinate coordinate) {
		if (board.getPieceAt(coordinate) == Piece.BLACK_PAWN && coordinate.getY() == BLACK_PAWN_INITIAL_Y) {
			return true;
		} else if (board.getPieceAt(coordinate) == Piece.WHITE_PAWN && coordinate.getY() == WHITE_PAWN_INITIAL_Y) {
			return true;
		}
		return false;
	}

	private boolean checkEnPassant() {
		Move lastMove = null;
		Piece movedPiece = null;

		if (board.getMoveHistory().size() != 0) {
			lastMove = board.getMoveHistory().get(board.getMoveHistory().size() - 1);
		}

		if (lastMove != null) {
			movedPiece = lastMove.getMovedPiece();
			if (movedPiece == Piece.BLACK_PAWN && lastMove.getFrom().getY() == BLACK_PAWN_INITIAL_Y
					|| movedPiece == Piece.WHITE_PAWN && lastMove.getFrom().getY() == WHITE_PAWN_INITIAL_Y) {
				if (movedPiece.getColor() != board.getPieceAt(from).getColor()
						&& lastMove.getFrom().getX() == to.getX()) {
					if (enPassantCapturePawnValidation()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean enPassantCapturePawnValidation() {
		if (Math.abs(from.getY() - to.getY()) == 1 && (Math.abs(from.getX() - to.getX()) == 1)) {
			if (thisFieldIsOccupied(to, board)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

}
