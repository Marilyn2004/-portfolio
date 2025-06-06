#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define ROWS 6
#define COLS 7
#define EMPTY ' '
#define PLAYER 'X'
#define AI 'O'

void initializeBoard(char board[ROWS][COLS]) {
    for (int r = 0; r < ROWS; r++)
        for (int c = 0; c < COLS; c++)
            board[r][c] = EMPTY;
}

void printBoard(char board[ROWS][COLS]) {
    printf("\n");
    for (int r = 0; r < ROWS; r++) {
        printf("|");
        for (int c = 0; c < COLS; c++) {
            printf(" %c |", board[r][c]);
        }
        printf("\n");
    }
    printf("  0   1   2   3   4   5   6 \n");
}

int dropPiece(char board[ROWS][COLS], int col, char piece) {
    if (col < 0 || col >= COLS)
        return 0;

    for (int r = ROWS - 1; r >= 0; r--) {
        if (board[r][col] == EMPTY) {
            board[r][col] = piece;
            return 1;
        }
    }
    return 0;
}

int isBoardFull(char board[ROWS][COLS]) {
    for (int c = 0; c < COLS; c++)
        if (board[0][c] == EMPTY)
            return 0;
    return 1;
}

int checkWin(char board[ROWS][COLS], char piece) {
    for (int r = 0; r < ROWS; r++)
        for (int c = 0; c <= COLS - 4; c++)
            if (board[r][c] == piece && board[r][c+1] == piece &&
                board[r][c+2] == piece && board[r][c+3] == piece)
                return 1;

    for (int c = 0; c < COLS; c++)
        for (int r = 0; r <= ROWS - 4; r++)
            if (board[r][c] == piece && board[r+1][c] == piece &&
                board[r+2][c] == piece && board[r+3][c] == piece)
                return 1;

    for (int r = 0; r <= ROWS - 4; r++)
        for (int c = 0; c <= COLS - 4; c++)
            if (board[r][c] == piece && board[r+1][c+1] == piece &&
                board[r+2][c+2] == piece && board[r+3][c+3] == piece)
                return 1;

    for (int r = 3; r < ROWS; r++)
        for (int c = 0; c <= COLS - 4; c++)
            if (board[r][c] == piece && board[r-1][c+1] == piece &&
                board[r-2][c+2] == piece && board[r-3][c+3] == piece)
                return 1;

    return 0;
}

int getAIRandomMove(char board[ROWS][COLS]) {
    int col;
    do {
        col = rand() % COLS;
    } while (board[0][col] != EMPTY);
    return col;
}

int main() {
    char board[ROWS][COLS];
    initializeBoard(board);
    srand(time(NULL));

    int isPlayerTurn = 1;
    int column;

    while (1) {
        printBoard(board);

        if (isPlayerTurn) {
            printf("Player (X), choose column (0-6): ");
            scanf("%d", &column);
        } else {
            column = getAIRandomMove(board);
            printf("AI (O) chose column: %d\n", column);
        }

        char piece = isPlayerTurn ? PLAYER : AI;
        if (!dropPiece(board, column, piece)) {
            if (isPlayerTurn) printf("Invalid move. Try again.\n");
            continue;
        }

        if (checkWin(board, piece)) {
            printBoard(board);
            printf("%s wins!\n", isPlayerTurn ? "Player" : "AI");
            break;
        }

        if (isBoardFull(board)) {
            printBoard(board);
            printf("It's a draw!\n");
            break;
        }

        isPlayerTurn = !isPlayerTurn;
    }

    return 0;
}
