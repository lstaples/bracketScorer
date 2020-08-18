package staples.bracketScorer


abstract class Round {
    Conference conference
    Integer number

    ArrayList<Team> getWinners(List matchups){
        matchups.collect{it.winner}
    }

    String print(){
        "${conference.toString()} conference round ${number}"
    }
}
