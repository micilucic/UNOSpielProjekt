package com.company;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class UnoSpiel {

    public static void main(String[] args) throws SQLException, IOException {
        Scanner input = new Scanner(System.in);

        UnoApp app = new UnoApp(input, System.out);

        app.Run();
    }
}
