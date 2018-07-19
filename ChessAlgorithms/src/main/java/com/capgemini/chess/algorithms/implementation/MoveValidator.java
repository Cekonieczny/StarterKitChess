package com.capgemini.chess.algorithms.implementation;

import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public abstract class MoveValidator{

	public abstract Move validate() throws InvalidMoveException;
	

	public boolean thisFieldIsOccupied(Coordinate to, Board board) {
		if (board.getPieceAt(to) != null) {
			return true;
		}
		return false;
	}
	
	public boolean pieceWasNotPerformingMove(Coordinate coordinate,Board board) {
		List<Move> moveHistory = board.getMoveHistory();
		for (Move move:moveHistory) {
			move = moveHistory.iterator().next();
			if (move.getFrom().equals(coordinate)) {
				return false;
			}
		}
		return true;
	}

}
