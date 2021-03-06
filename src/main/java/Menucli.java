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
            System.out.println("Listing menus :\n\n");
            URL url = new URL(ServeurURL+"/menus");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
                    String lines[]=reader.readLine().split("}]}");
                    if (lines[0].contains("[]")) {
                        System.out.println("Aucun menu disponible...");
                    }
                    else{
                        for (int i=0; i<lines.length-1; i++) {
                            String line=lines[i];

                            System.out.println("ID du menu : "+line.split(":")[1].split(",")[0]);
                            System.out.println("Nom du menu :"+line.split(":")[2].split(",")[0]+"\n"); 
                            System.out.println("Plats :") ;
                            int len = line.split(":").length;
                            for (int k=0 ; k<(len-4)/2; k++){
                                System.out.println("Plat "+(k+1)+" :"+line.split(":")[3+(k+1)*2].split("}")[0]); 
                            }
                            System.out.println("\n");
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println("Aucun menu disponible...");
                }
        } else if (Action.equals("delete-menu")){
            System.out.println("Menu "+ idMenu + " deleted");
            URL url = new URL(ServeurURL+"/menus/"+idMenu);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestProperty("Content-Type","application/json");
            httpCon.setRequestMethod("DELETE");
            httpCon.connect();
        }
        return 0;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new Menucli()).execute(args);
        System.exit(exitCode);
    }
}