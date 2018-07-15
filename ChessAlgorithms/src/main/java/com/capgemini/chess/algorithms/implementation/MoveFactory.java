package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.generated.Board;

public class MoveFactory {
	private Move move = new Move();

	public void setAttack(Coordinate from, Coordinate to,Board board) {
		move.setFrom(from);
		move.setTo(to);
		move.setType(MoveType.ATTACK);
		move.setMovedPiece(board.getPieceAt(from));
	}
	
	public void setCapture(Coordinate from, Coordinate to,Board board){
		move.setType(MoveType.CAPTURE);
		move.setFrom(from);
		move.setTo(to);
		move.setMovedPiece(board.getPieceAt(from));
	}
	
	public void setCastling(Coordinate from, Coordinate to,Board board){
		move.setType(MoveType.CASTLING);
		move.setFrom(from);
		move.setTo(to);
		move.setMovedPiece(board.getPieceAt(from));
	}

	public void setEnPassant(Coordinate from, Coordinate to,Board board){
		move.setType(MoveType.EN_PASSANT);
		move.setFrom(from);
		move.setTo(to);
		move.setMovedPiece(board.getPieceAt(from));
	}
	public Move getMove() {
		return move;
	}

}
