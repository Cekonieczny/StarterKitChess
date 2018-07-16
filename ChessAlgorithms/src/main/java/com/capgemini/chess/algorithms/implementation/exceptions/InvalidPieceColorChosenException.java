package com.capgemini.chess.algorithms.implementation.exceptions;

public class InvalidPieceColorChosenException extends InvalidMoveException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2040023115193621513L;
	/**
	 * 
	 */
	public InvalidPieceColorChosenException() {
		super("Invalid move! - Wrong piece's color chosen");
	}

	public InvalidPieceColorChosenException(String message) {
		super("Invalid move! - Wrong piece's color chosen" + message);
	}

}
