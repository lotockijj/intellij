package com.dominyuk.pgn2pdf;

import com.itextpdf.text.pdf.CMYKColor;

/**
 * Created by roman on 12.01.17.
 */
public class PdfConfig {
//    public String inputFilePath = "/mnt/mystorage/notes/focus/programming/YaroslavTraining/PgnToPdf/etc/pgn_files/Akobian.pgn";
//    public String inputFilePath = "/mnt/mystorage/notes/focus/programming/YaroslavTraining/PgnToPdf/etc/pgn_files/Ivanchuk.pgn";
    public String inputFilePath = "/mnt/mystorage/notes/focus/programming/YaroslavTraining/PgnToPdf/etc/pgn_files/1game.pgn";
    public String resultingDirectoryPath = "/mnt/mystorage/notes/focus/programming/YaroslavTraining/PgnToPdf/etc/";
    public String resultingFileName = "chess_game";
    public String fontFilePath = "/mnt/mystorage/notes/focus/programming/YaroslavTraining/PgnToPdf/src/main/resources/ufonts.com_arial-unicode-ms.ttf";
    // fixme: is used during creation - should be deleted
    public String resultingFilePath = resultingDirectoryPath + resultingFileName + ".pdf";
    // If png file has many games inside - one or many files can be generated
    // Pattern for multiple files is resultingFileName_000.pdf, where 000 is a game number
    public boolean generateMultipleFiles = false;
    // All dimensions indicated in user units: 1 in. = 25.4 mm = 72 user units
    public static final int DOCUMENT_WIDTH = 780;
    public static final int DOCUMENT_HEIGHT = 600;
    // Info about fonts: fixme
    public static final int TITLE_FONT_SIZE = 16;
    public static final int PARAGRAPH_FONT_SIZE = 16;
    public static final int BOARD_INSCRIPTION_FONT_SIZE = 12;
    // Colors of chess board cells: fixme
    public static final CMYKColor WHITE_CELLS_COLOR = new CMYKColor(0f, 0.10f, 0.26f, 0.07f);
    public static final CMYKColor BLACK_CELLS_COLOR = new CMYKColor(0f, 0.29f, 0.5f, 0.48f);
    public static final CMYKColor FROM_CELL_COLOR = new CMYKColor(0.33f, 0.33f, 0f, 0f);
    public static final CMYKColor TO_CELL_COLOR = new CMYKColor(0.33f, 0.49f, 0f, 0f);
    public static final int BUTTONS_COLOR = 26;

    // Board size
    public static final int BOARD_CELL_SIZE = 36;
    public static final int BOARD_LETTERS_FRAME_WIDTH = 13;
    public static final int STASH_ELEMENTS_QUANTITY = 49;
    // margins to place board inside the pdf file:
    public static final int PART_OF_BOARD_AND_BUTTONS_LEFT_MARGIN = 18;
    public static final int BOARD_AND_BUTTONS_LEFT_MARGIN
            = PART_OF_BOARD_AND_BUTTONS_LEFT_MARGIN + BOARD_CELL_SIZE
            - BOARD_LETTERS_FRAME_WIDTH;
    public static final int BOARD_AND_BUTTONS_BOTTOM_MARGIN = 54;
    // to draw chess pieces properly:
    public static final int MARGIN_AROUND_MOST_CHESS_PIECES = 4;
    public static final int MARGIN_AROUND_PAWNS_AND_ROCKS_SIDES = 8;
    // to calculate movements inside JS code:
    public static final int BOARD_X0 = BOARD_CELL_SIZE + PART_OF_BOARD_AND_BUTTONS_LEFT_MARGIN;
    public static final int BOARD_Y0 = BOARD_CELL_SIZE * 2 + BOARD_AND_BUTTONS_BOTTOM_MARGIN;
}