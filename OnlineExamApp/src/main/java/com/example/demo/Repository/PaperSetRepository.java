package com.example.demo.Repository;

import com.example.demo.Model.PaperSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class PaperSetRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(PaperSet paperSet) {
        String sql = "INSERT INTO paper_set (paper_name, subject) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, paperSet.getPaperName());
            ps.setString(2, paperSet.getSubject());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }
}
