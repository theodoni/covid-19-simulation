import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The type Person.
 */
public class Person {
    private double x, y, xVel, yVel;
    private int status;
    /**
    *0 for non vaccinated healthy,
    *1 for infected,
    *2 for hospitalized ,
    *3 for immune,
    *4 for 1st dose pfizer,
    *5 for 2nd dose pfizer,
    *6 for 1st dose AZ,
    *7 for 2nd dose AZ,
    *8 for 1st dose Moderna,
    *9 for 2nd dose Moderna,
    *10 for JnJ
    */
    private int ticksSinceInfected, ticksSinceHospitalized, ticksSince1stDose, ticksSince2ndDose, ticksSinceHospitalizedAndRecovered;
    private boolean firstInfected, ignore, immobile, isRight;
    private Vaccine vaccinated;
    private double probability = 0.5;

    /**
     * Instantiates a new Person.
     *
     * @param x        the x
     * @param y        the y
     * @param infected the infected
     * @param immobile the immobile
     * @param isRight  the is right
     */
    public Person(int x, int y, boolean infected, boolean immobile, boolean isRight) {
        this.x = x;
        this.y = y;
        this.immobile = immobile;
        this.isRight = isRight;
        xVel = Math.random() * 4.0 - 2.0;
        yVel = Math.sqrt(4 - Math.pow(xVel, 2)) * (Math.random() < 0.5 ? -1.0 : 1.0);
        ticksSinceInfected = 0;
        ticksSinceHospitalized = 0;
        ticksSince1stDose = 0;
        ticksSince2ndDose = 0;
        ticksSinceHospitalizedAndRecovered = 0;
        status = (infected ? 1 : 0);
        firstInfected = infected;
        isVaccinated();
    }

    /**
     * Is vaccinated.
     */
    public void isVaccinated() {
        if (isInfected()) {vaccinated = new Vaccine(7);return;}
        Random rd = new Random();
        float a = rd.nextFloat();
        if (a > 0.45) //fully vaccinated
        {
            int randomNum = ThreadLocalRandom.current().nextInt(0, 4);
            if (randomNum == 0){ vaccinated = new Vaccine(1); status=5;probability=0.95;}
            if (randomNum == 1){ vaccinated = new Vaccine(3);status=7;probability=0.9;}
            if (randomNum == 2){ vaccinated = new Vaccine(5);status=9;probability=0.92;}
            if (randomNum == 3){ vaccinated = new Vaccine(6);status=10;probability=0.87;}
            return;
        }
        if (a > 0.35)//only one dose
        {
            int randomNum = ThreadLocalRandom.current().nextInt(0, 3);
            if (randomNum == 0){ vaccinated = new Vaccine(0);status=4;probability=0.65;}
            if (randomNum == 1){ vaccinated = new Vaccine(2);status=6;probability=0.65;}
            if (randomNum == 2){ vaccinated = new Vaccine(4); status=8;probability=0.65;}
            return;
        }
        vaccinated=new Vaccine(7);

    }

    /**
     * Tick.
     */
    public void tick()//ticks per day = 33
    {
        if (status != 2 && !immobile) {
            if (!isRight) {
                if (x + xVel + 10 > CoronaPanel.WIDTH || x + xVel < 0) {
                    xVel = -xVel;
                }
                if (y + yVel + 10 > CoronaPanel.HEIGHT || y + yVel < 0) {
                    yVel = -yVel;
                }
            } else {
                if (x + xVel + 10 > CoronaPanel.WIDTH * 2 || x + xVel < CoronaPanel.WIDTH) {
                    xVel = -xVel;
                }
                if (y + yVel + 10 > CoronaPanel.HEIGHT || y + yVel < 0) {
                    yVel = -yVel;
                }
            }
            x += xVel;
            y += yVel;
        }
        if (!firstInfected) {
            if (status == 1) {
                ticksSinceInfected++;
                if (ticksSinceInfected > 800) {
                    status = 3;
                }
            } else if (status == 2) {
                ticksSinceHospitalized++;
                ticksSinceHospitalizedAndRecovered++;
                if (ticksSinceHospitalized > 500) {
                    status = 3;
                }
            }
        }
        if(status==3&&ticksSinceHospitalizedAndRecovered!=0){ticksSinceHospitalizedAndRecovered++; if(ticksSinceHospitalizedAndRecovered>6400)status=0;}
        if(status==4){ticksSince1stDose++; if(ticksSince1stDose>660&&!isInfected()) status=5;}
        if(status==6){ticksSince1stDose++; if(ticksSince1stDose>2770&&!isInfected()) status=7;}
        if(status==8){ticksSince1stDose++; if(ticksSince1stDose>920&&!isInfected()) status=9;}
        if(status==5){ticksSince2ndDose++; if(ticksSince2ndDose>660&&!isInfected()) status=3;}
        if(status==7){ticksSince2ndDose++; if(ticksSince2ndDose>660&&!isInfected()) status=3;}
        if(status==9){ticksSince2ndDose++; if(ticksSince2ndDose>660&&!isInfected()) status=3;}
        if(status==10){ticksSince2ndDose++; if(ticksSince2ndDose>660&&!isInfected()) status=3;}
    }

    /**
     * Check.
     *
     * @param p the p
     */
    public void check(Person p) {
        if (!ignore) {
            if (Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2)) < 10) {
                if (p.getStatus() == 1 && Math.random() < 0.85 && status == 0 && Math.random()>probability) {
                    status = 1;
                }
                if (!immobile && status != 2) {
                    xVel = (Math.random() * 2 * (x - p.getX() < 0 ? -1.0 : 1.0));
                    yVel = (Math.sqrt(4 - Math.pow(xVel, 2))) * (y - p.getY() < 0 ? -1.0 : 1.0);
                    p.ignoreCheck();
                }
                if (firstInfected) {
                    firstInfected = false;
                }
            }
        } else {
            ignore = false;
        }
    }

    /**
     * Ignore check.
     */
    public void ignoreCheck() {
        ignore = true;
    }

    /**
     * Hospitalize.
     */
    public void hospitalize() {
        if (!firstInfected) {
            status = 2;
        }
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public int getStatus() {  return status; }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Is infected boolean.
     *
     * @return the boolean
     */
    public boolean isInfected() {
        return status == 1;
    }
}