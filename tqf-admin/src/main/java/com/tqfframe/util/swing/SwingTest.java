package com.tqfframe.util.swing;

import javax.swing.*;
/**
 * Created by Tang-QiFeng on 2019/3/5
 */
public class SwingTest {
    public static void main(String[] args) {
        System.out.println("添加任务到事件调度线程:");
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        createGUI();
                    }
                }
        );
        System.out.println("程序正在异步执行中。。。");
    }

    public static void createGUI() {
        JFrame jf = new JFrame("测试窗口");
        jf.setSize(300, 300);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        final JLabel label = new JLabel();
        label.setText("正在计算");
        panel.add(label);
        jf.setContentPane(panel);
        jf.setVisible(true);

        // 创建后台任务
        SwingWorker<String, Object> task = new SwingWorker<String, Object>() {

            @Override
            protected String doInBackground() throws Exception {
                // 此处处于 SwingWorker 线程池中
                // 延时 5 秒，模拟耗时操作
                Thread.sleep(5000);
                // 返回计算结果
                return "线程执行完毕";
            }
            @Override
            protected void done() {
                System.out.println("进来了，回调了");
                // 此方法将在后台任务完成后在事件调度线程中被回调
                String result = null;
                try {
                    // 获取计算结果
                    result = get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(result);
                label.setText("结算结果: " + result);
            }
        };

        // 启动任务
        task.execute();
    }

}
