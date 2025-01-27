package hell;

import hell.entities.miscellaneous.HeroInventory;
import hell.entities.miscellaneous.heroes.Assassin;
import hell.entities.miscellaneous.heroes.Barbarian;
import hell.entities.miscellaneous.heroes.Wizard;
import hell.entities.miscellaneous.items.CommonItem;
import hell.entities.miscellaneous.items.RecipeItem;
import hell.interfaces.*;
import hell.io.ConsoleInputReader;
import hell.io.ConsoleOutputWriter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    private static Map<String, Hero> heroes = new LinkedHashMap<>();
    public static void main(String[] args) {
        InputReader inputReader = new ConsoleInputReader();
        OutputWriter outputWriter = new ConsoleOutputWriter();

        while (true){
            String line = inputReader.readLine();
            String[] tokens = line.split("\\s+");

            String command = tokens[0];
            String[] arguments = Arrays.stream(tokens).skip(1).toArray(String[]::new);

            String result = interpetCommand(command, arguments);

            outputWriter.writeLine(result);

            if ("Quit".equals(line)){
                break;
            }
        }
    }

    private static String interpetCommand(String command, String[] arguments) {
        Hero hero;
        switch (command){
            case "Hero":
                hero = createHero(arguments[0], arguments[1]);

                heroes.put(hero.getName(), hero);

                return String.format("Created %s - %s", hero.getClass().getSimpleName(), hero.getName());

            case "Item":
                hero = heroes.get(arguments[1]);
                Item commonItem = new CommonItem(
                        arguments[0],
                        Integer.parseInt(arguments[2]),
                        Integer.parseInt(arguments[3]),
                        Integer.parseInt(arguments[4]),
                        Integer.parseInt(arguments[5]),
                        Integer.parseInt(arguments[6])
                );

                hero.addItem(commonItem);

                return String.format("Added item - %s to Hero - %s", commonItem.getName(), hero.getName());

            case "Recipe":
                hero = heroes.get(arguments[1]);
                Recipe recipeItem = new RecipeItem(
                        arguments[0],
                        Integer.parseInt(arguments[2]),
                        Integer.parseInt(arguments[3]),
                        Integer.parseInt(arguments[4]),
                        Integer.parseInt(arguments[5]),
                        Integer.parseInt(arguments[6]),
                        Arrays.stream(arguments).skip(7).collect(Collectors.toList())
                );

                hero.addRecipe(recipeItem);

                return String.format("Added recipe - %s to Hero - %s", recipeItem.getName(), hero.getName());

            case "Inspect":
                hero = heroes.get(arguments[0]);
                return hero.toString();

            case "Quit":
                return printHeroesInfo();
        }

        return null;
    }

    private static String printHeroesInfo() {
        StringBuilder heroesinfo = new StringBuilder();

        final int[] index = {1};
        heroes.values().stream()
                .sorted(getHeroesComparator())
                .forEach(hero -> {

                    String items = null;
                    try {
                        items = hero.getItems().stream()
                                .map(item -> item.getName())
                                .collect(Collectors.joining(", "));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    if (items.equals("")){
                        items = "None";
                    }

                    heroesinfo
                            .append(String.format("%d. %s: %s", index[0]++,
                                    hero.getClass().getSimpleName(),
                                    hero.getName()))
                            .append(System.lineSeparator())
                            .append(String.format("###HitPoints: %d", hero.getHitPoints()))
                            .append(System.lineSeparator())
                            .append(String.format("###Damage: %d", hero.getDamage()))
                            .append(System.lineSeparator())
                            .append(String.format("###Strength: %d", hero.getStrength()))
                            .append(System.lineSeparator())
                            .append(String.format("###Agility: %d", hero.getAgility()))
                            .append(System.lineSeparator())
                            .append(String.format("###Intelligence: %d", hero.getIntelligence()))
                            .append(System.lineSeparator())
                            .append(String.format("###Items: %s", items))
                            .append(System.lineSeparator());
                });

        return heroesinfo.toString();
    }

    private static Comparator<? super Hero> getHeroesComparator() {
        return (hero1, hero2) -> {
            long sumStrengthAgilityIntelligenceOfHero1 = hero1.getStrength() + hero1.getAgility() + hero1.getIntelligence();
            long sumStrengthAgilityIntelligenceOfHero2 = hero2.getStrength() + hero2.getAgility() + hero2.getIntelligence();
            long comparatorBySumStrengthAgilityIntelligence = sumStrengthAgilityIntelligenceOfHero2 - sumStrengthAgilityIntelligenceOfHero1;
            if (comparatorBySumStrengthAgilityIntelligence > 0){
                return 1;
            } else if (comparatorBySumStrengthAgilityIntelligence < 0){
                return -1;
            }

            long sumHitpointsDamageOfHero1 = hero1.getHitPoints() + hero1.getDamage();
            long sumHitpointsDamageOfHero2 = hero2.getHitPoints() + hero2.getDamage();
            long comparatorBySumHitpointsDamage =  sumHitpointsDamageOfHero2 - sumHitpointsDamageOfHero1;
            if (comparatorBySumHitpointsDamage == 0){
                return 0;
            } else if (comparatorBySumHitpointsDamage > 1){
                return 1;
            } else {
                return -1;
            }
        };
    }

    private static Hero createHero(String name, String type) {
        switch (type){
            case "Barbarian":
                return new Barbarian(name, new HeroInventory());
            case "Assassin":
                return new Assassin(name, new HeroInventory());
            case "Wizard":
                return new Wizard(name, new HeroInventory());
        }

        return null;
    }
}