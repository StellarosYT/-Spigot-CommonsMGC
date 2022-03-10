package net.mythigame.commonsMGC.Kronos.managers;

import net.mythigame.commons.AccountCase;
import net.mythigame.commons.Utils.TimeUnit;

public class TimeManager {

    public static String getTimeLeft(AccountCase accountCase){
        long endTime = accountCase.getEnd_time();
        if(endTime == -1){
            return "Permanent";
        }
        long tempsRestant = (endTime - System.currentTimeMillis()) / 1000;
        int annees = 0;
        int mois = 0;
        int semaines = 0;
        int jours = 0;
        int heures = 0;
        int minutes = 0;
        int secondes = 0;

        while (tempsRestant >= TimeUnit.ANNEE.getToSecond()){
            annees++;
            tempsRestant -= TimeUnit.ANNEE.getToSecond();
        }
        while (tempsRestant >= TimeUnit.MOIS.getToSecond()){
            mois++;
            tempsRestant -= TimeUnit.MOIS.getToSecond();
        }
        while (tempsRestant >= TimeUnit.SEMAINE.getToSecond()){
            semaines++;
            tempsRestant -= TimeUnit.SEMAINE.getToSecond();
        }
        while (tempsRestant >= TimeUnit.JOUR.getToSecond()){
            jours++;
            tempsRestant -= TimeUnit.JOUR.getToSecond();
        }
        while (tempsRestant >= TimeUnit.HEURE.getToSecond()){
            heures++;
            tempsRestant -= TimeUnit.HEURE.getToSecond();
        }
        while (tempsRestant >= TimeUnit.MINUTE.getToSecond()){
            minutes++;
            tempsRestant -= TimeUnit.MINUTE.getToSecond();
        }
        while (tempsRestant >= TimeUnit.SECONDE.getToSecond()){
            secondes++;
            tempsRestant -= TimeUnit.SECONDE.getToSecond();
        }
        String StrAnnees;
        String StrMois;
        String StrSemaines;
        String StrJours;
        String StrHeures;
        String StrMinutes;
        String StrSecondes;

        if(annees != 0){
            StrAnnees = annees + " " + TimeUnit.ANNEE.getName() + ", ";
        }else StrAnnees = "";

        if(mois != 0){
            StrMois = mois + " " + TimeUnit.MOIS.getName() + ", ";
        }else StrMois = "";

        if(semaines != 0){
            StrSemaines = semaines + " " + TimeUnit.SEMAINE.getName() + ", ";
        }else StrSemaines = "";

        if(jours != 0){
            StrJours = jours + " " + TimeUnit.JOUR.getName() + ", ";
        }else StrJours = "";

        if(heures != 0){
            StrHeures = heures + " " + TimeUnit.HEURE.getName() + ", ";
        }else StrHeures = "";

        if(minutes != 0){
            StrMinutes = minutes + " " + TimeUnit.MINUTE.getName() + ", ";
        }else StrMinutes = "";

        if(secondes != 0){
            StrSecondes = secondes + " " + TimeUnit.SECONDE.getName() + ", ";
        }else StrSecondes = "";

        if(annees > 0){
            return StrAnnees.replace(", ", ".");
        }else if(mois > 0 || semaines > 0){
            return StrMois + StrSemaines.replace(", ", ".");
        }else if(jours > 0){
            return StrJours;
        }else return StrHeures + StrMinutes + StrSecondes.replace(", ", ".");
    }
}
