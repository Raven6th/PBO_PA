// File: ChessCoach.java
package com.catur;

public class ChessCoach extends ChessPlayer {
    private int coachingExperience;
    private String specialization;

    public ChessCoach(String name, int age, int rating, String preferredOpenings, int coachingExperience, String specialization) {
        super(name, age, rating, preferredOpenings);
        this.coachingExperience = coachingExperience;
        this.specialization = specialization;
    }

    public int getCoachingExperience() {
        return coachingExperience;
    }

    public void setCoachingExperience(int coachingExperience) {
        this.coachingExperience = coachingExperience;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public void displayRole() {
        System.out.println("Peran: Pelatih Catur");
    }

    @Override
    public String toString() {
        return super.toString() + ", Pengalaman Melatih: " + coachingExperience + " tahun, Spesialisasi: " + specialization;
    }
}
