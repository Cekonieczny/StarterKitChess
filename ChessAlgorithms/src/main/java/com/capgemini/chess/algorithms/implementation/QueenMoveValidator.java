package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class QueenMoveValidator extends MoveValidator {
	private Coordinate from;
	private Coordinate to;
	private Board board;

	public QueenMoveValidator(Coordinate from, Coordinate to, Board board) {
		this.from = from;
		this.to = to;
		this.board = board;
	}

	@Override
	public Move validate() throws InvalidMoveException {
		Move move = rookValidate();
		if (move != null) {
			return move;
		}
		move = bishopValidate();
		if (move != null) {
			return move;
		}
		throw new InvalidMoveException();
	}

	private Move rookValidate() throws InvalidMoveException {
		RookMoveValidator rookMoveValidator = new RookMoveValidator(from, to, board);

		try {
			return rookMoveValidator.validate();
		} catch (InvalidMoveException e) {
			return null;
		}
	}

	private Move bishopValidate() {
		BishopMoveValidator bishopMoveValidator = new BishopMoveValidator(from, to, board);

		try {
			return bishopMoveValidator.validate();
		} catch (InvalidMoveException e) {
			return null;
		}
	}
}
