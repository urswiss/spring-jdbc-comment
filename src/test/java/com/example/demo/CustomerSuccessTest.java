package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author Urs Wiss
 */
@RunWith(SpringRunner.class)
@JdbcTest
public class CustomerSuccessTest {
    private Resource inserts = new ClassPathResource("data.sql");
    private Resource deletes = new ClassPathResource("delete.sql");

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        try {
            new ResourceDatabasePopulator(deletes, inserts).execute(dataSource);
        } catch (NoSuchBeanDefinitionException e) {
            // ignore
        }
    }
    @Test
    public void createCustomer() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from customer");
        assert !maps.isEmpty();
        assert "Doe".equals(maps.get(0).get("NAME"));
    }
}