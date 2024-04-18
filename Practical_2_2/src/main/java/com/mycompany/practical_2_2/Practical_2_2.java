/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.practical_2_2;

import java.io.FileWriter;
import java.io.IOException;
import static java.time.Clock.system;
import static java.time.InstantSource.system;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author dmirtypoluektov
 */
public class Practical_2_2 {
    public static void main(String[] args) {
        System.out.println("Practical task 2.2, Student Dmitry Poluektov, RIBO-01-22");
        String server = "https://android-for-students.ru";
        String serverPath = "/materials/practical/hello.php";
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "Poluektov");
        map.put("group", ""); 
        HTTPRunnable httpRunnable = new HTTPRunnable(server + serverPath, map);
        Thread th = new Thread(httpRunnable);
        th.start();
        try {
            th.join();
        } catch (InterruptedException ex) {
            
        } finally {
            String responseBody = httpRunnable.getResponseBody();
            if (responseBody != null && !responseBody.isEmpty()) {
                JSONObject jSONObject = new JSONObject(responseBody);
                int result = jSONObject.optInt("result_code");
                System.out.println("Result: " + result);
                System.out.println("Type: " + jSONObject.optString("message_type"));
                System.out.println("Text: " + jSONObject.optString("message_text"));
                switch (result) {
                    case 1:
                        JSONArray jSONArray = jSONObject.optJSONArray("task_list");
                        if (jSONArray != null) {
                            System.out.println("Task list: ");
                            for (int i = 0; i < jSONArray.length(); i++) {
                                System.out.println((i + 1) + ") " + jSONArray.getString(i));
                            }
                        }
                        break;
                    case 0:
                        break;
                    default:
                        String errorMessage = jSONObject.optString("error_message", "Unknown error");
                        System.out.println("Error: " + errorMessage);
                        break;
                }
            }
        }
    }
}