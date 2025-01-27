package p08_MilitaryElite;

import p08_MilitaryElite.contracts.Mission;
import p08_MilitaryElite.contracts.Private;
import p08_MilitaryElite.contracts.Repair;
import p08_MilitaryElite.contracts.Soldier;
import p08_MilitaryElite.impl.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Map<String, Soldier> soldiers = new LinkedHashMap<>();
        Map<String, Private> privates = new HashMap<>();
        
        while (true){
            String line = reader.readLine();
            if ("End".equals(line)){
                break;
            }
            
            String[] tokens = line.split("\\s+");
            String soldierType = tokens[0];
            String id = tokens[1];
            String firstName = tokens[2];
            String lastName = tokens[3];
            double salary;

            Soldier soldier = null;
            switch (soldierType){
                case "Private":
                    salary = Double.parseDouble(tokens[4]);
                    Private privateSoldier = new PrivateImpl(id, firstName, lastName, salary);
                    soldiers.put(id, privateSoldier);
                    privates.put(id, privateSoldier);
                    break;
                case "LeutenantGeneral":
                    salary = Double.parseDouble(tokens[4]);
                    List<String> privatesIds = Arrays.stream(tokens).skip(5).collect(Collectors.toList());
                    Set<Private> privateSet = getPrivates(privatesIds, privates);
                    soldier = new LeutenantGeneralImpl(id, firstName, lastName, salary, privateSet);
                    break;
                case "Engineer":
                    try {
                        salary = Double.parseDouble(tokens[4]);
                        String corps = tokens[5];
                        String[] repairsTokens = Arrays.stream(tokens).skip(6).toArray(String[]::new);
                        Set<Repair> repairs = crateRepairs(repairsTokens);
                        soldier = new EngineerImpl(id, firstName, lastName, salary, corps, repairs);
                    } catch (IllegalArgumentException ignored){
                        ;
                    }
                    break;
                case "Commando":

                        salary = Double.parseDouble(tokens[4]);
                        String corps = tokens[5];
                        String[] missionsTokens = Arrays.stream(tokens).skip(6).toArray(String[]::new);
                        Set<Mission> missions = crateMissions(missionsTokens);
                        soldier = new CommandoImpl(id, firstName, lastName, salary, corps, missions);
                    break;
                case "Spy":
                    String codeNumber = tokens[4];
                    soldier = new SpyImpl(id, firstName, lastName, codeNumber);
                    break;
                default:
                    break;
            }

            if (soldier != null){
                soldiers.put(id, soldier);
            }
        }

        soldiers.values()
                .forEach(s -> System.out.println(s.toString()));
    }

    private static Set<Mission> crateMissions(String[] missionsTokens) {

        Set<Mission> missions = new LinkedHashSet<>();

        Mission mission;
        for (int i = 0; i < missionsTokens.length; i+= 2) {
            String codeName = missionsTokens[i];
            String state = missionsTokens[i + 1];

            try {
                mission = new MissionImpl(codeName, state);
                missions.add(mission);
            } catch (IllegalArgumentException ignored){
                ;
            }
        }

        return missions;
    }

    private static Set<Repair> crateRepairs(String[] repairsTokens) {

        Set<Repair> repairs = new LinkedHashSet<>();

        for (int i = 0; i < repairsTokens.length; i+= 2) {
            String partName = repairsTokens[i];
            int hoursWoring = Integer.parseInt(repairsTokens[i + 1]);

            Repair repair = new RepairImpl(partName, hoursWoring);
            repairs.add(repair);
        }

        return repairs;
    }

    private static Set<Private> getPrivates(List<String> privatesIds, Map<String,Private> privates) {
        return privates.values().stream()
                .filter(p -> privatesIds.contains(p.getId()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
