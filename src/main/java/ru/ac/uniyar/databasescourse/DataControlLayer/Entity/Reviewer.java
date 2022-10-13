package ru.ac.uniyar.databasescourse.DataControlLayer.Entity;

import de.siegmar.fastcsv.reader.CsvRow;

public class Reviewer {
    private Integer id;
    private String surname;
    private String department;

    public Reviewer(
            int id,
            String surname,
            String department
    ) {
        this.id = id;
        this.surname = surname;
        this.department = department;
    }

    public Reviewer(CsvRow csvRow) {
        this.id = Integer.parseInt(csvRow.getField(4));
        this.surname = csvRow.getField(5);
        this.department = csvRow.getField(6);
    }

    public int getId() {
        return this.id;
    }
    public String getSurname() {
        return this.surname;
    }
    public String getDepartment() {
        return this.department;
    }

    @Override
    public String toString() {
        return String.format("id: %d, surname: %s, department: %s", this.id, this.surname, this.department);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Reviewer rw = (Reviewer) obj;
        return this.id.equals(rw.id);
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        return prime * (1 + (this.id == null ? 0: this.id.hashCode()));
    }
}
