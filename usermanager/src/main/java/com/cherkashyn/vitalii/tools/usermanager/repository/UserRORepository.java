package com.cherkashyn.vitalii.tools.usermanager.repository;

import com.cherkashyn.vitalii.tools.usermanager.domain.UserReadOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;

@Repository
public class UserRORepository {

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    public UserReadOnly getUser(String login, String password) throws UserRORepositoryException {
        List<UserReadOnly> list = new JdbcTemplate(this.dataSource).query(
                "select * from users_user where login=? and password=?",
                new Object[]{login, password},
                (resultSet, i) -> {
            UserReadOnly user = new UserReadOnly();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            return user;
        });
        if(list.isEmpty()){
            return null;
        }
        UserReadOnly returnValue = list.get(0);
        returnValue.setGroups(new HashSet<String>(new JdbcTemplate(this.dataSource)
                .query("select users_group.* from users_group inner join users_user_groups join_table on join_table.groups_id=users_group.id and join_table.user_id = ?",
                        new Object[]{returnValue.getId()},
                        (resultSet , i ) -> resultSet.getString("name")
                )
        ));
        returnValue.setProcesses(new HashSet<String>(new JdbcTemplate(this.dataSource)
                .query("select users_process.* from users_process inner join users_user_processes join_table on join_table.processes_id=users_process.id and join_table.user_id = ?",
                        new Object[]{returnValue.getId()},
                        (resultSet , i ) -> resultSet.getString("name")
                )
        ));

        return returnValue;
    }

}
