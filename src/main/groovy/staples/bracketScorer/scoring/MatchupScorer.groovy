package staples.bracketScorer.scoring

import staples.bracketScorer.prediction.MatchupPrediction

interface MatchupScorer {
    Integer pointsAvailable(MatchupPrediction m)
    Integer pointsAwared(MatchupPrediction m)

}