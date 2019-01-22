package pl.piwosz.chat_gui.ui.model;

import pl.piwosz.server.Message;

import java.sql.*;
import java.util.Properties;

public class Database {
    //metody do obsługi bazy danych

    /**
     * Metoda ładuje sterownik jdbc
     *
     * @return true/false
     */
    public static boolean checkDriver(String driver) {
        // LADOWANIE STEROWNIKA
        System.out.print("Sprawdzanie sterownika:");
        try {
            Class.forName(driver).newInstance();
            return true;
        } catch (Exception e) {
            System.out.println("Blad przy ladowaniu sterownika bazy!");
            return false;
        }
    }

    /**
     * Metoda służy do nawiązania połączenia z bazą danych
     *
     * @param adress       - adres bazy danych
     * @param dataBaseName - nazwa bazy
     * @param userName     - login do bazy
     * @param password     - hasło do bazy
     * @return - połączenie z bazą
     */
    public static Connection connectToDatabase(String kindOfDatabase, String adress,
                                               String dataBaseName, String userName, String password) {
        System.out.print("\nLaczenie z bazą danych:");
        String baza = kindOfDatabase + adress + "/" + dataBaseName;
        // objasnienie opisu bazy:
        // jdbc: - mechanizm laczenia z baza (moze byc inny, np. odbc)
        // mysql: - rodzaj bazy
        // adress - adres serwera z baza (moze byc tez z nazwy)
        // dataBaseName - nazwa bazy
        java.sql.Connection connection = null;
        try {
            connection = DriverManager.getConnection(baza, userName, password);
        } catch (SQLException e) {
            System.out.println("Blad przy połączeniu z bazą danych!");
            System.exit(1);
        }
        return connection;
    }

    /**
     * Metoda służy do połączenia z MySQL bez wybierania konkretnej bazy
     *
     * @return referencja do uchwytu bazy danych
     */
    public static Connection getConnection(String kindOfDatabase, String adres, int port, String userName, String password) {

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        try {
            conn = DriverManager.getConnection(kindOfDatabase + adres + ":" + port + "/",
                    connectionProps);
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą danych! " + e.getMessage() + ": " + e.getErrorCode());
            System.exit(2);
        }
        System.out.println("Polaczenie z baza danych: ... OK");
        return conn;
    }

    /**
     * tworzenie obiektu Statement przesyłającego zapytania do bazy connection
     *
     * @param connection - połączenie z bazą
     * @return obiekt Statement przesyłający zapytania do bazy
     */
    public static Statement createStatement(Connection connection) {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Błąd createStatement! " + e.getMessage() + ": " + e.getErrorCode());
            System.exit(3);
        }
        return null;
    }

    /**
     * Zamykanie połączenia z bazą danych
     *
     * @param connection - połączenie z bazą
     * @param s          - obiekt przesyłający zapytanie do bazy
     */
    private static void closeConnection(Connection connection, Statement s) {
        System.out.print("\nZamykanie polaczenia z bazą:");
        try {
            s.close();
            connection.close();
        } catch (SQLException e) {
            System.out
                    .println("Bląd przy zamykaniu polączenia z bazą! " + e.getMessage() + ": " + e.getErrorCode());
            ;
            System.exit(4);
        }
        System.out.print(" zamknięcie OK");
    }

    /**
     * Wykonanie kwerendy i przesłanie wyników do obiektu ResultSet
     *
     * @param s   - Statement
     * @param sql - zapytanie
     * @return wynik
     */
    public static ResultSet executeQuery(Statement s, String sql) {
        try {
            return s.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Zapytanie nie wykonane! " + e.getMessage() + ": " + e.getErrorCode());
        }
        return null;
    }

    public static int executeUpdate(Statement s, String sql) {
        try {
            return s.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Zapytanie nie wykonane! " + e.getMessage() + ": " + e.getErrorCode());
            return -1;
        }

    }

    /**
     * Wyświetla dane uzyskane zapytaniem select
     *
     * @param r - wynik zapytania
     */
    public static String printDataFromQuery(ResultSet r) {
        String res = "";
        ResultSetMetaData rsmd;
        try {
            rsmd = r.getMetaData();
            int numcols = rsmd.getColumnCount(); // pobieranie liczby kolumn
            // wyswietlanie nazw kolumn:
//            for (int i = 1; i <= numcols; i++) {
//                System.out.print("\t" + rsmd.getColumnLabel(i) + "\t|");
//            }
//            System.out
//                    .print("\n____________________________________________________________________________\n");
            /**
             * r.next() - przej�cie do kolejnego rekordu (wiersza) otrzymanych wynik�w
             */
            // wyswietlanie kolejnych rekordow:
            while (r.next()) {
                String nick = r.getObject(1).toString();
                String reciver = r.getObject(2).toString();
                String text = r.getObject(3).toString();
                Boolean isPrivate = r.getObject(4).toString().equals(0) ? false: true;
                Message message = new Message(text, nick, reciver, isPrivate);
                if(isPrivate) continue;
                res+= message+ "\r\n";
//
//                for (int i = 1; i <= numcols; i++) {
//                    Message obj = (Message) r.getObject(i);
//                    if (obj != null)
//                        res += obj.getText() + "\t";
//                    else
//                        System.out.print("\t");
//                }
//                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Bl�d odczytu z bazy! " + e.getMessage() + ": " + e.getErrorCode());
        }
        return res;
    }

    /**
     * Metoda pobiera dane na podstawie nazwy kolumny
     */
    public static void sqlGetDataByName(ResultSet r) {
        System.out.println("Pobieranie danych z wykorzystaniem nazw kolumn");
        try {
            ResultSetMetaData rsmd = r.getMetaData();
            int numcols = rsmd.getColumnCount();
            // Tytul tabeli z etykietami kolumn zestawow wynikow
            for (int i = 1; i <= numcols; i++) {
                System.out.print(rsmd.getColumnLabel(i) + "\t|\t");
            }
            System.out
                    .print("\n____________________________________________________________________________\n");
            while (r.next()) {
                int size = r.getMetaData().getColumnCount();
                for (int i = 1; i <= size; i++) {
                    switch (r.getMetaData().getColumnTypeName(i)) {
                        case "INT":
                            System.out.print(r.getInt(r.getMetaData().getColumnName(i)) + "\t|\t");
                            break;
                        case "DATE":
                            System.out.print(r.getDate(r.getMetaData().getColumnName(i)) + "\t|\t");
                            break;
                        case "VARCHAR":
                            System.out.print(r.getString(r.getMetaData().getColumnName(i)) + "\t|\t");
                            break;
                        default:
                            System.out.print(r.getMetaData().getColumnTypeName(i));
                    }
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Bląd odczytu z bazy! " + e.getMessage() + ": " + e.getErrorCode());

        }
    }
}