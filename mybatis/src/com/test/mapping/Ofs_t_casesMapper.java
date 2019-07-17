package com.test.mapping;

import org.apache.ibatis.annotations.Select;

public interface Ofs_t_casesMapper {
	@Select("select * from ofs_t_cases where id = #{id}")
	Ofs_t_cases selectOneCase(int id);
}
