# scala-wordle

I started this as just a basic solver to solve the daily Wordle using the algorithm I read about in the
[538 (classic) Riddler](https://fivethirtyeight.com/features/when-the-riddler-met-wordle/) from January 14th. 
Here I am a couple of months later, and it has expanded quite a bit. 

I started expanding the solver to utilize word frequency as returned by Google's ngram service. 
That led to abstracting out the "strategy" part of the solver. 
Then I added a strategy that utilized character frequency within the guessable word set.
This led to trying to combine strategies together to fine tune the results. 
At some point, a friend of mine wondered if you could build a solver that tried to fail, so I did. 
But I required it to follow hard-mode rules, so it couldn't just use the same rare word/letters over and over again.
See more about the various strategies and their results in the readme for the strategy package.

Of course in order to really fine tune the results, I needed to build something that simulated running a strategy 
across all the answers in order to determine if a given strategy was more or less effective 
at reducing the average number of guesses and/or reducing the number of times it failed to get the answer in 6 guesses.
This led to the abstraction of the Wordle (and eventually Xrdle) processor as the simulation needed to bypass 
the interactive nature of the original solution which required entering the color pattern for each guess. 
Because the clustering strategy could make many millions of evaluations of a potential answers and words from the guess list
and that many of comparisons are repeated, I built a caching mechanism for saving the results of a given comparison across the full simulation. 
The individual comparisons take in the order of microseconds, but when running tens of millions of times, it adds up. 
I also multi-threaded the solving of multiple runs of the simulator to further reduce the time it takes to run a simulation.

There is, theoretically, an optimal first guess for each strategy (excluding the Random Guess strategy). 
Making that determination can be relatively time-consuming, particularly for clustering-based strategies
determining the number of unique clusters across a 10k+ word set. 
This is then multiplied when running a simulation across the 2309 word answer set. 
So I created a first-run optimizer to determine the best first guess (or some cases 2 first guesses) for a given strategy.
However, I discovered that just pointing a strategy at the full word set and having it give the best "next guess" didn't always
result in the optimal first guess in terms of average number of guesses.
So I built a multi-simulation utility that would take the top set of first guesses from the optimizer and then run 
the full simulation across each of those first guess candidates to determine which one had the lowest average.

And then,,, I had the idea of using what I had already written to try to solve Nerdle puzzles. I eventually discovered
that storing the "words" as equations (actually expressions from which the equations could be derived) was a 
much more effective solution than trying to solve the "words" as string and converting to equations when needed. 
Converting an equation to a string is much simpler (re: less compute-intensive) than going the other way. 

This led to a number of abstractions, as I didn't want to have separate processor, strategy, and runner implementations 
for both Wordle and Nerdle (and hence the term Xrdle). 
This included things like, XrdleWord, Guess Props (valid characters and length of guess), and
Hint Props (the in-word hint block for Wordle is "yellow" but is "red" for Nerdle). 
I also implemented a version that ran Mini-Nerdle.

At one point I did start down the path of building an Absurdle solver. But it became clear that there are a number
of sets of first 3 guesses that resulted in only having 2 choices left. I was unable to find a set of 3 guesses that 
reduced it down to 1 choice. 
Since Absurdle doesn't have a "new" puzzle every day, it basically meant that once you solved for the optimal solution, 
in the case solving in 4 guesses 50% of the time and 5 guesses the other 50%, 
there really wasn't a need to run it again. So I abandoned the Absurdle Solver.

An (evil) friend of mine said to me, hey what about Waffle? This was on a Friday and by Sunday afternoon I had determined
that likely every waffle puzzle lends itself to being solved in terms of what words are in the puzzle and where. 
I have since confirmed this over dozens of Waffle puzzles, although there was one where 2 words could have been exchanged.
This basically just left optimizing the swaps which, while I didn't implement, I'm pretty confident this could be done
deterministically such that any waffle puzzle could be solved in 10 swaps. 
I left the Waffle runner in, but it just outputs the correct words in their positions, 
I also didn't build an interactive way to input the initial puzzle. 

My intention is to write readme for the various packages, but so far that is for a future me to do.

Note: The base package for the code is com.skidis.wordle. 
This was due to the fact that I had originally only considered doing a wordle solver. 
That means there are package names like wordle.wordle, wordle.nerdle, and wordle.waffle.
I considered renaming the base package to com.skidis.xrdle like I had for the abstraction traits/object, 
but decided it wasn't worth essentially wiping out all the history on those files. 
Although in subsequent package reorganizations I basically did that anyway, but I'm not going to worry about that now.

* frequency - Code that calls the google ngram service and assign a word frequency to each word in a word set
* hintgen - Generate hints given a potential answer and a word, also for determining whether a word matches a given answer/word hints combination  
* input - Inputting and validating guesses and hints (color blocks) 
* nerdle - Code specific to the nerdle solver 
* nerdle.runner - Pre-defined mix-in traits for running various combinations of solvers for nerdle and mini-nerdle
* runners - The abstractions for the solving processor and various type of runners (interactive, simultion, first-guess optimization)
* strategy - The various strategies, sub-strategies, and combined strategies
* waflle  - The Waffle runner, as mentioned it's not complete
* wordle - Code specific to the wordle solver
* wordle.runner - Pre-defined mix-in traits for running various combinations of solvers for wordle






The outline of processor is as follows 
* Read in the list of potential words
* Loop through the following
  * Pick a guess (usually done automatically for the first guess)
    * For each remaining word, create a score for each word based on the strategy
    * The word with the highest score is then selected as the next guess
  * Determine (input or based on known answer) Color Pattern of guess
  * If all are green, return the guesses and hints
  * If 6 guesses have been tried, exit the loop
  * Filter out words list based on words that wouldn't match the existing color pattern (and the current guess)

####Note: My "original" intention for writing this to use a reference implementation when I implement the algorithm in Python which I'm in the process of learning.
