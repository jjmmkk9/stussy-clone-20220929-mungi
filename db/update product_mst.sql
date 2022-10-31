UPDATE
	product_mst
SET
	category = 'TEES',
	NAME = CONCAT(SUBSTRING(NAME, 1, 13), 'TEES', SUBSTRING(NAME, 19))
WHERE
	id > 40 
	AND < 121
	

SELECT
	ROW_NUMBER() OVER(PARTITION BY pm.id ORDER BY pm.id) AS seq2,
	
FROM
	(SELECT
		ROW_NUMBER() OVER(PARTITION BY group_id ORDER BY id) AS seq,
		id,
		NAME,
		price
	FROM
		product_mst) pm
	LEFT OUTER JOIN product_img pi ON(pm.seq = 1 and pi.product_id = pm.id)
user_mst


# 하나의 상품정보 & 
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
										AND category = 'denim'
									GROUP BY 
										group_id) cnt) pc ON(1 = 1)					
WHERE
	1 = 1
	AND category = 'denim'								
GROUP BY#그룹 아이디가 같은 것들끼리 묶어서 젤 위에거 가져옴
	pm.group_id;
	




#total 가져오기
SELECT
	COUNT(*) AS total_count
FROM
	product_mst pm
	LEFT OUTER JOIN product_img pig ON(pig.product_id = pm.id)

GROUP BY#그룹 아이디가 같은 것들끼리 묶어서 젤 위에거 가져옴
	pm.group_id

	
	