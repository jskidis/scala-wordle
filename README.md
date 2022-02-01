# scala-wordle
### An app that attempts to guess the wordle in the fewest guesses

The basis for the algorithm came from the [538 (classic) Riddler](https://fivethirtyeight.com/features/when-the-riddler-met-wordle/) from January 14th.

The outline of algorithm is as follows 
* Read in the list of potential words
* Pick a first guess (always TRACE, see Riddler answer for detail)
* Loop through the following
  * Determine (input) Color Pattern of guess
  * If all are green, return the guessed word
  * First out list based on words that wouldn't match the existing color pattern (and the current guess)
  * For each remaining word, determine how many Clusters are created by comparing the other words against this word
  * The word with the greatest number of clusters is then selected as the next guess

Color Pattern
: The combination of Green, Yellow, Black/Grey blocks generate when comparing a word to the answer

Clusters
: Words that have same color pattern generated for a given word. For example, BRACE AND GRACE are in the same cluster when TRACE is the (potential) answer as they generate the same color pattern (Black, Green, Green, Green, Green)

####Note: My intention for writing this to use a reference implementation when I implement the algorithm in Python which I'm in the process of learning.
