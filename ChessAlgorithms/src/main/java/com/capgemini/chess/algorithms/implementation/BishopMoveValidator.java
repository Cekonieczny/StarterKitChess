package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class BishopMoveValidator {
	private Coordinate from;
	private Coordinate to;
	private Board board;

	public BishopMoveValidator(Coordinate from, Coordinate to, Board board) {
		this.from = from;
		this.to = to;
		this.board = board;
	}

	public Move validation() throws InvalidMoveException {
		MoveCreator moveCreator = new MoveCreator();
		if (Math.abs(to.getX() - from.getX()) == Math.abs(to.getY() - from.getY())) {
			if (this.noCollisionOnForwardMove() && this.noCollisionOnBackwardMove()) {
				if (this.fieldIsOccupiedByEnemyPiece()) {
					moveCreator.setCapture(from, to, board);
					return moveCreator.getMove();
				} else {
					moveCreator.setAttack(from, to, board);
					return moveCreator.getMove();
				}
			}
		}
		throw new InvalidMoveException();
	}

	private boolean noCollisionOnForwardMove() {

		for (int i = from.getX() + 1, j = from.getY() + 1; i < to.getX() && j < to.getY(); i++, j++) {
			Coordinate coordinate = new Coordinate(i, j);
			if (board.getPieceAt(coordinate) != null) {
				return false;
			}
		}
		return true;
	}

	private boolean noCollisionOnBackwardMove() {
		for (int i = from.getX() - 1, j = from.getY() - 1; i > to.getX() && j > to.getY(); i--, j--) {
			Coordinate coordinate = new Coordinate(i, j);
			if (board.getPieceAt(coordinate) != null) {
				return false;
			}
		}
		return true;
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
