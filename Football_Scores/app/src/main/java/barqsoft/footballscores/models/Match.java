package barqsoft.footballscores.models;

import android.database.Cursor;

import barqsoft.footballscores.DatabaseContract.scores_table;

/**
 * Created by rien on 30-11-15.
 */
public class Match {

    private String homeTeam;
    private String awayTeam;
    private int homeGoals;
    private int awayGoals;
    private String time;

    private Match() {

    }

    public static Match createFromCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        Match match = new Match();
        int homeTeamColumn = cursor.getColumnIndex(scores_table.HOME_COL);
        int awayTeamColumn = cursor.getColumnIndex(scores_table.AWAY_COL);
        int homeTeamGoalsColumn = cursor.getColumnIndex(scores_table.HOME_GOALS_COL);
        int awayTeamGoalsColumn = cursor.getColumnIndex(scores_table.AWAY_GOALS_COL);
        int timeColumn = cursor.getColumnIndex(scores_table.TIME_COL);

        match.homeTeam = cursor.getString(homeTeamColumn);
        match.awayTeam = cursor.getString(awayTeamColumn);
        match.homeGoals = cursor.getInt(homeTeamGoalsColumn);
        match.awayGoals = cursor.getInt(awayTeamGoalsColumn);
        match.time = cursor.getString(timeColumn);

        return match;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
