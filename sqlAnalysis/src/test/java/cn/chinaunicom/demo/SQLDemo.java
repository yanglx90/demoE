package cn.chinaunicom.demo;

import org.junit.Test;

public class SQLDemo {
    public static String sql1 = "create or replace procedure proccursor(\n" +
            "  p varchar2\n" +
            ") as\n" +
            "  v_rownum number(10) := 1;\n" +
            "  cursor c_postype is select pos_type from pos_type_tb1 where rownum = 1;\n" +
            "  cursor c_postype1 is select pos_type from pos_type_tb1 where rownum = v_rownum;\n" +
            "  cursor c_postype2(p_rownum number) is select pos_type from pos_type_tb1 where rownum = p_rownum;\n" +
            "  type t_postype is ref cursor;\n" +
            "  c_postype3 t_postype;\n" +
            "  v_postype varchar2(20);\n" +
            " \n" +
            "begin\n" +
            "  open c_postype;\n" +
            "  fetch c_postype into v_postype;\n" +
            "    dbms_output.put_line(v_postype);\n" +
            "  close c_postype;\n" +
            "  open c_postype1;\n" +
            " \n" +
            "  fetch c_postype1 into v_postype;\n" +
            "    dbms_output.put_line(v_postype);\n" +
            "  close c_postype1;\n" +
            "  open c_postype2(1);\n" +
            " \n" +
            "  fetch c_postype2 into v_postype;\n" +
            "    dbms_output.put_line(v_postype);\n" +
            "  close c_postype2;\n" +
            " \n" +
            "  open c_postype3 for select post_type from pos_type_tb1 where rownum=1;\n" +
            "  fetch c_postype3 into v_postype;\n" +
            "    dbms_output.put_line(v_postype);\n" +
            "  close c_postype3\n" +
            "end;";
    public static String sql2 = "CREATE OR REPLACE PROCEDURE process_student1 AS\n" +
            "    CURSOR CUR_STUDENT IS SELECT s_no FROM student;\n" +
            "    TYPE REC_STUDENT IS VARRAY(100000) OF VARCHAR2(16);\n" +
            "    students REC_STUDENT;\n" +
            "BEGIN\n" +
            "  OPEN CUR_STUDENT;\n" +
            "  WHILE (TRUE) LOOP\n" +
            "    FETCH CUR_STUDENT BULK COLLECT INTO students LIMIT 100000;\n" +
            "    FORALL i IN 1..students.count SAVE EXCEPTIONS\n" +
            "      UPDATE student SET s_grade=s_grade+1 WHERE s_no=students(i);\n" +
            "    COMMIT;\n" +
            "    EXIT WHEN CUR_STUDENT%NOTFOUND OR CUR_STUDENT%NOTFOUND IS NULL;\n" +
            "  END LOO;\n" +
            "  dbms_output.put_line('finished');\n" +
            "\n" +
            "END;\n";


    @Test
    public void sqlDemo() {
        sqlDemo(sql1);
        System.out.println("----------");
        sqlDemo(sql2);
    }

    private void sqlDemo(String sql) {
        sql = sql.toLowerCase().replaceAll("\\u00A0+", "");
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < sql.length(); i++) {
            if (temp.length() > 0 && (sql.charAt(i) == ' ' || sql.charAt(i) == '\n')) {
                if (temp.charAt(temp.length() - 1) != ' ') {
                    temp.append(" ");
                }
                if (temp.indexOf("cursor") == -1 || temp.indexOf(";") != -1) {
                    if (temp.indexOf("cursor") != -1) {
                        System.out.println(temp);
                    }
                    temp.delete(0, temp.length());
                }
            } else {
                if (sql.charAt(i) != ' ') {
                    temp.append(sql.charAt(i));
                }
            }
        }

    }
}
