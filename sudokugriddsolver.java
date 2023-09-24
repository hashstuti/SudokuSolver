import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class sudokugriddsolver {
    private static JTextField[][] sudokuCells;
    private static int[][] board;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Sudoku Grid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        sudokuCells = new JTextField[9][9];
        board = new int[9][9];

        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        Border compoundBorder;

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudokuCells[row][col] = new JTextField();
                sudokuCells[row][col].setHorizontalAlignment(JTextField.CENTER);
                sudokuCells[row][col].setFont(new Font("Arial", Font.PLAIN, 20));
                sudokuCells[row][col].setPreferredSize(new Dimension(50, 50));

                // Create a compound border to add inner and outer borders
                compoundBorder = BorderFactory.createCompoundBorder(lineBorder, lineBorder);
                sudokuCells[row][col].setBorder(compoundBorder);

                gridPanel.add(sudokuCells[row][col]);
            }
        }

        // Add thicker borders to highlight the 3x3 grids
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j ++) {
                

                Border thickBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE);

                        compoundBorder = BorderFactory.createCompoundBorder(thickBorder, lineBorder);
                        sudokuCells[i][j].setBorder(compoundBorder);
                
            }
        }
        for (int i = 0; i < 3; i ++) {
            for (int j = 6; j < 9; j ++) {
                

                Border thickBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GREEN);

                        compoundBorder = BorderFactory.createCompoundBorder(thickBorder, lineBorder);
                        sudokuCells[i][j].setBorder(compoundBorder);
                
            }
        }
        for (int i = 3; i < 6; i ++) {
            for (int j = 3; j < 6; j ++) {
                

                Border thickBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.PINK);

                        compoundBorder = BorderFactory.createCompoundBorder(thickBorder, lineBorder);
                        sudokuCells[i][j].setBorder(compoundBorder);
                
            }
        }
        for (int i = 6; i < 9; i ++) {
            for (int j = 6; j < 9; j ++) {
                

                Border thickBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.CYAN);

                        compoundBorder = BorderFactory.createCompoundBorder(thickBorder, lineBorder);
                        sudokuCells[i][j].setBorder(compoundBorder);
                
            }
        }
        for (int i = 6; i < 9; i ++) {
            for (int j = 0; j < 3; j ++) {
                

                Border thickBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY);

                        compoundBorder = BorderFactory.createCompoundBorder(thickBorder, lineBorder);
                        sudokuCells[i][j].setBorder(compoundBorder);
                
            }
        }
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveBoard();
                if (solveSudoku()) {
                    updateGrid();
                } else {
                    JOptionPane.showMessageDialog(frame, "No solution found!", "Sudoku Solver", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solveButton);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(gridPanel, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void saveBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String cellValue = sudokuCells[row][col].getText();
                if (cellValue.isEmpty()) {
                    board[row][col] = 0;
                } else {
                    board[row][col] = Integer.parseInt(cellValue);
                }
            }
        }
    }

    private static boolean solveSudoku() {
        return solve(0, 0);
    }

    private static boolean solve(int row, int col) {
        if (col == 9) {
            col = 0;
            row++;
            if (row == 9) {
                return true; // All cells have been filled (base case)
            }
        }

        if (board[row][col] != 0) {
            return solve(row, col + 1); // Cell is already filled, move to next column
        }

        for (int num = 1; num <= 9; num++) {
            if (isValidPlacement(row, col, num)) {
                board[row][col] = num; // Place the number in the cell

                if (solve(row, col + 1)) {
                    return true; // Move to next column
                }

                board[row][col] = 0; // Undo the placement if it leads to an invalid solution
            }
        }

        return false; // No valid number can be placed, backtrack
    }

    private static boolean isValidPlacement(int row, int col, int num) {
        // Check if the number is already present in the row
        for (int c = 0; c < 9; c++) {
            if (board[row][c] == num) {
                return false;
            }
        }

        // Check if the number is already present in the column
        for (int r = 0; r < 9; r++) {
            if (board[r][col] == num) {
                return false;
            }
        }

        // Check if the number is already present in the 3x3 grid
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (board[r][c] == num) {
                    return false;
                }
            }
        }

        return true; // Valid placement
    }

    private static void updateGrid() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudokuCells[row][col].setText(String.valueOf(board[row][col]));
            }
        }
    }
}
