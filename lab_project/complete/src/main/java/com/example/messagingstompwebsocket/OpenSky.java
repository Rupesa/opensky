package com.example.messagingstompwebsocket;

import java.net.*;
import java.util.*;
import java.io.*;
import com.google.gson.Gson; 

public class OpenSky {

    
    public static Data getJson() {

        String output = "";

        Data dados = null;

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

            //output = output + "TIME: " + dados.getTime()+"\n\n";

            
        } catch (IOException ex) {
            //Logger.getLogger(APIRest.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dados;
    }

    public static Flight[] getHistoric(String begin, String end) {

        String output = "";

        Flight[] flights = null;

        try {
            //String url = "https://opensky-network.org/api/states/all";

            String url = "https://opensky-network.org/api/flights/departure?airport=LSZH&begin="+begin+"&end="+end+"";
            
            //String url = "https://opensky-network.org/api/flights/departure?airport=LSZH&begin=1617836400&end=1618393890";

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
            flights = gson.fromJson(new String(output.getBytes()), Flight[].class);
            
        } catch (IOException ex) {
            //Logger.getLogger(APIRest.class.getName()).log(Level.SEVERE, null, ex);
        }

        return flights;
    }

}