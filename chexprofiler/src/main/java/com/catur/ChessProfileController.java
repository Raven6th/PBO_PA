// File: ChessProfileController.java
package com.catur;

import java.sql.*;
import java.util.ArrayList;

public class ChessProfileController {
    private Connection connection;

    public ChessProfileController(Connection connection) {
        this.connection = connection;
    }

    public void addChessProfile(ChessProfile profile) throws SQLException {
        String query = "INSERT INTO ChessProfiles (name, age, rating, preferredOpenings, role, tournamentWins, coachingExperience, specialization) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, profile.getName());
            stmt.setInt(2, profile.getAge());
            stmt.setInt(3, profile.getRating());
            stmt.setString(4, profile.getPreferredOpenings());
            stmt.setString(5, profile.getClass().getSimpleName());
            if (profile instanceof ProfessionalChessPlayer) {
                stmt.setInt(6, ((ProfessionalChessPlayer) profile).getTournamentWins());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }
            if (profile instanceof ChessCoach) {
                stmt.setInt(7, ((ChessCoach) profile).getCoachingExperience());
                stmt.setString(8, ((ChessCoach) profile).getSpecialization());
            } else {
                stmt.setNull(7, Types.INTEGER);
                stmt.setNull(8, Types.VARCHAR);
            }
            stmt.executeUpdate();
        }
    }

    public ChessProfile getProfileByName(String name) throws SQLException {
        String query = "SELECT * FROM ChessProfiles WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int age = rs.getInt("age");
                    int rating = rs.getInt("rating");
                    String preferredOpenings = rs.getString("preferredOpenings");
                    String role = rs.getString("role");
    
                    ChessProfile profile;
                    if ("Professional".equals(role)) {
                        int tournamentWins = rs.getInt("tournamentWins");
                        profile = new ProfessionalChessPlayer(name, age, rating, preferredOpenings, tournamentWins);
                    } else if ("Coach".equals(role)) {
                        int coachingExperience = rs.getInt("coachingExperience");
                        String specialization = rs.getString("specialization");
                        profile = new ChessCoach(name, age, rating, preferredOpenings, coachingExperience, specialization);
                    } else {
                        profile = new ChessPlayer(name, age, rating, preferredOpenings);
                    }
    
                    return profile;
                }
            }
        }
        return null; // Profile not found
    }
    

    public ArrayList<ChessProfile> getAllProfiles() throws SQLException {
        ArrayList<ChessProfile> profiles = new ArrayList<>();
        String query = "SELECT * FROM ChessProfiles";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                int rating = rs.getInt("rating");
                String preferredOpenings = rs.getString("preferredOpenings");
                String role = rs.getString("role");

                ChessProfile profile;
                if ("Professional".equals(role)) {
                    int tournamentWins = rs.getInt("tournamentWins");
                    profile = new ProfessionalChessPlayer(name, age, rating, preferredOpenings, tournamentWins);
                } else if ("Coach".equals(role)) {
                    int coachingExperience = rs.getInt("coachingExperience");
                    String specialization = rs.getString("specialization");
                    profile = new ChessCoach(name, age, rating, preferredOpenings, coachingExperience, specialization);
                } else {
                    profile = new ChessPlayer(name, age, rating, preferredOpenings);
                }

                profiles.add(profile);
            }
        }
        return profiles;
    }

    public void updateProfileByName(String name, ChessProfile updatedProfile) throws SQLException {
        String query = "UPDATE ChessProfiles SET name = ?, age = ?, rating = ?, preferredOpenings = ?, tournamentWins = ?, coachingExperience = ?, specialization = ? WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, updatedProfile.getName());
            stmt.setInt(2, updatedProfile.getAge());
            stmt.setInt(3, updatedProfile.getRating());
            stmt.setString(4, updatedProfile.getPreferredOpenings());
            if (updatedProfile instanceof ProfessionalChessPlayer) {
                stmt.setInt(5, ((ProfessionalChessPlayer) updatedProfile).getTournamentWins());
                stmt.setNull(6, Types.INTEGER);
                stmt.setNull(7, Types.VARCHAR);
            } else if (updatedProfile instanceof ChessCoach) {
                stmt.setNull(5, Types.INTEGER);
                stmt.setInt(6, ((ChessCoach) updatedProfile).getCoachingExperience());
                stmt.setString(7, ((ChessCoach) updatedProfile).getSpecialization());
            } else {
                stmt.setNull(5, Types.INTEGER);
                stmt.setNull(6, Types.INTEGER);
                stmt.setNull(7, Types.VARCHAR);
            }
            stmt.setString(8, name);
            stmt.executeUpdate();
        }
    }

    public void deleteProfileByName(String name) throws SQLException {
        String query = "DELETE FROM ChessProfiles WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }
}
