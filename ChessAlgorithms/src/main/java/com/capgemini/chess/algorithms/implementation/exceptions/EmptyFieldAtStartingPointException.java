package com.capgemini.chess.algorithms.implementation.exceptions;

/**
 * Exception which is thrown in case player will choose empty field instead of a piece
 * 
 * @author Michal Bejm
 *
 */
public class EmptyFieldAtStartingPointException extends InvalidMoveException {

	private static final long serialVersionUID = -6420052739649643607L;
	
	public EmptyFieldAtStartingPointException() {
		super("Invalid move! - Starting point field is empty");
	}
	
	public EmptyFieldAtStartingPointException(String message) {
		super("Invalid move! - Starting point field is empty " + message);
	}
}
