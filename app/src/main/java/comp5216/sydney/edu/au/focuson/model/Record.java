package comp5216.sydney.edu.au.focuson.model;

/**
 * The type Record.
 */
public class Record {
    private String time;
    private String FocusTime;
    private int Pause;
    private int ScreenChange;
    private int PickUp;
    private int score;

    /**
     * Instantiates a new Record.
     *
     * @param time         the time
     * @param FocusTime    the focus time
     * @param Pause        the pause
     * @param ScreenChange the screen change
     * @param PickUp       the pick up
     * @param score        the score
     */
    public Record(String time, String FocusTime, int Pause, int ScreenChange, int PickUp, int score) {
        this.time = time;
        this.FocusTime = FocusTime;
        this.Pause = Pause;
        this.ScreenChange = ScreenChange;
        this.PickUp = PickUp;
        this.score = score;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Gets focus time.
     *
     * @return the focus time
     */
    public String getFocusTime() {
        return FocusTime;
    }

    /**
     * Sets focus time.
     *
     * @param FocusTime the focus time
     */
    public void setFocusTime(String FocusTime) {
        this.FocusTime = FocusTime;
    }

    /**
     * Gets pause.
     *
     * @return the pause
     */
    public int getPause() {
        return Pause;
    }

    /**
     * Sets pause.
     *
     * @param Pause the pause
     */
    public void setPause(int Pause) {
        this.Pause = Pause;
    }

    /**
     * Gets screen change.
     *
     * @return the screen change
     */
    public int getScreenChange() {
        return ScreenChange;
    }

    /**
     * Sets screen change.
     *
     * @param ScreenChange the screen change
     */
    public void setScreenChange(int ScreenChange) {
        this.ScreenChange = ScreenChange;
    }

    /**
     * Gets pick up.
     *
     * @return the pick up
     */
    public int getPickUp() {
        return PickUp;
    }

    /**
     * Sets pick up.
     *
     * @param PickUp the pick up
     */
    public void setPickUp(int PickUp) {
        this.PickUp = PickUp;
    }

    /**
     * Gets .
     *
     * @return the
     */
    public int getscore() {
        return score;
    }

    /**
     * Sets .
     *
     * @param score the score
     */
    public void setscore(int score) {
        this.score = score;
    }
}
