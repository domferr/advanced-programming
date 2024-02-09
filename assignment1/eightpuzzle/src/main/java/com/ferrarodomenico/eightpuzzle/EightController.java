package com.ferrarodomenico.eightpuzzle;

import java.beans.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;

/**
 * The EightController class serves as the controller for managing the game logic 
 * of the 8 Puzzle game.
 */
public class EightController extends JLabel implements Serializable, 
        VetoableChangeListener, PropertyChangeListener {
    // The property name for layout change events
    public static final String LAYOUT_PROPERTY = "layout_property";
    
    // Map each tile value to its position
    private final Map<Integer, Integer> tileValueToPosition;
    private final int LAYOUT_SIZE = 3;
    
    /** 
     * Constructs a new instance of EightController.
     * Initializes the tileValueToPosition map with tile values and their initial positions.
     */
    public EightController() {
        this.tileValueToPosition = new HashMap<>();
        for(int i=1; i<=LAYOUT_SIZE*LAYOUT_SIZE; i++) tileValueToPosition.put(i, i);
    }
    
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        if (!evt.getPropertyName().equals(EightTile.TILE_LABEL_PROPERTY)) return;
        this.onTileValueChange(evt);
    }
    
    private int[] getXYCoordsOfTile(int tileValue) {
        int pos = tileValueToPosition.get(tileValue) - 1;
        return new int[]{ pos / LAYOUT_SIZE, pos % LAYOUT_SIZE };
    }
    
    /**
     * Handles the change of tile value on the board.
     * Throws PropertyVetoException if the move is not valid.
     * 
     * @param evt The PropertyChangeEvent object containing information about the event.
     * @throws PropertyVetoException if the move is not valid.
     */
    private void onTileValueChange(PropertyChangeEvent evt) throws PropertyVetoException {
        int newHoleTileValue = (int) evt.getOldValue();
        int[] tilePos = getXYCoordsOfTile(newHoleTileValue);
        int[] holePos = getXYCoordsOfTile(EightTile.HOLE_TILE_VALUE);
        
        int manhattanDistance = Math.abs(tilePos[0] - holePos[0]) + Math.abs(tilePos[1] - holePos[1]);
        
        boolean isValidMove = manhattanDistance == 1;
        if (!isValidMove) {
            this.setText("KO");
            throw new PropertyVetoException(
                String.format("Tile at coords (%d, %d): not a valid move, distance from hole (%d, %d) is %d", tilePos[0], tilePos[1], tilePos[0], holePos[0], manhattanDistance),
                evt
            );
        }
        
        // the move is valid
        this.setText("OK");
        
        // update the tileValueToPosition
        int oldHolePos = this.tileValueToPosition.get(EightTile.HOLE_TILE_VALUE);
        int newHolePos = this.tileValueToPosition.get(newHoleTileValue);
        this.tileValueToPosition.put(EightTile.HOLE_TILE_VALUE, newHolePos);
        this.tileValueToPosition.put(newHoleTileValue, oldHolePos);
        
        // notify the changes made on the tileValueToPosition
        int[][] oldTilesValue = {{oldHolePos, EightTile.HOLE_TILE_VALUE}};
        int[][] newTilesValue = {{oldHolePos, newHoleTileValue}};
        this.firePropertyChange(LAYOUT_PROPERTY, oldTilesValue, newTilesValue);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (!propertyName.equals(EightBoard.RESTART_PROPERTY)) return;
        
        int[] newLayout = (int[]) evt.getNewValue();
        onRestart(newLayout);
    }
    
    private void onRestart(int[] newLayout) {
        tileValueToPosition.clear();
        for(int pos = 0; pos < newLayout.length; pos++) {
            tileValueToPosition.put(newLayout[pos], pos+1);
        }
    }
    
    /**
     * Flips the tiles if the hole is in position 9.
     * 
     * @throws Exception if the first or second tile values cannot be found.
     */
    public void flip() throws Exception {
        //if the hole is NOT in position 9, the flip has no effect
        if (tileValueToPosition.get(EightTile.HOLE_TILE_VALUE) != 9) return;
        
        int firstTileValue = -1, secondTileValue = -1;
        for(Map.Entry<Integer, Integer> entry: tileValueToPosition.entrySet()) {
            if (entry.getValue() == 1) {
                firstTileValue = entry.getKey();
                break;
            }
        }
        for(Map.Entry<Integer, Integer> entry: tileValueToPosition.entrySet()) {
            if (entry.getValue() == 2) {
                secondTileValue = entry.getKey();
                break;
            }
        }
        
        if (firstTileValue == -1 || secondTileValue == -1) {
            throw new Exception("Can't find first or second tile values");
        }
        
        this.tileValueToPosition.put(firstTileValue, 2);
        this.tileValueToPosition.put(secondTileValue, 1);
        
        // notify the changes made on the tileValueToPosition
        int[][] oldTilesValue = {
            {1, firstTileValue},
            {2, secondTileValue}
        };
        int[][] newTilesValue = {
            {1, secondTileValue},
            {2, firstTileValue}
        };
        this.firePropertyChange(LAYOUT_PROPERTY, oldTilesValue, newTilesValue);
    }
}
