package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public interface Validator {

	default boolean fieldIsOccupiedByEnemyPiece(Coordinate from, Board board) {
		if (Validator.fieldIsOccupied(from, board)) {
			if (board.getPieceAt(from).getColor().equals(board.getPieceAt(from).getColor()))
				return false;
		}
		return true;
	}
	
	public Move validation() throws InvalidMoveException;

	static boolean fieldIsOccupied(Coordinate to,Board board){
		if (board.getPieceAt(to) != null) {
			return true;
		}
		return false;
	}

	

}
