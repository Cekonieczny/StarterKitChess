package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.generated.Board;
/**
 * Creates a chosen type of move with given parameters
 * @author CEKONIEC
 *
 */
public class MoveCreator {
	private Move move = new Move();
	/**
	 * Sets parameters of move that is about to be performed
	 * @param from
	 * @param to
	 * @param board
	 */
	public MoveCreator(Coordinate from,Coordinate to,Board board){
		move.setFrom(from);
		move.setTo(to);
		move.setMovedPiece(board.getPieceAt(from));
	}
	/**
	 * Sets type of move as attack
	 */
	public void setAttack() {
		move.setType(MoveType.ATTACK);
	}
	/**
	 * Sets type of move as capture
	 */
	public void setCapture(){
		move.setType(MoveType.CAPTURE);		
	}
	/**
	 * Sets type of move as castling
	 */
	public void setCastling(){
		move.setType(MoveType.CASTLING);
	}
	/**
	 * Sets type of move as en passant
	 */
	public void setEnPassant(){
		move.setType(MoveType.EN_PASSANT);
	}
	/**
	 * Returns move with previously set parameters
	 * @return
	 * @throws NoSetTypeForMoveCreatorException
	 */
	public Move getMove(){
		return move;
	}
}
