package Tools;

import GameObjects.*;
import Handlers.GrabHandler;
import Handlers.ShotHandler;
import Handlers.StepHandler;
import Inventories.DataAccessPoint;
import Players.Colonel;
import Players.Jaffa;
import Players.Replicator;

import java.io.BufferedReader;
import java.io.FileReader;

// Viktor

/**
 * Pályabetöltő osztály
 */
public class LoadField {
    private String[] objs;
    private DataAccessPoint data;

    /**
     * Pályafájl betöltése
     *
     * @param FileName Fájl neve
     * @param Data     referencia az adattárolóra
     */
    public void Load(String FileName, DataAccessPoint Data) // <> helyett ; lesz, igy formatum: FieldObject;x;y
    {
        data = Data;
        try {
            BufferedReader read = new BufferedReader(new FileReader(FileName));
            read.readLine();
            read.readLine();
            String s;
            while ((s = read.readLine()) != null) {
                objs = s.split(";");
                switch (objs[0]) // itt mindenbol uj van letrehozva, ezert ilyen hosszuak
                {
                    case "p":
                        data.Colonel = new Colonel(new ShotHandler(data), new GrabHandler(data),
                                new StepHandler(data, true), Integer.parseInt(objs[2]), Integer.parseInt(objs[1]));
                        break;
                    case "j":
                        data.Jaffa = new Jaffa(new ShotHandler(data), new GrabHandler(data),
                                new StepHandler(data, true), Integer.parseInt(objs[2]), Integer.parseInt(objs[1]));
                        break;
                    case "r":
                        data.Repli = new Replicator(new ShotHandler(data), new GrabHandler(data),
                                new StepHandler(data, false), Integer.parseInt(objs[2]), Integer.parseInt(objs[1]));
                        break;
                    case "a":
                        data.fields.addFieldObject(new Coordinate(Integer.parseInt(objs[2]),
                                Integer.parseInt(objs[1])), new Abyss());
                        break;
                    case "z":
                        data.collectables.addCollectible(new Coordinate(Integer.parseInt(objs[2]),
                                Integer.parseInt(objs[1])), new ZPM());
                        break;
                    case "d":
                        this.addButtonDoor(read.readLine(), s);
                        break;
                    case "wl":
                        data.fields.addFieldObject(new Coordinate(Integer.parseInt(objs[2]),
                                Integer.parseInt(objs[1])), new Wall());
                        break;
                    case "sw":
                        data.fields.addFieldObject(new Coordinate(Integer.parseInt(objs[2]),
                                Integer.parseInt(objs[1])), new SpecialWall());
                        break;
                    case "bx":
                        data.boxes.addBox(new Coordinate(Integer.parseInt(objs[2]),
                                Integer.parseInt(objs[1])), new Box());
                        data.buttons.EventOn(new Coordinate(Integer.parseInt(objs[2]),
                                Integer.parseInt(objs[1])), data.boxes.isThereV2(new Coordinate(Integer.parseInt(objs[2]),
                                Integer.parseInt(objs[1]))));
                        break;
                    case "wy":
                        data.fields.addFieldObject(new Coordinate(Integer.parseInt(objs[2]),
                                Integer.parseInt(objs[1])), new Way());
                        break;
                }
            }
            read.close();
        } catch (Exception ignored) {
        }
    }
    /* Kulon rakom ossze az ajtot, meg a merleget, hogy ne legyen ronda
     * 	viszont a merlegek sulyozasara nem gondoltam, azt meg ki kell kuszobolni*/
    /* Done */

    /**
     * Ajtó / Gomb páros megadása
     *
     * @param s1 Nyomólap adatai
     * @param s2 Ajtó adatai
     */
    private void addButtonDoor(String s1, String s2) {
        objs = s2.split(";");
        Door door = new Door();
        data.fields.addFieldObject(new Coordinate(Integer.parseInt(objs[2]), Integer.parseInt(objs[1])), door);
        objs = s1.split(";");
        data.buttons.addButton(new Coordinate(Integer.parseInt(objs[2]), Integer.parseInt(objs[1])),
                new Button(door, Integer.parseInt(objs[3])));
    }
}
