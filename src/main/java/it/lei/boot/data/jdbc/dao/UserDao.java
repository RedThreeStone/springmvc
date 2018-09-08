package it.lei.boot.data.jdbc.dao;

import it.lei.boot.data.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     *简单查询
     * @return
     */
    public int getUserNum(){
        String findsql="select count(*) from users";
        int num=jdbcTemplate.queryForObject(findsql,Integer.class);
        return  num;
    }

    /**
     * 带参查询
     * @param username
     * @return
     */
    public int getUserByUsername(String username){
        String findSql="select count(*) from users where username = ? ";
        int num=jdbcTemplate.queryForObject(findSql,Integer.class,username);
        return num;
    }

    /**
     * 返回对象查询
     * @param userid
     * @return
     */
    public User getUserByUserId(String userid){
        String findSql="select * from users where ssm_user_id = ? ";
        User user=jdbcTemplate.queryForObject(findSql,new UserRowMapper(),userid);
        return  user;
    }

    public List<User> findAllUser(){

        String findSql="select * from users ";
        List<User> users = jdbcTemplate.query(findSql,new UserRowMapper());
        return  users;
    }

    public void  updateUser(User user){
        String updateSql="update users set username=?,user_age=?,password=?,user_sex=? where ssm_user_id= ?";
        jdbcTemplate.update(updateSql,user.getUsername(),user.getUserAge(),user.getPassword(),user.getUserSex(),user.getSsmUserId());
    }
    /**
     * 插入一个用户
     * @param user
     * @return
     */
    public int insertUser(User user){
        String updateSql="insert into users(user_age,username,password,user_sex)  values (?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(updateSql, new String[]{"ssm_user_id"});
                preparedStatement.setInt(1,18);
                preparedStatement.setString(2,"测试小明账号");
                preparedStatement.setString(3,"测试小明密码");
                preparedStatement.setString(4,"男");
                return preparedStatement;
            }
        },keyHolder);
        return  keyHolder.getKey().intValue();
    }
    public void deleteUserById(int userId){
        String sql="delete from users where ssm_user_id = ? ";
        jdbcTemplate.update(sql,userId);
    }

    static  class UserRowMapper implements  RowMapper<User>{

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user=new User();
            user.setSsmUserId(resultSet.getInt("ssm_user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setUserAge(resultSet.getInt("user_age"));
            return  user;
        }
    }
}
