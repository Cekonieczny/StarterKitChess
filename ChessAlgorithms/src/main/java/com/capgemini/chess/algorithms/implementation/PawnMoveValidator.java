package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class PawnMoveValidator {
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

	public boolean fieldIsOccupiedByAlliedPiece() {
		if (this.fieldIsOccupied()) {
			if (board.getPieceAt(from).getColor().equals(board.getPieceAt(from).getColor()))
				return true;
		}
		return false;
	}

	public Move validation() throws InvalidMoveException {
		if (this.moveBlackPawnValidation() != null) {
			return this.moveBlackPawnValidation();
		} else if (this.moveWhitePawnValidation() != null) {
			return this.moveWhitePawnValidation();
		} else {
			throw new InvalidMoveException();
		}
	}

	private Move moveBlackPawnValidation() {
		MoveFactory moveFactory = new MoveFactory();
		Piece piece = board.getPieceAt(from);

		if (piece.equals(Piece.BLACK_PAWN)) {
			if (this.attackBlackPawnValidation()) {
				moveFactory.setAttack(from, to, board);
				return moveFactory.getMove();
			} else if (this.captureBlackPawnValidation()) {
				moveFactory.setCapture(from, to, board);
				return moveFactory.getMove();
			}
		}
		return null;
	}

	private Move moveWhitePawnValidation() {
		MoveFactory moveFactory = new MoveFactory();
		Piece piece = board.getPieceAt(from);

		if (piece.equals(Piece.WHITE_PAWN)) {
			if (this.attackWhitePawnValidation()) {
				moveFactory.setAttack(from, to, board);
				return moveFactory.getMove();
			} else if (this.captureWhitePawnValidation()) {
				moveFactory.setCapture(from, to, board);
				return moveFactory.getMove();
			}

		}
		return null;
	}

	private Validator nextValidator(Validator validator) {
		return validator;

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
		if ((from.getY() == 1 && (to.getY() == 2 || to.getY() == 3)) && (from.getX() == to.getX())) {
			if (this.fieldIsOccupied()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean normalAttackWhitePawnValidation() {
		if (from.getY() == to.getY() - 1 && from.getX() == to.getX()) {
			if (this.fieldIsOccupied()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean firstAttackBlackPawnValidation() {
		if ((from.getY() == 6 && (to.getY() == 5 || to.getY() == 4)) && (from.getX() == to.getX())) {
			if (this.fieldIsOccupied()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean normalAttackBlackPawnValidation() {
		if (from.getY() == to.getY() + 1 && from.getX() == to.getX()) {
			if (this.fieldIsOccupied()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean captureBlackPawnValidation() {
		if (from.getY() == to.getY() + 1 && (from.getX() == to.getX() + 1 || from.getX() == to.getX() - 1)) {
			if (this.fieldIsOccupiedByEnemyPiece()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	private boolean captureWhitePawnValidation() {
		if (from.getY() == to.getY() - 1 && (from.getX() == to.getX() + 1 || from.getX() == to.getX() - 1)) {
			if (this.fieldIsOccupiedByEnemyPiece()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	private boolean fieldIsOccupied() {
		if (board.getPieceAt(to) != null) {
			return true;
		}
		return false;
	}

	private boolean fieldIsOccupiedByEnemyPiece() {
		if (this.fieldIsOccupied()) {
			if (board.getPieceAt(from).getColor().equals(board.getPieceAt(from).getColor()))
				return true;
		}
		return false;
	}

}
