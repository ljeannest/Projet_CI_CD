package com.cicdlecturesy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "Menucli", mixinStandardHelpOptions = true, version = "checksum 4.0",
        description = "")
class Menucli implements Callable<Integer> {

    @Parameters(index = "0", description = "Choose the action to do between list-menus and delete-menu")
    private String Action;

    @Option(names={"-i"}, description = "id of the menu to delete")
    private String idMenu;

    @Option(names = {"--server-url"}, description = "Set url API Url")
    private String ServeurURL= "https://menu-server-jmz.herokuapp.com";

    @Override
    public Integer call() throws Exception {
        if (Action.equals("list-menus")) {
            System.out.println("Listing menus :");
            URL url = new URL(ServeurURL+"/menus");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(con.getInputStream()))) {
                    for (String line; (line = reader.readLine()) != null; ) {
                        for (int j = 0; j<line.split("]").length-1; j++){
                            System.out.println("id = " + line.split("]")[j].split(":")[1].split(",")[0] + " (" + line.split("]")[j].split("\"")[5] + ") :");
                            for (int i = 0; i<line.split("]")[j].split("id").length-2; i++) {
                                System.out.println("- " + line.split("]")[j].split("id")[i+2].split("\"")[4]);
                            }
                        }   
                    }
                }
        } else if (Action.equals("delete-menu")){
            System.out.println("Menu "+ idMenu + " deleted");
        }
        return 0;
    }

    // this example implements Callable, so parsing, error handling and handling user
    // requests for usage help or version help can be done with one line of code.
    public static void main(String... args) {
        int exitCode = new CommandLine(new Menucli()).execute(args);
        System.exit(exitCode);
    }
}