package fr.overcraftor.mmo.utils.jobs;

public enum JobsLevel {

    LEVEL_1(5000, 1, 5000),
    LEVEL_2(15000, 2, 20000),
    LEVEL_3(50000, 3, 70000),
    LEVEL_4(125000, 4, 195000),
    LEVEL_5(250000, 5, 445000),
    LEVEL_6(300000, 6, 745000),
    LEVEL_7(500000, 7, 1245000),
    LEVEL_8(750000, 8, 1995000),
    LEVEL_9(1000000, 9, 2995000),
    LEVEL_10(0, 10, 2995000);

    private final int objective;
    private final int level;
    private final int totalXp;

    JobsLevel(int objective, int level, int totalXp) {
        this.objective = objective;
        this.level = level;
        this.totalXp = totalXp;
    }

    public int getObjective() {
        return objective;
    }

    public static JobsLevel getFromLevel(int level){
        for(JobsLevel lvl : values()){
            if(lvl.getLevel() == level)
                return lvl;
        }
        return LEVEL_10;
    }

    public int getLevel() {
        return level;
    }

    public int getTotalXp() {
        return totalXp;
    }
}
