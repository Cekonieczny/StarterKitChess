package com.capgemini.chess.algorithms.implementation.exceptions;

/**
 * Exception which is thrown in case players king will after performing his move 
 * 
 * @author Michal Bejm
 *
 */
public class KingInCheckException extends InvalidMoveException {

	private static final long serialVersionUID = -7109029342454067452L;
	
	public KingInCheckException() {
		super("King must not be checked!");
	}

}
