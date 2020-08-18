import spock.lang.Specification
import staples.bracketScorer.importer.BracketImporter
import staples.bracketScorer.importer.ResultImporter
import staples.bracketScorer.importer.RoundOneImporter
import staples.bracketScorer.result.ResultSet
import staples.bracketScorer.scoring.BracketScorer
import staples.bracketScorer.scoring.McKidsScorer
import staples.bracketScorer.scoring.StaplesFamilyScorer

class testRunnerSpec extends Specification{
    //too lazy to write real tests.  so this is a runner to eyeball results

    def " does this fucking look right to you?"(){
        when:
        def data = new RoundOneImporter().loadResources().data
        def brackets = new BracketImporter(data).importBrackets()
        def scorer = new StaplesFamilyScorer()
        brackets.each{println(it.print(true,scorer))}
        then: println("end of output")
    }

    def "how about this son a bitch?"(){
        when:
        def data = new RoundOneImporter().loadResources().data
        ResultSet resultSet = new ResultImporter(data).importResults()
        println(resultSet.print(true))
        then: println("end of output")
    }

    def "and this beautiful bastard"(){
        when:
        def data = new RoundOneImporter().loadResources().data
        def brackets = new BracketImporter(data).importBrackets()
        ResultSet resultSet = new ResultImporter(data).importResults()
        new BracketScorer().scoreBracket(brackets[0],resultSet)
        then:
        def scorer = new StaplesFamilyScorer()
        brackets.each{println(it.print(true,scorer))
            println(it.getAwardedPoints(scorer))
            println(it.getAvailablePoints(scorer))
        }
        println("end of output")


    }
}
