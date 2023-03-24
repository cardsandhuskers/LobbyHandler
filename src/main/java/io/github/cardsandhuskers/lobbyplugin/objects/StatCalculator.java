package io.github.cardsandhuskers.lobbyplugin.objects;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class StatCalculator {
    LobbyPlugin plugin;
    HashMap<Integer, HashMap<String, StatHolder>> fullStatMap;
    ArrayList<PlayerHolder> playerHolders;

    public StatCalculator(LobbyPlugin plugin) {
        this.plugin = plugin;
        fullStatMap = new HashMap<>();
        playerHolders = new ArrayList<>();
    }

    public void calculateStats() {
        //integer is event num, then the game number, then the list of stat holders for every team for that event
        //HashMap<Integer, HashMap<LobbyPlugin.NextGame, ArrayList<StatHolder>>> fullStatHolderMap = new HashMap<>();


        //For each event starting from 1
        for(int i = 1; i <= plugin.getConfig().getInt("eventNum"); i++) {
            HashMap<String,StatHolder> statHolderMap = new HashMap<>();
            //read the CSV
            //HashMap<LobbyPlugin.NextGame, ArrayList<StatHolder>> statHolderMap = new HashMap<>();

            FileReader reader = null;
            try {
                reader = new FileReader("plugins/LobbyPlugin/points" + i + ".csv");
            } catch (IOException e) {
                plugin.getLogger().warning("Points file for event " + i + " not found!");
                continue;
            }

            String[] headers = {"Game", "Team", "Name", "Points", "Temp Points, Multiplier"};

            CSVFormat.Builder builder = CSVFormat.Builder.create();
            builder.setHeader(headers);
            CSVFormat format = builder.build();

            CSVParser parser;
            try {
                parser = new CSVParser(reader, format);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            List<CSVRecord> recordList = parser.getRecords();

            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (CSVRecord r : recordList) {
                if (r.getRecordNumber() == 1) continue;
                String team = r.values()[1];

                if(statHolderMap.containsKey(team)) statHolderMap.get(team).addItem(r);
                else {
                    StatHolder h = new StatHolder(team, i);
                    h.addItem(r);
                    statHolderMap.put(team, h);
                }
            }

            //create totals
            for(StatHolder h:statHolderMap.values()) {
                h.addTotal();
            }

            for(StatHolder h:statHolderMap.values()) {
                //System.out.println(h.toString());
            }

            fullStatMap.put(i, statHolderMap);
        }

        generatePlayerHolders();
        ArrayList<PlayerHolder> playerHolders1 = getPlayerHolders(LobbyPlugin.NextGame.SKYWARS);
        for(PlayerHolder p:playerHolders1) {
            //System.out.println(p.toString());
        }

        ArrayList<StatHolder> statHolderArrayList = getStatHolders();


        for(StatHolder h:statHolderArrayList) {
            //System.out.println(h.toString());
        }
        StatHolderComparator statHolderComparator = new StatHolderComparator(LobbyPlugin.NextGame.TOTAL);
        Collections.sort(statHolderArrayList, statHolderComparator);
        //System.out.println("------------------");
        for(StatHolder h:statHolderArrayList) {
            //System.out.println(h.toString());
        }

    }

    public void generatePlayerHolders() {
        for(int i = 1; i <= plugin.getConfig().getInt("eventNum"); i++) {
            if(!fullStatMap.containsKey(i)) continue;
            for (StatHolder h : fullStatMap.get(i).values()) {
                for (GameHolder gh : h.gameHolderMap.values()) {
                    for (String player : gh.playerTempPointsMap.keySet()) {
                        PlayerHolder playerHolder = null;
                        boolean exists = false;
                        for (PlayerHolder ph : playerHolders) {
                            if (ph.name.equalsIgnoreCase(player) && ph.event == i) {
                                playerHolder = ph;
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) {
                            playerHolder = new PlayerHolder(player, h.name, h.event);
                            playerHolders.add(playerHolder);
                        }

                        playerHolder.putGame(gh.game, gh.playerTempPointsMap.get(player), gh.multiplier);

                    }
                }
            }
        }
    }

    public ArrayList<PlayerHolder> getPlayerHolders(LobbyPlugin.NextGame game) {
        PlayerHolderComparator comparator = new PlayerHolderComparator(game);
        Collections.sort(playerHolders, comparator);
        Collections.reverse(playerHolders);

        return (ArrayList<PlayerHolder>) playerHolders.clone();
    }

    public ArrayList<StatHolder>  getStatHolders() {
        ArrayList<StatHolder> statHolderArrayList = new ArrayList<>();
        for(HashMap<String, StatHolder> h:fullStatMap.values()) {
            for(StatHolder sh:h.values()) {
                statHolderArrayList.add(sh);
            }
        }

        return statHolderArrayList;
    }

    public ArrayList<StatHolder>  getStatHolders(LobbyPlugin.NextGame g) {
        ArrayList<StatHolder> statHolderArrayList = new ArrayList<>();
        for(HashMap<String, StatHolder> h:fullStatMap.values()) {
            for(StatHolder sh:h.values()) {
                statHolderArrayList.add(sh);
            }
        }

        StatHolderComparator comparator = new StatHolderComparator(g);
        Collections.sort(statHolderArrayList, comparator);
        Collections.reverse(statHolderArrayList);

        return statHolderArrayList;
    }

    public StatHolder getEventWinner(int event) {
        ArrayList<StatHolder> statHolders = getStatHolders();
        ArrayList<StatHolder> finalHolders = new ArrayList<>();
        for(StatHolder h:statHolders) {
            if(h.event == event) finalHolders.add(h);
        }
        StatHolderComparator comparator = new StatHolderComparator(LobbyPlugin.NextGame.TOTAL);
        Collections.sort(finalHolders, comparator);
        Collections.reverse(finalHolders);
        return finalHolders.get(0);
    }

    public void getLifetimePoints() {
        HashMap<String, Integer> lifetimePointsMap;
        for(PlayerHolder p:playerHolders) {

        }
    }

    /**
     *
     */
    public class StatHolder {
        public String name;
        public ArrayList<String> players;
        public HashMap<LobbyPlugin.NextGame, GameHolder> gameHolderMap;
        int event;

        public StatHolder(String name, int event) {
            this.name = name;
            this.event = event;
            gameHolderMap = new HashMap<>();
            players = new ArrayList<>();
        }

        public void addItem(CSVRecord record) {
            String[] values = record.values();
            LobbyPlugin.NextGame game = LobbyPlugin.NextGame.valueOf(values[0]);
            GameHolder gameHolder;


            if(gameHolderMap.containsKey(game)) gameHolder = gameHolderMap.get(game);
            else {
                gameHolder = new GameHolder(Double.parseDouble(values[5]), game);
                gameHolderMap.put(game, gameHolder);
            }

            if(values[2].equalsIgnoreCase("Total-")) {
                gameHolder.tempPoints = (int) Double.parseDouble(values[4]);
            } else {
                if(!players.contains(values[2])) players.add(values[2]);
                gameHolder.addPlayer(record);
            }

        }
        public void addTotal() {
            GameHolder gameHolder;
            if(gameHolderMap.containsKey(LobbyPlugin.NextGame.TOTAL)) gameHolder = gameHolderMap.get(LobbyPlugin.NextGame.TOTAL);
            else {
                gameHolder = new GameHolder(1, LobbyPlugin.NextGame.TOTAL);
                gameHolderMap.put(LobbyPlugin.NextGame.TOTAL, gameHolder);
            }
            for(String player:players) {
                if(player.equalsIgnoreCase("Total-")) continue;
                int total = 0;
                for (GameHolder g : gameHolderMap.values()) {
                    if(g.game != LobbyPlugin.NextGame.TOTAL && g.playerTempPointsMap.containsKey(player)) total += g.playerTempPointsMap.get(player);
                }
                gameHolder.putTotal(player, total);
            }

        }

        public int getPoints(LobbyPlugin.NextGame game) {
            if(gameHolderMap.containsKey(game)) return gameHolderMap.get(game).tempPoints;
            else return 0;
        }
        public double getMultiplier(LobbyPlugin.NextGame game) {
            if(gameHolderMap.containsKey(game)) return gameHolderMap.get(game).multiplier;
            else return 1;
        }

        @Override
        public String toString() {
            String str = name + "\n";
            for(String s:players) {
                str += s + ", ";
            }
            for(GameHolder g: gameHolderMap.values()) {
                str += "\n  " + g.toString();
            }
            return str;
        }
    }

    /**
     *
     */
    public class StatHolderComparator implements Comparator<StatCalculator.StatHolder> {
        private LobbyPlugin.NextGame sortGame;

        public StatHolderComparator(LobbyPlugin.NextGame game) {
            sortGame = game;
        }

        public void setSortingGame(LobbyPlugin.NextGame game) {
            sortGame = game;
        }

        @Override
        public int compare(StatCalculator.StatHolder holder1, StatCalculator.StatHolder holder2) {
            int cmp = Integer.compare((int)(holder1.getPoints(sortGame)/holder1.getMultiplier(sortGame)), (int)(holder2.getPoints(sortGame)/holder2.getMultiplier(sortGame)));
            if(cmp == 0) holder1.name.compareTo(holder2.name);
            if(cmp == 0) Integer.compare(holder1.event, holder2.event);
            return cmp;
        }
    }



    /**
     *
     */
    public class GameHolder {
        double multiplier;
        LobbyPlugin.NextGame game;
        int tempPoints = 0;
        HashMap<String ,Integer> playerTempPointsMap;

        public GameHolder(double multiplier, LobbyPlugin.NextGame game) {
            this.multiplier = multiplier;
            this.game = game;
            playerTempPointsMap = new HashMap<>();
        }

        public void addPlayer(CSVRecord r) {
            String[] values = r.values();
            playerTempPointsMap.put(values[2], (int)Double.parseDouble(values[4]));
        }


        public void putTotal(String player, int total) {
            playerTempPointsMap.put(player, total);
            tempPoints += total;
        }

        @Override
        public String toString() {
            String str = game + " Multiplier: " + multiplier + " Points: " + tempPoints;
            for(String s: playerTempPointsMap.keySet()) {
                str += "    \n" + s + ": " + playerTempPointsMap.get(s);
            }
            return str;
        }
    }


    /**
     *
     */
    public class PlayerHolder {
        String name;
        int event;
        HashMap<LobbyPlugin.NextGame, Integer> gamePointsMap;
        HashMap<LobbyPlugin.NextGame, Double> gameMultiplierMap;
        String playerTeam;

        public PlayerHolder(String name, String playerTeam, int event) {
            this.name = name;
            this.playerTeam = playerTeam;
            this.event = event;
            gamePointsMap = new HashMap<>();
            gameMultiplierMap = new HashMap<>();
        }

        public void putGame(LobbyPlugin.NextGame game, int points, double multiplier) {
            gamePointsMap.put(game, points);
            gameMultiplierMap.put(game, multiplier);
        }

        public int getPoints(LobbyPlugin.NextGame game) {
            if(gamePointsMap.containsKey(game)) return gamePointsMap.get(game);
            else return 0;
        }
        public double getMultiplier(LobbyPlugin.NextGame game) {
            if(gameMultiplierMap.containsKey(game)) return gameMultiplierMap.get(game);
            else return 1;
        }


        @Override
        public String toString() {
            String str = name + " Event: " + event + " Team: " + playerTeam;
            for(LobbyPlugin.NextGame g:gamePointsMap.keySet()) {
                str += "\n  Game: " + g + " Pts: " + gamePointsMap.get(g);
            }
            return str;
        }

    }

    /**
     *
     */
    public class PlayerHolderComparator implements Comparator<StatCalculator.PlayerHolder> {
        private LobbyPlugin.NextGame sortGame;

        public PlayerHolderComparator(LobbyPlugin.NextGame game) {
            sortGame = game;
        }

        public void setSortingGame(LobbyPlugin.NextGame game) {
            sortGame = game;
        }

        @Override
        public int compare(StatCalculator.PlayerHolder holder1, StatCalculator.PlayerHolder holder2) {
            int cmp = Integer.compare((int)(holder1.getPoints(sortGame)/holder1.getMultiplier(sortGame)), (int)(holder2.getPoints(sortGame)/holder2.getMultiplier(sortGame)));
            if(cmp == 0) cmp = holder1.name.compareTo(holder2.name);
            if(cmp == 0) cmp = Integer.compare(holder1.event, holder2.event);
            return cmp;
        }
    }
}