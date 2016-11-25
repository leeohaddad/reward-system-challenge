# nu-challenge

This is a challenge for evaluating my Clojure learning process and my skills at the current moment.
Thank you Rodrigo Flores for all the good Clojure references you provided to me!

## Installation

Download from https://github.com/leeohaddad/reward-system-challenge.

## Prerequisites

This project was created using a project automation tool called Leiningen.
Using it, any user can run this project in a quick and easy manner.
You will need [Leiningen][] 2.0.0 or above [installed][leiningen-download].

This solution uses the following libraries:

- [Compojure][http-endpoints-lib]
- [data.json][json-lib]

So, if you don't have them, Leiningen will download it for you, since they are marked as dependencies.

## Running

With Leiningen installed, first move to the project root directory.

To start the web server for the application, run:

```sh
$ lein ring server
```

If you don't have Ring yet, Leiningen will also download ir for you.

After the server starts, `/` and `/index.html` show you a greetings page, while `/getRanking` gives you the json-formatted ranking.

You can also run the application locally:

```sh
$ lein run "resources/input.txt"
```

## Line of thought to build the solution

### The Awesome Foresight

Why limiting this source code to this specific challenge? All that functions can be extremely useful for other features.
Even the same feature may request something like another input method some day.
Of course we don't want to anticipate work that can end up not being used at all. But we can leave the field ready for our need from the future.
With that on mind, I tried to make the code as modular and reusable as possible.

### The Wonderful Data Structure

I chose to use a Tree to store the data for a pair of reasons:

 - To calculate the score of a customer, we need to analyze the score of all his invitees. So, something close to a DFS looks like a good solution, and Clojure may help us to use this structure to bring concurrency.
 - The inviter-invitee relation is similar to a parent-child relation, and that, allied to the one-inviter-per-invitee rule (comparable to one-parent-per-node/child rule), makes our data fit perfectly with the tree structure.

I implemented the Tree data structure in a very simple manner: each customer/invitee is a vector in the form [index & invitees].

### The *Even Better* Data Structure

Despite being the best option for the solution computation, the tree has been proven inefficient for inclusion, since we can't access elements without searching in the tree.

To solve this problem, I changed my data structure to something that still allows me quickly transverse the structure looking for result, but that also allows me to make inclusion in something close to O(1).

The result was a hash-map that contains:
 - Root: Customer that made the first invitation.
 - Data: hash-map of 2-dim vectors of sets. Customers are the hash-map keys and lead to the pair of sets that contain the invitees of that customer. The first set stores the valid invitations, while the second set stores the invalid invitations.
 - Invitees: A set of the already-invited customers, for interal control.

This data structure assumes there is only one non-invited customer (root).

### The Amazing Solution

The fact that invitations are worth (1/2)^k (k: invitation level) means that each direct invitee contributes to the inviter score with half it's own total score.
This means that, as mentioned, something DFS-like seems good to solve the problem.
This can be done through recursion or dynamic programming. Let's stick to the recursion, since we don't use the computed score of any invitee in any task other than computing his inviter score. 

### Current Bugs

Oh, well, not everything smells like flower, right? I found a bug when I was testing the file input reader with the provided input.txt. I manage to find the only one line that makes my program crash: "42 1". Removing it, the program goes fine. We all know that 42 is a sacred number, but what would it be causing into my data structures and functions? I'll investigate that in the next chapters!

### Next Steps

 - Fix the "42 1" bug.
 - Implement additional input from http endpoint.
 - Bring some concurrency.
 - Implement tests.

### Future Possibilities

 - Implement multiple roots (non-invited customers). For now, assumes it's unique.

...

## License

Copyright © 2016 Leonardo Haddad Carlos

Distributed under the Eclipse Public License, the same as Clojure.

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [leiningen-download]: <http://leiningen.org/>
   [leiningen]: <https://github.com/technomancy/leiningen>
   [http-endpoints-lib]: <https://github.com/weavejester/compojure>
   [json-lib]: <https://github.com/clojure/data.json>