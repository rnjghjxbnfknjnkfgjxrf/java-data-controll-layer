package ru.ac.uniyar.databasescourse.utils;

import de.siegmar.fastcsv.reader.CsvReader;
import ru.ac.uniyar.databasescourse.DataControlLayer.Entity.Reviewer;
import ru.ac.uniyar.databasescourse.DataControlLayer.Entity.Solution;
import ru.ac.uniyar.databasescourse.DataControlLayer.Entity.Student;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;

public class SomeCsvDataLoader {
        public static HashSet<Student> loadStudents(Path path) throws IOException {
            HashSet<Student> students = new HashSet<>();
            try (CsvReader csvReader = CsvReader.builder().build(path)) {
                // .skip(1) <=> skip the header
                csvReader.stream().skip(1).forEach(csvRow -> {
                    students.add(new Student(csvRow));
                });
            }
            return students;
        }
        public static HashSet<Solution> loadSolutions(Path path) throws IOException {
            HashSet<Solution> solutions = new HashSet<>();
            try (CsvReader csvReader = CsvReader.builder().build(path)) {
                csvReader.stream().skip(1).forEach(csvRow -> {
                    solutions.add(new Solution(csvRow));
                });
            }
            return solutions;
        }
        public static HashSet<Reviewer> loadReviewers(Path path) throws IOException {
            HashSet<Reviewer> reviewers = new HashSet<>();
            try (CsvReader csvReader = CsvReader.builder().build(path)) {
                csvReader.stream().skip(1).forEach(csvRow -> {
                    reviewers.add(new Reviewer(csvRow));
                });
            }
            return reviewers;
        }
}
