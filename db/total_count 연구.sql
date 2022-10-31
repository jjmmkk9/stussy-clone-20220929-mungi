SELECT
	COUNT(*)
FROM
	(SELECT
		*
	from
		product_mst
	GROUP by
		group_id) cnt
		

SELECT
	
FROM 
	(SELECT
		COUNT(*)
	FROM
		(SELECT
			*
		from
			product_mst
		GROUP by
			group_id) cnt
			
UNION




	SELECT
     pm.id,
     pm.category,
     pm.group_id,
     pm.name,
     pm.price,
     pig.temp_name,
     pc.total_count
   FROM
     product_mst pm
     LEFT OUTER JOIN product_img pig ON(pig.product_id = pm.id)
     LEFT OUTER JOIN (select
                           	COUNT(*) AS total_count
                     	FROM
                             (select
                                 *
                             from
                                 product_mst
                             WHERE
                                 1 = 1
                             GROUP BY
                                 group_id) cnt) pc ON(1 = 1)
   WHERE
      1 = 1
   GROUP BY
      pm.group_id;
   LIMIT 0, 16


	

	
	