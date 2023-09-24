import java.awt.*;
import javax.swing.*;

public class SudokuGrid {
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

        JTextField[][] sudokuCells = new JTextField[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudokuCells[row][col] = new JTextField();
                sudokuCells[row][col].setHorizontalAlignment(JTextField.CENTER);
                sudokuCells[row][col].setFont(new Font("Arial", Font.PLAIN, 20));
                sudokuCells[row][col].setPreferredSize(new Dimension(50, 50));
                gridPanel.add(sudokuCells[row][col]);
            }
        }

        frame.getContentPane().add(gridPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
