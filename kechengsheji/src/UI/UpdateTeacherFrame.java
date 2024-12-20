/***********************************************************
 * 版权所有 (C)2024, Lishiyun
 *
 * 文件名称： UpdateTeacherFrame.java
 * 文件标识：无
 * 内容摘要：用于管理员端修改教师信息操作
 * 其它说明：
 * 当前版本： V1.0
 * 作   者：李世赟
 * 完成日期： 20241130
 **********************************************************/
package UI;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import server.DBUtil;

public class UpdateTeacherFrame extends JFrame {
    /*********************************************************
     * 功能描述：构造函数，用于初始化修改教师信息的图形用户界面框架
     * 输入参数：无
     * 返回值：无
     * 其它说明：设置框架标题、关闭操作、大小和位置，并使其可见。创建包含教师工号、姓名、性别、年龄、职称、手机号标签及文本框与修改、重置按钮的面板，设置组件字体、布局及按钮背景透明等样式，为按钮添加事件监听器，用于处理教师信息修改和文本框重置操作，构建修改教师信息交互界面布局。
     ************************************************************/
    public UpdateTeacherFrame() {
        super("修改教师信息");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 550);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel frame = new JPanel();
        add(frame);

        // 教师信息标签和文本框
        JLabel j9 = new JLabel("教师工号:");
        JLabel j10 = new JLabel("教师姓名:");
        JLabel j11 = new JLabel("教师性别:");
        JLabel j12 = new JLabel("教师年龄:");
        JLabel j13 = new JLabel("教师职称:");
        JLabel j14 = new JLabel("手机号:");

        j9.setFont(new Font("楷体", Font.PLAIN, 20));
        j10.setFont(new Font("楷体", Font.PLAIN, 20));
        j11.setFont(new Font("楷体", Font.PLAIN, 20));
        j12.setFont(new Font("楷体", Font.PLAIN, 20));
        j13.setFont(new Font("楷体", Font.PLAIN, 20));
        j14.setFont(new Font("楷体", Font.PLAIN, 20));

        JTextField c9 = new JTextField(15);
        JTextField c10 = new JTextField(15);
        JTextField c11 = new JTextField(15);
        JTextField c12 = new JTextField(15);
        JTextField c13 = new JTextField(15);
        JTextField c14 = new JTextField(15);

        JButton aa = new JButton("修改");
        JButton bb = new JButton("重置");

        aa.setFont(new Font("楷体", Font.PLAIN, 20));
        bb.setFont(new Font("楷体", Font.PLAIN, 20));

        // 设置按钮背景透明
        aa.setContentAreaFilled(false);
        bb.setContentAreaFilled(false);

        // 教师信息组件布局
        j9.setBounds(20, 30, 120, 20);
        c9.setBounds(80, 30, 120, 25);
        j10.setBounds(20, 70, 120, 20);
        c10.setBounds(80, 70, 100, 25);
        j11.setBounds(20, 110, 120, 30);
        c11.setBounds(80, 110, 100, 25);
        j12.setBounds(20, 150, 120, 30);
        c12.setBounds(80, 150, 100, 25);
        j13.setBounds(20, 190, 120, 30);
        c13.setBounds(80, 190, 100, 25);
        j14.setBounds(20, 230, 120, 30);
        c14.setBounds(80, 230, 100, 25);

        aa.setBounds(100, 400, 100, 30);
        bb.setBounds(300, 400, 100, 30);

        frame.setLayout(null);
        frame.add(j9);
        frame.add(c9);
        frame.add(j10);
        frame.add(c10);
        frame.add(j11);
        frame.add(c11);
        frame.add(j12);
        frame.add(c12);
        frame.add(j13);
        frame.add(c13);
        frame.add(j14);
        frame.add(c14);
        frame.add(aa);
        frame.add(bb);
/*********************************************************
 * 功能描述：“修改”按钮点击事件处理方法
 * 输入参数：e - 动作事件对象，包含按钮点击的相关信息
 * 返回值：无
 * 其它说明：获取工号文本框输入，调用 queryTeacher 查教师是否存在。若存在，获取其他文本框信息，调用 updateTeacher 方法修改教师信息并提示结果；若不存在则提示错误。遇 SQL 或类加载异常则打印堆栈跟踪排查问题，确保修改功能稳定可靠。
 ************************************************************/
        aa.addActionListener(e -> {
            String tno = c9.getText().trim();
            String tname = c10.getText().trim();
            String tsex = c11.getText().trim();
            String tage = c12.getText().trim();
            String ttitle = c13.getText().trim();
            String tphone = c14.getText().trim();

            try {
                if (updateTeacher(tno, tname, tsex, tage, ttitle, tphone)) {
                    JOptionPane.showMessageDialog(null, "修改教师信息成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "修改教师信息失败，可能教师工号不存在", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
/*********************************************************
 * 功能描述：“重置”按钮点击事件处理方法
 * 输入参数：e - 动作事件对象，包含按钮点击触发的事件详情
 * 返回值：无
 * 其它说明：点击时清空所有教师信息文本框内容，方便用户重新输入修改内容，提升交互便利性与数据准确性，优化修改操作流程。
 ************************************************************/
        bb.addActionListener(e -> {
            c9.setText("");
            c10.setText("");
            c11.setText("");
            c12.setText("");
            c13.setText("");
            c14.setText("");
        });
    }
    /*********************************************************
     * 功能描述：修改教师信息的方法
     * 输入参数：
     * - tno - 工号
     * - tname - 姓名
     * - tsex - 性别
     * - tage - 年龄
     * - ttitle - 职称
     * - tphone - 手机号
     * 返回值：true - 更新成功；false - 更新失败（如工号不存在或 SQL 操作异常）
     * 其它说明：获取数据库连接，构建更新教师信息的 SQL 语句并预编译，设置教师各属性参数后执行更新操作。根据影响行数判断更新是否成功，成功则关闭资源返回 true；失败则显示错误信息并返回 false，确保数据库教师数据一致性与准确性。
     ************************************************************/
    private boolean updateTeacher(String tno, String tname, String tsex, String tage, String ttitle, String tphone) throws SQLException, ClassNotFoundException {
        Connection conn = DBUtil.getConnection();
        String sql = "UPDATE dbo.Teacher SET Tname =?, Tsex =?, Tage =?, Ttitle =?, phone =? WHERE Tno =?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, tname);
        pstmt.setString(2, tsex);
        pstmt.setString(3, tage);
        pstmt.setString(4, ttitle);
        pstmt.setString(5, tphone);
        pstmt.setString(6, tno);

        try {
            int result = pstmt.executeUpdate();
            DBUtil.closeResources(conn, pstmt, null);
            return result == 1;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "修改教师信息失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}