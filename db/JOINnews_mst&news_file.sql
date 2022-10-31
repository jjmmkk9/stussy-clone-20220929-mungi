SELECT
	nm.news_id,
	nm.news_title,
	nm.news_writer,
	nm.news_broadcasting,
	nm.news_content,

	nf.file_origin_name,
	nf.file_temp_name,
	
	nm.create_date
FROM
	news_mst nm
	LEFT OUTER JOIN news_file nf ON(nf.news_id = nm.news_id)
WHERE
	nm.news_id = 19;