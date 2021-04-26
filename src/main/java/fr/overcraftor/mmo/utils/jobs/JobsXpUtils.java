package fr.overcraftor.mmo.utils.jobs;

public class JobsXpUtils {

    private final int xpRemain;
    private final int totalXp;
    private int levels = 1;

    public JobsXpUtils(int xp){
        this.totalXp = xp;
        int xpRemain = xp;

        if(xp >= JobsLevel.LEVEL_9.getTotalXp()){
            this.levels = 10;
            this.xpRemain = 0;
            return;
        }

        for(JobsLevel jobsXp : JobsLevel.values()){
            if(xpRemain >= jobsXp.getObjective() && jobsXp != JobsLevel.LEVEL_10){
                xpRemain -= jobsXp.getObjective();
                this.levels++;
            }
        }

        this.xpRemain = xpRemain;
    }

    public JobsLevel checkLevelUp(int addXp){
        final JobsXpUtils jobsXpUtils = new JobsXpUtils(totalXp + addXp);

        if(jobsXpUtils.getLevels() != getLevels())
            return JobsLevel.getFromLevel(jobsXpUtils.getLevels());

        return null;
    }

    public int getLevels() {return levels;}
    public int getXpRemain() {return xpRemain;}
}
