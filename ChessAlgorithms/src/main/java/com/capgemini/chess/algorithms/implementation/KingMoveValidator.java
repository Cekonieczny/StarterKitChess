package com.capgemini.chess.algorithms.implementation;

import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class KingMoveValidator extends MoveValidator {
	private Coordinate from;
	private Coordinate to;
	private Board board;
	private static final Coordinate WHITE_KING_STARTING_POSITION = new Coordinate(4, 0);
	private static final Coordinate BLACK_KING_STARTING_POSITION = new Coordinate(4, 7);

	public KingMoveValidator(Coordinate from, Coordinate to, Board board) {
		this.from = from;
		this.to = to;
		this.board = board;
	}

	@Override
	public Move validate() throws InvalidMoveException {
		MoveCreator moveCreator = new MoveCreator(from,to,board);
		
		boolean canMoveOnlyByMoreThanOneFieldHorizontally = (Math.abs(to.getX() - from.getX())>1);
		boolean canMoveOnlyByMoreThanOneFieldVertically = (Math.abs(to.getY() - from.getY()) >1);
		
		if(castlingValidate()){
			moveCreator.setCastling();
			return moveCreator.getMove();
		}

		if (!(canMoveOnlyByMoreThanOneFieldHorizontally || canMoveOnlyByMoreThanOneFieldVertically)) {
			if (thisFieldIsOccupied(to, board)) {
				moveCreator.setCapture();
				return moveCreator.getMove();
			} else {
				moveCreator.setAttack();
				return moveCreator.getMove();
			}
		} 
		throw new InvalidMoveException();
	}
	
	
	private boolean castlingValidate(){
	
		if ((from.equals(WHITE_KING_STARTING_POSITION)|| from.equals(BLACK_KING_STARTING_POSITION))
				&& pieceWasNotPerformingMove(from)) {
			if (to.getY() == from.getY() && to.getX() == from.getX() + 2) {
				Coordinate rightRookPosition = new Coordinate(to.getX() + 1, from.getY());
				if (noPiecesBetweenKingAndRookRight() && pieceWasNotPerformingMove(rightRookPosition)) {
					return true;
				}
			} else if (to.getY() == from.getY() && to.getX() == from.getX() - 2) {
				Coordinate leftRookPosition = new Coordinate(to.getX() - 2, from.getY());
				if (noPiecesBetweenKingAndRookLeft() && pieceWasNotPerformingMove(leftRookPosition)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean pieceWasNotPerformingMove(Coordinate coordinate) {
		List<Move> moveHistory = board.getMoveHistory();
		for (Move move:moveHistory) {
			move = moveHistory.iterator().next();
			if (move.getFrom().equals(coordinate)) {
				return false;
			}
		}
		return true;
	}

	private boolean noPiecesBetweenKingAndRookRight() {
		for (int i = from.getX() + 1; i < to.getX() + 1; i++) {
			Coordinate coordinate = new Coordinate(i, from.getY());
			if (thisFieldIsOccupied(coordinate, board)) {
				return false;
			}
		}
		return true;
	}

	private boolean noPiecesBetweenKingAndRookLeft() {
		for (int i = from.getX() - 1; i > to.getX() - 2; i--) {
			Coordinate coordinate = new Coordinate(i, from.getY());
			if (thisFieldIsOccupied(coordinate, board)) {
				return false;
			}
		}
		return true;
	}
}
