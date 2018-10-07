/* 
delete values to rerun query.  initially, if forgot to exclude null values in aggregate functions
it appears that postgres does not do so by itself
*/
--delete from "CS6500".task_03;

--/*
insert into "CS6500".task_03 (themin, themax, themean, theyear)
(
	select min(ay.temps) as theMin, max(ay.temps) as theMax, avg(ay.temps) theMean, extract(year from ay.yyyymmddhhmm) as theYear
	from "CS6500".all_years_reduced ay
	where ay.temps is not null
	group by theYear
	order by theYear
)
;
--*/
