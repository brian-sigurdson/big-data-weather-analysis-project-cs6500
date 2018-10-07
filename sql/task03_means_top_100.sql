﻿SELECT extract(year from yyyymmddhhmm) as theyear, avg(temps) as avgtemp
FROM "CS6500".all_years_reduced
where temps is not null and usaf in (
29110
,29750
,103380
,108650
,29170
,106370
,228020
,28360
,104190
,101200
,101270
,103840
,104330
,267020
,28750
,29350
,29440
,29820
,40650
,100190
,100670
,100910
,101310
,101470
,101700
,103120
,103610
,104000
,104100
,104160
,104270
,104380
,104530
,104680
,104690
,104880
,105010
,105130
,105540
,105690
,105770
,105780
,106770
,106850
,107270
,107280
,107630
,107760
,108030
,108660
,109350
,110350
,111200
,112310
,115180
,116430
,121140
,121160
,122050
,124000
,124250
,228920
,14030
,23610
,28970
,29700
,30050
,30260
,30750
,30910
,31000
,31400
,31620
,31710
,32620
,33010
,33110
,33790
,33960
,34970
,35310
,36010
,36270
,36930
,37660
,37750
,37770
,37950
,38040
,38280
,38560
,38640
,38940
,39520
,39530
,39660
,39730
,39800
,60110
,60410
)
group by theyear
order by theyear
;


