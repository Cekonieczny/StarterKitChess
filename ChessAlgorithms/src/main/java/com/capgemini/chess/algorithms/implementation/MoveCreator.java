package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.generated.Board;

public class MoveCreator {
	private Move move = new Move();
	
	public MoveCreator(Coordinate from,Coordinate to,Board board){
		move.setFrom(from);
		move.setTo(to);
		move.setMovedPiece(board.getPieceAt(from));
	}

	public void setAttack() {
		move.setType(MoveType.ATTACK);
	}
	
	public void setCapture(){
		move.setType(MoveType.CAPTURE);		
	}
	
	public void setCastling(){
		move.setType(MoveType.CASTLING);
	}

	public void setEnPassant(){
		move.setType(MoveType.EN_PASSANT);
	}
	public Move getMove() {
		return move;
	}
}
