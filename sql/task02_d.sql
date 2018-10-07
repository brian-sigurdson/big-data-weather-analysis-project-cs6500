/*CREATE TEMP TABLE mytable AS
SELECT *
FROM orig_table;
*/

CREATE TABLE "CS6500".task02_d AS
SELECT distinct usaf, extract(year from yyyymmddhhmm) as theyear
FROM "CS6500".all_years_reduced
group by usaf, theyear
order by usaf, theyear
;