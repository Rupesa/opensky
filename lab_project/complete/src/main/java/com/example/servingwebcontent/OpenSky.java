package com.example.servingwebcontent;

import java.net.*;
import java.util.*;
import java.io.*;
import com.google.gson.Gson; 

public class OpenSky {

    
    public static String getJson() {

        /*
        String inline = "";

        try {
            URL url = new URL("https://opensky-network.org/api");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();


            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if(responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " +responsecode);
            else {
                Scanner sc = new Scanner(url.openStream());
                while(sc.hasNext()) {
                    inline+=sc.nextLine();
                }
                sc.close();
            }
        } catch(IOException ex) {
             
        }
        */

        String output = "";

        try {
            String url = "https://opensky-network.org/api/states/all";

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.out.println("Erro " + conn.getResponseCode() + " ao obter dados da URL " + url);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            //String output = "";
            String line;
            while ((line = br.readLine()) != null) {
                output += line;
            }

            conn.disconnect();

            Gson gson = new Gson();
            Data dados = gson.fromJson(new String(output.getBytes()), Data.class);

            System.out.println("TIME: " + dados.getTime());
            System.out.println("STATES: " + Arrays.toString(dados.getStates()[0]));
            
        } catch (IOException ex) {
            //Logger.getLogger(APIRest.class.getName()).log(Level.SEVERE, null, ex);
        }

        return output;
    }

}