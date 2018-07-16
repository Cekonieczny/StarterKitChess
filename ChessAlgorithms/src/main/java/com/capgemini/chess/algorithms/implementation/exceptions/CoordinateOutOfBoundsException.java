package com.capgemini.chess.algorithms.implementation.exceptions;

public class CoordinateOutOfBoundsException extends InvalidMoveException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3310256122327196017L;
	/**
	 * 
	 */
	

	public CoordinateOutOfBoundsException() {
		super("Invalid move! - Coordinate is out of board's bounds");
	}
	
	public CoordinateOutOfBoundsException(String message) {
		super("Invalid move! - Coordinate is out of board's bounds" + message);
	}

}
