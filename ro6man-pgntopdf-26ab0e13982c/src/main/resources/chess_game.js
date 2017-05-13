var document = this;
var currentPly = -1;   // represent current board state according to boardPositions
var DEFAULT_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
var positions = [ /* represents game flaw */
<#list positions as position>
  "${position}"<#if position_has_next>,</#if>
</#list>
];
/* is used to highlight starting and end position of the last moove */
var moves = [
<#list moves as move>
  "${move}"<#if move_has_next>,</#if>
</#list>
];
var plyQuantity = moves.length;
var movesQuantity = moves.length;
var BOARD_X0 = ${BOARD_X0};
var BOARD_Y0 = ${BOARD_Y0};
var cellSize = ${cellSize};
var disp = false;
var stashRect = [0, 0, cellSize, cellSize];

var CELL_NAMES = ["a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
  "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
  "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
  "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
  "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
  "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
  "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
  "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
  ];
// pieces on the board and in the stash
positionLayerChessPieces = ["K1", "N1", "B1", "R1", "Q1", "N2", "B2", "R2",
  "Q2", "P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8", "k1", "n1", "b1", "r1",
  "q1", "n2", "b2", "r2", "q2", "p1", "p2", "p3", "p4", "p5", "p6", "p7", "p8"];
moveLayerChessPieces = ["P", "N", "B", "R", "Q", "K", "p", "n", "b", "r", "q",
  "k"];

function first() {
// from DEFAULT_POSITION we are makin 0th move to gain 0th piecesPositions
  currentPly = -1;
  placePiecesInStash();
  showPieces();
}

function backward() {
  if (currentPly >= 0) {
    currentPly--;
    placePiecesInStash();
    showMove();
    showPieces();
  }
}

function forward() {
  if (currentPly < plyQuantity - 1) {
    currentPly++;
    placePiecesInStash();
    showMove();
    showPieces();
  }
}

function last() {
  currentPly = plyQuantity - 1;
  placePiecesInStash();
  showMove();
  showPieces();
}


function stop() {
}

function play() {
}

function showPieces() {
  var position;
  if (currentPly == -1) {
    // show default position
    position = DEFAULT_POSITION;
  } else {
    position = positions[currentPly];
  }

  var pointer = 0;
  for (var cellNum = 0; cellNum < CELL_NAMES.length; ) {
    var piece = position.charAt(pointer++);
    if (piece === "/") {
      continue;
    } else if (is_digit(piece)) { //todo change with correct function isNaN()
      cellNum += parseInt(piece, 10);
    } else {
      placePieceOnBoard(piece, cellNum++);
    }
  }
}

function placePieceOnBoard(piece, cellNum) {
  var cellRectangle = getCellRectangle(CELL_NAMES[cellNum]);
// fixme
// possible way to solve:
// - place all pieces in default positon
// - check if it is not in default positon - take another one
// - should have list of all pieces sent to JS file
  var i = 1;
  while (true) {
    var possibleChessPiece = document.getField(piece + i);
    if (possibleChessPiece.rect[0] == 0) {
      possibleChessPiece.rect = cellRectangle;
      break;
    }
    i++;
  }
}

function placePiecesInStash() {
  for (var i = 0; i < positionLayerChessPieces.length; i++) {
    var chessPiece = document.getField(positionLayerChessPieces[i]);
    chessPiece.rect = stashRect;
  }
  for (var i = 0; i < moveLayerChessPieces.length; i++) {
    var chessPiece = document.getField(moveLayerChessPieces[i]);
    chessPiece.rect = stashRect;
  }
  var chessPiece = document.getField("fromCell");
  chessPiece.rect = stashRect;
  chessPiece = document.getField("toCell");
  chessPiece.rect = stashRect;
  chessPiece = document.getField("whiteCell");
  chessPiece.rect = stashRect;
}

function is_digit(c) {
  return "0123456789".indexOf(c) !== -1;
}

// takes "a1" and returns corresponding rectangle
function getCellRectangle(cell) {
// todo q: do we need to check if cell is String?
  var file = cell.charAt(0); //a-h
//  console.println(file);
  var rank = cell.charAt(1); //1-8
//  console.println(rank);
  var RANK = "12345678";
  var rankNumber = RANK.indexOf(rank);
//  console.println(rankNumber);
  var FILE = "abcdefgh";
  var fileNumber = FILE.indexOf(file);
//  console.println(fileNumber);
  var llx = BOARD_X0 + fileNumber * cellSize;
  var lly = BOARD_Y0 + rankNumber * cellSize;
  var urx = BOARD_X0 + fileNumber * cellSize + cellSize;
  var ury = BOARD_Y0 + rankNumber * cellSize + cellSize;
  return [llx, lly, urx, ury];
}



function showMove() {
  if (currentPly >= 0) {
    var isWhiteMove = currentPly % 2 == 0;
    var lanMove = moves[currentPly];
    var firstChar = lanMove.charAt(0);
    var pieceType;
    //if first letter is small, then chess piece is pawn
    if (firstChar < "a") {
      if (firstChar == "O") {
        makeCastling(isWhiteMove, lanMove);
        return; //todo q: is it correct?
      } else {
      pieceType = (isWhiteMove)? firstChar : firstChar.toLowerCase();
      var fromCellId = lanMove.substring(1, 3);
      var toCellId = lanMove.substring(4, 6);
      animate(fromCellId, toCellId, pieceType);
      }
    } else {
      pieceType = (isWhiteMove)? "P" : "p";
      // can just move forward
      var fromCellId = lanMove.substring(0, 2);
      var toCellId = lanMove.substring(3, 5);
      animate(fromCellId, toCellId, pieceType);
      // can take another piece
    }
  }
}

function makeCastling(isWhiteMove, lanMove) {
  var pieceType;
  if (isWhiteMove) {
    pieceType = "K";
    if (lanMove.length == 3) {
      animate("e1", "g1", pieceType);
    } else {
      animate("e1", "c1", pieceType);
    }
  } else {
    pieceType = "k";
    if (lanMove.length == 3) {
      animate("e8", "g8", pieceType);
    } else {
      animate("e8", "c8", pieceType);
    }
  }
}

var m;
var counter = 0;
var verticalOffset;
var horizontalOffset;

function animate(fromCellId, toCellId, pieceType) {
//traverse through all board pieces
//compare bp rectangle with toCellRect
//turn the bp off
//wait to the end of move
//turn the bp on
  highlight(fromCellId, toCellId);
  var movablePiece = document.getField(pieceType);
  var fromCellRect = getCellRectangle(fromCellId);
  var toCellRect = getCellRectangle(toCellId);
  verticalOffset = (fromCellRect[1] - toCellRect[1]) / 10;
console.println(fromCellRect[1]);
console.println(toCellRect[1]);
//console.println(verticalOffset);
  horizontalOffset = (fromCellRect[0] - toCellRect[0]) / 10;
console.println(fromCellRect[0]);
console.println(toCellRect[0]);
//console.println(horizontalOffset);
  movablePiece.rect = fromCellRect;
  var moveCommand = "moveIt('" + pieceType + "')";
  m = app.setInterval(moveCommand, 1);
}

function moveIt(pieceType) {
  //todo: check if I will delete the row below
  document.getField("moveLayer");
  var movablePiece = document.getField(pieceType);
  if (counter < 10) {
    counter++;
      var moveRect = movablePiece.rect ;
      var offset = 3;
      var newRect = [moveRect[0] - horizontalOffset, moveRect[1] - verticalOffset, moveRect[2] - horizontalOffset, moveRect[3] - verticalOffset] ;
      movablePiece.rect = newRect ;
  } else {
    counter = 0;
    app.clearInterval(m);
  }
}

function highlight(fromCellId, toCellId) {
  var chessPiece = document.getField("fromCell");
  chessPiece.rect = getCellRectangle(fromCellId);
  chessPiece = document.getField("toCell");
  chessPiece.rect = getCellRectangle(toCellId);
}

// code to be executed before start
placePiecesInStash();
showPieces();