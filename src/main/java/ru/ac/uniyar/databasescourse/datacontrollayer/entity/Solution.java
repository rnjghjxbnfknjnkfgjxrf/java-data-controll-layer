package ru.ac.uniyar.databasescourse.datacontrollayer.entity;

import de.siegmar.fastcsv.reader.CsvRow;

public class Solution {
    private Integer id;
    private float score;
    private boolean hasPassed;
    private int studentId;
    private int reviewerId;

    public Solution(
            int id,
            float score,
            boolean hasPassed,
            int studentId,
            int reviewerId
    ) {
        this.id = id;
        this.score = score;
        this.hasPassed = hasPassed;
        this.studentId = studentId;
        this.reviewerId = reviewerId;
    }

    public Solution(CsvRow csvRow) {
        this.id = Integer.parseInt(csvRow.getField(3));
        this.score = Float.parseFloat(csvRow.getField(7));
        this.hasPassed = csvRow.getField(8).equals("Yes");
        this.studentId = Integer.parseInt(csvRow.getField(0));
        this.reviewerId = Integer.parseInt(csvRow.getField(4));
    }

    public int getId() {
        return this.id;
    }
    public float getScore() {
        return this.score;
    }
    public boolean getHasPassed() {
        return this.hasPassed;
    }
    public int getStudentId() {
        return this.studentId;
    }
    public int getReviewerId() {
        return this.reviewerId;
    }

    @Override
    public String toString() {
        return String.format("id: %d, studentId: %d", this.id, this.studentId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Solution sol = (Solution) obj;
        return this.id.equals(sol.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime * (1 + (this.id == null ? 0: this.id.hashCode()));
    }
}
