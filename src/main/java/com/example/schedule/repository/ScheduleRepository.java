package com.example.schedule.repository;

import com.example.schedule.domain.ScheduleObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ScheduleRepository {

    // SQL 실행 + 자원관리 자동화 + 예외처리 통합
    private final JdbcTemplate jdbcTemplate;

    // 의존성 주입 DI 내가 직접 생성하는게 아니라 JdbcTemplate 빈을 주입받음
    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 데이터 저장하기
    public ScheduleObject saveSchedule(ScheduleObject scheduleObject) {

        // DB테이블에 데이터를 삽입하기 위해 insert 쿼리를 만들어주는 도구 설정
        // 쿼리문을 직접 안써도 된다
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        // 나는 schedule 테이블에 데이터를 넣을꺼에요.
        // 데이터를 삽입 후 자동 생성된 키를 반환받기 위해 설정
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");
        // DB table 넣을 데이터를 key : value 형태로 담기위한 Map 객체
        // column 이름 String : 컬럼에 넣을 값 Object(어떤 데이터가 들어올지 모름)
        Map<String, Object> params = new HashMap<>();

        params.put("title", scheduleObject.getTitle());
        params.put("contents", scheduleObject.getContents());
        params.put("userName", scheduleObject.getUserName());
        params.put("password", scheduleObject.getPassword());
        params.put("postDate", scheduleObject.getPostDate());
        params.put("updateDate", scheduleObject.getUpdateDate());
        // 생성한 Map params를 직접 DB table에 넣어줘야함
        // 넣어주면서 새로 생성한 row 데이터의 key값을 우리는 모르기 때문에 다시 반환받아야한다.
        Number key = jdbcInsert.executeAndReturnKey(params);
        // 이제 정보들이 다 주어졌으니 정보를 활용하여 재조립하자.
        return new ScheduleObject(
                key.longValue(),
                scheduleObject.getTitle(),
                scheduleObject.getContents(),
                scheduleObject.getUserName(),
                scheduleObject.getPassword(),
                scheduleObject.getPostDate(),
                scheduleObject.getUpdateDate()
        );
    }

    // 들어온 Id값을 활용해 DB에서 원하는 Row데이터 가져오기
    // Row데이터를 조회하는 메서드인 scheduleObjectRowMapper를 활용하자
    // dbcTemplate.query를 통해 간단한 쿼리문을 실행하자
    // ?로 id값이 바인딩된다.
    // 값이 0개일 가능성이 존재함으로 바로 도메인 객체로 받지않고 컬렉션으로 받는다.
    // 값이 null로 반환될 수 있기 때문에 Otional로 데이터를 반환한다.

    public Optional<ScheduleObject> findScheduleById(Long id) {
        List<ScheduleObject> result = jdbcTemplate.query("select * from schedule where scheduleId = ?", scheduleObjectRowMapper(), id);
        return result.stream().findAny();
    }

    public List<ScheduleObject> findAllSchedules() {
        return jdbcTemplate.query("select * from schedule", scheduleObjectRowMapper());
    }

    public List<ScheduleObject> searchUserNameAndDate(String userName, LocalDate date) {
        String sql = "select * from schedule where userName like ? or date(updateDate) = ? order by updatedate";
        return jdbcTemplate.query(sql, scheduleObjectRowMapper(), userName, date);
    }


    // DB에 있는 정보를 도메인 객체에 넘겨주어 Java가 데이터를 다룰 수 있게 바꿔주자
    // RowMapper class -> ResultSet을 Java객체로 매핑하기 위한 클래스
    // JDBC는 쿼리(DB에 요청하는 명령)를 실행하면 ResultSet형태의 테이블 모양의 데이터를 준다.
    // 1쿼리에 1전진 -> 쿼리를 실행해야 그만큼 진행된 row데이터에 접근할 수 있다.
    // 이걸 활용하려면 ResultSet을 Java 객체로 변환해야 한다.
    // 이것이 RowMapper
    private RowMapper<ScheduleObject> scheduleObjectRowMapper() { // 반환해야할 타입을 명시
        return new RowMapper<ScheduleObject>() { // 반환할 객체를 조립해서 반환함.
            @Override
            // 반환 타입이 스케쥴 도메인 객체 / 매핑하는 메서드 호출 / rs 쿼리에서 처리중인 Row 데이터 /
            // 몇번째  row를 처리중인지 알려주는 rowNum / JDBC관련 예외 처리 SQLException
            public ScheduleObject mapRow(ResultSet rs, int rowNum) throws SQLException {
                // Resultset에 있는 값을 꺼내서 객체를 직접 조립함
                return new ScheduleObject(
                        rs.getLong("scheduleId"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        // timestamp형식의 데이터를 LocalDateTime형식의 데이터로 변환해서 받는다.
                        rs.getTimestamp("postDate").toLocalDateTime(),
                        rs.getTimestamp("updateDate").toLocalDateTime()
                );
            }
        };
    }

}

