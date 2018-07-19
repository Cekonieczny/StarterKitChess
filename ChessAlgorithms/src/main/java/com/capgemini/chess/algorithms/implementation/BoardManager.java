package com.capgemini.chess.algorithms.implementation;

import java.util.Arrays;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.BoardState;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.CoordinateOutOfBoundsException;
import com.capgemini.chess.algorithms.implementation.exceptions.DestinationFieldIsOccupiedByAlliedPieceException;
import com.capgemini.chess.algorithms.implementation.exceptions.EmptyFieldAtStartingPointException;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidPieceColorChosenException;
import com.capgemini.chess.algorithms.implementation.exceptions.KingInCheckException;

/**
 * Class for managing of basic operations on the Chess Board.
 *
 * @author Michal Bejm
 *
 */
public class BoardManager {

	private Board board = new Board();

	public BoardManager() {
		initBoard();
	}

	public BoardManager(List<Move> moves) {
		initBoard();
		for (Move move : moves) {
			addMove(move);
		}
	}

	public BoardManager(Board board) {
		this.board = board;
	}

	/**
	 * Getter for generated board
	 *
	 * @return board
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Performs move of the chess piece on the chess board from one field to
	 * another.
	 *
	 * @param from
	 *            coordinates of 'from' field
	 * @param to
	 *            coordinates of 'to' field
	 * @return move object which includes moved piece and move type
	 * @throws InvalidMoveException
	 *             in case move is not valid
	 */
	public Move performMove(Coordinate from, Coordinate to) throws InvalidMoveException {

		Move move = validateMove(from, to);

		addMove(move);

		return move;
	}

	/**
	 * Calculates state of the chess board.
	 *
	 * @return state of the chess board
	 */
	public BoardState updateBoardState() {

		Color nextMoveColor = calculateNextMoveColor();

		boolean isKingInCheck = isKingInCheck(nextMoveColor);
		boolean isAnyMoveValid = isAnyMoveValid(nextMoveColor);

		BoardState boardState;
		if (isKingInCheck) {
			if (isAnyMoveValid) {
				boardState = BoardState.CHECK;
			} else {
				boardState = BoardState.CHECK_MATE;
			}
		} else {
			if (isAnyMoveValid) {
				boardState = BoardState.REGULAR;
			} else {
				boardState = BoardState.STALE_MATE;
			}
		}
		this.board.setState(boardState);
		return boardState;
	}

	/**
	 * Checks threefold repetition rule (one of the conditions to end the chess
	 * game with a draw).
	 *
	 * @return true if current state repeated at list two times, false otherwise
	 */
	public boolean checkThreefoldRepetitionRule() {

		// there is no need to check moves that where before last capture/en
		// passant/castling
		int lastNonAttackMoveIndex = findLastNonAttackMoveIndex();
		List<Move> omittedMoves = this.board.getMoveHistory().subList(0, lastNonAttackMoveIndex);
		BoardManager simulatedBoardManager = new BoardManager(omittedMoves);

		int counter = 0;
		for (int i = lastNonAttackMoveIndex; i < this.board.getMoveHistory().size(); i++) {
			Move moveToAdd = this.board.getMoveHistory().get(i);
			simulatedBoardManager.addMove(moveToAdd);
			boolean areBoardsEqual = Arrays.deepEquals(this.board.getPieces(),
					simulatedBoardManager.getBoard().getPieces());
			if (areBoardsEqual) {
				counter++;
			}
		}

		return counter >= 2;
	}

	/**
	 * Checks 50-move rule (one of the conditions to end the chess game with a
	 * draw).
	 *
	 * @return true if no pawn was moved or not capture was performed during
	 *         last 50 moves, false otherwise
	 */
	public boolean checkFiftyMoveRule() {

		// for this purpose a "move" consists of a player completing his turn
		// followed by his opponent completing his turn
		if (this.board.getMoveHistory().size() < 100) {
			return false;
		}

		for (int i = this.board.getMoveHistory().size() - 1; i >= this.board.getMoveHistory().size() - 100; i--) {
			Move currentMove = this.board.getMoveHistory().get(i);
			PieceType currentPieceType = currentMove.getMovedPiece().getType();
			if (currentMove.getType() != MoveType.ATTACK || currentPieceType == PieceType.PAWN) {
				return false;
			}
		}

		return true;
	}

	// PRIVATE

	private void initBoard() {

		this.board.setPieceAt(Piece.BLACK_ROOK, new Coordinate(0, 7));
		this.board.setPieceAt(Piece.BLACK_KNIGHT, new Coordinate(1, 7));
		this.board.setPieceAt(Piece.BLACK_BISHOP, new Coordinate(2, 7));
		this.board.setPieceAt(Piece.BLACK_QUEEN, new Coordinate(3, 7));
		this.board.setPieceAt(Piece.BLACK_KING, new Coordinate(4, 7));
		this.board.setPieceAt(Piece.BLACK_BISHOP, new Coordinate(5, 7));
		this.board.setPieceAt(Piece.BLACK_KNIGHT, new Coordinate(6, 7));
		this.board.setPieceAt(Piece.BLACK_ROOK, new Coordinate(7, 7));

		for (int x = 0; x < Board.SIZE; x++) {
			this.board.setPieceAt(Piece.BLACK_PAWN, new Coordinate(x, 6));
		}

		this.board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(0, 0));
		this.board.setPieceAt(Piece.WHITE_KNIGHT, new Coordinate(1, 0));
		this.board.setPieceAt(Piece.WHITE_BISHOP, new Coordinate(2, 0));
		this.board.setPieceAt(Piece.WHITE_QUEEN, new Coordinate(3, 0));
		this.board.setPieceAt(Piece.WHITE_KING, new Coordinate(4, 0));
		this.board.setPieceAt(Piece.WHITE_BISHOP, new Coordinate(5, 0));
		this.board.setPieceAt(Piece.WHITE_KNIGHT, new Coordinate(6, 0));
		this.board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(7, 0));

		for (int x = 0; x < Board.SIZE; x++) {
			this.board.setPieceAt(Piece.WHITE_PAWN, new Coordinate(x, 1));
		}
	}

	private void addMove(Move move) {

		addRegularMove(move);

		if (move.getType() == MoveType.CASTLING) {
			addCastling(move);
		} else if (move.getType() == MoveType.EN_PASSANT) {
			addEnPassant(move);
		}

		this.board.getMoveHistory().add(move);
	}

	private void addRegularMove(Move move) {
		Piece movedPiece = this.board.getPieceAt(move.getFrom());
		this.board.setPieceAt(null, move.getFrom());
		this.board.setPieceAt(movedPiece, move.getTo());

		performPromotion(move, movedPiece);
	}

	private void performPromotion(Move move, Piece movedPiece) {
		if (movedPiece == Piece.WHITE_PAWN && move.getTo().getY() == (Board.SIZE - 1)) {
			this.board.setPieceAt(Piece.WHITE_QUEEN, move.getTo());
		}
		if (movedPiece == Piece.BLACK_PAWN && move.getTo().getY() == 0) {
			this.board.setPieceAt(Piece.BLACK_QUEEN, move.getTo());
		}
	}

	private void addCastling(Move move) {
		if (move.getFrom().getX() > move.getTo().getX()) {
			Piece rook = this.board.getPieceAt(new Coordinate(0, move.getFrom().getY()));
			this.board.setPieceAt(null, new Coordinate(0, move.getFrom().getY()));
			this.board.setPieceAt(rook, new Coordinate(move.getTo().getX() + 1, move.getTo().getY()));
		} else {
			Piece rook = this.board.getPieceAt(new Coordinate(Board.SIZE - 1, move.getFrom().getY()));
			this.board.setPieceAt(null, new Coordinate(Board.SIZE - 1, move.getFrom().getY()));
			this.board.setPieceAt(rook, new Coordinate(move.getTo().getX() - 1, move.getTo().getY()));
		}
	}

	private void addEnPassant(Move move) {
		Move lastMove = this.board.getMoveHistory().get(this.board.getMoveHistory().size() - 1);
		this.board.setPieceAt(null, lastMove.getTo());
	}

	private Move validateMove(Coordinate from, Coordinate to) throws InvalidMoveException, KingInCheckException {

		initialConditionsAreMet(from, to);

		Move move = validateMovePattern(from, to);

		isKingInCheckForCastling(move);
		
		Piece movedPiece = move.getMovedPiece();
		board.setPieceAt(movedPiece, to);
		board.setPieceAt(null, from);

		if (isKingInCheck(calculateNextMoveColor()) == false) {
			board.setPieceAt(movedPiece, from);
			board.setPieceAt(null, to);
			return move;
		} else {
			throw new KingInCheckException();
		}

	}

	private boolean isKingInCheck(Color kingColor) {
		int i = 0, j = 0;
		Coordinate piecePosition = new Coordinate(i, j);

		Coordinate kingPosition = getCurrentKingPosition(kingColor);

		for (i = 0; i < Board.SIZE; i++)
			for (j = 0; j < Board.SIZE; j++) {
				piecePosition = new Coordinate(i, j);
				if (thisFieldIsEmpty(piecePosition) == false) {
					if (board.getPieceAt(piecePosition).getColor() != kingColor)
						try {
							if (validateMovePattern(piecePosition, kingPosition).getType() == MoveType.CAPTURE) {
								return true;
							}
						} catch (InvalidMoveException e) {
						}
				}
			}
		return false;
	}

	private boolean isAnyMoveValid(Color nextMoveColor) {
		int i = 0, j = 0;
		Coordinate to = new Coordinate(i, j);
		Coordinate from = new Coordinate(0, 0);

		while (from != null) {
			for (i = 0; i < Board.SIZE; i++) {
				for (j = 0; j < Board.SIZE; j++) {
					to = new Coordinate(i, j);
					try {
						validateMove(from, to);
						return true;
					} catch (InvalidMoveException e) {
					}
				}
			}
			from = getGivenColorPiecePosition(nextMoveColor, from);
		}
		return false;
	}

	private boolean coordinateIsOutOfBounds(Coordinate from, Coordinate to) {
		if (from.getY() >= Board.SIZE || to.getY() >= Board.SIZE || from.getX() >= Board.SIZE
				|| to.getX() >= Board.SIZE) {
			return true;
		} else if (from.getY() < 0 || to.getY() < 0 || from.getX() < 0 || to.getX() < 0) {
			return true;
		}
		return false;
	}

	private boolean fieldIsOccupiedByAlliedPiece(Coordinate from, Coordinate to) {
		if (this.thisFieldIsEmpty(to) == false) {
			if (board.getPieceAt(from).getColor().equals(board.getPieceAt(to).getColor()))
				return true;
		}
		return false;
	}

	private Color calculateNextMoveColor() {
		if (this.board.getMoveHistory().size() % 2 == 0) {
			return Color.WHITE;
		} else {
			return Color.BLACK;
		}
	}

	private int findLastNonAttackMoveIndex() {
		int counter = 0;
		int lastNonAttackMoveIndex = 0;

		for (Move move : this.board.getMoveHistory()) {
			if (move.getType() != MoveType.ATTACK) {
				lastNonAttackMoveIndex = counter;
			}
			counter++;
		}

		return lastNonAttackMoveIndex;
	}

	private Move validateMovePattern(Coordinate from, Coordinate to) throws InvalidMoveException {
		PieceType pieceType = board.getPieceAt(from).getType();
		MoveValidator moveValidator = null;

		if (pieceType == PieceType.PAWN) {
			moveValidator = new PawnMoveValidator(from, to, board);
		} else if (pieceType == PieceType.BISHOP) {
			moveValidator = new BishopMoveValidator(from, to, board);
		} else if (pieceType == PieceType.KNIGHT) {
			moveValidator = new KnightMoveValidator(from, to, board);
		} else if (pieceType == PieceType.KING) {
			moveValidator = new KingMoveValidator(from, to, board);
		} else if (pieceType == PieceType.ROOK) {
			moveValidator = new RookMoveValidator(from, to, board);
		} else if (pieceType == PieceType.QUEEN) {
			moveValidator = new QueenMoveValidator(from, to, board);
		}
		return moveValidator.validate();
	}

	private void initialConditionsAreMet(Coordinate from, Coordinate to) throws InvalidMoveException {
		if (coordinateIsOutOfBounds(from, to)) {
			throw new CoordinateOutOfBoundsException();
		}

		if (thisFieldIsEmpty(from)) {
			throw new EmptyFieldAtStartingPointException();
		}

		if (calculateNextMoveColor() != board.getPieceAt(from).getColor()) {
			throw new InvalidPieceColorChosenException();
		}

		if (fieldIsOccupiedByAlliedPiece(from, to)) {
			throw new DestinationFieldIsOccupiedByAlliedPieceException();
		}

	}

	private Coordinate getGivenColorPiecePosition(Color givenColor, Coordinate previousPiecePosition) {
		int i = previousPiecePosition.getX();
		int j = previousPiecePosition.getY();

		Coordinate piecePosition = previousPiecePosition;

		for (i = previousPiecePosition.getX(); i < Board.SIZE; i++) {
			for (j = previousPiecePosition.getY(); j < Board.SIZE; j++) {
				piecePosition = new Coordinate(i, j);
				if (thisFieldIsEmpty(piecePosition) == false) {
					if (!piecePosition.equals(previousPiecePosition)
							&& board.getPieceAt(piecePosition).getColor() == givenColor) {
						return piecePosition;
					}
				}
			}
		}
		return null;
	}

	private Coordinate getCurrentKingPosition(Color kingColor) {
		int i = 0, j = 0;
		Coordinate kingPosition = new Coordinate(i, j);

		for (i = 0; i < Board.SIZE; i++) {
			for (j = 0; j < Board.SIZE; j++) {
				kingPosition = new Coordinate(i, j);
				if (thisFieldIsEmpty(kingPosition) == false) {
					if (board.getPieceAt(kingPosition).getType() == PieceType.KING
							&& board.getPieceAt(kingPosition).getColor() == kingColor)
						return kingPosition;
				}
			}
		}
		return kingPosition;
	}

	private boolean thisFieldIsEmpty(Coordinate coordinate) {
		if (board.getPieceAt(coordinate) == null) {
			return true;
		}
		return false;
	}

	private void isKingInCheckForCastling(Move move) throws KingInCheckException {
		int initialKingY = move.getTo().getY();
		int initialKingX = 4;
		int destinationKingX = move.getTo().getX();

		if (move.getType() == MoveType.CASTLING) {
			Piece testKing;
			if (calculateNextMoveColor() == Color.WHITE) {
				testKing = Piece.WHITE_KING;
			} else {
				testKing = Piece.BLACK_KING;
			}

			if (destinationKingX == 6) {
				board.setPieceAt(null, new Coordinate(initialKingX, initialKingY));
				board.setPieceAt(testKing, new Coordinate(initialKingX + 1, initialKingY));
				if (isKingInCheck(calculateNextMoveColor()) == false) {
					board.setPieceAt(testKing, new Coordinate(initialKingX, initialKingY));
					board.setPieceAt(null, new Coordinate(initialKingX + 1, initialKingY));
				} else {
					throw new KingInCheckException();
				}
			} else if (move.getTo().getX() == 2) {
				board.setPieceAt(null, new Coordinate(initialKingX, initialKingY));
				board.setPieceAt(testKing, new Coordinate(initialKingX - 1, initialKingY));
				if (isKingInCheck(calculateNextMoveColor()) == false) {
					board.setPieceAt(testKing, new Coordinate(initialKingX, initialKingY));
					board.setPieceAt(null, new Coordinate(initialKingX - 1, initialKingY));
				} else {
					throw new KingInCheckException();
				}
			}

		}
	}
}
