package game;

public class Main {

    public static void main(String[] args) {
        int n = 9;
        int worldSize = WorldConstants.CELLSIZE * n;
        BridgItGame g = new BridgItGame(n);

        g.bigBang(worldSize + 300, worldSize + 80);
    }
}
