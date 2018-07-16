package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidPawnMoveException;

public class PawnMoveValidator {
	private Coordinate from;
	private Coordinate to;
	private Board board;
	private static final int WHITE_PAWN_INITIAL_Y = 1;
	private static final int BLACK_PAWN_INITIAL_Y = 6;
	private Move move = new Move();

	public PawnMoveValidator(Coordinate from, Coordinate to, Board board) {
		this.from = from;
		this.to = to;
		this.board = board;
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
		MoveCreator moveCreator = new MoveCreator();
		Piece piece = board.getPieceAt(from);

		if (piece.equals(Piece.BLACK_PAWN)) {
			if (this.attackBlackPawnValidation()) {
				moveCreator.setAttack(from, to, board);
				return moveCreator.getMove();
			} else if (this.captureBlackPawnValidation()) {
				moveCreator.setCapture(from, to, board);
				return moveCreator.getMove();
			}
		}
		return null;
	}

	private Move moveWhitePawnValidation() {
		MoveCreator moveCreator = new MoveCreator();
		Piece piece = board.getPieceAt(from);

		if (piece.equals(Piece.WHITE_PAWN)) {
			if (this.attackWhitePawnValidation()) {
				moveCreator.setAttack(from, to, board);
				return moveCreator.getMove();
			} else if (this.captureWhitePawnValidation()) {
				moveCreator.setCapture(from, to, board);
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
		if ((from.getY() == BLACK_PAWN_INITIAL_Y
				&& (to.getY() == BLACK_PAWN_INITIAL_Y - 1 || to.getY() == BLACK_PAWN_INITIAL_Y - 2))
				&& (from.getX() == to.getX())) {
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
