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
		if (this.moveBlackPawnValidation() != null) {
			return this.moveBlackPawnValidation();
		} else if (this.moveWhitePawnValidation() != null) {
			return this.moveWhitePawnValidation();
		} else {
			throw new InvalidMoveException();
		}
	}

	private Move moveBlackPawnValidation() {
		MoveCreator moveCreator = new MoveCreator(from, to, board);
		Piece piece = board.getPieceAt(from);

		if (piece.equals(Piece.BLACK_PAWN)) {
			if (this.attackBlackPawnValidation()) {
				moveCreator.setAttack();
				return moveCreator.getMove();
			} else if (this.captureBlackPawnValidation()) {
				moveCreator.setCapture();
				return moveCreator.getMove();
			}
		}
		return null;
	}

	private Move moveWhitePawnValidation() {
		MoveCreator moveCreator = new MoveCreator(from, to, board);
		Piece piece = board.getPieceAt(from);

		if (piece.equals(Piece.WHITE_PAWN)) {
			if (this.attackWhitePawnValidation()) {
				moveCreator.setAttack();
				return moveCreator.getMove();
			} else if (this.captureWhitePawnValidation()) {
				moveCreator.setCapture();
				return moveCreator.getMove();
			}

		}
		return null;
	}

	private boolean attackWhitePawnValidation() {
		if (this.firstAttackWhitePawnValidation()) {
			return true;
		} else if (this.normalAttackWhitePawnValidation()) {
			return true;
		}
		return false;
	}

	private boolean attackBlackPawnValidation() {
		if (this.firstAttackBlackPawnValidation()) {
			return true;
		} else if (this.normalAttackBlackPawnValidation()) {
			return true;
		}
		return false;
	}

	private boolean firstAttackWhitePawnValidation() {
		if ((from.getY() == WHITE_PAWN_INITIAL_Y
				&& (to.getY() == WHITE_PAWN_INITIAL_Y + 1 || to.getY() == WHITE_PAWN_INITIAL_Y + 2))
				&& (from.getX() == to.getX())) {
			if (destinationFieldIsOccupied(to, board)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean normalAttackWhitePawnValidation() {
		if (from.getY() == to.getY() - 1 && from.getX() == to.getX()) {
			if (destinationFieldIsOccupied(to, board)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean firstAttackBlackPawnValidation() {
		if ((from.getY() == BLACK_PAWN_INITIAL_Y
				&& (to.getY() == BLACK_PAWN_INITIAL_Y - 1 || to.getY() == BLACK_PAWN_INITIAL_Y - 2))
				&& (from.getX() == to.getX())) {
			if (destinationFieldIsOccupied(to, board)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean normalAttackBlackPawnValidation() {
		if (from.getY() == to.getY() + 1 && from.getX() == to.getX()) {
			if (destinationFieldIsOccupied(to, board)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean captureBlackPawnValidation() {
		if (from.getY() == to.getY() + 1 && (Math.abs(from.getX() - to.getX()) == 1)) {
			if (destinationFieldIsOccupied(to, board)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	private boolean captureWhitePawnValidation() {
		if (from.getY() == to.getY() - 1 && (Math.abs(from.getX() - to.getX()) == 1)) {
			if (destinationFieldIsOccupied(to, board)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
