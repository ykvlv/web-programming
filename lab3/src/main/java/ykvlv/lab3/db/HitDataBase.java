package ykvlv.lab3.db;

import ykvlv.lab3.hit.Hit;
import ykvlv.lab3.hit.HitCollection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HitDataBase implements HitCollection {
    private Connection connection;

    @Override
    public boolean addHit(Hit hit) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("insert into HITS (id, x, y, r, current_timestamp, execution_time, result) " + "values (" +
                    "'" + hit.getId() + "', " +
                    "'" + oracleNumber(hit.getX()) + "', " +
                    "'" + oracleNumber(hit.getY()) + "', " +
                    "'" + oracleNumber(hit.getR()) + "', " +
                    "timestamp '" + Timestamp.valueOf(hit.getCurrentDateTime()) + "', " +
                    "'" + oracleNumber(hit.getExecutionTime()) + "', " +
                    "'" + (hit.isResult() ? 1 : 0) + "')");
            return true;
        } catch (SQLException | NullPointerException e) {
            return false;
        }
    }

    @Override
    public List<Hit> getHits() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from HITS");
            List<Hit> hits = new ArrayList<>();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                double r = resultSet.getDouble("r");
                LocalDateTime currentTime = resultSet.getTimestamp("current_timestamp").toLocalDateTime();
                double executionTime = resultSet.getDouble("execution_time");
                boolean result = resultSet.getInt("result") == 1;

                hits.add(new Hit(id, x, y, r, currentTime, executionTime, result));
            }
            return hits;
        } catch (SQLException | NullPointerException  e) {
            return null;
        }
    }

    @Override
    public boolean clearHits() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("truncate table HITS");
            return true;
        } catch (SQLException | NullPointerException  e) {
            return false;
        }
    }

    public boolean restoreConnection() {
        try {
            connection.isValid(1);
            return true;
        } catch (SQLException | NullPointerException e) {
            try {
                connection = ConnectionManager.open();
                return true;
            } catch (SQLException ee) {
                return false;
            }
        }
    }

    public String oracleNumber(double number) throws SQLException {
       return String.valueOf(number).replace(".", ",");
    }
}
