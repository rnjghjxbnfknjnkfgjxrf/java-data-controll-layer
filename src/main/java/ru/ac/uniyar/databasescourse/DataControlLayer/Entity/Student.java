package ru.ac.uniyar.databasescourse.DataControlLayer.Entity;

import de.siegmar.fastcsv.reader.CsvRow;

public class Student {
    private Integer id;
    private String name;
    private String surname;

    public Student (
            int id,
            String name,
            String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public Student (CsvRow csvRow) {
        this.id = Integer.parseInt(csvRow.getField(0));
        this.name = csvRow.getField(1);
        this.surname = csvRow.getField(2);
    }

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getSurname() {
        return this.surname;
    }

    @Override
    public String toString() {
        return String.format("id: %d, name: %s, surname: %s", this.id, this.name, this.surname  );
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Student st = (Student) obj;
        return this.id.equals(st.id);
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        return prime * (1 + (this.id == null ? 0: this.id.hashCode()));
    }
}
