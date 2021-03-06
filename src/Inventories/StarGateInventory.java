package Inventories;

import GameObjects.StarGate;
import Tools.CVector;
import Tools.StarGateColor;

import static Tools.Direction.*;
import static Tools.StarGateColor.*;

/**
 * Csillagkapukat tároló osztály
 */
public class StarGateInventory {

    // Különböző színű csillagkapuk
    private StarGate yellow, blue, red, green;

    /**
     * Konstruktor
     */
    public StarGateInventory() {
    }

    /**
     * Adott színű csillagkapu létrehozása adott pozíción
     *
     * @param portalPosition Csillagkapu helye
     * @param color          Csillagkapu színe
     */
    public void Create(CVector portalPosition, StarGateColor color) {
        switch (color) {
            case Yellow:
                Delete(Yellow);
                yellow = new StarGate(EnterPos(portalPosition), color);
                break;
            case Blue:
                Delete(Blue);
                blue = new StarGate(EnterPos(portalPosition), color);
                break;
            case Red:
                Delete(Red);
                red = new StarGate(EnterPos(portalPosition), color);
                break;
            case Green:
                Delete(Green);
                green = new StarGate(EnterPos(portalPosition), color);
                break;
        }
    }

    /**
     * Adott színű csillagkapu törlése
     *
     * @param color A törölni kívánt csillagkapu színe
     */
    private void Delete(StarGateColor color) {
        switch (color) {
            case Yellow:
                yellow = null;
                break;
            case Blue:
                blue = null;
                break;
            case Red:
                red = null;
                break;
            case Green:
                green = null;
                break;
        }
    }

    /**
     * @param pos A player lépésének helye
     * @return A belépés irányának megfelelő irányú pozíció
     * Amikor Belépünk egy csillagkapuba
     * akkor az a lépés irányának
     * ellenkező irányból nyílik.
     */
    private CVector EnterPos(CVector pos) {
        switch (pos.GetDir()) {
            case North:
                return new CVector(pos.GetX(), pos.GetY(), South);
            case South:
                return new CVector(pos.GetX(), pos.GetY(), North);
            case East:
                return new CVector(pos.GetX(), pos.GetY(), West);
            case West:
                return new CVector(pos.GetX(), pos.GetY(), East);
            default:
                return null;
        }
    }

    /**
     * @param from irány
     * @return A megadott koordinátán van-e csillagkapu
     */
    public boolean isThere(CVector from) {
        CVector f = EnterPos(from);
        return yellow != null && yellow.GetPos().equals(f)
                || blue != null && blue.GetPos().equals(f)
                || red != null && red.GetPos().equals(f)
                || green != null && green.GetPos().equals(f);
    }

    /**
     * Getter fgv. adott színű csillagkapura
     *
     * @param from lekérés helye
     * @return csillagkapu referencia
     */
    public StarGate getStarGate(CVector from) {
        if (yellow != null && yellow.GetPos().equals(from)) {
            return yellow;
        } else if (blue != null && blue.GetPos().equals(from)) {
            return blue;
        } else if (red != null && red.GetPos().equals(from)) {
            return red;
        } else if (green != null && green.GetPos().equals(from)) {
            return green;
        } else return null;
    }

    /**
     * @param exitGatePos A féregjárat kijáratának pozíciója
     * @return A féregjárat előtti pozíció koordinátája
     */
    private CVector exitPos(CVector exitGatePos) {
        switch (exitGatePos.GetDir()) {
            case North:
                return new CVector(exitGatePos.GetX(), exitGatePos.GetY() + 1, North);
            case South:
                return new CVector(exitGatePos.GetX(), exitGatePos.GetY() - 1, South);
            case East:
                return new CVector(exitGatePos.GetX() + 1, exitGatePos.GetY(), East);
            case West:
                return new CVector(exitGatePos.GetX() - 1, exitGatePos.GetY(), West);
            default:
                return null;
        }
    }


    /**
     * @param from A belépés iránya
     * @return A féregjárat előtti pozíció
     */
    public CVector stepIn(CVector from) {
        CVector f = EnterPos(from);
        if (yellow != null && yellow.GetPos().equals(f)) {
            if (blue != null) {
                return exitPos(blue.GetPos());
            } else return from;
        } else if (blue != null && blue.GetPos().equals(f)) {
            if (yellow != null) {
                return exitPos(yellow.GetPos());
            } else return from;
        } else if (red != null && red.GetPos().equals(f)) {
            if (green != null) {
                return exitPos(green.GetPos());
            } else return from;
        } else if (green != null && green.GetPos().equals(f)) {
            if (red != null) {
                return exitPos(red.GetPos());
            } else return from;
        } else {
            return from;
        }
    }

}