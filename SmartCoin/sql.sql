select sum(match.runs)/ count(match.match_id)
from match_scores as match
where match.player_id =
    (select player_id
       from players as player
       where player.name == 'Rohit Sharma'
    )





select match.match_id
from match_scores as match
where match.runs >= 200 and match.player_id =
    (select player_id
       from players as player
       where player.name == 'Rohit Sharma'
    )
-- IN this match Rohit Sharma hit >200. return value -1
