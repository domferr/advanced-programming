package com.ferrarodomenico.eightpuzzle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The EightBoard class represents a graphical user interface (GUI) for a 
 * game board with nine tiles arranged in a 3x3 grid. It allows users to play 
 * a puzzle game where they need to arrange the tiles in ascending order by 
 * sliding them horizontally or vertically.
 */
public class EightBoard extends javax.swing.JFrame 
        implements PropertyChangeListener {
    
    public static final String RESTART_PROPERTY = "restart_property";
    
    /**
     * Creates a new instance of the EightBoard class.
     * Initializes the GUI components, registers event listeners, and initiates 
     * the game by calling the restart() method.
     */
    public EightBoard() {
        initComponents();
        
        // Register controller as vetoable to all the tiles
        tile1.addVetoableChangeListener(controller);
        tile2.addVetoableChangeListener(controller);
        tile3.addVetoableChangeListener(controller);
        tile4.addVetoableChangeListener(controller);
        tile5.addVetoableChangeListener(controller);
        tile6.addVetoableChangeListener(controller);
        tile7.addVetoableChangeListener(controller);
        tile8.addVetoableChangeListener(controller);
        tile9.addVetoableChangeListener(controller);
        // Register the board as listener for the controller to handle the moves
        controller.addPropertyChangeListener(this);
        
        // Register the tiles as listeners for the restart event        
        this.addPropertyChangeListener(tile1);
        this.addPropertyChangeListener(tile2);
        this.addPropertyChangeListener(tile3);
        this.addPropertyChangeListener(tile4);
        this.addPropertyChangeListener(tile5);
        this.addPropertyChangeListener(tile6);
        this.addPropertyChangeListener(tile7);
        this.addPropertyChangeListener(tile8);
        this.addPropertyChangeListener(tile9);
        // Register the controller as listener for the restart event   
        this.addPropertyChangeListener(controller);
        
        restart(); // Initiate game restart
    }
    
    /**
     * Handles property change events.
     * This method is invoked when a property change event occurs. It checks if 
     * the property change is related to the layout and updates the tile labels 
     * based on the changes received in the event.
     * 
     * @param evt The PropertyChangeEvent object containing information about 
     * the event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        // Check if the property change is related to layout
        if (!propertyName.equals(EightController.LAYOUT_PROPERTY))
            return; 
        
        // handle the layout change. The old and new values are a matrix with one
        // row for each tile that changed its value. A row is a pair with the 
        // tile position followed by the tile label
        int[][] oldTileValue = (int[][]) evt.getOldValue();
        int[][] newTileValue = (int[][]) evt.getNewValue();
        // Ensure old and new values arrays are of equal length
        if (oldTileValue.length != newTileValue.length) {
            throw new IllegalArgumentException("Arrays of old values and new values are not equal");
        }
        
        // handle the change of each tile that changed
        for(int i = 0; i < oldTileValue.length; i++) {
            int[] oldValue = oldTileValue[i];
            int[] newValue = newTileValue[i];
            // Check if the tile position is the same
            if (oldValue[0] != newValue[0]) return;
            
            int tilePos = newValue[0];
            int newLabel = newValue[1];

            EightTile tile = pickTileAtPosition(tilePos);
            if (tile == null) throw new IllegalArgumentException("Invalid tile");
            
            // Set the new label for the tile
            tile.setTileLabel(newLabel);
        }
    }
    
    /**
     * Given a position, returns the object of the tile at that position. Returns
     * null if the position is not valid.
     * @param pos the position of the tile
     * @return the tile at the given position. null if the position is not valid
     */
    private EightTile pickTileAtPosition(int pos) {
        switch (pos) {
            case 1: return tile1;
            case 2: return tile2;
            case 3: return tile3;
            case 4: return tile4;
            case 5: return tile5;
            case 6: return tile6;
            case 7: return tile7;
            case 8: return tile8;
            case 9: return tile9;
        }
        return null;
    }
    
    /**
     * Generates a new layout, which is a random permutation of the numbers 1 to 9 
     * representing the initial arrangement of tiles on the board.
     * 
     * @return An array representing the permutation. The value in position i means 
     * that the tile at position (i+1) has that value.
     */
    private int[] buildRandomLayout() {
        int[] layout = new int[9];
        boolean[] values = new boolean[9];
        for(int i=0; i<layout.length; i++) layout[i] = -1;
        Random rand = new Random();
        
        int size = 0;
        while (size < 9){
            int tileValue = rand.nextInt(1, 10);
            // Ensure the tile value is unique
            if (!values[tileValue - 1]) {
                layout[size] = tileValue;
                values[tileValue - 1] = true;
                size++;
            }
        }
        
        return layout;
    }
    
    /**
     * Restarts the game by generating a new layout and firing a property change 
     * event to notify listeners (tiles and controller) about the restart.
     */
    private void restart() {
        // Generate a new layout
        int[] layout = buildRandomLayout();
        // Fire a property change event to notify listeners about the restart
        this.firePropertyChange(RESTART_PROPERTY, null, layout);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        board = new javax.swing.JPanel();
        tile1 = new EightTile(1);
        tile2 = new EightTile(2);
        tile3 = new EightTile(3);
        tile4 = new EightTile(4);
        tile5 = new EightTile(5);
        tile6 = new EightTile(6);
        tile7 = new EightTile(7);
        tile8 = new EightTile(8);
        tile9 = new EightTile(9);
        buttonGroup = new javax.swing.JPanel();
        restartButton = new javax.swing.JButton();
        controller = new com.ferrarodomenico.eightpuzzle.EightController();
        flipButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("8 Puzzle");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(500, 600));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        board.setBackground(new java.awt.Color(255, 255, 255));
        board.setPreferredSize(new java.awt.Dimension(500, 0));
        board.setLayout(new java.awt.GridLayout(3, 3, 2, 2));

        tile1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        tile1.setForeground(new java.awt.Color(255, 255, 255));
        tile1.setFocusPainted(false);
        tile1.setFont(tile1.getFont().deriveFont(tile1.getFont().getStyle() | java.awt.Font.BOLD, 48));
        tile1.setMaximumSize(new java.awt.Dimension(40, 40));
        tile1.setMinimumSize(new java.awt.Dimension(40, 40));
        tile1.setPreferredSize(new java.awt.Dimension(40, 40));
        board.add(tile1);

        tile2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        tile2.setForeground(new java.awt.Color(255, 255, 255));
        tile2.setFocusPainted(false);
        tile2.setFont(tile2.getFont().deriveFont(tile2.getFont().getStyle() | java.awt.Font.BOLD, 48));
        tile2.setMaximumSize(new java.awt.Dimension(40, 40));
        tile2.setMinimumSize(new java.awt.Dimension(40, 40));
        tile2.setPreferredSize(new java.awt.Dimension(40, 40));
        board.add(tile2);

        tile3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        tile3.setForeground(new java.awt.Color(255, 255, 255));
        tile3.setFocusPainted(false);
        tile3.setFont(tile3.getFont().deriveFont(tile3.getFont().getStyle() | java.awt.Font.BOLD, 48));
        tile3.setMaximumSize(new java.awt.Dimension(40, 40));
        tile3.setMinimumSize(new java.awt.Dimension(40, 40));
        tile3.setPreferredSize(new java.awt.Dimension(40, 40));
        board.add(tile3);

        tile4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        tile4.setForeground(new java.awt.Color(255, 255, 255));
        tile4.setFocusPainted(false);
        tile4.setFont(tile4.getFont().deriveFont(tile4.getFont().getStyle() | java.awt.Font.BOLD, 48));
        tile4.setMaximumSize(new java.awt.Dimension(40, 40));
        tile4.setMinimumSize(new java.awt.Dimension(40, 40));
        tile4.setPreferredSize(new java.awt.Dimension(40, 40));
        board.add(tile4);

        tile5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        tile5.setForeground(new java.awt.Color(255, 255, 255));
        tile5.setFocusPainted(false);
        tile5.setFont(tile5.getFont().deriveFont(tile5.getFont().getStyle() | java.awt.Font.BOLD, 48));
        tile5.setMaximumSize(new java.awt.Dimension(40, 40));
        tile5.setMinimumSize(new java.awt.Dimension(40, 40));
        tile5.setPreferredSize(new java.awt.Dimension(40, 40));
        board.add(tile5);

        tile6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        tile6.setForeground(new java.awt.Color(255, 255, 255));
        tile6.setFocusPainted(false);
        tile6.setFont(tile6.getFont().deriveFont(tile6.getFont().getStyle() | java.awt.Font.BOLD, 48));
        tile6.setMaximumSize(new java.awt.Dimension(40, 40));
        tile6.setMinimumSize(new java.awt.Dimension(40, 40));
        tile6.setPreferredSize(new java.awt.Dimension(40, 40));
        board.add(tile6);

        tile7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        tile7.setForeground(new java.awt.Color(255, 255, 255));
        tile7.setFocusPainted(false);
        tile7.setFont(tile7.getFont().deriveFont(tile7.getFont().getStyle() | java.awt.Font.BOLD, 48));
        tile7.setMaximumSize(new java.awt.Dimension(40, 40));
        tile7.setMinimumSize(new java.awt.Dimension(40, 40));
        tile7.setPreferredSize(new java.awt.Dimension(40, 40));
        board.add(tile7);

        tile8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        tile8.setForeground(new java.awt.Color(255, 255, 255));
        tile8.setFocusPainted(false);
        tile8.setFont(tile8.getFont().deriveFont(tile8.getFont().getStyle() | java.awt.Font.BOLD, 48));
        tile8.setMaximumSize(new java.awt.Dimension(40, 40));
        tile8.setMinimumSize(new java.awt.Dimension(40, 40));
        tile8.setPreferredSize(new java.awt.Dimension(40, 40));
        board.add(tile8);

        tile9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        tile9.setForeground(new java.awt.Color(255, 255, 255));
        tile9.setFocusPainted(false);
        tile9.setFont(tile9.getFont().deriveFont(tile9.getFont().getStyle() | java.awt.Font.BOLD, 48));
        tile9.setMaximumSize(new java.awt.Dimension(40, 40));
        tile9.setMinimumSize(new java.awt.Dimension(40, 40));
        tile9.setPreferredSize(new java.awt.Dimension(40, 40));
        board.add(tile9);

        buttonGroup.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup.setLayout(new java.awt.GridLayout(1, 0));

        restartButton.setBackground(new java.awt.Color(235, 235, 235));
        restartButton.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N
        restartButton.setText("RESTART");
        restartButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        restartButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                restartButtonMouseClicked(evt);
            }
        });
        buttonGroup.add(restartButton);

        controller.setBackground(new java.awt.Color(255, 255, 255));
        controller.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        controller.setText("START");
        controller.setFont(controller.getFont().deriveFont(controller.getFont().getStyle() | java.awt.Font.BOLD));
        controller.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonGroup.add(controller);

        flipButton.setBackground(new java.awt.Color(235, 235, 235));
        flipButton.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N
        flipButton.setText("FLIP");
        flipButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        flipButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                flipButtonMouseClicked(evt);
            }
        });
        buttonGroup.add(flipButton);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(board, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(buttonGroup, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(board, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addContainerGap(78, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(524, Short.MAX_VALUE)
                    .addComponent(buttonGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(15, 15, 15)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void restartButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_restartButtonMouseClicked
        restart();
    }//GEN-LAST:event_restartButtonMouseClicked

    private void flipButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_flipButtonMouseClicked
        try {
            controller.flip();
        } catch (Exception ex) {
            Logger.getLogger(EightBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_flipButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EightBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EightBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EightBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EightBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EightBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel board;
    private javax.swing.JPanel buttonGroup;
    private com.ferrarodomenico.eightpuzzle.EightController controller;
    private javax.swing.JButton flipButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton restartButton;
    private com.ferrarodomenico.eightpuzzle.EightTile tile1;
    private com.ferrarodomenico.eightpuzzle.EightTile tile2;
    private com.ferrarodomenico.eightpuzzle.EightTile tile3;
    private com.ferrarodomenico.eightpuzzle.EightTile tile4;
    private com.ferrarodomenico.eightpuzzle.EightTile tile5;
    private com.ferrarodomenico.eightpuzzle.EightTile tile6;
    private com.ferrarodomenico.eightpuzzle.EightTile tile7;
    private com.ferrarodomenico.eightpuzzle.EightTile tile8;
    private com.ferrarodomenico.eightpuzzle.EightTile tile9;
    // End of variables declaration//GEN-END:variables
}
