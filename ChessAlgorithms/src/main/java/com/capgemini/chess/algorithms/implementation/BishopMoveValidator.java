package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class BishopMoveValidator extends MoveValidator {
	private Coordinate from;
	private Coordinate to;
	private Board board;

	public BishopMoveValidator(Coordinate from, Coordinate to, Board board) {
		this.from = from;
		this.to = to;
		this.board = board;
	}

	@Override
	public Move validate() throws InvalidMoveException {
		MoveCreator moveCreator = new MoveCreator(from, to, board);
		if (Math.abs(to.getX() - from.getX()) == Math.abs(to.getY() - from.getY())) {
			if (noCollisionOnForwardMove() && noCollisionOnBackwardMove()) {
				if (destinationFieldIsOccupied(to, board)) {
					moveCreator.setCapture();
					return moveCreator.getMove();
				} else {
					moveCreator.setAttack();
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
}
