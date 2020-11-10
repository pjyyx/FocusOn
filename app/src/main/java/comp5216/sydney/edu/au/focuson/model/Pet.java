package comp5216.sydney.edu.au.focuson.model;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Pet POJO. A pet can have multiple equipments.
 */
public class Pet {
    private String name;
    private ArrayList<Equipment> equipment;
    private long power;
    private String image;

    /**
     * Instantiates a new Pet.
     *
     * @param name      the name
     * @param equipment the equipment
     * @param power     the power
     * @param image     the image
     */
    public Pet(String name, ArrayList<Equipment> equipment, long power, String image) {
        this.name = name;
        this.equipment = equipment;
        this.power = power;
        this.image = image;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets equipment.
     *
     * @return the equipment
     */
    public ArrayList<Equipment> getEquipment() {
        return equipment;
    }

    /**
     * Sets equipment.
     *
     * @param equipment the equipment
     */
    public void setEquipment(ArrayList<Equipment> equipment) {
        this.equipment = equipment;
    }

    /**
     * Gets power.
     *
     * @return the power
     */
    public long getPower() {
        return power;
    }

    /**
     * Sets power.
     *
     * @param power the power
     */
    public void setPower(long power) {
        this.power = power;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(String image) {
        this.image = image;
    }
}
