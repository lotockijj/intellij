package com.dominyuk.pgn2pdf;


import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import static com.dominyuk.pgn2pdf.PdfConfig.*;

/*
 * This class is written by ro6man starting from 28.12.2016.
 * This class uses AGPL version of iText.
 */
public class PdfCreator {
    // cells quantity in the row or column of chess board:
    public static final int BOARD_SIZE = 8;
    private BaseFont bf;
    /* field PdfWriter for almost every method in the class */
    private PdfWriter writer;

    // info from pgn file
    private String header;
    private String description;
    private String allMoves;
    // info from JsCreator
    private String jsContent;
    // config info:
    PdfConfig config;

    /* Chess board representation */
    private Rectangle[][] chessBoardCell = new Rectangle[8][8];
    private Rectangle[] columnNameRectangle = new Rectangle[8];
    private Rectangle[] rowNameRectangle = new Rectangle[8];
    /* Representation of letters column and rows names */
    private String[] columnLetter = {"a", "b", "c", "d", "e", "f", "g", "h"};
    private String[] rowLetter    = {"1", "2", "3", "4", "5", "6", "7", "8"};
    /* Control buttons representation */
    private Rectangle first, forward, backward, last, play, stop;
    /* To make mooves on the layer above others we are using this Rectangle: */
    private Rectangle invisibleRectangle = new Rectangle(0, 0, BOARD_CELL_SIZE, BOARD_CELL_SIZE);
    // Ornamentals chess pieces
    private Rectangle[] ornamentalChessPieces = new Rectangle[config.STASH_ELEMENTS_QUANTITY];

    // pieces on the board and in the stash
    private String[] positionLayerChessPieces = {"K1", "N1", "B1", "R1", "Q1",
        "N2", "B2", "R2", "Q2", "P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8",
        "k1", "n1", "b1", "r1", "q1", "n2", "b2", "r2", "q2", "p1", "p2", "p3",
        "p4", "p5", "p6", "p7", "p8"};
    private String[] moveLayerChessPieces = {"P", "N", "B", "R", "Q", "K", "p",
        "n", "b", "r", "q", "k"};

    public PdfCreator(PdfConfig config, PgnParser parser, String jsContent) throws IOException, DocumentException {
        this.config = config;
        header = parser.getHeader();
        description = parser.getDescription();
        allMoves = parser.getAllMoves();
        initializeChessBoardChessPiecesAndButtons();
        this.jsContent = jsContent;
    }
    /**
     * Creates a PDF document.
     * @param filename the path to the new PDF document
     * @throws    DocumentException
     * @throws    IOException
     */
    //todo: check what exceptions here are thrown and how to handle them
    public void createPdf(String filename) throws IOException, DocumentException {
        Document document = new Document(new Rectangle(DOCUMENT_WIDTH, DOCUMENT_HEIGHT));
        writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();

        Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, TITLE_FONT_SIZE, Font.BOLDITALIC);
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, PARAGRAPH_FONT_SIZE, Font.NORMAL);
        Chunk chunk = new Chunk(header, chapterFont);
        Chapter chapter = new Chapter(new Paragraph(chunk), 1);
        chapter.setNumberDepth(0);
        Paragraph p = new Paragraph(description, paragraphFont);
        DottedLineSeparator dottedline = new DottedLineSeparator();
        dottedline.setOffset(-6);
        dottedline.setGap(2f);
        p.add(dottedline);
        chapter.add(p);
        document.add(chapter);
        p = new Paragraph(allMoves);
        document.add(p);
        writer.addJavaScript(jsContent);
        /* add the field cells */
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ( (i + j) % 2 == 0) {
                    addCell(chessBoardCell[i][j], BLACK_CELLS_COLOR);
                } else {
                    addCell(chessBoardCell[i][j], WHITE_CELLS_COLOR);
                }
            }
        }
        // add small square to lower left corner
        addCell(new Rectangle(BOARD_X0 - BOARD_LETTERS_FRAME_WIDTH,
                BOARD_Y0 - BOARD_LETTERS_FRAME_WIDTH, BOARD_X0, BOARD_Y0),
                BLACK_CELLS_COLOR);
        /* add the field names sidebar cells */
        for (int i = 0; i < BOARD_SIZE; i++) {
            addFrameCell(rowNameRectangle[i], rowLetter[i],  BaseColor.WHITE, BOARD_INSCRIPTION_FONT_SIZE);
            addFrameCell(columnNameRectangle[i], columnLetter[i], BaseColor.WHITE, BOARD_INSCRIPTION_FONT_SIZE);
        }
        /* control buttons initialization */
        addPushButton(first, "first", "▮◀◀", "this.first()", BaseColor.LIGHT_GRAY);
        addPushButton(backward, "backward", "▮◀", "this.backward()", BaseColor.LIGHT_GRAY);
        addPushButton(forward, "forward", "▶▮", "this.forward()", BaseColor.LIGHT_GRAY);
        addPushButton(last, "last", "▶▶▮", "this.last()", BaseColor.LIGHT_GRAY);
        addPushButton(play, "play", "▶", "this.play()", BaseColor.GRAY);
        addPushButton(stop, "stop", "▮▮", "this.stop()", BaseColor.GRAY);

        PdfContentByte cb = writer.getDirectContent();
        int stashCounter = 0;

        PdfLayer boardLayer = new PdfLayer("boardLayer", writer);
        cb.beginLayer(boardLayer);
        // todo: place blue? sqares in the buttom (above cells) layer - to indicate moves
        addMovableCell(ornamentalChessPieces[stashCounter++], config.FROM_CELL_COLOR, "fromCell");
        addMovableCell(ornamentalChessPieces[stashCounter++], config.TO_CELL_COLOR, "toCell");
        cb.endLayer();

        PdfLayer positionLayer = new PdfLayer("positionLayer", writer);
        cb.beginLayer(positionLayer);
        for (String pieceName: positionLayerChessPieces) {
            addChessPiece(ornamentalChessPieces[stashCounter++], pieceName);
        }
        cb.endLayer();

        PdfLayer moveLayer = new PdfLayer("moveLayer", writer);
        cb.beginLayer(moveLayer);
        for (String pieceName: moveLayerChessPieces) {
            addChessPiece(ornamentalChessPieces[stashCounter++], pieceName);
        }
        cb.endLayer();

        //todo add stashLayer with white sqare;
        PdfLayer stashLayer = new PdfLayer("stashLayer", writer);
        cb.beginLayer(stashLayer);
        addMovableCell(ornamentalChessPieces[stashCounter++], new CMYKColor(0,0,0,0), "whiteCell");
        cb.endLayer();

        document.close();
    }

    private void addChessPiece(String chessPieceType, int column, int row) {
        addChessPiece(chessBoardCell[column][row], chessPieceType + columnLetter[column] + rowLetter[row]);

    }

    /**
     * Add a text field.
     * @param rect the position of the text field
     * @param name the name of the text field
     */
    public void addTextField(Rectangle rect, String name) {
        PdfFormField field = PdfFormField.createTextField(writer, false, false, 0);
        field.setFieldName(name);
        field.setWidget(rect, PdfAnnotation.HIGHLIGHT_NONE);
        field.setQuadding(PdfFormField.Q_CENTER);
        field.setFieldFlags(PdfFormField.FF_READ_ONLY);
        writer.addAnnotation(field);

        PushbuttonField button = new PushbuttonField(writer, rect, "btn" + name);
        button.setFont(bf);
        try {
            writer.addAnnotation(button.getField());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initializes the font
     * @throws DocumentException
     * @throws IOException
     */
    public void initializeFont() throws DocumentException, IOException {
        bf = BaseFont.createFont(config.fontFilePath, BaseFont.IDENTITY_H, false);
    }

    /**
     * Initializes the rectangles for the viewer elements
     */
    public void initializeChessBoardChessPiecesAndButtons() {
        /* Chess board cells initialization */
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                /* make gray rectangles in chess order */
                chessBoardCell[i][j] = createRectangle(i + 1, j + 2);
            }
        }

        /* Fields with chess board cells names initialization */
        for (int i = 0; i < 8; i++) {
            rowNameRectangle[i] = createNarrowRectangle(0, i + 2, true);
            columnNameRectangle[i] = createNarrowRectangle(i + 1, 1, false);
        }

        // control buttons initialization
        first = createRectangle(2, 0);
        backward = createRectangle(3,0);
        forward = createRectangle(4, 0);
        last = createRectangle(5, 0);
        play = createRectangle(6, 0);
        stop = createRectangle(7, 0);

        // simplified initialization of ornamental chess pieces fixme
        // in two rows - the first for white - second for black
        int size = config.BOARD_CELL_SIZE;
        for (int i = 0; i < config.STASH_ELEMENTS_QUANTITY; i++) {
            ornamentalChessPieces[i] = new Rectangle(i * size, 0,
                    (i + 1) * size, size);
        }

    }

    /**
     * Create a rectangle object for elements in viewer grid (cell pad).
     * @param column column of the cell, 0th is the most to the left
     * @param row row of the cell, 0th is the first from bottom
     * @return a rectangle defining the position of a cell.
     */
    public Rectangle createRectangle(int column, int row) {
        int x = column * BOARD_CELL_SIZE + PART_OF_BOARD_AND_BUTTONS_LEFT_MARGIN;
        int y = row * BOARD_CELL_SIZE + BOARD_AND_BUTTONS_BOTTOM_MARGIN;
        return new Rectangle(x, y, x + BOARD_CELL_SIZE, y + BOARD_CELL_SIZE);
    }

    /**
     * Create a rectangle object for elements in viewer grid (cell pad).
     * @param column column of the cell on the cell pad
     * @param row row of the cell on the cell pad
     * @return a rectangle defining the position of a cell.
     */
    public Rectangle createNarrowRectangle(int column, int row, boolean isVertical) {
        int width, height, x, y;
        int blankSpace = BOARD_CELL_SIZE - BOARD_LETTERS_FRAME_WIDTH;
        if (isVertical) {
            x = column * BOARD_CELL_SIZE + PART_OF_BOARD_AND_BUTTONS_LEFT_MARGIN + blankSpace;
            y = row * BOARD_CELL_SIZE + BOARD_AND_BUTTONS_BOTTOM_MARGIN;
            width = BOARD_LETTERS_FRAME_WIDTH;
            height = BOARD_CELL_SIZE;
        } else {
            x = column * BOARD_CELL_SIZE + PART_OF_BOARD_AND_BUTTONS_LEFT_MARGIN;
            y = row * BOARD_CELL_SIZE + BOARD_AND_BUTTONS_BOTTOM_MARGIN + blankSpace;
            width = BOARD_CELL_SIZE;
            height = BOARD_LETTERS_FRAME_WIDTH;
        }
        return new Rectangle(x, y, x + width, y + height);
    }

    public void addCell(Rectangle rect, CMYKColor colorFill) {
        float x = rect.getLeft();
        float y = rect.getBottom();

        PdfContentByte canvas = writer.getDirectContent();
        canvas.rectangle(x, y, BOARD_CELL_SIZE, BOARD_CELL_SIZE);
        canvas.setColorFill(colorFill);
        canvas.fill();
    }

    public void addFrameCell(Rectangle rect, String label,
                             BaseColor inscriptionColor, int inscriptionSize) {
        float w = rect.getWidth();
        float h = rect.getHeight();
        float x = rect.getLeft();
        float y = rect.getBottom();

        PdfContentByte canvas = writer.getDirectContent();
        canvas.rectangle(x, y, w, h);
        canvas.setColorFill(BLACK_CELLS_COLOR);
        canvas.fill();
        canvas.setColorFill(inscriptionColor);
        canvas.setFontAndSize(bf, inscriptionSize);
        canvas.beginText();
        canvas.showTextAligned(Element.ALIGN_CENTER, label,
                x + w / 2, y + h / 4, 0);
        canvas.endText();
    }


    /**
     * Create a pushbutton for a control key
     * @param rect the position of the control key
     * @param btn the label for the control key
     * @param script the script to be executed when the button is pushed
     * @param color of the button
     */
    public void addPushButton(Rectangle rect, String btnName,
                              String btn, String script, BaseColor color) {
        float w = rect.getWidth();
        float h = rect.getHeight();
        PdfFormField pushbutton = PdfFormField.createPushButton(writer);
        pushbutton.setFieldName("btn_" + btnName);
        pushbutton.setWidget(rect, PdfAnnotation.HIGHLIGHT_PUSH);
        PdfContentByte cb = writer.getDirectContent();
        pushbutton.setAppearance(PdfAnnotation.APPEARANCE_NORMAL,
                createAppearance(cb, btn, color, w, h));
        pushbutton.setAppearance(PdfAnnotation.APPEARANCE_ROLLOVER,
                createAppearance(cb, btn, BaseColor.RED, w, h));
        pushbutton.setAppearance(PdfAnnotation.APPEARANCE_DOWN,
                createAppearance(cb, btn, BaseColor.BLUE, w, h));
        pushbutton.setAdditionalActions(PdfName.U,
                PdfAction.javaScript(script, writer));
//        pushbutton.setAdditionalActions(PdfName.E, PdfAction.javaScript(
//                "this.showMove('" + btn + "');", writer));
//        pushbutton.setAdditionalActions(PdfName.X, PdfAction.javaScript(
//                "this.showMove(' ');", writer));
        writer.addAnnotation(pushbutton);
    }

    /**
     * Creates an appearance for a key
     * @param cb the canvas
     * @param btn the label for the key
     * @param color the color of the key
     * @param w the width
     * @param h the height
     * @return an appearance
     */
    public PdfAppearance createAppearance(
            PdfContentByte cb, String btn, BaseColor color, float w, float h) {
        PdfAppearance app = cb.createAppearance(w, h);
        app.setColorFill(color);
        app.rectangle(2, 2, w - 4, h - 4);
        app.fill();
        app.beginText();
        app.setColorFill(BaseColor.BLACK);
        app.setFontAndSize(bf, h / 2);
        app.showTextAligned(Element.ALIGN_CENTER, btn, w / 2, h / 4, 0);
        app.endText();
        return app;
    }

    /**
     * Create a pushbutton for a control key
     * @param rect the position of the control key
     */
    public void addChessPiece(Rectangle rect, String chessPieceName) {
        float w = rect.getWidth();
        float h = rect.getHeight();
        PdfFormField chessPiece = PdfFormField.createPushButton(writer);
        chessPiece.setFieldName(chessPieceName);
        chessPiece.setWidget(rect, PdfAnnotation.APPEARANCE_NORMAL);
        PdfContentByte cb = writer.getDirectContent();
        chessPiece.setAppearance(PdfAnnotation.APPEARANCE_NORMAL,
                createChessPieceAppearance(cb, chessPieceName, w, h));
        writer.addAnnotation(chessPiece);
    }

    public PdfAppearance createChessPieceAppearance(
            PdfContentByte cb, String chessPieceName, float w, float h) {
        int doubleMargin = 2 * MARGIN_AROUND_MOST_CHESS_PIECES;
        int doublePawnMargin = 2 * MARGIN_AROUND_PAWNS_AND_ROCKS_SIDES;
        String pieceType = chessPieceName.substring(0, 1);
        PdfAppearance app = cb.createAppearance(w, h);
//        app.setColorFill(color);
        if (pieceType.equals("p") || pieceType.equals("P") || pieceType.equals("r") || pieceType.equals("R")) {
            app.rectangle(MARGIN_AROUND_PAWNS_AND_ROCKS_SIDES, MARGIN_AROUND_MOST_CHESS_PIECES, w - doublePawnMargin, h - doubleMargin);
        } else {
            app.rectangle(MARGIN_AROUND_MOST_CHESS_PIECES,
                    MARGIN_AROUND_MOST_CHESS_PIECES, w -  doubleMargin,
                    h - doubleMargin);
        }
//        app.fill();
        try {
            Image image = Image.getInstance(chessPieceNameToImage(pieceType));
            if (pieceType.equals("p") || pieceType.equals("P") || pieceType.equals("r") || pieceType.equals("R")) {
                image.setAbsolutePosition(MARGIN_AROUND_PAWNS_AND_ROCKS_SIDES, MARGIN_AROUND_MOST_CHESS_PIECES);
            } else {
                image.setAbsolutePosition(MARGIN_AROUND_MOST_CHESS_PIECES, MARGIN_AROUND_MOST_CHESS_PIECES);
            }
            if (pieceType.equals("p") || pieceType.equals("P") || pieceType.equals("r") || pieceType.equals("R")) {
                /* make pawns narrower then other pieces */
                image.scaleAbsolute(BOARD_CELL_SIZE - doublePawnMargin, BOARD_CELL_SIZE - doubleMargin);
            } else {
                image.scaleAbsolute(BOARD_CELL_SIZE - doubleMargin, BOARD_CELL_SIZE - doubleMargin);
            }
            app.addImage(image);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return app;
    }

    private String chessPieceNameToImage(String chessPieceName) {
        return "/mnt/mystorage/notes/focus/programming/YaroslavTraining/PgnToPdf/src/main/resources/chesspieces/"
                + chessPieceName + ".wmf";
    }


    /**
     * Create a pushbutton for a control key
     * @param rect the position of the control key
     */
    public void addMovableCell(Rectangle rect, CMYKColor color, String cellName) {
        float w = rect.getWidth();
        float h = rect.getHeight();
        PdfFormField cell = PdfFormField.createPushButton(writer);
        cell.setFieldName(cellName);
        cell.setWidget(rect, PdfAnnotation.APPEARANCE_NORMAL);
        PdfContentByte cb = writer.getDirectContent();
        cell.setAppearance(PdfAnnotation.APPEARANCE_NORMAL,
                createMovableCellAppearance(cb, w, h, color));
        writer.addAnnotation(cell);
    }

    public PdfAppearance createMovableCellAppearance( PdfContentByte cb,
                                            float w, float h, CMYKColor color) {
        PdfAppearance app = cb.createAppearance(w, h);
        app.setColorFill(color);
        app.rectangle(0, 0, w, h);
        app.fill();
        return app;
    }

}
