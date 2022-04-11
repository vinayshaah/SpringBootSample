package com.example.wpdelhi.dao;

import com.example.wpdelhi.entity.UserInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class OnboardingDAO implements InitializingBean {

    @Value("${db.url}")
    String url;
    @Value("${db.user}")
    String dbuser;
    @Value("${db.pwd}")
    String dbpwd;

    private static String CREATE_TABLE_QUERY =" CREATE TABLE IF NOT EXISTS user_info(" +
            "  id INT NOT NULL AUTO_INCREMENT," +
            "  email VARCHAR(50) DEFAULT NULL," +
            "  name VARCHAR(50) DEFAULT NULL," +
            "  address VARCHAR(250) DEFAULT NULL," +
            "  phone_number INT DEFAULT NULL," +
            "  PRIMARY KEY (id)" +
            ");";

    private static String CREATE_RECORD_QUERY ="insert into user_info(email, name, address," +
            " phone_number) values (?,?,?,?)" ;

    private static final String FETCH_ALL_USER = "select * from user_info";

    public UserInfo persistUserInfo(UserInfo userInfo) throws Exception {
        Connection connection = DriverManager.getConnection(url,dbuser,dbpwd);
        PreparedStatement ps = connection.prepareStatement(CREATE_RECORD_QUERY);
        ps.setString(1,userInfo.getEmail());
        ps.setString(2,userInfo.getName());
        ps.setString(3,userInfo.getAddress());
        ps.setInt(4,userInfo.getPhoneNumber());
        int result = ps.executeUpdate();
        log.info("Result record query:"+result);
        connection.close();
        return userInfo;
    }

    @SneakyThrows
    public List<UserInfo> fetchAllUser () {
        List<UserInfo> userInfoList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(url,dbuser,dbpwd);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(FETCH_ALL_USER);
        while (resultSet.next()){
            UserInfo userInfo = UserInfo.builder().
                    email(resultSet.getString("email")).
                    name(resultSet.getString("name")).
                    address(resultSet.getString("address")).
                    phoneNumber(resultSet.getInt("phone_number")).build();
            userInfoList.add(userInfo);
        }
        return  userInfoList;
    }

    /**
     * We will use this to create JDBC connection
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {

        Connection connection = DriverManager.getConnection(url,dbuser,dbpwd);

        Statement stmt = connection.createStatement();
        System.out.println("CREATE_TABLE_QUERY:"+CREATE_TABLE_QUERY);
        boolean result = stmt.execute(CREATE_TABLE_QUERY);
        log.info("Table created successfully");

        connection.close();
        UserInfo userInfo = UserInfo.builder().name("John").address("Lombard").
                email("john@gmail.com").phoneNumber(123456).build();
       persistUserInfo(userInfo);
       List<UserInfo> lui = fetchAllUser();
       lui.forEach(userInfo1 -> log.info("user{}",userInfo1));

    }
}
