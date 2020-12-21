package fr.overcraftor.mmo.utils.generalXp;

public class XpLevel {

    private final int xpRemain;
    private final int level;
    private final int xpNeed;

    public XpLevel(int totalXp) {
        int xpRemain = totalXp;
        int xpNeed = 250;
        int level = 1;

        while(xpRemain >= xpNeed){
            xpRemain -= xpNeed;
            xpNeed *= 1.05;
            level++;
        }

        this.xpRemain = xpRemain;
        this.level = level;
        this.xpNeed = xpNeed;
    }

    public int getXpRemain() {
        return xpRemain;
    }

    public int getXpNeed() {
        return xpNeed;
    }

    public int getLevel() {
        return level;
    }
}
