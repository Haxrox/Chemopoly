import java.util.ArrayList;

public class GameManager {
    // [ Variables ] //
    ArrayList<Integer> Players;
    int playerCount = 0;
    int remainingPlayers;
    // [ Constructor ] //
    public GameManager(int playerCount){
        Players = new ArrayList<Integer>();
        this.playerCount = playerCount;
        this.remainingPlayers = playerCount;
        for (int index = 0; index < playerCount; index ++){
            Players.add(index, 0);
        }
    }
    // [ Functions ] //
    // GetPlayers
    public ArrayList getPlayers() {
        return Players;
    }
    // GetPlayerCount
    public int getPlayerCount() {
        return playerCount;
    }
    // RemainingPlayers
    public int getRemainingPlayers() {
        return remainingPlayers;
    }
    // GetPlayerMoney
    public int getPlayerMoney(int player) {
        return Players.get(player);
    }
    // EditMoney
    public int editMoney(int player, int value) {
        int playerMoney = Players.get(player);
        playerMoney = playerMoney + value;
        Players.set(player, playerMoney);
        if (playerMoney <= 0) {
            remainingPlayers --;
        }
        return playerMoney;
    }
}
