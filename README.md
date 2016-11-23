# nu-challenge

This is a challenge for evaluating my Clojure learning process and my skills at the current moment.
Thank you Rodrigo Flores for all the good Clojure references you provided to me!

## Installation

Download from https://github.com/leeohaddad/reward-system-challenge.

## Usage

This project was created using a project automation tool called Leiningen. Using it, any user can run this project in a quick and easy manner.
Leiningen is available at http://leiningen.org/ and is very straight-forward to use.
After installing Leiningen, just move to the project root directory and run the following command:

    $ lein run FILENAME

## Thought line to build the solution

### The Awesome Foresight

Why limiting this source code to this specific challenge? All that functions can be extremely useful for other features.
Even the same feature may resquest something like another input method some day.
Of course we don't want to antecipate work that can end up not being used at all. But we can leave the field ready for our need from the future.
With that on mind, I tried to make the code as modular and reusable as possible.

### The Wonderful Data Structure

I chose to use a Tree to store the data for a pair of reasons:
 - To calculate the score of a customer, we need to analyse the score of all his invitees. So, something close to a DFS looks like a good solution, and Clojure may help us to use this structure to bring concurrency.
 - The inviter-invitee relation is similar to a parent-child relation, and that, allied to the one-inviter-per-invitee rule (comparable to one-parent-per-node/child rule), makes our data fit perfectly with the tree structure.
I implemented the Tree data structure in a very simple manner: each customer/invitee is a vector in the form [index & invitees].

### The Amazing Solution

The fact that invitations are worth (1/2)^k (k: invitation level) means that each direct invitee contributes to the inviter score with half it's own total score.
This means that, as mentioned, something DFS-like seems good to solve the problem.
This can be done through recursion or dynamic programming. Let's stick to the recursion, since we don't use the computed score of any invitee in any task other than computing his inviter score. 

### Next Steps

 - Implement input from file.
 - Implement additional input from http endpoint.
 - Sort the ranking.
 - Implement JSON format.
 - Implement output to http endpoint.
 - Bring some concurrency.

...

## License

Copyright © 2016 Leonardo Haddad Carlos

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
