SELECT usaf, extract(year from yyyymmddhhmm) as theyear, temps --, count(temps) as cnttmp
from "CS6500".all_years_reduced 
-- '''qrystr += 'where usaf = ' + 'theStation' + ' '''
where usaf = 106770 and temps is not null and extract(year from yyyymmddhhmm) = 1939
--group by usaf, theyear, temps
order by theyear, temps