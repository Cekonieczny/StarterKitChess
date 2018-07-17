package com.capgemini.chess.algorithms.implementation.exceptions;

public class DestinationFieldIsOccupiedByAlliedPieceException extends InvalidMoveException {

	private static final long serialVersionUID = -8363576130105826013L;

	public DestinationFieldIsOccupiedByAlliedPieceException() {
		super("Invalid move! - Destination field is occupied by alliedPiece ");
	}
	
	public DestinationFieldIsOccupiedByAlliedPieceException(String message) {
		super("Invalid move! - Destination field is occupied by alliedPiece " + message);
	}
}
