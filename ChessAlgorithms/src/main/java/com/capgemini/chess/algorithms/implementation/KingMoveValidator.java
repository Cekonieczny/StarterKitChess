package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class KingMoveValidator {
	private Coordinate from;
	private Coordinate to;
	private Board board;

	public KingMoveValidator(Coordinate from, Coordinate to, Board board) {
		this.from = from;
		this.to = to;
		this.board = board;
	}

	public Move validate() throws InvalidMoveException {
		MoveCreator moveCreator = new MoveCreator(from,to,board);
		if (!(Math.abs(to.getX() - from.getX()) >1 || (Math.abs(to.getY() - from.getY()) >1))) {
			if (destinationFieldIsOccupied()) {
				moveCreator.setCapture();
				return moveCreator.getMove();
			} else {
				moveCreator.setAttack();
				return moveCreator.getMove();
			}
		} 
		throw new InvalidMoveException();
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


}
