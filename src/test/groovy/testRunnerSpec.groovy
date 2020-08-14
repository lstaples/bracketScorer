import spock.lang.Specification
import staples.bracketScorer.importer.BracketImporter
import staples.bracketScorer.importer.ResultImporter
import staples.bracketScorer.importer.RoundOneImporter
import staples.bracketScorer.result.ResultSet
import staples.bracketScorer.scoring.BracketScorer

class testRunnerSpec extends Specification{
    //too lazy to write real tests.  so this is a runner to eyeball results

    def " does this fucking look right to you?"(){
        when:
        def data = new RoundOneImporter().loadResources().data
        def brackets = new BracketImporter(data).importBrackets()
        brackets.each{println(it.print(true))}
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
        brackets.each{println(it.print(true))
            println(it.getdAwardedPoints())
            println(it.getAvailablePoints())
        }
        println("end of output")


    }
}
