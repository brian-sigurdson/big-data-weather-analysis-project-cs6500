-- how many records from stations without a latitude value
-- by station and year

/*
SELECT usaf, extract(year from yyyymmddhhmm) as theyear, count(usaf)
FROM "CS6500".all_years_reduced
where lat is null
group by usaf, theyear
order by theyear, usaf
;
*/

select count(*)
from "CS6500".all_years_reduced
where lat is null
;
