package com.xoka74.chess.ui.custom.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TableLayout
import no.bakkenbaeck.chessboardeditor.model.FenParseException
import no.bakkenbaeck.chessboardeditor.model.Move
import no.bakkenbaeck.chessboardeditor.model.Piece
import no.bakkenbaeck.chessboardeditor.model.Position
import no.bakkenbaeck.chessboardeditor.util.Constants
import no.bakkenbaeck.chessboardeditor.util.FenUtil
import no.bakkenbaeck.chessboardeditor.view.cell.ChessCellView
import no.bakkenbaeck.chessboardeditor.view.cell.ChessInnerCellView
import no.bakkenbaeck.chessboardeditor.view.cell.ChessSideCellView
import no.bakkenbaeck.chessboardeditor.view.piece.ChessPieceView
import no.bakkenbaeck.chessboardeditor.view.row.ChessInnerRowView
import no.bakkenbaeck.chessboardeditor.view.row.ChessSideRowView
import no.bakkenbaeck.chessboardeditor.view.row.ChessWhiteSpaceRowView
import kotlin.math.min

class CustomChessBoardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : TableLayout(context, attrs) {
    companion object {
        const val BOARD_WIDTH_RATIO = 8
        const val BOARD_HEIGHT_RATIO = 11
    }

    private lateinit var position: Position

    private var isDraggable: Boolean = true

    private var isWhiteOriented: Boolean = false

    fun setIsDraggable(value: Boolean) {
        isDraggable = value
    }

    fun setIsWhiteOriented(value: Boolean) {
        if (isWhiteOriented == value) return
        isWhiteOriented = value
        initBoard(isWhiteOriented)
    }

    var onPieceMoved: ((ChessInnerCellView, ChessInnerCellView, ChessPieceView) -> Unit)? = null


    init {
        setFen("")
    }

    fun setFen(fen: String) {
        try {
            position = FenUtil.readFEN(fen)
            initBoard(isWhiteOriented)
        } catch (ex: FenParseException) {
            Log.e("ChessBoardView", "Error parsing FEN", ex)
        }
    }

    fun getFen() = FenUtil.toFen(position)

    private fun initBoard(isWhiteOriented: Boolean) {
        post {
            removeAllViews()
            createBoard(isWhiteOriented)
            loadPieces()
        }
    }

    private fun createBoard(isWhiteOriented: Boolean) {
        insertEmptySpace()
        if (isWhiteOriented) {
            (0 until Constants.BOARD_SIZE).reversed().forEach { insertRow(it) }
        } else {
            (0 until Constants.BOARD_SIZE).forEach { insertRow(it) }
        }
        insertEmptySpace()
    }

    private fun insertSideRow(isWhite: Boolean) {
        val row = ChessSideRowView(context)
        row.setSide(isWhite, ::pieceDragged)
        addView(row)
    }

    private fun insertEmptySpace() {
        addView(ChessWhiteSpaceRowView(context))
    }

    private fun insertRow(rowIndex: Int) {
        val row = ChessInnerRowView(context)
        row.setRow(rowIndex, ::pieceDragged)
        addView(row)
    }

    private fun pieceDragged(cellTag: String, pieceTag: String) {
        val pieceView =
            this@CustomChessBoardView.findViewWithTag<View>(pieceTag) as? ChessPieceView ?: return
        val fromCell = pieceView.parent as? ChessCellView ?: return
        val toCell = this@CustomChessBoardView.findViewWithTag<View>(cellTag) as? ChessInnerCellView
            ?: return
        val piece = pieceView.getPiece() ?: return
        Log.i("PIECE IS WHITE", pieceIsWhite(piece).toString())
        if (isDraggable && (fromCell is ChessInnerCellView) && (pieceIsWhite(piece) == isWhiteOriented)) {
            onPieceMoved?.invoke(fromCell, toCell, pieceView)
//            applyNormalPieceMove(fromCell, toCell, pieceView)
        }
//        else if (fromCell is ChessSideCellView) {
//            if (piece is Piece.Delete) applyRemovePieceMove(toCell)
//            else applyInsertPieceMove(fromCell, toCell, piece)
//        }
    }

    private fun pieceIsWhite(piece: Piece): Boolean {
        return listOf(
            Piece.WhiteKing(),
            Piece.WhiteQueen(),
            Piece.WhiteRook(),
            Piece.WhiteBishop(),
            Piece.WhiteKnight(),
            Piece.WhitePawn(),
        ).firstOrNull { piece::class == it::class } != null
    }

    private fun applyNormalPieceMove(
        fromCell: ChessInnerCellView,
        toCell: ChessInnerCellView,
        pieceView: ChessPieceView
    ) {

        applyNormalPieceMoveToPosition(fromCell, toCell)
        applyNormalPieceMoveToUI(fromCell, toCell, pieceView)
    }

    private fun applyInsertPieceMove(
        fromCell: ChessSideCellView,
        toCell: ChessInnerCellView,
        piece: Piece
    ) {
        applyInsertPieceMoveToPosition(fromCell, toCell)
        applyInsertPieceMoveToUI(toCell, piece)
    }

    private fun applyRemovePieceMove(removedCell: ChessInnerCellView) {
        applyRemovePieceMoveToPosition(removedCell)
        applyRemovePieceMoveToUI(removedCell)
    }

    private fun applyNormalPieceMoveToPosition(
        fromCell: ChessInnerCellView,
        toCell: ChessInnerCellView
    ) {
        val move = Move.NormalPieceMove(
            fromCell.getRowCol(),
            toCell.getRowCol()
        )
        position.makeMove(move)
    }

    private fun applyNormalPieceMoveToUI(
        fromCell: ChessInnerCellView,
        toCell: ChessInnerCellView,
        pieceView: ChessPieceView
    ) {
        fromCell.removePiece()
        toCell.setPiece(pieceView)
    }

    private fun applyInsertPieceMoveToPosition(
        fromCell: ChessSideCellView,
        toCell: ChessInnerCellView
    ) {
        val currentRowCol = fromCell.getRowCol()
        val piece =
            ChessSideRowView.getPieceFromRowCol(currentRowCol.first, currentRowCol.second) ?: return
        val move = Move.InsertPieceMove(
            toCell.getRowCol(),
            piece
        )
        position.makeMove(move)
    }

    private fun applyInsertPieceMoveToUI(
        toCell: ChessInnerCellView,
        piece: Piece
    ) {
        val clonePieceView = ChessPieceView(context)
        clonePieceView.setPiece(piece)
        toCell.setPiece(clonePieceView)
    }

    private fun applyRemovePieceMoveToPosition(removedCell: ChessInnerCellView) {
        val move = Move.RemovePieceMove(removedCell.getRowCol())
        position.makeMove(move)
    }

    private fun applyRemovePieceMoveToUI(removedCell: ChessInnerCellView) {
        removedCell.removePiece()
    }

    private fun loadPieces() {
        position.forEach { row, col, piece ->
            val tag = ChessCellView.getTagFromRowCol(row, col)
            val cell = findViewWithTag<ChessCellView>(tag) ?: return@forEach
            val pieceView = ChessPieceView(context)
            pieceView.setPiece(piece)
            cell.setPiece(pieceView)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val (desiredWidth, desiredHeight) = getDesiredDimensions(
            widthMeasureSpec,
            heightMeasureSpec
        )
        setMeasuredDimension(desiredWidth, desiredHeight)
        setWidthHeight(desiredWidth, desiredHeight)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun getDesiredDimensions(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ): Pair<Int, Int> {
        val measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measuredHeight = MeasureSpec.getSize(heightMeasureSpec)
        val normalizedWidth = measuredWidth * BOARD_HEIGHT_RATIO
        val normalizedHeight = measuredHeight * BOARD_WIDTH_RATIO
        val minNormalized = min(normalizedWidth, normalizedHeight)
        val desiredWidth = minNormalized / BOARD_HEIGHT_RATIO
        val desiredHeight = minNormalized / BOARD_WIDTH_RATIO
        return desiredWidth to desiredHeight
    }

    private fun setWidthHeight(width: Int, height: Int) {
        layoutParams.width = width
        layoutParams.height = height
    }
}