import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class MyFrame {
	JFrame mainFrame;
	JFrame addFrame;
	JFrame editFrame;
//	JFrame detailFrame;
	JButton addBtn;
	JButton cancelAddBtn;
	JButton confirmAddBtn;
	int dataCounter = 0;

	JPanel mainBtnPanel;
	
	
	JPanel addBtnPanel;
	JPanel mainPanel;
	JLabel showDateLabel; // 显示日期标题的标签;
	JPanel showDatePanel;
	JButton flushBtn;
	
	


	public void showMainframe() { // 主框架
		mainFrame = new JFrame();
		mainFrame.setTitle("Best Time");
		mainFrame.setSize(550, 900);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// mainFrame.setLayout(null);
		Container contentPane = mainFrame.getContentPane();
		mainBtnPanel = new JPanel(); // 主框架按钮容器;
		contentPane.add(mainBtnPanel);

		// scrollPanelContainer = new JPanel();
		// mainTxtPanel = new JScrollPane(); // 主框架可滚动文本容器;
		// contentPane.add(scrollPanelContainer, BorderLayout.NORTH);
		// scrollPanelContainer.add(mainTxtPanel); // 将可滚动容器放入Panel容器;

		// mainTxtPanel = new JScrollPane();
		// contentPane.add(mainTxtPanel);

		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(0, 833));
//		mainPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		contentPane.add(mainPanel, BorderLayout.NORTH);

		//// 添加按钮
		addBtn = new JButton("添加日期");
		flushBtn = new JButton("更新数据");
		mainBtnPanel.add(addBtn);
		mainBtnPanel.add(flushBtn);
		contentPane.add(mainBtnPanel, BorderLayout.SOUTH); // 给按钮容器设置边界布局;
		mainBtnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black)); // 给按钮容器设置上边框;

		//// 给添加按钮绑定监听器;
		addBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					showAddframe();
					mainFrame.setVisible(false);
				}
			}
		});

		//// 给刷新按钮绑定监听器;
		flushBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					flushData();
					flushBtn.setVisible(false);   //只允许点击一次;
				}
			}
		});

		mainFrame.setVisible(true);
	}

	public void showAddframe() { // 添加内容框架;
		addFrame = new JFrame();
		addFrame.setTitle("添加日期");
		addFrame.setSize(400, 150);
		addFrame.setResizable(false);
		addFrame.setLocationRelativeTo(mainFrame);
		Container contentPane = addFrame.getContentPane();
		//// 设置按钮容器;
		addBtnPanel = new JPanel();
		contentPane.add(addBtnPanel);
		//// 设置文本容器;
		JPanel addTxtPanel_left = new JPanel();
		contentPane.add(addTxtPanel_left);
		JPanel addTxtPanel_right = new JPanel();
		contentPane.add(addTxtPanel_right);

		//// 确认添加按钮,取消添加按钮加入容器;
		confirmAddBtn = new JButton("确认");
		cancelAddBtn = new JButton("取消");
		addBtnPanel.add(confirmAddBtn);
		addBtnPanel.add(cancelAddBtn);
		contentPane.add(addBtnPanel, BorderLayout.SOUTH);
		addBtnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));

		//// 文本容器添加内容;
		addTxtPanel_left.setLayout(new GridLayout(2, 1, 0, 14)); // 设置2*1的网格布局;
		addTxtPanel_right.setLayout(new GridLayout(2, 1, 0, 14));
		contentPane.add(addTxtPanel_left, BorderLayout.WEST);
		contentPane.add(addTxtPanel_right, BorderLayout.EAST);

		JLabel titleInfo = new JLabel("    输入标题："); // 添加标签;
		addTxtPanel_left.add(titleInfo); // 向文本容器加入标签;
		titleInfo.setFont(new java.awt.Font("微软雅黑", 1, 20)); // 改变标签字体样式;
		titleInfo.setHorizontalAlignment(SwingConstants.CENTER); // 使标签中文字居中

		JLabel dateInfo = new JLabel("    输入日期：");
		addTxtPanel_left.add(dateInfo);
		dateInfo.setFont(new java.awt.Font("微软雅黑", 1, 20));
		dateInfo.setHorizontalAlignment(SwingConstants.CENTER);

		JTextField titleInput = new JTextField(12); // 添加文本框;
		titleInput.setFont(new java.awt.Font("微软雅黑", 1, 20));
		titleInput.setText("给你的日期加个标题…");
		JTextField dateInput = new JTextField(12);
		dateInput.setFont(new java.awt.Font("微软雅黑", 1, 20));
		dateInput.setText("格式为：YYYY-MM-DD");
		addTxtPanel_right.add(titleInput); // 向文本容器加入文本框;
		addTxtPanel_right.add(dateInput);

		//// 限制文本框输入字符;
		dateInput.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (((keyChar >= '0' && keyChar <= '9') || (keyChar == '-') || (keyChar == '\b'))) {
					return;
				}
				e.consume(); // 阻止在限制字符以外的字符出现在文本框中;
			}
		});


		
		//// 给确认添加按钮绑定监听器
		confirmAddBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					int date[] = new int[3];
					int dyear, dmonth, dday;
					int year, month, day;
					String showtitle;
					String title = titleInput.getText().toString(); // 获取标题文本框输入内容，并转化为字符串;
					String dateString = dateInput.getText().toString();
					String dateTemp[] = dateString.split("-"); // 获取年月日的字符数组;
					//// 将字符数组年月日转换为整型数组;
					for (int i = 0; i < 3; i++) {
						date[i] = Integer.parseInt(dateTemp[i]);
					}
					dyear = date[0];
					dmonth = date[1];
					dday = date[2];
					//// 获取本机年月日;
					Calendar calendar = Calendar.getInstance();
					year = calendar.get(Calendar.YEAR);
					month = calendar.get(Calendar.MONTH);
					month += 1; // java默认月份为0-11;
					day = calendar.get(Calendar.DAY_OF_MONTH);

					//// 判断输入的日期是否合法;
					if (judgeDate(dyear, dmonth, dday, year) > 0) { // 日期合法;

						//// 计算两者时间长度;
						int duringdays = duringDaysFromFirstYear(year, month, day)
								- duringDaysFromFirstYear(dyear, dmonth, dday);
						if (duringdays > 0) { // 输入日期已经过去;
							showtitle = title + "(纪念日)";
						} else if (duringdays < 0) { // 输入日期还未到来;
							showtitle = title + "(提醒)";
						} else {
							showtitle = title;
						}

						if (judgeTitleIsSame(showtitle) > 0) { // 如果标题不重复;
							flushBtn.setVisible(false);
							dataCounter++;         //点击确定成功一次计数器+1;
							if (duringdays > 0) {    //纪念日 ;
								//// 添加到主界面一个新的JPanel;
								showDatePanel = new JPanel();
								showDateLabel = new JLabel("距       " + showtitle + "(" + dateString + ")"
										+ "       已有       " + Math.abs(duringdays) + "       天！");
								showDatePanel.add(showDateLabel);
								mainPanel.add(showDatePanel);
								showDatePanel.setPreferredSize(new Dimension(550, 25));
								showDatePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.gray));
								mainPanel.validate(); // 刷新mainPanel;
							} else if (duringdays < 0) {    //提醒日;
								//// 添加到主界面一个新的JPanel;
								showDatePanel = new JPanel();
								showDateLabel = new JLabel("距       " + showtitle + "(" + dateString + ")"
										+ "       还有       " + Math.abs(duringdays) + "       天！");
								showDatePanel.add(showDateLabel);
								mainPanel.add(showDatePanel);
								showDatePanel.setPreferredSize(new Dimension(550, 25));
								showDatePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.gray));
								mainPanel.validate(); // 刷新mainPanel;
							} else {    //日期为今天;
								//// 添加到主界面一个新的JPanel;
								showDatePanel = new JPanel();
								showDateLabel = new JLabel(showtitle + "(" + dateString + ")" + "       到了！");
								showDatePanel.add(showDateLabel);
								mainPanel.add(showDatePanel);
								showDatePanel.setPreferredSize(new Dimension(550, 25));
								showDatePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.gray));
								mainPanel.validate(); // 刷新mainPanel;
							}

							//// 将数据写入文件;
							try {
								FileWriter out = new FileWriter("src/Date.txt", true); // true代表不被复写;
								BufferedWriter bw = new BufferedWriter(out);
								bw.write(showtitle + "," + dateString + "," + duringdays);
								bw.newLine(); // 新起一行;
								bw.close();
							} catch (FileNotFoundException e0) {
								e0.printStackTrace();
							} catch (ArrayIndexOutOfBoundsException e1) {
								e1.printStackTrace();
							} catch (IOException e2) {
								e2.printStackTrace();
							}

							JOptionPane.showMessageDialog(addFrame, "保存成功！", "系统提示", JOptionPane.WARNING_MESSAGE);
							addFrame.setVisible(false);
							mainFrame.setVisible(true);
						} else { // 标题重复;
							JOptionPane.showMessageDialog(addFrame, "保存失败！输入的标题已存在！", "错误提示",
									JOptionPane.WARNING_MESSAGE);
							addFrame.setVisible(false);
							mainFrame.setVisible(true);
						}

					} else { // 日期不合法;
						JOptionPane.showMessageDialog(addFrame, "保存失败！输入的日期不合法！", "错误提示", JOptionPane.WARNING_MESSAGE);
						addFrame.setVisible(false);
						mainFrame.setVisible(true);
					}
				}
			}
		});

		//// 给取消添加按钮绑定监听器
		cancelAddBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					int n = JOptionPane.showConfirmDialog(addFrame, "确定放弃添加？", "系统提示", JOptionPane.YES_NO_OPTION);
					if (n == 1) {
						addFrame.setVisible(true);
						mainFrame.setVisible(false);
					} else {
						addFrame.setVisible(false);
						mainFrame.setVisible(true);
					}
					if (dataCounter <= 1) {
						flushBtn.setVisible(true);
					} else {
						flushBtn.setVisible(true);
					}
				}
			}
		});

		addFrame.setVisible(true);
	}



	//// 判断是否为合法年月日;
	public int judgeDate(int y, int m, int d, int dy) {
		if (y < 1900 || y > dy + 50 || m > 12 || d > 31 || m == 0 || d == 0)
			return -1;
		else {
			if ((y % 4 == 0 && y % 100 != 0) || (y % 400 == 0)) {
				if (m == 2 && d > 29) {
					return -1;
				} else if ((m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) && d > 31) {
					return -1;
				} else if ((m == 4 || m == 6 || m == 9 || m == 11) && d > 30) {
					return -1;
				} else
					return 1;
			} else {
				if (m == 2 && d > 28) {
					return -1;
				} else if ((m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) && d > 31) {
					return -1;
				} else if ((m == 4 || m == 6 || m == 9 || m == 11) && d > 30) {
					return -1;
				} else
					return 1;
			}
		}
	}

	//// 计算日期到公元1年的天数;
	public int duringDaysFromFirstYear(int y, int m, int d) {
		int x[] = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int i, s = 0;
		for (i = 1; i < y; i++) {
			if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)
				s += 366;// 闰年
			else
				s += 365;// 平年
		}
		if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0)
			x[2] = 29;
		for (i = 1; i < m; i++)
			s += x[i];// 整月的天数

		s += d;// 日的天数
		return s;// 返回总天数,相对公元1年
	}

	//// 判断输入的标题是否重复;
	public int judgeTitleIsSame(String s) {
		int flag = 1;
		try {

			FileReader in = new FileReader("src/Date.txt");
			BufferedReader br = new BufferedReader(in);
			String t = br.readLine();
			while (!(t == null)) {
				String date[] = t.split(",");
				t = br.readLine();
				if (s.equals(date[0])) {
					flag = -1;
					break;
				}
			}
			br.close();
		} catch (FileNotFoundException e0) {
			e0.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		if (flag > 0) {
			return flag;
		} else
			return flag;
	}
	
	
	//// 更新数据;
	public void flushData() {
		String t;
		int duringdays;
		try {
			FileReader in = new FileReader("src/Date.txt");
			BufferedReader br = new BufferedReader(in);
			t = br.readLine();
			while (!(t == null)) {
				String temp[] = t.split(",");
				t = br.readLine();
				duringdays = Integer.parseInt(temp[2]);
				if (duringdays > 0) {
					//// 添加到主界面一个新的JPanel;
					showDatePanel = new JPanel();
					showDateLabel = new JLabel("距       " + temp[0] + "(" + temp[1] + ")" + "       已有       "
							+ Math.abs(duringdays) + "       天！");
					showDatePanel.add(showDateLabel);
					mainPanel.add(showDatePanel);
					showDatePanel.setPreferredSize(new Dimension(550, 25));
					showDatePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.gray));
					mainPanel.validate(); // 刷新mainPanel;
				} else if (duringdays < 0) {
					//// 添加到主界面一个新的JPanel;
					showDatePanel = new JPanel();
					showDateLabel = new JLabel("距       " + temp[0] + "(" + temp[1] + ")" + "       还有       "
							+ Math.abs(duringdays) + "       天！");
					showDatePanel.add(showDateLabel);
					mainPanel.add(showDatePanel);
					showDatePanel.setPreferredSize(new Dimension(550, 25));
					showDatePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.gray));
					mainPanel.validate(); // 刷新mainPanel;
				} else {
					//// 添加到主界面一个新的JPanel;
					showDatePanel = new JPanel();
					showDateLabel = new JLabel(temp[0] + "(" + temp[1] + ")" + "       到了！");
					showDatePanel.add(showDateLabel);
					mainPanel.add(showDatePanel);
					showDatePanel.setPreferredSize(new Dimension(550, 25));
					showDatePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.gray));
					mainPanel.validate(); // 刷新mainPanel;
				}
			}
			br.close();
		} catch (FileNotFoundException e0) {
			e0.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
}


public class MyProject {
	public static void main(String[] args) {
		MyFrame showMain = new MyFrame();
		showMain.showMainframe();
	}
}
