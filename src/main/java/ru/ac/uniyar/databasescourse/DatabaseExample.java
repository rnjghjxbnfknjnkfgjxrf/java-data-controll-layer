package ru.ac.uniyar.databasescourse;


import ru.ac.uniyar.databasescourse.DataControlLayer.DataBase;
import ru.ac.uniyar.databasescourse.DataControlLayer.Entity.Reviewer;
import ru.ac.uniyar.databasescourse.DataControlLayer.Entity.Solution;
import ru.ac.uniyar.databasescourse.DataControlLayer.Entity.Student;
import ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions.DataControlLayerException;
import ru.ac.uniyar.databasescourse.utils.SomeCsvDataLoader;

import java.io.IOException;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.HashSet;

public class DatabaseExample {

    public static void main(String[] args){
        try {
            DataBase db = new DataBase();
            ArrayList<Solution> solutions = db.getSolutions();
            solutions.forEach(System.out::println);
        } catch (DataControlLayerException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static HashSet<Student> getStudentsFromCsv(String fileName) {
        HashSet<Student> students;
        try {
            students = SomeCsvDataLoader.loadStudents(Path.of(fileName));
        } catch (IOException e) {
            students = new HashSet<>();
            e.printStackTrace();
        }
        return students;
    }

    private static HashSet<Solution> getSolutionsFromCsv(String fileName) {
        HashSet<Solution> solutions;
        try {
            solutions = SomeCsvDataLoader.loadSolutions(Path.of(fileName));
        } catch (IOException e) {
            solutions = new HashSet<>();
            e.printStackTrace();
        }
        return solutions;
    }

    private static HashSet<Reviewer> getReviewersFromCsv(String fileName) {
        HashSet<Reviewer> reviewers;
        try {
            reviewers = SomeCsvDataLoader.loadReviewers(Path.of(fileName));
        } catch (IOException e) {
            reviewers = new HashSet<>();
            e.printStackTrace();
        }
        return reviewers;
    }
}

