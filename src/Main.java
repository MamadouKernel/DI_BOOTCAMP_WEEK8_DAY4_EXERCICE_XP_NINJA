import database.Connexion;
import models.Actor;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Actor donnieYen = new Actor("Donnie", "Yen");
        new Main().addActorAndAssignFilm(donnieYen, 14);
    }

    /**
     * insert an actor and assign him to a specific film
     *
     * @param actor
     * @param filmId
     */
    public void addActorAndAssignFilm(Actor actor, int filmId) {

        // Request for insert an actor into the actor table
        String SQL_INSERT_ACTOR = """
                    INSERT INTO actor(first_name, last_name)
                    VALUES(?, ?)
                """;

        // Request for assign actor to a film
        final String SQL_ASSIGN_ACTOR = """
                    INSERT INTO film_actor(actor_id, film_id)
                    VALUES(?, ?)
                """;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;


        int actorId = 0;
        try {
            // connection to database
            connection = new Connexion().getConnecion();
            connection.setAutoCommit(false);

            // add actor
            preparedStatement = connection.prepareStatement(SQL_INSERT_ACTOR, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, actor.getFirstName());
            preparedStatement.setString(2, actor.getLastName());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                // get actor id
                resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    actorId = resultSet.getInt(1);
                    if (actorId > 0) {
                        preparedStatement2 = connection.prepareStatement(SQL_ASSIGN_ACTOR);
                        preparedStatement2.setInt(1, actorId);
                        preparedStatement2.setInt(2, filmId);
                        preparedStatement2.executeUpdate();
                    }
                }
            } else {
                // rollback the transaction if the insert failed
                connection.rollback();
            }

            // commit the transaction if everything is fine
            connection.commit();

            System.out.println(String.format("The actor was inserted with id %d and " + "assigned to the film %d", actorId, filmId));

        } catch (SQLException ex) {
            ex.printStackTrace();
            // roll back the transaction
            System.out.println("Rolling back the transaction...");
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } finally {
            this.closeResources(resultSet).closeResources(preparedStatement).closeResources(preparedStatement2).closeResources(connection);
        }
    }

    /**
     * Close a AutoCloseable object
     *
     * @param closeable
     */
    private Main closeResources(AutoCloseable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}
