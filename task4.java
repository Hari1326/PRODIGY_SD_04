import javax.swing.*;
import java.awt.*;

public class task4 extends JFrame {
    private JTextField[][] cells = new JTextField[9][9];
    private JButton solveButton, clearButton;

    public task4() {
        setTitle("Sudoku Solver");
        setSize(500, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        Font font = new Font("Arial", Font.BOLD, 20);

        // Create 9x9 Sudoku grid
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(font);
                gridPanel.add(cells[row][col]);
            }
        }

        JPanel buttonPanel = new JPanel();
        solveButton = new JButton("Solve");
        clearButton = new JButton("Clear");

        solveButton.addActionListener(e -> solveSudoku());
        clearButton.addActionListener(e -> clearGrid());

        buttonPanel.add(solveButton);
        buttonPanel.add(clearButton);

        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Backtracking Sudoku Solver
    private boolean solve(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solve(board)) return true;
                            board[row][col] = 0; // backtrack
                        }
                    }
                    return false; // no valid number found
                }
            }
        }
        return true; // solved
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        // Check row and column
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num) return false;
        }

        // Check 3x3 box
        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == num) return false;
            }
        }
        return true;
    }

    private void solveSudoku() {
        int[][] board = new int[9][9];

        // Read from text fields
        try {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    String text = cells[row][col].getText().trim();
                    if (!text.isEmpty()) {
                        board[row][col] = Integer.parseInt(text);
                    } else {
                        board[row][col] = 0;
                    }
                }
            }

            if (solve(board)) {
                // Fill solution back into grid
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        cells[row][col].setText(String.valueOf(board[row][col]));
                    }
                }
                JOptionPane.showMessageDialog(this, "Sudoku Solved!");
            } else {
                JOptionPane.showMessageDialog(this, "No solution exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter only numbers (1-9).", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearGrid() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col].setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new task4().setVisible(true));
    }
}
