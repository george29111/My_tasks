import java.util.ArrayList;
import java.util.Scanner;
import java.io.UnsupportedEncodingException;
import java.sql.*;


public class Main{

    public static String validation(Scanner sc) throws UnsupportedEncodingException{
        System.out.println("When you are ready type exit!");
        System.out.print("Enter the name of food you want to calculate: ");
        String food = sc.nextLine();

        while (food.isEmpty() || food == null) {
            System.out.print("Invalid date please enter a String! Enter again: ");
            food = sc.nextLine();
        }

        return food;
    }


    public static double weight_validation(Scanner sc){
        System.out.print("Enter the grams of the porsion: ");
        double grams = sc.nextDouble();
        sc.nextLine();

        while (grams <= 0  || grams > 999) {
            System.out.print("Invalid date please enter a double! Enter again: ");
            grams = sc.nextDouble();
            sc.nextLine();
        }
        double divider = 100;

        return grams/divider;
        
    }


    private static double[] executeQuery( String food_name) {

        String url = "jdbc:mysql://localhost:3306/food";
        String user = "root";
        String password = ""; //here you paste your password


        
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "SELECT * FROM food_macros WHERE food_macros.name_of_food = ? or food_macros.name_of_food is null ";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, food_name);
                try (ResultSet rs = pstmt.executeQuery()) {
                    int columnCount = rs.getMetaData().getColumnCount();
                    double[] arr = new double[columnCount];
    
                    while (rs.next()) {
                        for (int i = 2; i <= columnCount-1; i++) {  
                            arr[i - 1] = rs.getDouble(i); 
                        }
                    }
                    return arr;
                }
    
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new double[0]; 
    }

    
    public static String ActionDecider( Scanner sc){
        System.out.print("What you want to do? \' Add \' calories or \' See \' results or \' Delete \' results: ");
        String action = sc.nextLine();

        while (action.isEmpty() || action == null || !(action.equalsIgnoreCase("See") || 
                                                       action.equalsIgnoreCase("Add") || 
                                                       action.equalsIgnoreCase("Delete"))){
            System.out.print("Invalid action please enter a Add or See! Enter again: ");
            action = sc.nextLine();
        }

        return action;

    }


    public static boolean YesOrNo(Scanner sc, String str){
        System.out.println(str + " Y/N");
        String action = sc.nextLine();

        while (action.isEmpty() || action == null || !(action.equals("Y") || action.equals("N"))){
            System.out.print("Invalid action please enter a Y or N! Enter again: ");
            action = sc.nextLine();
        }

        if(action.equals("Y")){
            return true;
        }
        return false;

    }
    

    private static void AddQuery(String date,double cal, double vug, double pro, double maz){
        String url = "jdbc:mysql://localhost:3306/food";
        String user = "root";
        String password = ""; //here you paste your password

        
        String insertQuery = "INSERT INTO records (record_date,calories, vuglehidrat, protein, maznini) VALUES (?, ?, ?, ?, ?)";

        try{
            
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            
            preparedStatement.setString(1, date);
            preparedStatement.setDouble(2, cal);
            preparedStatement.setDouble(3, vug);
            preparedStatement.setDouble(4, pro);
            preparedStatement.setDouble(5, maz);

            
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Record is aded succesfuly!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int limitValidator(Scanner sc){
        int limit = 0;
        do{
        System.out.print("Enter the number of days you want to see. The MAX is 1 000:  ");
        limit = sc.nextInt();
        sc.nextLine();
        }while(limit <= 0 || limit >1000);

        return limit;

    }


    private static ArrayList<Records> SeeTableQuery(Scanner sc){
        String url = "jdbc:mysql://localhost:3306/food";
        String user = "root";
        String password = ""; //here you paste your password

        int limit = limitValidator(sc);
        ArrayList <Records> records = new ArrayList<>();
        
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
    
            String query = "SELECT record_date,calories,vuglehidrat,protein,maznini FROM records ORDER by record_date DESC limit ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, limit);

                try (ResultSet rs = pstmt.executeQuery()) {
                    int columnCount = rs.getMetaData().getColumnCount();
                    

                    while (rs.next()) {
                        double[] row = new double[columnCount - 1]; 
                        for (int i = 2; i < columnCount+1; i++) { 
                            row[i - 2] = rs.getDouble(i);
                        }
                        Records r1 = new Records(rs.getString(1), row);
                        records.add(r1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return records;
    }

    public static boolean food_validation(double[] arr){
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        if(sum == 0){
            System.out.println("\nSorry, invalid food name or food not in the database!!!\n ");
            return false;
        }
        else{
            System.out.println("\nFood successful added! \n");
            return true;
        }
    }

    public static double CustomMacroses(Scanner sc, String str){
        double temp = 0;
        do{ 
            System.out.print("Enter the " + str+ " :");
            temp =sc.nextDouble();
            sc.nextLine();
        }while(temp <=0 || temp >10_000);
        
        return temp;
    }
    

    public static StringBuilder DateValidator(Scanner sc){
        int year = 0;
        int month = 0;
        int day = 0 ;
        
        do{
        System.out.print("Enter the year: ");
        year = sc.nextInt();
        }while(year < 2024 || year > 2028);

        do{
            System.out.print("Enter the month: ");
            month = sc.nextInt();
        }while(month < 1 || month > 12);

        do{
            System.out.print("Enter the day: ");
            day = sc.nextInt();
        }while(day < 1 || day > 31);

        sc.nextLine();
        
        StringBuilder s1 = new StringBuilder(year + "-"+ month + "-" + day);
        return s1; 
        
    }
    
    private static void DeleteQuery(String date){
        String url = "jdbc:mysql://localhost:3306/food";
        String user = "root";
        String password = ""; //here you paste your password

        
        String insertQuery = "DELETE FROM records WHERE cast(record_date AS DATE) = ? ";

        try{
            
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            
            preparedStatement.setString(1, date);
            
            
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Record is deleted succesfuly!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String food_input = "";
        double quantity = 0;

        double calories = 0;
        double vuglehidrat = 0;
        double beltuci = 0;
        double maznini = 0;
        String action= ActionDecider(sc);

        if(action.equalsIgnoreCase("Add")){
            try{
                do {

                    food_input = validation(sc);
                    quantity = weight_validation(sc);
                    double[] mass = executeQuery(food_input);

                    if (food_validation(mass)) {
                        calories += (mass[1]*quantity);
                        vuglehidrat += (mass[2]*quantity);
                        beltuci += (mass[3]*quantity);
                        maznini += (mass[4]*quantity);
                    }
                } while (!(food_input.equalsIgnoreCase("exit")));
                 
                
            
                calories += CustomMacroses(sc, "Calories");
                vuglehidrat += CustomMacroses(sc, "Carbohydrates");
                beltuci += CustomMacroses(sc, "Protein");
                maznini += CustomMacroses(sc, "Fats");

                String date = DateValidator(sc).toString();

                System.out.println("The macros  of the food you have entered is:");
                System.out.println("Calories: " + (int)calories + " Vuglehidrat: " + (int)vuglehidrat + " Protrin: " + (int)beltuci + " Maznini: " + (int)maznini + date);

                boolean flag = YesOrNo(sc, "Do ypou want to save ths data!");
                if (flag) {
                    AddQuery(date,calories,vuglehidrat,beltuci, maznini);
                }


            }catch (Exception e) {
                e.printStackTrace();
            }

        }else if(action.equalsIgnoreCase("See")){
            ArrayList<Records> records = SeeTableQuery(sc);
            
            for (int i =0; i< records.size(); i++) {
                String key = records.get(i).getDate();

                double[] values = records.get(i).getMacros();
                int cal = (int) values[0];
                int vug = (int) values[1];
                int pro = (int) values[2];
                int maz = (int) values[3];
                System.out.println(key + " | Calories:  " + cal + " | Carbohydrates: " + vug + " | Protein: " + pro + " | Fats: " + maz);
            }
        }else if(action.equalsIgnoreCase("Delete")){
            String date = DateValidator(sc).toString();
            DeleteQuery(date);
        }
        
        sc.nextLine();
        sc.close();
    }
}
