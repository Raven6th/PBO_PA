package com.catur;

public abstract class ChessProfile {
    private String name;
    private int age;
    private int rating;
    private String preferredOpenings;

    public ChessProfile(String name, int age, int rating, String preferredOpenings) {
        this.name = name;
        this.age = age;
        this.rating = rating;
        this.preferredOpenings = preferredOpenings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPreferredOpenings() {
        return preferredOpenings;
    }

    public void setPreferredOpenings(String preferredOpenings) {
        this.preferredOpenings = preferredOpenings;
    }

    public abstract void displayRole();

    @Override
    public String toString() {
        return "Nama: " + name + ", Umur: " + age + ", Rating: " + rating + ", Pembuka Favorit: " + preferredOpenings;
    }
}
