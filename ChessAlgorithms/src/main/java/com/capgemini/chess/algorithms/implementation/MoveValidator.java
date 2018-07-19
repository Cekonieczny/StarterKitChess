package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;
/**
 * 
 * @author CEKONIEC
 *
 */
public abstract class MoveValidator{

/**
 * Validates a pattern of movement for a certain piece type
 * @return
 * @throws InvalidMoveException
 * @throws NoSetTypeForMoveCreatorException TODO
 */
	public abstract Move validate() throws InvalidMoveException;
	
/**
 * Checks if a field described by given coordinate is occupied by a piece
 * @param coordinate
 * @param board
 * @return
 */
	public boolean thisFieldIsOccupied(Coordinate coordinate, Board board) {
		if (board.getPieceAt(coordinate) != null) {
			return true;
		}
		return false;
	}
}
