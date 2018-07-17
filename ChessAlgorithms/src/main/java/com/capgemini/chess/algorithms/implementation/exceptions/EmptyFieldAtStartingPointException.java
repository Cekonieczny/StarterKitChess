package com.capgemini.chess.algorithms.implementation.exceptions;

public class EmptyFieldAtStartingPointException extends InvalidMoveException {

	private static final long serialVersionUID = -6420052739649643607L;
	
	public EmptyFieldAtStartingPointException() {
		super("Invalid move! - Starting point field is empty");
	}
	
	public EmptyFieldAtStartingPointException(String message) {
		super("Invalid move! - Starting point field is empty " + message);
	}
}
