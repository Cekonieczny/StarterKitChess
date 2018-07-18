package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class PawnMoveValidator extends MoveValidator {
	private Coordinate from;
	private Coordinate to;
	private Board board;
	private static final int WHITE_PAWN_INITIAL_Y = 1;
	private static final int BLACK_PAWN_INITIAL_Y = 6;

	public PawnMoveValidator(Coordinate from, Coordinate to, Board board) {
		this.from = from;
		this.to = to;
		this.board = board;
	}

	@Override
	public Move validate() throws InvalidMoveException {
		MoveCreator moveCreator = new MoveCreator(from, to, board);
		
		if (board.getPieceAt(from) == Piece.WHITE_PAWN && movesForward()) {
			if(normalAttackValidation()||firstAttackWhitePawnValidation()){
				moveCreator.setAttack();
				return moveCreator.getMove();
			}
			else if(capturePawnValidation()){
				moveCreator.setCapture();
				return moveCreator.getMove();
			}
		} else if (board.getPieceAt(from) == Piece.BLACK_PAWN && !movesForward()) {
			if(normalAttackValidation()||firstAttackBlackPawnValidation()){
				moveCreator.setAttack();
				return moveCreator.getMove();
			}
			else if(capturePawnValidation()){
				moveCreator.setCapture();
				return moveCreator.getMove();
			}
		}
		throw new InvalidMoveException();
	}	
	
	private boolean firstAttackWhitePawnValidation() {
		if ((from.getY() == WHITE_PAWN_INITIAL_Y
				&& (to.getY() == WHITE_PAWN_INITIAL_Y + 1 || to.getY() == WHITE_PAWN_INITIAL_Y + 2))
				&& (from.getX() == to.getX())) {
			if (destinationFieldIsOccupied(to, board)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean firstAttackBlackPawnValidation() {
		if ((from.getY() == BLACK_PAWN_INITIAL_Y
				&& (to.getY() == BLACK_PAWN_INITIAL_Y - 1 || to.getY() == BLACK_PAWN_INITIAL_Y - 2))
				&& (from.getX() == to.getX())) {
			if (destinationFieldIsOccupied(to, board)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	private boolean normalAttackValidation() {
		if (Math.abs(from.getY()-to.getY()) == 1 && from.getX() == to.getX()) {
			if (destinationFieldIsOccupied(to, board)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	
	private boolean capturePawnValidation() {
		if (Math.abs(from.getY()-to.getY()) ==  1 && (Math.abs(from.getX() - to.getX()) == 1)) {
			if (destinationFieldIsOccupied(to, board)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	private boolean movesForward(){
		if(to.getY()>from.getY()){
			return true;
		}
		return false;
	}
	
}
