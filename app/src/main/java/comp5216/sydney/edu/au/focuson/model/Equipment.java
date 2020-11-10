package comp5216.sydney.edu.au.focuson.model;

import android.net.Uri;

/**
 * Equipment POJO.
 */
public class Equipment {
    private String name;
    private int basePower;
    /**
     * Level of the equipment.
     * 1 for wood,
     * 2 for iron,
     * 3 for gold,
     * 4 for diamond.
     */
    private int level;
    /**
     * Type of the equipment.
     * 1 for hat,
     * 2 for pants,
     * 3 for jacket,
     * 4 for weapon.
     */
    private int type;
    private String image;
    private boolean isCheck;

    /**
     * Instantiates a new Equipment.
     *
     * @param name      the name
     * @param basePower the base power
     * @param level     the level
     * @param type      the type
     * @param image     the image
     */
    public Equipment(String name, int basePower, int level, int type, String image) {
        this.name = name;
        this.basePower = basePower;
        this.level = level;
        this.type = type;
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
     * Gets base power.
     *
     * @return the base power
     */
    public int getBasePower() {
        return basePower;
    }

    /**
     * Sets base power.
     *
     * @param basePower the base power
     */
    public void setBasePower(int basePower) {
        this.basePower = basePower;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(int type) {
        this.type = type;
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

    /**
     * Is check boolean.
     *
     * @return the boolean
     */
    public boolean isCheck() {
        return isCheck;
    }

    /**
     * Sets check.
     *
     * @param check the check
     */
    public void setCheck(boolean check) {
        isCheck = check;
    }
}
