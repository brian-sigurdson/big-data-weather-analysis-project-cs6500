COPY 
(
SELECT usaf, 
extract(year from yyyymmddhhmm) as theYear, 
--extract(month from yyyymmddhhmm) as theMonth, 
-- lon,
-- lat,

avg(temps) as avg_temp
/*, avg(spd) as avg_spd, avg(gus) as avg_gus, avg(vsb) as avg_vsb, avg(stp) as avg_stp, 
sum(pcp01) as sum_pcp01, sum(pcp06) as sum_pcp06, sum(pcp24) as sum_pcp24, sum(sd) as sum_sd
*/

FROM "CS6500".all_years_reduced
where temps is not null
group by usaf, theYear--, theMonth
order by usaf, theYear--, theMonth
)
TO 
'E:/bks006_shared/School/cs6500/Project/Data/2_postgres_queries/csv/usaf_year_avg_temp.csv'
 DELIMITER ',' CSV HEADER;