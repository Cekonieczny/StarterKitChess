package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public abstract class MoveValidator {

	public abstract Move validate() throws InvalidMoveException;

	public boolean destinationFieldIsOccupied(Coordinate to, Board board) {
		if (board.getPieceAt(to) != null) {
			return true;
		}
		return false;
	}

}
