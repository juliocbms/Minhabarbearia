package com.minhabarbearia.barbearia;

import java.io.File;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class TriggerUpdater {
    public static void main(String[] args) {
        try {
            File trigger = new File("src/main/resources/trigger.txt");


            if (!trigger.exists()) {
                trigger.createNewFile();
            }


            try (FileWriter writer = new FileWriter(trigger, false)) {
                writer.write(String.valueOf(new Date().getTime()));
            }

            System.out.println("Trigger atualizado: " + trigger.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

