// File: ChessPlayer.java
package com.catur;

public class ChessPlayer extends ChessProfile {

    public ChessPlayer(String name, int age, int rating, String preferredOpenings) {
        super(name, age, rating, preferredOpenings);
    }

    @Override
    public void displayRole() {
        System.out.println("Peran: Pemain Catur Kasual");
    }
}
