package com.catur;

public final class TopChessPlayers {
    private static final int num = 10;
    private static final String[] topPlayers = {
        "Magnus Carlsen", "Fabiano Caruana", "Ding Liren", "Ian Nepomniachtchi",
        "Levon Aronian", "Wesley So", "Maxime Vachier-Lagrave", 
        "Shakhriyar Mamedyarov", "Anish Giri", "Alexander Grischuk"
    };

    public static void showTopPlayers() {
        System.out.println("\nTop 10 Pemain Chess:");
        for (int i = 0; i < Math.min(num, topPlayers.length); i++) {
            System.out.println((i + 1) + ". " + topPlayers[i]);
        }
    }
}