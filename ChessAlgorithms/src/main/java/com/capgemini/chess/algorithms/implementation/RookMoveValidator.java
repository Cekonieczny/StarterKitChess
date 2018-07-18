package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class RookMoveValidator {
	private Coordinate from;
	private Coordinate to;
	private Board board;

	public RookMoveValidator(Coordinate from, Coordinate to, Board board) {
		this.from = from;
		this.to = to;
		this.board = board;
	}

	public Move validate() throws InvalidMoveException {
		MoveCreator moveCreator = new MoveCreator(from, to, board);
		if (verticalMoveValidation() || horizontalMoveValidation()) {
			{
				if (destinationFieldIsOccupied()) {
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

	private boolean verticalMoveValidation() {
		boolean movesVerticalForwards = to.getY() > from.getY();
		boolean movesVerticalBackwards = to.getY() < from.getY();

		if (Math.abs(to.getX() - from.getX()) == 0 && Math.abs(to.getY() - from.getY()) != 0) {
			if (movesVerticalForwards && this.noCollisionOnVerticalMoveForward()) {
				return true;
			} else if (movesVerticalBackwards && this.noCollisionOnVerticalMoveBackward()) {
				return true;
			}
		}
		return false;
	}

	private boolean horizontalMoveValidation() {
		boolean movesHorizontalForwards = to.getX() > from.getX();
		boolean movesHorizontalBackwards = to.getX() < from.getX();

		if (Math.abs(to.getY() - from.getY()) == 0 && Math.abs(to.getX() - from.getX()) != 0) {
			if (movesHorizontalForwards && this.noCollisionOnHorizontalMoveForward()) {
				return true;
			} else if (movesHorizontalBackwards && this.noCollisionOnHorizontalMoveBackward()) {
				return true;
			}
		}
		return false;
	}

	private boolean noCollisionOnVerticalMoveForward() {
		for (int j = from.getY() + 1; j < to.getY(); j++) {
			Coordinate coordinate = new Coordinate(from.getX(), j);
			if (thisFieldIsOccupied(coordinate)) {
				return false;
			}
		}
		return true;
	}

	private boolean noCollisionOnVerticalMoveBackward() {
		for (int j = from.getY() - 1; j > to.getY(); j--) {
			Coordinate coordinate = new Coordinate(from.getX(), j);
			if (thisFieldIsOccupied(coordinate)) {
				return false;
			}
		}
		return true;
	}

	private boolean noCollisionOnHorizontalMoveForward() {
		for (int i = from.getX() + 1; i < to.getX(); i++) {
			Coordinate coordinate = new Coordinate(i, from.getY());
			if (thisFieldIsOccupied(coordinate)) {
				return false;
			}
		}
		return true;
	}

	private boolean noCollisionOnHorizontalMoveBackward() {
		for (int i = from.getX() - 1; i > to.getX(); i--) {
			Coordinate coordinate = new Coordinate(i, from.getY());
			if (thisFieldIsOccupied(coordinate)) {
				return false;
			}
		}
		return true;
	}

	private boolean destinationFieldIsOccupied() {
		if (this.getPieceAtToWithNoNullException() != null) {
			return true;
		}
		return false;

	}

	private Piece getPieceAtToWithNoNullException() {
		try {
			return board.getPieceAt(to);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	private Piece getPieceAtWithNoNullException(Coordinate coordinate) {
		try {
			return board.getPieceAt(coordinate);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	private boolean thisFieldIsOccupied(Coordinate coordinate) {
		if (this.getPieceAtWithNoNullException(coordinate) != null) {
			return true;
		}
		return false;

	}
}
