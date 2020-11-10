package comp5216.sydney.edu.au.focuson.model;

import android.net.Uri;

import java.util.ArrayList;

/**
 * User POJO. A user can have multiple pets.
 */
public class User {
    private String email;
    private String nickName;
    private long point;
    private Pet pet;
    private String avatar;


    /**
     * Instantiates a new User.
     *
     * @param email    the email
     * @param nickName the nick name
     * @param point    the point
     * @param pet      the pet
     * @param avatar   the avatar
     */
    public User(String email, String nickName, long point, Pet pet, String avatar) {
        this.email = email;
        this.nickName = nickName;
        this.point = point;
        this.pet = pet;
        this.avatar = avatar;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets nick name.
     *
     * @return the nick name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Sets nick name.
     *
     * @param nickName the nick name
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Gets point.
     *
     * @return the point
     */
    public long getPoint() {
        return point;
    }

    /**
     * Sets point.
     *
     * @param point the point
     */
    public void setPoint(long point) {
        this.point = point;
    }

    /**
     * Gets pet.
     *
     * @return the pet
     */
    public Pet getPet() {
        return pet;
    }

    /**
     * Sets pet.
     *
     * @param pet the pet
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Gets avatar.
     *
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Sets avatar.
     *
     * @param avatar the avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
