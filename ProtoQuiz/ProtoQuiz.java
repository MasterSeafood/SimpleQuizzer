import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ProtoQuiz{
    public static void main(String[] args){
        int counter = 1;
        
        String underCurrentTitle = "";
        String prev = "";
        boolean questionMode = false;

        HashMap<String, ArrayList<String>> collection = new HashMap<String, ArrayList<String>>();

        Scanner myScanner = new Scanner(System.in);

        try {
            File myNotes = new File("source.txt");
            Scanner myReader = new Scanner(myNotes);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                if (counter == 1){ //add title (only activates on first line)
                    collection.computeIfAbsent(data, k -> new ArrayList<>());
                    counter++;
                    underCurrentTitle = data;
                }
                if ((prev.endsWith("?")|| prev.equals("") || prev.endsWith(")")) && !data.endsWith("?") && !data.endsWith(".") && !data.endsWith(")") && questionMode && !data.equals("") && !data.endsWith(" ")){//new title
                    collection.computeIfAbsent(data, k -> new ArrayList<>());
                    underCurrentTitle = data;
                    questionMode = false;
                } 


                // if (data.length() < 12){ //skip if line is too short to be a term or question
                //     prev = data;
                //     continue;
                // }

                if (data.length() == 0)
                    continue;

                if (questionMode && !data.startsWith("Question")){ //add questions to current title
                    collection.computeIfAbsent(underCurrentTitle, k -> new ArrayList<>()).add(data);
                }

                if (data.startsWith("Terms:")){ //if the line is Terms
                    data = data.replace("Terms: ", "");
                    for (String term : data.split(", ")){ //add each term to current title
                        collection.computeIfAbsent(underCurrentTitle, k -> new ArrayList<>()).add("What is " + term + "?");
                        
                    }
                    questionMode = true;
                }
                
                prev = data;

            }
            myReader.close();
        } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
        }

        String[] questionArray = {};
        
        // List<String> questionList = Arrays.asList(questionArray);
        List<String> questionList = new LinkedList<String>(Arrays.asList(questionArray));

        for (String title : collection.keySet()) {
            for (String question: collection.get(title)){
                questionList.add(question + " - " + title);
                System.out.println("Added " + question + " - " + title);
            }
            
        }

        Collections.shuffle(questionList);

        while (true){
            for (String question : questionList){ //loop through all questions and ask them
                System.out.println("---------------------");
                System.out.println("Question " + (questionList.indexOf(question)+1) + " of " + questionList.size());
                System.out.println("From: " + question.split(" - ")[1]);
                System.out.println(question.split(" - ")[0]);
                String empty = myScanner.next();
            }
            System.out.println("Set completed. Do you want to reshuffle and loop again? (y/n)");
            if (myScanner.next().equals("y")){
                Collections.shuffle(questionList);
            }else
                break;
                
        }
        


    }


}