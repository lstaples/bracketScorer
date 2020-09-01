package staples.bracketScorer.importer

import staples.bracketScorer.Matchup

class RoundOneData {
    Map teams= [:]
    ArrayList<Matchup> matchups= new ArrayList<Matchup>()

    void resetScoring(){
        teams.each {it.value.eliminated = false}
    }
}
