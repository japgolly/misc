package example.snippet

import org.scalatest.FreeSpec
import org.scalatest.matchers.ShouldMatchers
import example.test.SeleniumTestSupport
import scala.collection.JavaConverters._
import example.test.Jetty

/**
 * Tests the use case editor.
 *
 * @since 29/04/2013
 */
class ZeSnippetTest extends FreeSpec with ShouldMatchers with SeleniumTestSupport {

  def helloButtons = s.findElementsByCssSelector(".hello").asScala
  def addButtons = s.findElementsByCssSelector(".add").asScala
  def assertButtons(numbers: Int*) {
    val buttons = addButtons
    buttons should have size (numbers.size)
    buttons.map(_.getText) should equal(numbers.map(n => s"Button $n"))
  }
  def clickAdd(button: Int) { addButtons(button).click(); Thread.sleep(100); }

  "bug catching..." in {
    s.get(Jetty.URL + "/example") // Start with [1]
    clickAdd(0) // [1,2]
    clickAdd(1) // [1,2,3]
    clickAdd(1) // [1,2,4,3]
    assertButtons(1, 2, 4, 3)

    // Click the third button which is "Button 4" (ie.   1  2  -->[4]<--  3)
    helloButtons(2).click(); Thread.sleep(100)
    s.findElementById("clicked").getText should equal("Button 4 clicked.")
  }
}
