package src.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import src.main.models.Game;
import src.main.models.Team;


public class GameTest {


    Team home = new Team("GRYFFINDOR", "Oliver", "Harry", new String[] {"Angelina", "Ginny", "Katie"});
    Team away = new Team("SLYTHERIN", "Vincent",  "Draco", new String[] {"Bridget", "Harper", "Malcolm"});
    Game game = new Game(home,away);

    @Test
    public void getPlaceholderTest( ){
        String exp = "chaser";
        assertEquals(exp,(game.getPlaceholder("<chaser> gets the next pass")));

    }

    @Test
    public void replacePlaceholderTest(){
        String exp = "Katie gets the next pass";
        assertEquals(exp,(game.replacePlaceholder("<chaser> gets the next pass", "chaser", "Katie")));
    }

    @Test
    public void quaffleScoreTest(){
        game.quaffleScore(home);
        game.quaffleScore(home);
        
        assertSame(20,game.getScore(home));
    }

    @Test
    public void catchSnitchTest(){
        game.catchSnitch(away);
        Assertions.assertEquals(150, game.getScore(away));
    }








}
