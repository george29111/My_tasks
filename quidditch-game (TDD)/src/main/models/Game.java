package src.main.models;


import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.Objects;

public class Game {

    public static final int QUAFFLE_POINTS = 10;
    public static final int SNITCH_POINTS = 150;

    private HashMap<Team,Integer> scoreboard; 
    private static int count;




    public Game(Team home, Team away){
        scoreboard = new HashMap<>();
        scoreboard.put(new Team(home), 0);
        scoreboard.put(new Team(away), 0);
        count++;
    }
    

    public Integer getScore(Team team){
        return scoreboard.get(team);

    }
    public void setScore(Team team,Integer score){
        if(score == 0|| score == null){
            throw new IllegalArgumentException("score cannot be 0");
        }
        
        scoreboard.replace(team, score);
    }



    public Team getTeam(String name){
        return scoreboard.keySet().stream().filter( i -> i.getHouse().equals(name)).collect(Collectors.toList()).get(0);
    }


    public String getPlaceholder(String play){
        return play.substring(play.indexOf("<")+1, play.lastIndexOf(">")).trim();
    }


    public String replacePlaceholder(String play,String placeholder, String value){
        String str = new String(play.replace("<" + placeholder + ">", value));
        return str;
    }

    public void quaffleScore(Team team){
        scoreboard.put(team,scoreboard.get(team) + QUAFFLE_POINTS);
    }

    public void catchSnitch(Team team){
        scoreboard.put(team,scoreboard.get(team) + SNITCH_POINTS);
    }



    public String simulate(String play){
        String player = getPlaceholder(play);

        Team randomTeam = getRandomTeam();

        if(player.equals(Team.POSITION_CHASER)){
            quaffleScore(randomTeam);
            String[] chasers = randomTeam.getChasers();
            return replacePlaceholder(play, player,chasers[randomNumber(chasers.length)]);
        }else if(player.equals(Team.POSITION_SEEKER)){
            catchSnitch(randomTeam);
            return replacePlaceholder(play, player,randomTeam.getSeeker());
            
        }else {
            return replacePlaceholder(play, player,randomTeam.getKeeper());
        }

    }

    public Team getRandomTeam(){
        Team t1 = new Team (scoreboard.keySet().stream().collect(Collectors.toList()).get(randomNumber(scoreboard.size())));
        return t1;
    }

    public int randomNumber(int range){
        if(range ==2){
            range++;
        }
        double result = Math.random()*(range-1);
        return (int) result;
    }

    


}
