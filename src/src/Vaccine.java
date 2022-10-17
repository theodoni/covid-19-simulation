/**
 * The type Vaccine.
 */
public class Vaccine {
    private boolean pfizer1st=false, pfizer2nd=false, az1st=false, az2nd=false, moderna1st=false, moderna2nd=false, jnj=false;

    /**
     * Set pfizer 1 st.
     *
     * @param a the a
     */
    public void setPfizer1st(boolean a){pfizer1st = a;}

    /**
     * Set pfizer 2 nd.
     *
     * @param a the a
     */
    public void setPfizer2nd(boolean a){pfizer2nd = a;}

    /**
     * Set az 1 st.
     *
     * @param a the a
     */
    public void setAz1st(boolean a){az1st = a;}

    /**
     * Set az 2 nd.
     *
     * @param a the a
     */
    public void setAz2nd(boolean a){az2nd = a;}

    /**
     * Set moderna 1 st.
     *
     * @param a the a
     */
    public void setModerna1st(boolean a){moderna1st = a;}

    /**
     * Set moderna 2 nd.
     *
     * @param a the a
     */
    public void setModerna2nd(boolean a){moderna2nd = a;}

    /**
     * Set jnj.
     *
     * @param a the a
     */
    public void setJnj(boolean a){jnj = a;}

    /**
     * Get pfizer 1 st boolean.
     *
     * @return the boolean
     */
    public boolean getPfizer1st(){return pfizer1st;}

    /**
     * Get pfizer 2 nd boolean.
     *
     * @return the boolean
     */
    public boolean getPfizer2nd(){return pfizer2nd;}

    /**
     * Get az 1 st boolean.
     *
     * @return the boolean
     */
    public boolean getAz1st(){return az1st;}

    /**
     * Get az 2 nd boolean.
     *
     * @return the boolean
     */
    public boolean getAz2nd(){return az2nd;}

    /**
     * Get moderna 1 st boolean.
     *
     * @return the boolean
     */
    public boolean getModerna1st(){return moderna1st;}

    /**
     * Get moderna 2 nd boolean.
     *
     * @return the boolean
     */
    public boolean getModerna2nd(){return moderna2nd;}

    /**
     * Get jnj boolean.
     *
     * @return the boolean
     */
    public boolean getJnj(){return jnj;}

    /**
     * Instantiates a new Vaccine.
     *
     * @param a the a
     */
    public Vaccine(int a){
        switch (a) {
            case 0:
                setPfizer1st(true);
                break;
            case 1:
                setPfizer1st(true);
                setPfizer2nd(true);
                break;
            case 2:
                setAz1st(true);
                break;
            case 3:
                setAz1st(true);
                setAz2nd(true);
                break;
            case 4:
                setModerna1st(true);
                break;
            case 5:
                setModerna1st(true);
                setModerna2nd(true);
                break;
            case 6:
                setJnj(true);
                break;
            case 7:
                break;
        }
    }
}
