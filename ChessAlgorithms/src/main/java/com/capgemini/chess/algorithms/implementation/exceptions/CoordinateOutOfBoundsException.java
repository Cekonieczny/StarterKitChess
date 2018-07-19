package com.capgemini.chess.algorithms.implementation.exceptions;

/**
 * Exception which is thrown in case coordinates chosen are bigger or lesser
 * than board's coordinate
 * 
 * @author Michal Bejm
 *
 */
public class CoordinateOutOfBoundsException extends InvalidMoveException {

	private static final long serialVersionUID = 3310256122327196017L;

	public CoordinateOutOfBoundsException() {
		super("Invalid move! - Coordinate is out of board's bounds");
	}

}
