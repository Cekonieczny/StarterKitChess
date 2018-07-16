package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.generated.Board;

public class MoveCreator {
	private Move move = new Move();

	public void setAttack(Coordinate from, Coordinate to,Board board) {
		this.setMove(from, to, board);
		move.setType(MoveType.ATTACK);
	}
	
	public void setCapture(Coordinate from, Coordinate to,Board board){
		this.setMove(from, to, board);
		move.setType(MoveType.CAPTURE);		
	}
	
	public void setCastling(Coordinate from, Coordinate to,Board board){
		move.setType(MoveType.CASTLING);
		this.setMove(from, to, board);
	}

	public void setEnPassant(Coordinate from, Coordinate to,Board board){
		move.setType(MoveType.EN_PASSANT);
		this.setMove(from, to, board);
	}
	public Move getMove() {
		return move;
	}
	private void setMove(Coordinate from, Coordinate to, Board board){
		move.setFrom(from);
		move.setTo(to);
		move.setMovedPiece(board.getPieceAt(from));
	}

}
