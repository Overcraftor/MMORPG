package fr.overcraftor.mmo.utils.jobs;

public enum JobsNames {

    WOOD_CUTTER("woodCutterExp", "Bûcheron"),
    MINER("minerExp", "Mineur"),
    FARMER("farmerExp", "Fermier"),
    ENCHANTER("enchanterExp", "Enchanteur"),
    BLACKSMITH("blacksmithExp", "Forgeron");

    private final String mysql, name;

    JobsNames(String mysql, String name){
        this.mysql = mysql;
        this.name = name;
    }

    public static JobsNames getFromName(String name){
        for(JobsNames job : JobsNames.values()){
            if(job.toName().equalsIgnoreCase(name))
                return job;
        }
        return null;
    }

    public String toMysql() {
        return mysql;
    }

    public String toName() {
        return name;
    }
}
