package src.main.models;

import java.util.Arrays;
import java.util.Objects;

public class Team {

    private String house;
    private String keeper;
    private String seeker;
    private String[] chasers;



    public Team(String house, String keeper, String seeker, String[] chasers) {
        setHouse(house);
        setKeeper(keeper);
        setSeeker(seeker);
        setChasers(chasers);
    }

    public Team(Team team){
        this.house = team.house;
        this.keeper = team.keeper;
        this.seeker = team.seeker;
        this.chasers = Arrays.copyOf(team.chasers, team.chasers.length);
        
    }

    
    public static final String POSITION_CHASER = "chaser";
    public static final String POSITION_SEEKER = "seeker";
    public static final String POSITION_KEEPER = "keeper";

/* FREQUENTLY ASKED QUESTIONS:
    
Question: the constants are final, so why can't we make them public? It's not possible for the caller to update them.
  Answer: Even if the constant is final, I prefer to expose a method instead of the variable. But if you want to expose the variable, that's also correct.

*/  
    public static String getPositionChaser() {
        return POSITION_CHASER;
    }

    public static String getPositionSeeker() {
        return POSITION_SEEKER;
    }

    public static String getPositionKeeper() {
        return POSITION_KEEPER;
    }


    public String getHouse() {
        return this.house;
    }

    public String getKeeper() {
        return this.keeper;
    }

    public String getSeeker() {
        return this.seeker;
    }

    public String[] getChasers() {
        return this.chasers = Arrays.copyOf(chasers,chasers.length);
    }


    public void setHouse(String house) {
        checkParam(house);
        this.house = house;
    }
    public void setKeeper(String keeper) {
        checkParam(keeper);
        this.keeper = keeper;
    }
    public void setSeeker(String seeker) {
        checkParam(seeker);
        this.seeker = seeker;
    }

    public void setChasers(String[] chasers) {
        if(chasers.length != 3){
            throw new IllegalArgumentException();
        }else if(hasBlank(chasers) == true){
            throw new IllegalArgumentException("You cannot have a blank element");
        }else if (hasNull(chasers) == true){
            throw new IllegalArgumentException("You cannot have a null element");
        }else{
            this.chasers = Arrays.copyOf(chasers, chasers.length);
        }
    }


    public static boolean hasNull(String[] arr){
        return Arrays.stream(arr).anyMatch((i) -> i == null);
    }

    public static boolean hasBlank(String[] arr){
        return Arrays.stream(arr).anyMatch((i) -> i.isEmpty());
    }

    public void checkParam(String param) {
        if (param == null || param.isEmpty()) {
            throw new IllegalArgumentException(param + " cannot be null or blank");
        }
    }




    @Override
    public String toString() {
         return "House: " + this.house + "\n" +
          "Keeper: " + this.keeper + "\n" +         
          "Seeker: "  + this.seeker + "\n" +         
          "Chasers: " + Arrays.toString(this.chasers) + "\n"; 
    }  



    @Override
    public boolean equals(Object o) {
        if( o == null){
            return false;
        }
        if(!(o instanceof Team)){
            return false;
        }
        Team team = (Team) o;
        return this.house.equals(team.house) 
        && this.keeper.equals(team.keeper) 
        && this.seeker.equals(team.seeker) 
        && Arrays.toString(this.chasers).equals(Arrays.toString(team.chasers));
    }

    @Override
    public int hashCode() {
        return Objects.hash(house, keeper, seeker, Arrays.toString(chasers));
    }
    




    
}
