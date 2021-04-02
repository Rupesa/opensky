package com.example.servingwebcontent;

import java.net.*;
import java.util.*;
import java.io.*;
import com.google.gson.Gson; 

public class OpenSky {

    
    public static Data getJson() {

        String output = "";
        String output1 = "";
        
        Data dados = new Data(); 
        
        try {
            //String url = "https://opensky-network.org/api/states/all";
            String url = "https://opensky-network.org/api/states/all?lamin=45.8389&lomin=5.9962&lamax=47.8229&lomax=10.5226";

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
            dados = gson.fromJson(new String(output.getBytes()), Data.class);

            System.out.println("TIME: " + dados.getTime());
            System.out.println("STATES: " + Arrays.toString(dados.getStates()[0]));
            
           

            //output1 = output1 + "TIME: " + dados.getTime()+"\n\n" + "1st in the list" + dados.getStates()[0][0]+ "";

            
        } catch (IOException ex) {
            //Logger.getLogger(APIRest.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dados;
    }

}