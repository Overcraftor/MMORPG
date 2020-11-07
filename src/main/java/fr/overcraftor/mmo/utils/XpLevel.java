package fr.overcraftor.mmo.utils;

public class XpLevel {

    private final int xpRemain;
    private final int level;

    public XpLevel(int totalXp) {
        int xpRemain = totalXp;
        int xpNeed = 250;
        int level = 1;

        while(xpRemain >= xpNeed){
            xpRemain -= xpNeed;
            xpNeed *= 2;
            level++;
        }

        this.xpRemain = xpRemain;
        this.level = level;
    }

    public int getXpRemain() {
        return xpRemain;
    }

    public int getLevel() {
        return level;
    }
}
