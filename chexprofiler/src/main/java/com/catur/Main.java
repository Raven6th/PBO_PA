// File: Main.java
package com.catur;

import java.util.Scanner;
import java.util.ArrayList;
import java.sql.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static AccountController accountController;
    private static ChessProfileController profileController;
    private static String loggedInState, loginusername;

    public static void main(String[] args) {
        DB db = new DB();
        accountController = new AccountController(db.getConnection());
        profileController = new ChessProfileController(db.getConnection());
        cls();
        System.out.println("Selamat datang dalam CheXProfiler!");
        while (true) {
    
            if (loggedInState == null) {
                cls();
                System.out.println("|==========================================|");
                System.out.println("|                                          |");
                System.out.println("|    Selamat datang di CheXProfiler        |");
                System.out.println("|                                          |");
                System.out.println("|==========================================|");
                System.out.println("| Silahkan registrasi jika belum terdaftar |");
                System.out.println("|==========================================|");
                System.out.println("                                           ");
                System.out.println("|==========================================|");
                System.out.println("|       1. Register                        |");
                System.out.println("|       2. Login                           |");
                System.out.println("|       3. Keluar                          |");
                System.out.println("|==========================================|");
                System.out.print(" Masukkan pilihan anda: ");

                int choice = getIntInput();

                switch (choice) {
                    case 1:
                        register();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        System.out.println("Program akan keluar");
                        System.exit(0);
                    default:
                        System.out.println("Pilihan tidak valid, mohon coba lagi.");
                }
            } else if (loggedInState.equals("admin")) {
                menuadmin();
            } else if (loggedInState.equals("user")) {
                menuuser();
            }
        }
    }

    // ============================================================================ USER
    private static void menuuser() {
        cls();
        System.out.println("=======================================");
        System.out.println("\n   Menu Utama User " + loginusername);
        System.out.println("                                       ");
        System.out.println("=======================================");
        System.out.println("                                       ");
        System.out.println("=======================================");
        System.out.println("|1. Buat profil saya                   |");
        System.out.println("|2. Lihat profil saya                  |");
        System.out.println("|3. Edit profil saya                   |");
        System.out.println("|4. Hapus profil saya                  |");
        System.out.println("|5. Tampilkan Top 10 Pemain Catur      |");
        System.out.println("|6. Logout                             |");
        System.out.println("|7. Keluar                             |");
        System.out.println("=======================================");
    
        System.out.print("Masukkan pilihan anda: ");
        int choice = getIntInput();
    
        switch (choice) {
            case 1:
                createMyProfile();
                break;
            case 2:
                viewMyProfile();
                break;
            case 3:
                updateMyProfile();
                break;
            case 4:
                deleteMyProfile();
                break;
            case 5:
                TopChessPlayers.showTopPlayers();
                waitinput();
                break;
            case 6:
                logout();
                break;
            case 7:
                System.out.println("Program akan keluar");
                System.exit(0);
            default:
                System.out.println("Pilihan tidak valid, mohon coba lagi.");
        }
    }

    private static void createMyProfile() {
        int playerType;
        while (true) { 
            cls();   
            System.out.print("==============================");
            System.out.println("\n   Masukkan role anda");
            System.out.print("==============================");
            System.out.println("\n1. Kasual \n2. Profesional \n3. Coach \n4. Keluar ");
            System.out.print("==============================");
            System.out.print("\nMasukkan pilihan anda: ");
            playerType = getIntInput();
            
            if (playerType >= 1 && playerType <= 3) {
                break;
            }
            else if (playerType == 4) {
                return;
            }
            else {
                System.out.println("Jenis profil tidak valid!");
                waitinput();
            }
        }

        System.out.println("\nMasukkan detail profil anda:");

        System.out.print("Nama: " + loginusername + "\n");
        String name = loginusername;

        System.out.print("Umur: ");
        int age = getIntInput();

        System.out.print("Rating: ");
        int rating = getIntInput();

        System.out.print("Pembuka Favorit: ");
        String preferredOpenings = scanner.nextLine();

        try {
            switch (playerType) {
                case 1:
                    profileController.addChessProfile(new ChessPlayer(name, age, rating, preferredOpenings));
                    System.out.println("Profil anda tersimpan.");
                    waitinput();
                    break;
                case 2:
                    System.out.print("Jumlah Turnamen yang dimenangkan: ");
                    int tournamentWins = getIntInput();
                    profileController.addChessProfile(new ProfessionalChessPlayer(name, age, rating, preferredOpenings, tournamentWins));
                    System.out.println("Profil anda tersimpan.");
                    waitinput();
                    break;
                case 3:
                    System.out.print("Pengalaman Melatih (tahun): ");
                    int coachingExperience = getIntInput();
                    System.out.print("Spesialisasi: ");
                    String specialization = scanner.nextLine();
                    profileController.addChessProfile(new ChessCoach(name, age, rating, preferredOpenings, coachingExperience, specialization));
                    System.out.println("Profil anda tersimpan.");
                    waitinput();
                    break;
                default:
                    System.out.println("Jenis profil tidak valid!");
                    waitinput();
                    break;
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat menambahkan profil: " + e.getMessage());
            waitinput();
        }
    }
    
    private static void viewMyProfile() {
        cls();
        try {
            ChessProfile profile = profileController.getProfileByName(loginusername);
            if (profile == null) {
                System.out.println("Anda belum memiliki profil.");
                waitinput();
            } else {
                System.out.println("|============|");
                System.out.println("|   Profil   |");
                System.out.println("|============|========================================");
                profile.displayRole();
                System.out.println(profile);
                System.out.println("=======================================================");
                waitinput();
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat mengambil profil: " + e.getMessage());
            waitinput();
        }
    }
    
    private static void updateMyProfile() {
        cls();
        try {
            ChessProfile profile = profileController.getProfileByName(loginusername);
            if (profile == null) {
                System.out.println("Anda belum memiliki profil.");
                waitinput();
            } else {
                System.out.println("=========================");
                System.out.println("     Profile " + loginusername);
                System.out.println("=========================");
                System.out.println("Masukkan detail baru:");
    
                System.out.print("Umur: ");
                int age = getIntInput();
    
                System.out.print("Rating: ");
                int rating = getIntInput();
    
                System.out.print("Pembuka Favorit: ");
                String preferredOpenings = scanner.nextLine();
    
                ChessProfile updatedProfile;
                if (profile instanceof ProfessionalChessPlayer) {
                    System.out.print("Jumlah Turnamen yang dimenangkan: ");
                    int tournamentWins = getIntInput();
                    updatedProfile = new ProfessionalChessPlayer(loginusername, age, rating, preferredOpenings, tournamentWins);
                } else if (profile instanceof ChessCoach) {
                    System.out.print("Pengalaman Melatih (tahun): ");
                    int coachingExperience = getIntInput();
                    System.out.print("Spesialisasi: ");
                    String specialization = scanner.nextLine();
                    updatedProfile = new ChessCoach(loginusername, age, rating, preferredOpenings, coachingExperience, specialization);
                } else {
                    updatedProfile = new ChessPlayer(loginusername, age, rating, preferredOpenings);
                }
    
                profileController.updateProfileByName(loginusername, updatedProfile);
                System.out.println("\nProfil anda telah diedit!");
                waitinput();
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat mengambil profil: " + e.getMessage());
            waitinput();
        }
    }
    
    private static void deleteMyProfile() {
        cls();
        try {
            profileController.deleteProfileByName(loginusername);
            System.out.println("Profil anda telah dihapus!");
            waitinput();
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat menghapus profil: " + e.getMessage());
            waitinput();
        }
    }
    
    // ============================================================================ AKUN
    private static void register() {
        cls();
        try {
            System.out.print("|======================|");
            System.out.print("\n| Silahkan Registrasi! |");
            System.out.print("\n|======================|");
            System.out.print("\nMasukkan username: ");
            String username = scanner.nextLine();

            if (isUsernameExist(username)) {
                System.out.println("Username sudah ada. Silahkan pilih username lain...");
                waitinput();
                return;
            }

            System.out.print("Masukkan password: ");
            String password = scanner.nextLine();
            accountController.createAkun(new Account(username, password));
            System.out.println("\nRegistrasi berhasil.");
            loggedInState = "user";
            loginusername = username;
            waitinput();
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat registrasi: " + e.getMessage());
            waitinput();
        }
    }

    private static boolean isUsernameExist(String username) {
        try {
            for (Account account : accountController.getAllAkun()) {
                if (account.getUsername().equals(username)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            cls();
            System.out.println("Terjadi kesalahan saat memeriksa username: " + e.getMessage());
            waitinput();
        }
        return false;
    }

    private static void login() {
        cls();
        try {
            System.out.print("|=================|");
            System.out.print("\n| Silahkan Login! |");
            System.out.print("\n|=================|");
            System.out.print("\nMasukkan username: ");
            String username = scanner.nextLine();
            System.out.print("Masukkan password: ");
            String password = scanner.nextLine();

            for (Account account : accountController.getAllAkun()) {
                if (account.authenticate(username, password)) {
                    loggedInState = account.getRole();
                    loginusername = account.getUsername();
                    System.out.println("Login berhasil.");
                    waitinput();
                    return;
                }
            }
            System.out.println("Username atau password salah.");
            waitinput();
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat login: " + e.getMessage());
            waitinput();
        }
    }

    private static void logout() {
        cls();
        loginusername = null;
        loggedInState = null;
    }

    // ============================================================================ ADMIN
    private static void menuadmin() {
        cls();
        System.out.println("=========================================");
        System.out.println("\n          Menu Utama Admin");
        System.out.println("=========================================");
        System.out.println("                                       ");
        System.out.println("=========================================");
        System.out.println("|1. Tambah profil                       |");
        System.out.println("|2. Lihat semua profil                  |");
        System.out.println("|3. Edit profil                         |");
        System.out.println("|4. Hapus profil                        |");
        System.out.println("|5. Tampilkan Top 10 Pemain Catur       |");
        System.out.println("|6. Logout                              |");
        System.out.println("|7. Keluar                              |");
        System.out.println("=========================================");

        System.out.print("Masukkan pilihan anda: ");
        int choice = getIntInput();

        switch (choice) {
            case 1:
                addPlayer();
                break;
            case 2:
                viewAllPlayers();
                break;
            case 3:
                updatePlayer();
                break;
            case 4:
                deletePlayer();
                break;
            case 5:
                TopChessPlayers.showTopPlayers();
                waitinput();
                break;
            case 6:
                logout();
                break;
            case 7:
                System.out.println("Program akan keluar");
                System.exit(0);
            default:
                System.out.println("Pilihan tidak valid, mohon coba lagi.");
        }
    }

    private static void addPlayer() {
        cls();
        int playerType;
        while (true) { 
            cls();   
            System.out.print("==============================");
            System.out.println("\n   Masukkan role anda : ");
            System.out.print("==============================");
            System.out.println("\n1. Kasual \n2. Profesional \n3. Coach \n4. Keluar");
            System.out.print("==============================");
            System.out.print("\nMasukkan pilihan anda: ");
            playerType = getIntInput();
            if (playerType >= 1 && playerType <= 3) {
                break;
            }
            else if (playerType == 4) {
                return;
            }
            else {
                System.out.println("Jenis profil tidak valid!");
                waitinput();
            }
        }

        System.out.println("\nMasukkan detail profil player:");

        System.out.print("Nama: ");
        String name = scanner.nextLine();

        System.out.print("Umur: ");
        int age = getIntInput();

        System.out.print("Rating: ");
        int rating = getIntInput();

        System.out.print("Pembuka Favorit: ");
        String preferredOpenings = scanner.nextLine();

        try {
            switch (playerType) {
                case 1:
                    profileController.addChessProfile(new ChessPlayer(name, age, rating, preferredOpenings));
                    System.out.println("Profil tersimpan.");
                    waitinput();
                    break;
                case 2:
                    System.out.print("Jumlah Turnamen yang dimenangkan: ");
                    int tournamentWins = getIntInput();
                    profileController.addChessProfile(new ProfessionalChessPlayer(name, age, rating, preferredOpenings, tournamentWins));
                    System.out.println("Profil tersimpan.");
                    waitinput();
                    break;
                case 3:
                    System.out.print("Pengalaman Melatih (tahun): ");
                    int coachingExperience = getIntInput();
                    System.out.print("Spesialisasi: ");
                    String specialization = scanner.nextLine();
                    profileController.addChessProfile(new ChessCoach(name, age, rating, preferredOpenings, coachingExperience, specialization));
                    System.out.println("Profil tersimpan.");
                    waitinput();
                    break;
                default:
                    System.out.println("Jenis profil tidak valid!");
                    waitinput();
                    break;
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat menambahkan profil: " + e.getMessage());
            waitinput();
        }
    }

    private static void viewAllPlayers() {
        cls();
        try {
            ArrayList<ChessProfile> profiles = profileController.getAllProfiles();
            if (profiles.isEmpty()) {
                System.out.println("Tidak ada profil yang tersimpan.");
                waitinput();
                return;
            }
            
            System.out.println("============================");
            System.out.println("|      Profil player:      |");
            System.out.println("=============================================================");
            for (ChessProfile player : profiles) {
                player.displayRole();
                System.out.println(player);
                System.out.println("=============================================================");
            }
            waitinput();
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat mengambil profil: " + e.getMessage());
            waitinput();
        }
    }

    private static void updatePlayer() {
        cls();
        System.out.println("===========================================");
        System.out.println("|Masukkan nama profil yang ingin diedit:  |");
        System.out.println("===========================================");
        System.out.println("Nama: ");
        String name = scanner.nextLine();
    
        try {
            ArrayList<ChessProfile> profiles = profileController.getAllProfiles();
            boolean found = false;
            for (ChessProfile player : profiles) {
                if (player.getName().equalsIgnoreCase(name)) {
                    System.out.println("Masukkan detail baru:");
    
                    System.out.print("Nama: ");
                    String newName = scanner.nextLine();
                    
                    System.out.print("Umur: ");
                    int age = getIntInput();
    
                    System.out.print("Rating: ");
                    int rating = getIntInput();
    
                    System.out.print("Pembuka Favorit: ");
                    String preferredOpenings = scanner.nextLine();
                    System.out.println("\n========================================");

                    ChessProfile updatedProfile;
                    if (player instanceof ProfessionalChessPlayer) {
                        System.out.print("Jumlah Turnamen yang dimenangkan: ");
                        int tournamentWins = getIntInput();
                        updatedProfile = new ProfessionalChessPlayer(newName, age, rating, preferredOpenings, tournamentWins);
                    } else if (player instanceof ChessCoach) {
                        System.out.print("Pengalaman Melatih (tahun): ");
                        int coachingExperience = getIntInput();
                        System.out.print("Spesialisasi: ");
                        String specialization = scanner.nextLine();
                        updatedProfile = new ChessCoach(newName, age, rating, preferredOpenings, coachingExperience, specialization);
                    } else {
                        updatedProfile = new ChessPlayer(newName, age, rating, preferredOpenings);
                    }
    
                    profileController.updateProfileByName(name, updatedProfile);
                    System.out.println("\nProfil player telah diedit!");
                    found = true;
                    waitinput();
                    break;
                }
            }
    
            if (!found) {
                System.out.println("Profil tidak ditemukan!");
                waitinput();
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat mengambil profil: " + e.getMessage());
            waitinput();
        }
    }
    

    private static void deletePlayer() {
        cls();
        System.out.println("\nMasukkan nama profil yang ingin dihapus:");
        String name = scanner.nextLine();

        try {
            profileController.deleteProfileByName(name);
            System.out.println("Profil dihapus!");
            waitinput();
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat menghapus profil: " + e.getMessage());
            waitinput();
        }
    }

    public static void cls() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka, mohon Coba lagi.");
            }
        }
    }


    private static void waitinput() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
}
