// File: ProfessionalChessPlayer.java
package com.catur;

public class ProfessionalChessPlayer extends ChessPlayer {
    private int tournamentWins;

    public ProfessionalChessPlayer(String name, int age, int rating, String preferredOpenings, int tournamentWins) {
        super(name, age, rating, preferredOpenings);
        this.tournamentWins = tournamentWins;
    }

    public int getTournamentWins() {
        return tournamentWins;
    }

    public void setTournamentWins(int tournamentWins) {
        this.tournamentWins = tournamentWins;
    }

    @Override
    public void displayRole() {
        System.out.println("Peran: Pemain Catur Profesional");
    }

    @Override
    public String toString() {
        return super.toString() + ", Jumlah Turnamen yang dimenangkan: " + tournamentWins;
    }
}
