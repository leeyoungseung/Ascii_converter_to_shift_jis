package com.ascii;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConvertAscii {

	public static void main(String[] args) {
		String beforeFilePath = "";
		try {
			beforeFilePath = args[0];

		}catch (Exception e) {
			System.out.println("It is not inputed default FilePath!!!");
		}
		
		if(beforeFilePath.equals("") || beforeFilePath == null) {
			beforeFilePath = "C:\\Users\\leeyoungseung\\project_source\\Ascii_converter\\test2.csv";
		}
		writeCSV(readCSV(beforeFilePath));
	}

	/**
	 * 変換した日本語を出力
	 * @author leeyoungseung
	 * @param list
	 * @param resultFilePath
	 */
	public static void writeCSV(List<ConvertAsciiDTO> list) {
		BufferedWriter bw = null;
		try {
			Date date = new Date();
			SimpleDateFormat todaysInfo = new SimpleDateFormat("yy-MM-dd_HH.mm.ss");
			String path = "C:\\resultLogs\\"+todaysInfo.format(date)+".txt";
			
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));

			for (ConvertAsciiDTO dto : list) {
				bw.write(dto.getLogInfo());
				bw.write(",");
				bw.write(dto.getLogString());
				bw.write(",");
				bw.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * データを読み込んで日本語のエンコーディングの"Shift_JIS"に変更、その後読み込んだデータの返却
	 * 
	 * @author leeyoungseung
	 * @param path : データを読み込むファイルのパス
	 * @return List
	 */
	public static List<ConvertAsciiDTO> readCSV(String path) {
		String csvFile = path;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		List<ConvertAsciiDTO> res = new ArrayList<ConvertAsciiDTO>() {
			private static final long serialVersionUID = 1L;
		};

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "Shift_JIS"));
			while ((line = br.readLine()) != null) {
				String[] country = line.split(cvsSplitBy);
				ConvertAsciiDTO dto = new ConvertAsciiDTO();
				dto.setLogInfo(country[0]);
				
				StringBuilder sb = new StringBuilder();
				char ch;
				String str = country[1];
				int len = str.length();
				for (int i = 0; i < len; i++) {
					ch = str.charAt(i);
					if (ch == '%' && str.charAt(i + 1) == 'u') {
						sb.append((char) Integer.parseInt(str.substring(i + 2, i + 6), 16));
						i += 5;
						continue;
					}
					sb.append(ch);
				}
				// System.out.println(sb);
				//res.add((String) sb.toString());
				dto.setLogString((String) sb.toString());
				res.add(dto);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
//        for(ConvertAsciiDTO str : res) {
//        	System.out.println(str);
//        }
		return res;
	}

}
