import java.util.Scanner;
public class Hangman {

    public static String[] words = {"ant", "baboon", "badger", "bat", "bear", "beaver", "camel",
    "cat", "clam", "cobra", "cougar", "coyote", "crow", "deer",
    "dog", "donkey", "duck", "eagle", "ferret", "fox", "frog", "goat",
    "goose", "hawk", "lion", "lizard", "llama", "mole", "monkey", "moose",
    "mouse", "mule", "newt", "otter", "owl", "panda", "parrot", "pigeon", 
    "python", "rabbit", "ram", "rat", "raven","rhino", "salmon", "seal",
    "shark", "sheep", "skunk", "sloth", "snake", "spider", "stork", "swan",
    "tiger", "toad", "trout", "turkey", "turtle", "weasel", "whale", "wolf",
    "wombat", "zebra"};

    public static String[] gallows = {"+---+\n" +
    "|   |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    "+---+\n" +
    "|   |\n" +
    "O   |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    "+---+\n" +
    "|   |\n" +
    "O   |\n" +
    "|   |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|   |\n" +
    "     |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + //if you were wondering, the only way to print '\' is with a trailing escape character, which also happens to be '\'
    "     |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" +
    "/    |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + 
    "/ \\  |\n" +
    "     |\n" +
    " =========\n"};

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        int count = 0;
        String duma = randomWord(words);
        char[] chars = duma.toCharArray();
        for(int i = 0; i< chars.length; i++){
            count++;
        }

        char[] placeholder = new char[count];
        
        int greshki = 0;
        System.out.println(gallows[greshki]);
        placeholder = printPLaceHolders(count, placeholder);
        System.out.println("\nMisses: "+ "\t");

        int index = -1;
        int proverka_win = 0;
        char[] misses = new char[6];


        while(true){
            System.out.print("Guess: \t");
            char bukva = scan.next().trim().charAt(0);
            
            for(int i = 0; i < count; i ++){
                if(chars[i] == bukva){
                    index = i;
                    placeholder = updatePlaceHolders(count, bukva, index, placeholder);
                }
            }

            

            if(index != -1){
                System.out.println(gallows[greshki]);
                System.out.print("Word: \t");
                for(int j = 0; j < count; j ++){
                    System.out.print(placeholder[j] + " ");
                }
                System.out.println("");
                System.out.print("Misees: ");
                for(int i = 0; i < 6; i++){
                    System.out.print(misses[i]+ " ");
                }
                System.out.println("");

            }else if(index == -1){
                greshki++;
                System.out.println(gallows[greshki]);
                System.out.print("Word: \t");
                for(int j = 0; j < count; j ++){
                    System.out.print(placeholder[j] + " ");
                }
                System.out.println("");
                misses = printMisses(bukva, misses);
                System.out.print("Misses: ");
                for(int i =0; i< misses.length; i++){
                    System.out.print(misses[i] + " ");
                }
                System.out.println("");
            }

            index = -1;
                    
            for(int k = 0; k < count; k++){
                if(placeholder[k] != '-'){
                    proverka_win++;
                }
            }
            if(proverka_win == count){
                System.out.println("");
                System.out.println("Congrats you win!");
                break;
            }else if(proverka_win != count){
                proverka_win = 0;
            }
            
            if(greshki == 6){
                System.out.println("");
                System.out.println("The word was: " + duma);
                break;
            }
        }   
    }
    


        
        







    public static String randomWord(String[] words){
        double random = Math.random() * 64;
        int number_of_word = (int) random;
        String word = words[number_of_word];
        return word;
    }

    public static char[] printPLaceHolders(int count,char[] placeholder){
        System.out.print("Word: " + "\t");
        for(int i = 0; i < count; i++){
            placeholder[i] =  '-' ;
            System.out.print(placeholder[i] + " ");
        }
        
        return placeholder;
    }


    public static char[] printMisses(char bukva, char[] misses){
        breakLoop:
        for(int i = 0; i < 6; i++){
            if(misses[i] == 0){
                misses[i] = bukva;
                break breakLoop;
            }
        }
        return misses;
    }

    public static char[] updatePlaceHolders(int count, char bukva, int index ,char[]placeholder){
        for(int i = 0; i < count; i ++){
            if(placeholder[i]  == '-'){
                if( i == index){
                    placeholder[i] = bukva;
                }
            }
        }
        return placeholder;
    }

}





