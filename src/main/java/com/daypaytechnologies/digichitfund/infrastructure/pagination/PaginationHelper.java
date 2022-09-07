package com.daypaytechnologies.digichitfund.infrastructure.pagination;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

public class PaginationHelper<E> {

    public Page<E> fetchPage(final JdbcTemplate jt, final String sqlCountRows, final String sqlFetchRows, final RowMapper<E> rowMapper,
                             final int page, final int pageSize) {

        final List<Object> args = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        args.add(offset);
        args.add(pageSize);
        final List<E> items = jt.query(sqlFetchRows, args.toArray(), rowMapper);
        // determine how many rows are available
        final int totalFilteredRecords = jt.queryForObject(sqlCountRows, Integer.class);
        final int totalPages = totalFilteredRecords / page;
        return new Page<>(items, totalFilteredRecords, page, pageSize,totalPages);
    }

    public Page<E> fetchPage(final JdbcTemplate jt, final String sqlCountRows, final String sqlFetchRows, final List<Object> args,
                             final RowMapper<E> rowMapper, final int page, final int pageSize) {
        final List<Object> arguments = new ArrayList<>();
        if(args != null && !args.isEmpty()) {
            arguments.addAll(args);
        }
        int offset = (page - 1) * pageSize;
        arguments.add(offset);
        arguments.add(pageSize);
        final List<E> items = jt.query(sqlFetchRows, arguments.toArray(), rowMapper);
        // determine how many rows are available
        int totalFilteredRecords;
        if(args != null && !args.isEmpty()) {
            totalFilteredRecords = jt.queryForObject(sqlCountRows, args.toArray(), Integer.class);
        } else {
            totalFilteredRecords = jt.queryForObject(sqlCountRows, Integer.class);
        }
        final int totalPages = totalFilteredRecords / pageSize;
        return new Page<>(items, totalFilteredRecords, page, pageSize, totalPages);
    }

    public Page<Long> fetchPage(JdbcTemplate jdbcTemplate, String sql, String sqlCountRows, Class<Long> type , final int currentPage, final int recordPerPage) {
        final List<Long> items = jdbcTemplate.queryForList(sql, type);

        // determine how many rows are available
        Integer totalFilteredRecords = jdbcTemplate.queryForObject(sqlCountRows, Integer.class);
        final int totalPages = totalFilteredRecords/recordPerPage;
        return new Page<>(items, ObjectUtils.defaultIfNull(totalFilteredRecords, 0),currentPage,recordPerPage,totalPages);
    }
}
