//NOTE: remember to add the jar to the runner class path
import staples.bracketScorer.importer.BracketImporter
import staples.bracketScorer.importer.ResultImporter
import staples.bracketScorer.importer.RoundOneImporter
import staples.bracketScorer.result.ResultSet
import staples.bracketScorer.scoring.BracketScorer
import staples.bracketScorer.scoring.StaplesFamilyScorer
import staples.bracketScorer.scoring.McKidsScorer
import staples.bracketScorer.prediction.League
import groovy.io.FileType

//fuck it...hack this in
outputDirectory = "C:\\Users\\lstaples\\IdeaProjects\\bracketScorer\\runner\\results"
        
 //clear the results
new File(outputDirectory).eachFile(FileType.FILES){it.delete()}
League.values().each{leagueName ->
    new File(outputDirectory + "\\" + leagueName).eachFile{it.delete()}
}
 
//parse the input JSON 
def data = new RoundOneImporter().loadResources().data
def brackets = new BracketImporter(data).importBrackets()
ResultSet resultSet = new ResultImporter(data).importResults()

//instantiate the scoring rules per league
def scoringRules = [:]
scoringRules[League.McKids] = new McKidsScorer()
scoringRules[League.StaplesFamily] = new StaplesFamilyScorer()

//score the brackets
def scorer = new BracketScorer()
brackets.each{scorer.scoreBracket(it,resultSet)}

//write the playoff results to disk
def resultsFile = new File(outputDirectory + '\\results.txt')
resultsFile.write(resultSet.print(true))

//write the brackets to disk
League.values().each{leagueName -> 
    brackets.each{bracket ->
        if(bracket.leagues.find{it == leagueName}){
            resultsFile = new File(outputDirectory + "\\" + leagueName + "\\${bracket.owner}.txt")
            resultsFile.write(bracket.print(true,scoringRules[leagueName]))
        }
    } 
}

//build and sort leaderboards and write that to disk

League.values().each{leagueName -> 

    def bracketsInleague = brackets.findAll{bracket -> bracket.leagues.find{it == leagueName}}
    bracketsInleague.sort{a,b -> a.getAwardedPoints(scoringRules[leagueName]) <=> b.getAwardedPoints(scoringRules[leagueName]) ?: a.getAvailablePoints(scoringRules[leagueName]) <=> b.getAvailablePoints(scoringRules[leagueName])}
    
    def leaderboard = ""
    bracketsInleague.eachWithIndex{b,i ->
        leaderboard += "${i+1}) ${b.owner} - ${b.getAwardedPoints(scoringRules[leagueName])} points awarded. ${b.getAvailablePoints(scoringRules[leagueName])} points available. \n" 
    }
    resultsFile = new File(outputDirectory + "\\" + leagueName  + '\\leaderboard.txt')
    resultsFile.write(leaderboard)
}



 