package ru.ac.uniyar.databasescourse;


import ru.ac.uniyar.databasescourse.datacontrollayer.DataBase;
import ru.ac.uniyar.databasescourse.datacontrollayer.entity.Report;
import ru.ac.uniyar.databasescourse.datacontrollayer.entity.Reviewer;
import ru.ac.uniyar.databasescourse.datacontrollayer.entity.Solution;
import ru.ac.uniyar.databasescourse.datacontrollayer.entity.Student;
import ru.ac.uniyar.databasescourse.datacontrollayer.exeptions.DataControlLayerException;
import ru.ac.uniyar.databasescourse.datacontrollayer.ReportsGenerator;
import ru.ac.uniyar.databasescourse.utils.SomeCsvDataLoader;

import java.io.IOException;
import java.nio.file.Path;

import java.util.HashSet;

public class DatabaseExample {

    public static void main(String[] args){
        try {
            DataBase db = new DataBase();
            ReportsGenerator repGen = new ReportsGenerator(db);
            Report rep =  repGen.getStudentsWithMinMaxScore();
            rep.discribe.forEach(System.out::println);
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

