package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class KnightMoveValidator extends MoveValidator {
	private Coordinate from;
	private Coordinate to;
	private Board board;

	public KnightMoveValidator(Coordinate from, Coordinate to, Board board) {
		this.from = from;
		this.to = to;
		this.board = board;
	}

	@Override
	public Move validate() throws InvalidMoveException {
		MoveCreator moveCreator = new MoveCreator(from, to, board);
		if (Math.abs(to.getX() - from.getX()) == 1 && (Math.abs(to.getY() - from.getY()) == 2)) {
			if (destinationFieldIsOccupied(to, board)) {
				moveCreator.setCapture();
				return moveCreator.getMove();
			} else {
				moveCreator.setAttack();
				return moveCreator.getMove();
			}
		} else if (Math.abs(to.getX() - from.getX()) == 2 && (Math.abs(to.getY() - from.getY()) == 1)) {
			if (destinationFieldIsOccupied(to, board)) {
				moveCreator.setCapture();
				return moveCreator.getMove();
			} else {
				moveCreator.setAttack();
				return moveCreator.getMove();
			}
		}
		throw new InvalidMoveException();
	}
}
