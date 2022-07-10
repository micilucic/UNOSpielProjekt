package com.company;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


import static com.company.UnoApp.players;
import static com.company.UnoApp.round;

public class DemoApp {
    private static final String CREATETABLE = "CREATE TABLE Sessions (Player varchar(100) NOT NULL, Session int NOT NULL, Round int NOT NULL, Score int NOT NULL, CONSTRAINT PK_Sessions PRIMARY KEY (Player, Session, Round));";
    private static final String INSERT_TEMPLATE = "INSERT INTO Sessions (Player, Session, Round, Score) VALUES ('%1s', %2d, %3d, %4d);";
    private static final String SELECT_BYPLAYERANDSESSION = "SELECT Player, SUM(Score) AS Score FROM Sessions WHERE Player = '%1s' AND Session = %2d;";
    private static final String SELECT_ACTUALPOINTS = "SELECT Player, Score FROM Sessions WHERE Player = '%1s' AND Session = %2d;";
    private static int session = 0;
    public static SqliteClient client;
    private static int sum = 0;

    static {
        try {
            SqliteClient client = new SqliteClient("demodatabase.sqlite");
            if (client.tableExists("Sessions")) {
                client.executeStatement("DROP TABLE Sessions;");
            }
            client.executeStatement(CREATETABLE);

            for (Player p : players) {
                if (p.getHandCards() == null) {
                    client.executeStatement(String.format(INSERT_TEMPLATE, p.getName(), session, round++, 0));
                } else {
                    client.executeStatement(String.format(INSERT_TEMPLATE, p.getName(), session, round++,0));   }
            }
           sum =  UnoApp.collectPointsFromThisRound();
            System.out.println("Round: " + round + " is starting! The winner has: " + sum + " points.");
            for(Player p: players) {
                ArrayList<HashMap<String, String>> results = client.executeQuery(String.format(SELECT_BYPLAYERANDSESSION, p.getName(), 1));

                for (HashMap<String, String> map : results) {
                    System.out.println(map.get("Player") + " hat derzeit:  " + map.get("Score") + " Punkte");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Ups! Something went wrong:" + ex.getMessage());
        }
    }

            private void readRoundUpdate () {
                for (Player p : players) {
                    readFromDatabase(p.getName(), session);
                }
            }

            private void readFromDatabase (String playerName,int session){
                ArrayList<HashMap<String, String>> results = null;
                try {
                    results = client.executeQuery(String.format(SELECT_BYPLAYERANDSESSION, playerName, session));
                    for (HashMap<String, String> map : results) {
                        System.out.println(map.get("Player") + " has currently:  " + map.get("Score") + " points.");
                    }
                } catch (SQLException ex) {
                    System.out.println("Ups! Something went wrong:" + ex.getMessage());
                }

            }




}