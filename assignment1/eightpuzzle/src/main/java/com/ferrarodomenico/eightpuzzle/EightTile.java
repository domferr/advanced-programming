package com.ferrarodomenico.eightpuzzle;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.beans.*;
import java.io.Serializable;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.plaf.metal.MetalButtonUI;

/**
 * The EightTile class represents a tile component used in the 8 Puzzle game.
 */
public class EightTile extends JButton implements Serializable, PropertyChangeListener {
    // The property name for tile label change events
    public static final String TILE_LABEL_PROPERTY = "tile_value_prop";
    // The value representing the hole tile
    public static final int HOLE_TILE_VALUE = 9;
    
    // Color constants representing transparency, green, red and yellow
    private static final Color COLOR_TRANSPARENT = Color.getColor("transparent");
    private static final Color COLOR_GREEN = Color.decode("#8AC926");
    private static final Color COLOR_RED = Color.decode("#F42B03");
    private static final Color COLOR_YELLOW = Color.decode("#E89005");
    
    private final int position;
    private int tileLabel;
    private final Timer backgroundFlashTimer;
    
    /** 
     * Constructs a new instance of EightTile with an invalid position.
     */
    public EightTile() {
        this(-1);
    }
    
    /** 
     * Constructs a new instance of EightTile with the specified position.
     * 
     * @param pos The position of the tile.
     */
    public EightTile(int pos) {
        this.position = pos;
        this.tileLabel = pos;
        this.addActionListener((ActionEvent e) -> onClick());
        this.setUI(new MetalButtonUI() {
            @Override
            protected Color getDisabledTextColor() {
                return Color.WHITE;
            }
        });
        refreshTileUI();
        this.backgroundFlashTimer = new Timer(500, e -> this.refreshTileUI());
    }
    
    /**
     * Refreshes the UI of the tile based on its label and position.
     */
    private void refreshTileUI() {
        Color backgroundColor;
        String text = "";
        switch(this.tileLabel) {
            case -1:
                backgroundColor = this.position == 9 ? COLOR_TRANSPARENT:COLOR_GREEN;
                text = this.position == 9 ? "":String.valueOf(this.position);
                break;
            case EightTile.HOLE_TILE_VALUE:
                backgroundColor = COLOR_TRANSPARENT;
                break;
            default:
                backgroundColor = this.tileLabel == this.position ? COLOR_GREEN:COLOR_YELLOW;
                text = String.valueOf(this.tileLabel);
        }
        
        this.setBackground(backgroundColor);
        this.setText(text);
        boolean isEnabled = this.tileLabel >= 0 && this.tileLabel < 9;
        this.setEnabled(isEnabled);
        this.setCursor(new Cursor(isEnabled ? Cursor.HAND_CURSOR:Cursor.DEFAULT_CURSOR));
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (!propertyName.equals(EightBoard.RESTART_PROPERTY))
            return;
        
        int[] newLayout = (int[]) evt.getNewValue();
        if (this.position - 1 < newLayout.length) {
            this.setTileLabel(newLayout[position-1]);
        }
    }

    /**
     * Returns the label value of the tile.
     * 
     * @return The label value of the tile.
     */
    public int getTileLabel() {
        return tileLabel;
    }

    /**
     * Sets the label value of the tile.
     * 
     * @param label The new label value to set.
     */
    public void setTileLabel(int label) {
        if (this.tileLabel == label) return;
        
        this.tileLabel = label;
        this.refreshTileUI();
    }
    
    /**
     * Handles the action event when the tile is clicked.
     */
    private void onClick() {
        if (this.tileLabel == -1) return;
        
        int oldValue = this.tileLabel;
        try{
            super.fireVetoableChange(EightTile.TILE_LABEL_PROPERTY, oldValue, EightTile.HOLE_TILE_VALUE);
            
            // update tile value since there is no veto
            this.setTileLabel(EightTile.HOLE_TILE_VALUE);
        } catch(PropertyVetoException e) {
            this.onVeto();
        }
    }
    
    /**
     * Handles the behavior when the veto is triggered.
     */
    private void onVeto() {
        this.setBackground(COLOR_RED);
        this.backgroundFlashTimer.restart();
    }
}
