package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class QueenMoveValidator {
	private Coordinate from;
	private Coordinate to;
	private Board board;
	private Move move;
	

	public QueenMoveValidator(Coordinate from, Coordinate to, Board board) {
		this.from = from;
		this.to = to;
		this.board = board;
	}

	public Move validation() throws InvalidMoveException {
		
		if (this.rookValidation()!=null){
			return move;
		}else if(this.bishopValidation()!=null) {
			return move;
		}
		throw new InvalidMoveException();
	}
	
	private Move rookValidation(){
		RookMoveValidator rookMoveValidator = new RookMoveValidator(from, to, board);
	
		try {
			return move = rookMoveValidator.validation();
		} catch (InvalidMoveException e) {
		    return  null;
		}	
	}
	
	private Move bishopValidation(){
		BishopMoveValidator bishopMoveValidator = new BishopMoveValidator(from, to, board);
	
		try {
			return move = bishopMoveValidator.validation();
		} catch (InvalidMoveException e) {
		    return  null;
		}	
	}
}
		
	
	
