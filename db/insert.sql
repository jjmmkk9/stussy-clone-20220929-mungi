INSERT INTO
	product_mst
	(
		select
			0,
			IFNULL((SELECT
					 		MAX(group_id) 
						FROM 
							product_mst 
						WHERE NAME = 'a'), ifnull(MAX(group_id), 0) + 1)
		from
			product_mst
			
	);


SELECT
	MAX(group_id)
FROM
	product_mst;
	
	
	
SELECT
	*
FROM
	prodouct_mst pm
	LEFT OUTER JOIN product_img pim ON(pim.product_id = pm.id);
	
	
