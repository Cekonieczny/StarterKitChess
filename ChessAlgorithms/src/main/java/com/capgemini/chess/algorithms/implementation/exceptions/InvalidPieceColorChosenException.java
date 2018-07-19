package com.capgemini.chess.algorithms.implementation.exceptions;
/**
 * Exception which is thrown in case player choses piece of wrong color
 * 
 * @author Michal Bejm
 *
 */
public class InvalidPieceColorChosenException extends InvalidMoveException {

	private static final long serialVersionUID = -2040023115193621513L;

	public InvalidPieceColorChosenException() {
		super("Invalid move! - Wrong piece's color chosen");
	}

	public InvalidPieceColorChosenException(String message) {
		super("Invalid move! - Wrong piece's color chosen" + message);
	}

}
