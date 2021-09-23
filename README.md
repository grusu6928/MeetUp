# MeetUp

## Motivation
With everyone's busy schedules, it can be difficult for college students to find time to get together with their friends during the week. Trying to figure out which of your friends has a break in their schedule that aligns with yours is a tedious process. And that's where MeetUp comes in: MeetUp is a service that automates the coordinating of meetups on college campuses, bringing friends together and cultivating stronger social relationships on campus.

## Description and Problem Statement
Users can take on one of two roles: a 'starter' or a 'looker'. Starters can submit proposed meetups to the server, specifying an activity, location, and time. Meanwhile, lookers can submit an activity preference and a time window during which they are free.

Think of a starter as a centroid of a K-Means cluster. The goal is to optimally surround each centroid wih lookers, such that individuals within each cluster are compatible and everyone is happy. The problem is a K-Means problem: how can we create groupings in a way that minimizes cumulative 'scatter', or in this case, incompatibility? Our solution is an iterative matching algorithm.

## Matching Algorithm
Consider one iteration of the algorithm. First, an order of starters is randomly generated. Then, between the first starter and each looker, the algorithm computes a compatibility heuristic that considers friendship status, activity preference, and time compatibility, and the looker with the minimal heuristic value is assigned to the starter. This assignment process repeats for each of the remaining starters until all lookers have been assigned to a starter. At this point, we have clusters for which we can compute 'scatter' by using the heuristic values. We calculate overall scatter for this iteration, store it, and begin another iteration of the algorithm by generating a different order of starters. After many iterations, we select the cluster arrangement proposed by the iteration with the least scatter as our optimal grouping of starters and lookers.

## Built With
- React.js
- Spark
- SQL
- Java
- HTML/CSS

## Contributors
[@hamzah-shah](https://github.com/hamzah-shah) - Hamzah Shah  
[@grusu6928](https://github.com/grusu6928) - George Rusu  
[@aminhijaz](https://github.com/aminhijaz) - Amin Hijaz  
[@ermias-genet](https://github.com/ermias-genet) - Ermias Genet




