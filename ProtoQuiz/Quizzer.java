import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files

public class Quizzer {
    public static void main(String[] args){

        try{
            File qFile = new File("questions.txt");
            Scanner qScanner = new Scanner(qFile);
            HashMap<String, ArrayList<String>> qCollection = new HashMap<String, ArrayList<String>>();
            Scanner myScanner = new Scanner(System.in);

            boolean incTitle = true;
            String currTitle = "";
            boolean qMode = false;


            while (qScanner.hasNextLine()) {
                String data = qScanner.nextLine();

                if(incTitle){ //if its title
                    qCollection.computeIfAbsent(data, k -> new ArrayList<>());
                    currTitle = data;
                    incTitle = false;
                    continue;
                }

                if(data.length() == 0){ //if empty line
                    incTitle = true; //incoming title
                    qMode = false;
                    continue;
                }

                if(qMode){ // if question
                    qCollection.computeIfAbsent(currTitle, k -> new ArrayList<>()).add(data);
                    continue;
                }

                if(data.startsWith("Question")){
                    qMode = true;
                    continue;
                }   

                if(data.startsWith("Terms")){ //if terms
                    data = data.replace("Terms: ", "");
                    for (String term : data.split(", ")){ //add each term to current title
                        qCollection.computeIfAbsent(currTitle, k -> new ArrayList<>()).add("What is " + term + "?");
                    }
                    continue;
                }

                

                
            }
            qScanner.close();

            String[] questionArray = {};
            
            // List<String> questionList = Arrays.asList(questionArray);
            List<String> questionList = new LinkedList<String>(Arrays.asList(questionArray));

            for (String title : qCollection.keySet()) {
                for (String question: qCollection.get(title)){
                    questionList.add(question + " - " + title);
                    System.out.println("Added " + question + " - " + title);
                }
                
            }

            
            do {
                Collections.shuffle(questionList);
                for (String question : questionList){ //loop through all questions and ask them
                    System.out.println("---------------------");
                    System.out.println("Question " + (questionList.indexOf(question)+1) + " of " + questionList.size());
                    System.out.println("From: " + question.split(" - ")[1]);
                    System.out.println(question.split(" - ")[0]);
                    String empty = myScanner.next();
                }
                System.out.println("Set completed. Do you want to reshuffle and loop again? (y/n)");

            } while (myScanner.next().equals("y"));

            

        } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
        }

    }
}
