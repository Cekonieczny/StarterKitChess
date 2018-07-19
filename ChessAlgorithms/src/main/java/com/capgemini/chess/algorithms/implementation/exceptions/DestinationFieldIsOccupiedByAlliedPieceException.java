package com.capgemini.chess.algorithms.implementation.exceptions;
/**
 * Exception which is thrown when destination field chosen for move is already occupied by allied piece 
 * 
 * @author Michal Bejm
 *
 */
public class DestinationFieldIsOccupiedByAlliedPieceException extends InvalidMoveException {

	private static final long serialVersionUID = -8363576130105826013L;

	public DestinationFieldIsOccupiedByAlliedPieceException() {
		super("Invalid move! - Destination field is occupied by alliedPiece ");
	}
	
	public DestinationFieldIsOccupiedByAlliedPieceException(String message) {
		super("Invalid move! - Destination field is occupied by alliedPiece " + message);
	}
}
