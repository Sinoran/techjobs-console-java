package org.launchcode.techjobs.console;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();

                findByValue(searchField, searchTerm);
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while (!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {

        for (HashMap jobList : someJobs) {
            System.out.println("\n" + "****");

            for (Object j : jobList.keySet()) {
                System.out.println(j + ": " + jobList.get(j));
            }
            System.out.println("****");
        }

    }

    public static void findByValue(String column, String value) { //case-insensitive search through csv file

        ArrayList<HashMap<String, String>> jobData = JobData.findAll();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        if (column.equals("all")) {
            for (HashMap<String, String> row : jobData) {
                for (String j : row.keySet()) {
                    String aValue = row.get(j).toLowerCase();
                    if (aValue.contains(value.toLowerCase())) {
                        jobs.add(row);
                    }
                }
            }
        } else {
            for (HashMap<String, String> row : jobData) {

                String aValue = row.get(column).toLowerCase();

                if (aValue.contains(value.toLowerCase())) { //adding found data to list
                    jobs.add(row);
                }
            }
        }
        if (jobs.size() <= 0) {
            System.out.println("No jobs found in search.");
        } else {
            printJobs(jobs);
        }
    }

}
