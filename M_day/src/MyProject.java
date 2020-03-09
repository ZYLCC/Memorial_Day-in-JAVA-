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
	JLabel showDateLabel; // ��ʾ���ڱ���ı�ǩ;
	JPanel showDatePanel;
	JButton flushBtn;
	
	


	public void showMainframe() { // �����
		mainFrame = new JFrame();
		mainFrame.setTitle("Best Time");
		mainFrame.setSize(550, 900);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// mainFrame.setLayout(null);
		Container contentPane = mainFrame.getContentPane();
		mainBtnPanel = new JPanel(); // ����ܰ�ť����;
		contentPane.add(mainBtnPanel);

		// scrollPanelContainer = new JPanel();
		// mainTxtPanel = new JScrollPane(); // ����ܿɹ����ı�����;
		// contentPane.add(scrollPanelContainer, BorderLayout.NORTH);
		// scrollPanelContainer.add(mainTxtPanel); // ���ɹ�����������Panel����;

		// mainTxtPanel = new JScrollPane();
		// contentPane.add(mainTxtPanel);

		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(0, 833));
//		mainPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		contentPane.add(mainPanel, BorderLayout.NORTH);

		//// ��Ӱ�ť
		addBtn = new JButton("�������");
		flushBtn = new JButton("��������");
		mainBtnPanel.add(addBtn);
		mainBtnPanel.add(flushBtn);
		contentPane.add(mainBtnPanel, BorderLayout.SOUTH); // ����ť�������ñ߽粼��;
		mainBtnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black)); // ����ť���������ϱ߿�;

		//// ����Ӱ�ť�󶨼�����;
		addBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					showAddframe();
					mainFrame.setVisible(false);
				}
			}
		});

		//// ��ˢ�°�ť�󶨼�����;
		flushBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					flushData();
					flushBtn.setVisible(false);   //ֻ������һ��;
				}
			}
		});

		mainFrame.setVisible(true);
	}

	public void showAddframe() { // ������ݿ��;
		addFrame = new JFrame();
		addFrame.setTitle("�������");
		addFrame.setSize(400, 150);
		addFrame.setResizable(false);
		addFrame.setLocationRelativeTo(mainFrame);
		Container contentPane = addFrame.getContentPane();
		//// ���ð�ť����;
		addBtnPanel = new JPanel();
		contentPane.add(addBtnPanel);
		//// �����ı�����;
		JPanel addTxtPanel_left = new JPanel();
		contentPane.add(addTxtPanel_left);
		JPanel addTxtPanel_right = new JPanel();
		contentPane.add(addTxtPanel_right);

		//// ȷ����Ӱ�ť,ȡ����Ӱ�ť��������;
		confirmAddBtn = new JButton("ȷ��");
		cancelAddBtn = new JButton("ȡ��");
		addBtnPanel.add(confirmAddBtn);
		addBtnPanel.add(cancelAddBtn);
		contentPane.add(addBtnPanel, BorderLayout.SOUTH);
		addBtnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));

		//// �ı������������;
		addTxtPanel_left.setLayout(new GridLayout(2, 1, 0, 14)); // ����2*1�����񲼾�;
		addTxtPanel_right.setLayout(new GridLayout(2, 1, 0, 14));
		contentPane.add(addTxtPanel_left, BorderLayout.WEST);
		contentPane.add(addTxtPanel_right, BorderLayout.EAST);

		JLabel titleInfo = new JLabel("    ������⣺"); // ��ӱ�ǩ;
		addTxtPanel_left.add(titleInfo); // ���ı����������ǩ;
		titleInfo.setFont(new java.awt.Font("΢���ź�", 1, 20)); // �ı��ǩ������ʽ;
		titleInfo.setHorizontalAlignment(SwingConstants.CENTER); // ʹ��ǩ�����־���

		JLabel dateInfo = new JLabel("    �������ڣ�");
		addTxtPanel_left.add(dateInfo);
		dateInfo.setFont(new java.awt.Font("΢���ź�", 1, 20));
		dateInfo.setHorizontalAlignment(SwingConstants.CENTER);

		JTextField titleInput = new JTextField(12); // ����ı���;
		titleInput.setFont(new java.awt.Font("΢���ź�", 1, 20));
		titleInput.setText("��������ڼӸ����⡭");
		JTextField dateInput = new JTextField(12);
		dateInput.setFont(new java.awt.Font("΢���ź�", 1, 20));
		dateInput.setText("��ʽΪ��YYYY-MM-DD");
		addTxtPanel_right.add(titleInput); // ���ı����������ı���;
		addTxtPanel_right.add(dateInput);

		//// �����ı��������ַ�;
		dateInput.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (((keyChar >= '0' && keyChar <= '9') || (keyChar == '-') || (keyChar == '\b'))) {
					return;
				}
				e.consume(); // ��ֹ�������ַ�������ַ��������ı�����;
			}
		});


		
		//// ��ȷ����Ӱ�ť�󶨼�����
		confirmAddBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					int date[] = new int[3];
					int dyear, dmonth, dday;
					int year, month, day;
					String showtitle;
					String title = titleInput.getText().toString(); // ��ȡ�����ı����������ݣ���ת��Ϊ�ַ���;
					String dateString = dateInput.getText().toString();
					String dateTemp[] = dateString.split("-"); // ��ȡ�����յ��ַ�����;
					//// ���ַ�����������ת��Ϊ��������;
					for (int i = 0; i < 3; i++) {
						date[i] = Integer.parseInt(dateTemp[i]);
					}
					dyear = date[0];
					dmonth = date[1];
					dday = date[2];
					//// ��ȡ����������;
					Calendar calendar = Calendar.getInstance();
					year = calendar.get(Calendar.YEAR);
					month = calendar.get(Calendar.MONTH);
					month += 1; // javaĬ���·�Ϊ0-11;
					day = calendar.get(Calendar.DAY_OF_MONTH);

					//// �ж�����������Ƿ�Ϸ�;
					if (judgeDate(dyear, dmonth, dday, year) > 0) { // ���ںϷ�;

						//// ��������ʱ�䳤��;
						int duringdays = duringDaysFromFirstYear(year, month, day)
								- duringDaysFromFirstYear(dyear, dmonth, dday);
						if (duringdays > 0) { // ���������Ѿ���ȥ;
							showtitle = title + "(������)";
						} else if (duringdays < 0) { // �������ڻ�δ����;
							showtitle = title + "(����)";
						} else {
							showtitle = title;
						}

						if (judgeTitleIsSame(showtitle) > 0) { // ������ⲻ�ظ�;
							flushBtn.setVisible(false);
							dataCounter++;         //���ȷ���ɹ�һ�μ�����+1;
							if (duringdays > 0) {    //������ ;
								//// ��ӵ�������һ���µ�JPanel;
								showDatePanel = new JPanel();
								showDateLabel = new JLabel("��       " + showtitle + "(" + dateString + ")"
										+ "       ����       " + Math.abs(duringdays) + "       �죡");
								showDatePanel.add(showDateLabel);
								mainPanel.add(showDatePanel);
								showDatePanel.setPreferredSize(new Dimension(550, 25));
								showDatePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.gray));
								mainPanel.validate(); // ˢ��mainPanel;
							} else if (duringdays < 0) {    //������;
								//// ��ӵ�������һ���µ�JPanel;
								showDatePanel = new JPanel();
								showDateLabel = new JLabel("��       " + showtitle + "(" + dateString + ")"
										+ "       ����       " + Math.abs(duringdays) + "       �죡");
								showDatePanel.add(showDateLabel);
								mainPanel.add(showDatePanel);
								showDatePanel.setPreferredSize(new Dimension(550, 25));
								showDatePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.gray));
								mainPanel.validate(); // ˢ��mainPanel;
							} else {    //����Ϊ����;
								//// ��ӵ�������һ���µ�JPanel;
								showDatePanel = new JPanel();
								showDateLabel = new JLabel(showtitle + "(" + dateString + ")" + "       ���ˣ�");
								showDatePanel.add(showDateLabel);
								mainPanel.add(showDatePanel);
								showDatePanel.setPreferredSize(new Dimension(550, 25));
								showDatePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.gray));
								mainPanel.validate(); // ˢ��mainPanel;
							}

							//// ������д���ļ�;
							try {
								FileWriter out = new FileWriter("src/Date.txt", true); // true��������д;
								BufferedWriter bw = new BufferedWriter(out);
								bw.write(showtitle + "," + dateString + "," + duringdays);
								bw.newLine(); // ����һ��;
								bw.close();
							} catch (FileNotFoundException e0) {
								e0.printStackTrace();
							} catch (ArrayIndexOutOfBoundsException e1) {
								e1.printStackTrace();
							} catch (IOException e2) {
								e2.printStackTrace();
							}

							JOptionPane.showMessageDialog(addFrame, "����ɹ���", "ϵͳ��ʾ", JOptionPane.WARNING_MESSAGE);
							addFrame.setVisible(false);
							mainFrame.setVisible(true);
						} else { // �����ظ�;
							JOptionPane.showMessageDialog(addFrame, "����ʧ�ܣ�����ı����Ѵ��ڣ�", "������ʾ",
									JOptionPane.WARNING_MESSAGE);
							addFrame.setVisible(false);
							mainFrame.setVisible(true);
						}

					} else { // ���ڲ��Ϸ�;
						JOptionPane.showMessageDialog(addFrame, "����ʧ�ܣ���������ڲ��Ϸ���", "������ʾ", JOptionPane.WARNING_MESSAGE);
						addFrame.setVisible(false);
						mainFrame.setVisible(true);
					}
				}
			}
		});

		//// ��ȡ����Ӱ�ť�󶨼�����
		cancelAddBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					int n = JOptionPane.showConfirmDialog(addFrame, "ȷ��������ӣ�", "ϵͳ��ʾ", JOptionPane.YES_NO_OPTION);
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



	//// �ж��Ƿ�Ϊ�Ϸ�������;
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

	//// �������ڵ���Ԫ1�������;
	public int duringDaysFromFirstYear(int y, int m, int d) {
		int x[] = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int i, s = 0;
		for (i = 1; i < y; i++) {
			if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)
				s += 366;// ����
			else
				s += 365;// ƽ��
		}
		if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0)
			x[2] = 29;
		for (i = 1; i < m; i++)
			s += x[i];// ���µ�����

		s += d;// �յ�����
		return s;// ����������,��Թ�Ԫ1��
	}

	//// �ж�����ı����Ƿ��ظ�;
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
	
	
	//// ��������;
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
					//// ��ӵ�������һ���µ�JPanel;
					showDatePanel = new JPanel();
					showDateLabel = new JLabel("��       " + temp[0] + "(" + temp[1] + ")" + "       ����       "
							+ Math.abs(duringdays) + "       �죡");
					showDatePanel.add(showDateLabel);
					mainPanel.add(showDatePanel);
					showDatePanel.setPreferredSize(new Dimension(550, 25));
					showDatePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.gray));
					mainPanel.validate(); // ˢ��mainPanel;
				} else if (duringdays < 0) {
					//// ��ӵ�������һ���µ�JPanel;
					showDatePanel = new JPanel();
					showDateLabel = new JLabel("��       " + temp[0] + "(" + temp[1] + ")" + "       ����       "
							+ Math.abs(duringdays) + "       �죡");
					showDatePanel.add(showDateLabel);
					mainPanel.add(showDatePanel);
					showDatePanel.setPreferredSize(new Dimension(550, 25));
					showDatePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.gray));
					mainPanel.validate(); // ˢ��mainPanel;
				} else {
					//// ��ӵ�������һ���µ�JPanel;
					showDatePanel = new JPanel();
					showDateLabel = new JLabel(temp[0] + "(" + temp[1] + ")" + "       ���ˣ�");
					showDatePanel.add(showDateLabel);
					mainPanel.add(showDatePanel);
					showDatePanel.setPreferredSize(new Dimension(550, 25));
					showDatePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.gray));
					mainPanel.validate(); // ˢ��mainPanel;
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
