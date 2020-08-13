package staples.bracketScorer.importer

import groovy.json.JsonSlurper
import staples.bracketScorer.Conference
import staples.bracketScorer.Matchup
import staples.bracketScorer.Team

class RoundOneImporter {

    RoundOneData data

    def loadResources(){
        Map teams= [:]
        ArrayList<Matchup> matchups= new ArrayList<Matchup>()

        def slurper = new JsonSlurper()
        def url = getClass().getResource('/round1Matchups.json')
        def round1 = slurper.parse(url)
        round1.each{
            def conference = it.conference as Conference
            it.matchups.each{matchup ->
                def team1 = new Team(name: matchup[0].team, seed: matchup[0].seed, conference: conference)
                def team2 = new Team(name: matchup[1].team, seed: matchup[1].seed, conference: conference)
                teams[team1.name] = team1
                teams[team2.name] = team2
                def highSeed = (team1.seed < team2.seed) ? team1: team2
                def lowSeed = (team1.seed > team2.seed) ? team1: team2
                matchups << new Matchup(highSeed: highSeed, lowSeed: lowSeed)

            }
        }
        data = new RoundOneData(teams:teams,matchups:matchups)
        //for method chaining
        this
    }
}
