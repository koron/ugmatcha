/**

<b>UG Matcha</b> matches against many words simultaneously, with less memory at
runtime.

<h2>Usage</h2>

Please use
{@link net.kaoriya.ugmatcha.UGMatcher#newMatcher(String...) UGMatcher#newMatcher()}
and
{@link net.kaoriya.ugmatcha.UGMatcher#match(String, MatchHandler) UGMatcher#match()}
for maximum performance.

<pre><code>
import net.kaoriya.ugmatcha.UGMatcher;
import net.kaoriya.ugmatcha.MatchHandler;

// Prepare a matcher.
UGMatcher m = UGMatcher.newMatcher("foo", "bar", "baz");

m.match("foobarbazqux", new MatchHandler() {
  // This method is called when found each words.
  public boolean matched(UGMatcher u, int wordId, String text, int index) {
    // You can retrieve matched word by UGMatcher#getWord()
    String word = u.getWord(wordId);
    // Do something with matched "word".

    // Return true to continue matching, otherwise false to terminate.
    return true;
  }
});
</code></pre>

For convenient usecase, you can use
{@link net.kaoriya.ugmatcha.UGMatcher#find(String) UGMatcher#find()}.
But this method expends more memory than
{@link net.kaoriya.ugmatcha.UGMatcher#match(String, MatchHandler) UGMatcher#match()}
at runtime.

<pre><code>
import net.kaoriya.ugmatcha.UGMatcher;
import net.kaoriya.ugmatcha.Match;

import java.util.List;

// Prepare a matcher.
UGMatcher m = UGMatcher.newMatcher("foo", "bar", "baz");

List<Match> list = m.find("foobarbazqux");
for (Match m : list) {
  // Access matched "word" by Match#text.
  String word = m.text;
  // Access poistion of matched "word" by Match.index.
  int index = m.index;
}
</code></pre>

*/
package net.kaoriya.ugmatcha;
