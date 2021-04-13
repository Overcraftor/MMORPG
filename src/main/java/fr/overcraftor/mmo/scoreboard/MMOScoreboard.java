package fr.overcraftor.mmo.scoreboard;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.mysql.GuildSQL;
import fr.overcraftor.mmo.utils.generalXp.XpLevel;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class MMOScoreboard {

    private final Player p;
    private final Scoreboard scoreboard;
    private Objective objective;

    private String level;
    private String xp;
    private String guild;

    public MMOScoreboard(Player p) {
        this.p = p;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective(p.getName(), "dummy", "§b§lMonster-MC");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        final XpLevel xpLevel = new XpLevel(Main.getInstance().generalXp.get(p));

        final Score pseudo1 = objective.getScore("§e> §cPseudo:");
        final Score pseudo2 = objective.getScore("§7" + p.getName());

        final Score separator1 = objective.getScore("    ");

        final Score grade1 = objective.getScore("§e> §cGrade:");
        final Score grade2 = objective.getScore(PlaceholderAPI.setPlaceholders(p, "§7%luckperms_primary_group_name%"));

        final Score separator2 = objective.getScore("  ");

        final Score level1 = objective.getScore("§e> §cNiveau:");
        final Score level2 = objective.getScore("§7" + xpLevel.getLevel());

        final Score separator3 = objective.getScore(" ");

        final Score xp1 = objective.getScore("§e> §cProgression XP:");
        final Score xp2 = objective.getScore("§7" + xpLevel.getXpRemain() + "§r / §7" + xpLevel.getXpNeed());

        final Score separator4 = objective.getScore("");

        final Score guild1 = objective.getScore("§e> §cGuilde:");
        final Score guild2 = objective.getScore("§7" + GuildSQL.getGuildName(p.getUniqueId()));

        pseudo1.setScore(14);
        pseudo2.setScore(13);
        separator1.setScore(12);
        grade1.setScore(11);
        grade2.setScore(10);
        separator2.setScore(9);
        level1.setScore(8);
        level2.setScore(7);
        separator3.setScore(6);
        xp1.setScore(5);
        xp2.setScore(4);
        separator4.setScore(3);
        guild1.setScore(2);
        guild2.setScore(1);

        p.setScoreboard(scoreboard);

        this.level = level2.getEntry();
        this.xp = xp2.getEntry();
        this.guild = guild2.getEntry();
    }

    public MMOScoreboard refreshGeneralLevel(){
        final XpLevel xpLevel = new XpLevel(Main.getInstance().generalXp.get(p));

        scoreboard.resetScores(this.level);
        scoreboard.resetScores(this.xp);
        this.level = "§7" + xpLevel.getLevel();
        this.xp = "§7" + xpLevel.getXpRemain() + "§r / §7" + xpLevel.getXpNeed();

        final Score level = objective.getScore(this.level);
        final Score xp = objective.getScore(this.xp);
        level.setScore(7);
        xp.setScore(4);
        return this;
    }

    public void setGuild(String guildName){
        scoreboard.resetScores(this.guild);
        this.guild = "§7" + guildName;

        final Score guild = objective.getScore(this.guild);
        guild.setScore(1);
    }
}
