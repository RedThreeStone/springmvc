package it.lei.boot.data.jpa.repository;

import it.lei.boot.data.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    /**
     * 根据用户名查询user并按照id降序排列
     * @param username
     * @return
     */
    public List<User> findAllByUsernameLikeOrderBySsmUserIdDesc(String username);

    /**
     * 匹配查询分页并排序
     * @param username
     * @param userAge
     * @param pageable
     * @return
     */
    @Query(value = "select * from  users where username =:username and  user_age = :userAge ",nativeQuery = true)
    public List<User> findUsersByUsernameAndUserAgePagebleAndSort(@Param(value = "username") String username,@Param(value = "userAge") int userAge, Pageable pageable);




}
