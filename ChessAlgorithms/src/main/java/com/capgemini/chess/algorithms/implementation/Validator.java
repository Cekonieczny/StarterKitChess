package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public interface Validator {
	public Move validation() throws InvalidMoveException;
}
